package com.kopo.trading.core.service;

import com.kopo.trading.core.dto.PredictResponseDto;
import com.kopo.trading.core.dto.ScheduledStockDto;
import com.kopo.trading.core.dto.StockData;
import com.kopo.trading.core.dto.Stocks;
import com.kopo.trading.core.entity.*;
import com.kopo.trading.core.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScheduledStockService {

    private final KospiRepository kospiRepository;

    private final StockRepository stockRepository;
    private final MachinePredictRepository machinePredictRepository;
    private final PortfolioRepository portfolioRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public ScheduledStockService(KospiRepository kospiRepository, StockRepository stockRepository,MachinePredictRepository machinePredictRepository, PortfolioRepository portfolioRepository,  TransactionRepository transactionRepository) {
        this.kospiRepository = kospiRepository;
        this.stockRepository = stockRepository;
        this.machinePredictRepository = machinePredictRepository;
        this.portfolioRepository = portfolioRepository;
        this.transactionRepository = transactionRepository;
    }
    @Transactional
//    @Scheduled(cron = "0 0 4 * * *")
    public void predict() {
        List<Kospi> kospiList = kospiRepository.findAll();
        List<StockData> stockDataList = new ArrayList<>();
        Long cnt = 0l;
        for(Kospi kospi : kospiList) {
            Optional<Stock> stock = stockRepository.findFirstByKospiId(kospi.getId());
            Stock s = stock.get();
            stockDataList.add(new StockData(kospi.getId(), s.getCap().intValue(), s.getChangedRate(), s.getHigh().intValue(),s.getLow().intValue(), s.getOpen().intValue(), s.getTradingDay(), s.getVolume()));
            log.info(String.valueOf(cnt));
            cnt++;
        }

        List<PredictResponseDto> predictResponseDtos = sendRequestPredict(new Stocks(stockDataList));
        int i =0;
        for(PredictResponseDto predictResponseDto : predictResponseDtos) {
            List<Stock> kospiIdOrderByTradingDayDesc = stockRepository.findFirstByKospiIdOrderByTradingDayDesc(predictResponseDto.getCode());
            Stock latestStock = kospiIdOrderByTradingDayDesc.get(0);
            machinePredictRepository.save(new MachinePredict(predictResponseDto.getCode(), latestStock.getKospi().getName(),  kospiIdOrderByTradingDayDesc.get(0).getClose(),null,predictResponseDto.getPredict_close(), null ));
            log.info(String.valueOf(i));
            i += 1;

        }
    }


    @Transactional
//    @Scheduled(cron = "0 0 8 * * *")
    public void initStocks() throws Exception {
        log.info("전체 주식 데이터 정보 가져오기 시작");
        List<ScheduledStockDto> stocks = getAllStocks();
        int i = 0;
        for(ScheduledStockDto scheduledStockDto : stocks) {
            log.info("테이블에 삽입 시작" + i);
            Optional<Kospi> kospi = kospiRepository.findById(scheduledStockDto.getCode());
            Kospi kospi1 = kospi.get();
            Stock stock = scheduledStockDto.toEntity();
            stock.setCap(kospi1.getStockSupply() * stock.getClose());
            stockRepository.save(stock);
            i++;
        }
        throw new Exception();
    }
    @Transactional
//    @Scheduled(cron = "0 0 18 * * *")
    public void updateToday() {
        log.info("업데이트 시작");
        List<ScheduledStockDto> stocks = getStocks();
        log.info("오늘 주식 정보 가져오기 성공 ");
        int i = 0;
        for(ScheduledStockDto scheduledStockDto : stocks) {
            Optional<Kospi> kospi = kospiRepository.findById(scheduledStockDto.getCode());
            Kospi kospi1 = kospi.get();
            scheduledStockDto.setCode(kospi1.getId());
            Stock stock = scheduledStockDto.toEntity();
            stock.setCap(kospi1.getStockSupply() * stock.getClose());
            stockRepository.save(stock);
            Optional<MachinePredict> predictBefore = machinePredictRepository.findByCodeOrderByCreatedDateDesc(kospi1.getId());
            MachinePredict mp = predictBefore.get();
            mp.setClose(stock.getClose());
            i++;
        }
    }

     // 매도
    @Transactional
//    @Scheduled(cron = "0 0 15 * * *")
    public void autoTrading() {
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        for(Portfolio portfolio : portfolioList) {
            List<Stock> first = stockRepository.findFirstByKospiIdOrderByTradingDayDesc(portfolio.getKospiId());
            Optional<Kospi> kospi = kospiRepository.findById(portfolio.getKospiId());

            Stock stock = first.get(0);
            Double close = stock.getClose();
            Double loss = portfolio.getLoss();
            if(close < loss) { // 손절 매도
                transactionRepository.save(new Transaction(-portfolio.getAmount(), new Date(), portfolio.getAvgPrice(), portfolio.getAmount() * portfolio.getAvgPrice(), portfolio.getMember(), kospi.get()));
            } else if (close >= portfolio.getTarget()) {
                transactionRepository.save(new Transaction(portfolio.getAmount(), new Date(), portfolio.getAvgPrice(), portfolio.getAmount() * portfolio.getAvgPrice(), portfolio.getMember(), kospi.get()));
            }else {
                if(close > loss && close < portfolio.getAvgPrice()) {
                    portfolio.setLoss(portfolio.getLoss() /2);
                } else if (close > portfolio.getAvgPrice() && close < portfolio.getTarget()) {
                    portfolio.setTarget(portfolio.getTarget() / 2);
                }
            }
        }
    }


    public List<ScheduledStockDto> getAllStocks() {
        List<String> kospiRepositoryAll = kospiRepository.findAll().stream().map(Kospi::getId).collect(Collectors.toList());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<String>> requestEntity = new HttpEntity<>(kospiRepositoryAll, headers);

        RestTemplate restTemplate = new RestTemplate();
        log.info("Calling the prediction API");

        ResponseEntity<List<ScheduledStockDto>> responseEntity;
        try {
            responseEntity = restTemplate.exchange(
                    "http://localhost:8000/stock/all",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<>() {}
            );
            log.info("Prediction API call successful");
            return responseEntity.getBody();
        } catch (RestClientException e) {
            log.error("Error occurred while calling the prediction API: {}", e.getMessage());
            throw new RuntimeException("Failed to call the prediction API", e);
        }
    }


    public List<ScheduledStockDto> getStocks() {
        List<String> kospiRepositoryAll = kospiRepository.findAll().stream().map(Kospi::getId).collect(Collectors.toList());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<String>> requestEntity = new HttpEntity<>(kospiRepositoryAll, headers);

        RestTemplate restTemplate = new RestTemplate();
        log.info("Calling the prediction API");

        ResponseEntity<List<ScheduledStockDto>> responseEntity;
        try {
            responseEntity = restTemplate.exchange(
                    "http://localhost:8000/update",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<>() {}
            );
            log.info("Prediction API call successful");
            return responseEntity.getBody();
        } catch (RestClientException e) {
            log.error("Error occurred while calling the prediction API: {}", e.getMessage());
            throw new RuntimeException("Failed to call the prediction API", e);
        }
    }

    public List<PredictResponseDto> sendRequestPredict(Stocks stocks) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<Stocks> requestEntity = new HttpEntity<>(stocks, headers);

        RestTemplate restTemplate = new RestTemplate();
        log.info("Calling the prediction API");

        ResponseEntity<List<PredictResponseDto>> responseEntity;
        try {
            responseEntity = restTemplate.exchange(
                    "http://localhost:8000/predict",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<>() {}
            );
            log.info("Prediction API call successful");
            return responseEntity.getBody();
        } catch (RestClientException e) {
            log.error("Error occurred while calling the prediction API: {}", e.getMessage());
            throw new RuntimeException("Failed to call the prediction API", e);
        }
    }



}



