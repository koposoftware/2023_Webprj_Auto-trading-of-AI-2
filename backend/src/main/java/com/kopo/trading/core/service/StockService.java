package com.kopo.trading.core.service;

import com.kopo.trading.core.constants.Trading;
import com.kopo.trading.core.dto.*;
import com.kopo.trading.core.entity.*;
import com.kopo.trading.core.repository.*;
import com.kopo.trading.security.JWTUtil;
import com.kopo.trading.user.entity.Member;
import com.kopo.trading.user.entity.MemberInfo;
import com.kopo.trading.user.exception.UserNotFoundException;
import com.kopo.trading.user.repository.MemberInfoRepository;
import com.kopo.trading.user.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class StockService {

    private final StockRepository stockRepository;
    private final KospiRepository kospiRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioProfitRepository portfolioProfitRepository;
    private final TransactionRepository transactionRepository;
    private final MemberRepository memberRepository;
    private final MemberInfoRepository memberInfoRepository;

    private final MachinePredictRepository machinePredictRepository;

    private final DeeplearningRepository deeplearningRepository;
    private final JWTUtil jwtUtil;


    @Autowired
    public StockService(StockRepository stockRepository, KospiRepository kospiRepository, PortfolioRepository portfolioRepository, PortfolioProfitRepository portfolioProfitRepository, TransactionRepository transactionRepository, MemberRepository memberRepository, MemberInfoRepository memberInfoRepository, JWTUtil jwtUtil, MachinePredictRepository machinePredictRepository, DeeplearningRepository deeplearningRepository) {
        this.stockRepository = stockRepository;
        this.kospiRepository = kospiRepository;
        this.portfolioRepository = portfolioRepository;
        this.portfolioProfitRepository = portfolioProfitRepository;
        this.transactionRepository = transactionRepository;
        this.memberRepository = memberRepository;
        this.memberInfoRepository = memberInfoRepository;
        this.jwtUtil = jwtUtil;
        this.machinePredictRepository = machinePredictRepository;
        this.deeplearningRepository = deeplearningRepository;
    }

    private static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public StockPriceResponseDto getStockHistory(String kospiId) {
        List<Stock> stocks = stockRepository.findByKospiIdOrderByTradingDayAsc(kospiId);

        Stock stock = stocks.get(0);
        Kospi kospi = stock.getKospi();
        String name = kospi.getName();

        List<CurrentStockPriceDto> currentStockPrices = new ArrayList<>();

        for (Stock stock1 : stocks) {
            currentStockPrices.add(new CurrentStockPriceDto(formatDate(stock1.getTradingDay()), stock1.getHigh(), stock1.getLow(), stock1.getOpen(), stock1.getClose(), stock1.getVolume()));
        }
//        for(CurrentStockPriceDto currentStockPriceDto: currentStockPrices) {
//            log.info(currentStockPriceDto.toString());
//        }
//        currentStockPrices.sort(Comparator.comparing(CurrentStockPriceDto::getTime));

        return new StockPriceResponseDto(kospi.getId(), name, currentStockPrices);
    }

    // 코스피 전체 종목 리스트
    public List<StockListResponseDto> getStockAll(Pageable page) {
        // stock repository의 가장 최신(1) 시가총액(2)을 가져와서 리스트 정렬
        Page<Kospi> kospiList = kospiRepository.findAll(page); // 일단 다 불러오기
        List<StockListResponseDto> stockListResponseDtos = new ArrayList<>();

        for (Kospi kospi : kospiList) {
            List<Stock> stocks = stockRepository.findFirstByKospiIdOrderByTradingDayDesc(kospi.getId());
            if (!stocks.isEmpty()) {
                Stock latestStock = stocks.get(0); // 가장 최신 주식
                stockListResponseDtos.add(new StockListResponseDto(kospi.getId(), kospi.getName(), latestStock.getClose(), latestStock.getChangedRate()));
            }
        }
        return stockListResponseDtos;
    }

    // 포트폴리오 리스트
    public List<PortFolioListResponseDto> getUserPortFolio(String token) {
        String email = jwtUtil.getSubject(token);
        Member member = memberRepository.findMemberByEmailAddress(email);
        List<TransactionMapping> transactionList = transactionRepository.findBalanceByMemberId(member.getId());
        List<PortFolioListResponseDto> portFolioListResponseDto = new ArrayList<>();

        for (TransactionMapping transactionMapping : transactionList) {
            List<Stock> stockOptional = stockRepository.findFirstByKospiIdOrderByTradingDayDesc(transactionMapping.getCode());
            Stock stock = stockOptional.get(0);
            portFolioListResponseDto.add(new PortFolioListResponseDto(transactionMapping.getCode(), transactionMapping.getName(), stock.getClose(), transactionMapping.getPricePerStock(), Math.round((stock.getClose() - transactionMapping.getPricePerStock()) / transactionMapping.getPricePerStock() * 10000) / 100.0));
        }
        return portFolioListResponseDto;

    }

    public void portfolioCheck(String token) throws Exception {
        String email = jwtUtil.getSubject(token);
        Member member = memberRepository.findMemberByEmailAddress(email);
        List<Portfolio> allByMemberId = portfolioRepository.findAllByMemberId(member.getId());
        if (allByMemberId.isEmpty()) {
            throw new Exception();
        }
    }

    // 포트폴리오 추천 하락률 top 5 - 머신러닝
    public List<PortfolioRecommendResponseDto> recommendLossMachineLearing() throws Exception {

        List<PortfolioRecommendResponseDto> portfolioRecommendResponseDtoList = new ArrayList<>();
        // 상승률 top 5
        List<MachinePredict> mostProfitStockByGapOrderByCreatedDate = machinePredictRepository.findMostLossStockByGapOrderByCreatedDate();
        List<KospiMapping> stockInfo = kospiRepository.findStockInfo();
        for (MachinePredict machinePredict : mostProfitStockByGapOrderByCreatedDate) {
            for (KospiMapping kospiMapping : stockInfo) {
                if (machinePredict.getCode().equals(kospiMapping.getCode())) {
                    portfolioRecommendResponseDtoList.add(new PortfolioRecommendResponseDto(machinePredict.getCode(), kospiMapping.getName(), kospiMapping.getCurrentPrice(), kospiMapping.getPredictPrice()));
                }
            }
        }

        return portfolioRecommendResponseDtoList;
    }

    // 포트폴리오 추천 - 상승률 top 5 - 머신러닝
    public List<PortfolioRecommendResponseDto> recommendProfitMachineLearing(String date) {

        List<PortfolioRecommendResponseDto> portfolioRecommendResponseDtoList = new ArrayList<>();
        // 상승률 top 5
        List<MachinePredict> mostProfitStockByGapOrderByCreatedDate = machinePredictRepository.findMostProfitStockByGapOrderByCreatedDate(date);

        for (MachinePredict machinePredict : mostProfitStockByGapOrderByCreatedDate) {
            List<Stock> byKospiIdOrderByTradingDayAsc = stockRepository.findByKospiIdOrderByTradingDayAsc(machinePredict.getCode());
//            Stock s = byKospiIdOrderByTradingDayAsc.get(0);

            portfolioRecommendResponseDtoList.add(new PortfolioRecommendResponseDto(machinePredict.getCode(), machinePredict.getName(), machinePredict.getPrevClose(), machinePredict.getPredClose()));

        }

        return portfolioRecommendResponseDtoList;
    }

    public List<PortfolioRecommendResponseDto> recommendProfitDeepLearing(String date) {

        List<PortfolioRecommendResponseDto> portfolioRecommendResponseDtoList = new ArrayList<>();
        // 상승률 top 5
        List<DeeplearningPredict> mostProfitStockByGapOrderByCreatedDate = deeplearningRepository.findMostProfitStockByGapOrderByCreatedDate(date);

        for (DeeplearningPredict deeplearningPredict : mostProfitStockByGapOrderByCreatedDate) {
            List<Stock> byKospiIdOrderByTradingDayAsc = stockRepository.findByKospiIdOrderByTradingDayAsc(deeplearningPredict.getCode());
//            Stock s = byKospiIdOrderByTradingDayAsc.get(0);
            portfolioRecommendResponseDtoList.add(new PortfolioRecommendResponseDto(deeplearningPredict.getCode(), deeplearningPredict.getName(), deeplearningPredict.getPrevClose(), deeplearningPredict.getPredClose()));

        }

        return portfolioRecommendResponseDtoList;
    }

    // 각 항목을 얼만큼 구매할지에 대한 결정 필요
    @Transactional
    public void initPortfolio(PortfolioInitDto portfolioInitDto, String token) throws Exception {
        String email = jwtUtil.getSubject(token);
        Member member = memberRepository.findMemberByEmailAddress(email);
        if (member == null) throw new UserNotFoundException();

        Double balance = member.getBalance();
        if (balance - portfolioInitDto.getSeed() < 0) {
            throw new Exception(); // 잔고가 부족할 때
        }


        for (String code : portfolioInitDto.getStocks()) {
            initBuy(member, code, portfolioInitDto.getSeed() * 0.2, portfolioInitDto.getTarget(), portfolioInitDto.getLoss(), portfolioInitDto.getTrading());
        }


    }

    @Transactional
    public void initBuy(Member member, String code, Double amount, Double target, Double loss, Trading trading) {
        List<Stock> stockList = stockRepository.findFirstByKospiIdOrderByTradingDayDesc(code);
        Double price = stockList.get(0).getClose();
        Optional<Kospi> kospiOptional = kospiRepository.findById(code);
        Kospi kospi = kospiOptional.get();
        Double stockAmt = Math.floor(amount / price * 100) / 100.0; // 구매할 주식 수
        Double totalPrice = Math.floor(price * stockAmt * 100) / 100.0;
        transactionRepository.save(new Transaction(stockAmt, new Date(), price, totalPrice, member, kospi));
        member.setBalance(member.getBalance() - totalPrice);
        portfolioRepository.save(new Portfolio(stockAmt, price, code, target, loss, trading, member));
    }

    public List<PortfolioStateResponseDto> portfolioProfitState(String token) throws Exception {
        List<PortfolioStateResponseDto> portfolioStateResponseDtoList = new ArrayList<>();

        String email = jwtUtil.getSubject(token);
        Member member = memberRepository.findMemberByEmailAddress(email);
        if (member == null) throw new UserNotFoundException();

        List<PortfolioProfit> portfolioProfitList = portfolioProfitRepository.findAllByMember(member);
        if (portfolioProfitList.isEmpty()) throw new Exception();
        ; // exception수정 예정 포프로리오 시작 첫날에 발생

        for (PortfolioProfit portfolioProfit : portfolioProfitList) {
            portfolioStateResponseDtoList.add(new PortfolioStateResponseDto(portfolioProfit.getTradingDate(), portfolioProfit.getProfit()));
        }

        return portfolioStateResponseDtoList;


    }

    @Transactional(readOnly = true)
    public PortfolioSummaryResponseDto getPortfolioSummary(String token) throws Exception {
        String email = jwtUtil.getSubject(token);
        Member member = memberRepository.findMemberByEmailAddress(email);
        if (member == null) throw new Exception(); // 수정 예정
        Long memberId = member.getId(); // 사용자id


        MemberInfo memberInfo = memberInfoRepository.findMemberInfoByMemberId(memberId); // 닉네임

        Double balance = member.getBalance(); // 잔고

        List<TransactionMapping> transactionList = transactionRepository.findBalanceByMemberId(memberId);
        double totalAmount = 0.0;
        for (TransactionMapping transactionMapping : transactionList) {
            Double price = transactionMapping.getAmount() * transactionMapping.getPricePerStock();
            totalAmount += price;
        }

        Map<String, Double> stockHoldingRatios = new HashMap<>();
        // 각 종목에 대한 비율 구하기
        for (TransactionMapping transactionMapping1 : transactionList) {

            double stockValue = transactionMapping1.getAmount() * transactionMapping1.getPricePerStock();
            Double holdStocksRatio = Math.round((stockValue / totalAmount) * 10000) / 100.0;
            if (totalAmount == 0) {
                throw new ArithmeticException();
            }
            stockHoldingRatios.put(transactionMapping1.getName(), holdStocksRatio);
        }

        return new PortfolioSummaryResponseDto(memberInfo.getNickname(), totalAmount, stockHoldingRatios);
    }

    @Transactional
    // 포트폴리오 종료
    public void shutPortfolio(String token) throws Exception {
        String email = jwtUtil.getSubject(token);
        Member member = memberRepository.findMemberByEmailAddress(email);
        if (member == null) throw new Exception(); // 수정 예정

        List<Portfolio> portfolioList = portfolioRepository.findAllByMemberId(member.getId());
        Double d = 0.0;
        for (Portfolio portfolio : portfolioList) {
            Double totalP = portfolio.getAvgPrice() * portfolio.getAmount();
            Optional<Kospi> kospi = kospiRepository.findById(portfolio.getKospiId());
            d += totalP;
            transactionRepository.save(new Transaction(-1 * portfolio.getAmount(), new Date(), portfolio.getAvgPrice(), totalP, member, kospi.get()));
        }

        portfolioRepository.deleteByMember(member);
        member.setBalance(member.getBalance() + d);
    }

    // 해당 종목이 목표 수익률 달성 가능한지 체크
    private Boolean checkTargetAvailable(Double target, List<Stock> stockList) {
        Double ma20 = ma(stockList, 20);
        Double ma60 = ma(stockList, 60);
        Double ma120 = ma(stockList, 120);
        // 정배열인지
        Boolean isRegular = ma20 > ma60 && ma60 > ma120;

        Double currentPrice = stockList.get(stockList.size() - 1).getClose();
        List<Double> bollingerBandList = bollingerBand(stockList, 20);
        Double upper = bollingerBandList.get(0);
        Double gap = (upper - currentPrice) / currentPrice * 100;
        // 3개의 이평선이 정배열 이면서 현재가와 불린저밴드의 상단의 갭이 target 이상 벌어져 있을 때
        log.info(stockList.get(0).getKospi().getName() + " " + ma20 + " " + ma60 + " " + ma120 + " " + upper + " " + stockList.get(stockList.size() - 1).getTradingDay());
        return isRegular && gap >= target;
    }

    private Double ma(List<Stock> stockList, int period) {

        Double total = 0.0;
        for (int i = stockList.size() - 1; i > stockList.size() - period; i--) {
            total += stockList.get(i).getClose();
        }
        return total / period;

    }

    private Double rsi(List<Stock> stockList, int period) {
        List<Double> diffList = new ArrayList<>();
        Double u = 0.0;
        Double d = 0.0;
        Double au = 0.0;
        Double ad = 0.0;

        for (int i = stockList.size() - period; i < stockList.size() - 1; i++) {
            diffList.add(stockList.get(i + 1).getClose() - stockList.get(i).getClose());
        }

        for (Double diff : diffList) {
            if (diff > 0) {
                u += diff;
            } else {
                d += diff;
            }
        }
        au = u / period;
        ad = d / period;
        return au / (au + ad) * 100;

    }

    private List<Double> bollingerBand(List<Stock> stockList, int period) {
        Double upper = 0.0;
        Double mid = ma(stockList, period); // 중단 20일 MA
        Double lower = 0.0;

        // 표준편차 계산
        Double avg = closeAvg(stockList, period);
        List<Double> diffList = new ArrayList<>();
        for (int i = stockList.size() - period; i < stockList.size(); i++) {
            diffList.add(Math.pow(stockList.get(i).getClose() - avg, 2));
        }

        Double total = 0.0;
        for (Double diff : diffList) {
            total += diff;
        }
        Double d = Math.sqrt(total / period); // 표준편차
        upper = mid + (d * 2);
        lower = mid - (d * 2);

        return Arrays.asList(upper, mid, lower);
    }

    private Double closeAvg(List<Stock> stockList, int period) {
        Double total = 0.0;
        for (int i = 0; i < period; i++) {
            total += stockList.get(i).getClose();
        }
        return total / period;
    }

    public List<TransactionResponseDto> getTransacitonList(String token, String stockName) {
        String email = jwtUtil.getSubject(token);
        Member member = memberRepository.findMemberByEmailAddress(email);
        if (member == null) throw new UserNotFoundException();
        Long memId = member.getId();

        List<TransactionResponseDto> transactionResponseDtoList = new ArrayList<>();
        List<Transaction> transactionList = transactionRepository.findByNameOrderByTradeDateDesc(memId, stockName);

        for (Transaction transaction : transactionList) {
            TransactionResponseDto transactionResponseDto = new TransactionResponseDto(transaction.getTradeDate(), transaction.getKospi().getId(), transaction.getKospi().getName(), transaction.getPricePerStock(), transaction.getAmount(), transaction.getTotalPrice());
            transactionResponseDtoList.add(transactionResponseDto);
        }

        return transactionResponseDtoList;
    }

}






