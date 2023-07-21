package com.kopo.trading.core.service;

import com.kopo.trading.core.dto.DeepListResponseDto;
import com.kopo.trading.core.dto.MachineListResponseDto;
import com.kopo.trading.core.dto.StockResponseDto;
import com.kopo.trading.core.entity.DeeplearningPredict;
import com.kopo.trading.core.entity.Kospi;
import com.kopo.trading.core.entity.MachinePredict;
import com.kopo.trading.core.entity.Stock;
import com.kopo.trading.core.repository.*;
import com.kopo.trading.security.JWTUtil;
import com.kopo.trading.user.repository.MemberInfoRepository;
import com.kopo.trading.user.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebService {

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
    public WebService(StockRepository stockRepository, KospiRepository kospiRepository, PortfolioRepository portfolioRepository, PortfolioProfitRepository portfolioProfitRepository, TransactionRepository transactionRepository, MemberRepository memberRepository, MemberInfoRepository memberInfoRepository, JWTUtil jwtUtil, MachinePredictRepository machinePredictRepository, DeeplearningRepository deeplearningRepository) {
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

    // 10년치 주식 정보 모두 뿌리기
    public List<StockResponseDto> getStockList(String stockName) {
        Kospi kospi = kospiRepository.findByName(stockName);
        if (kospi == null) {
            throw new IllegalArgumentException("Invalid stock name: " + stockName);
        }
        String kospiId = kospi.getId();
        List<StockResponseDto> stockTenYear = new ArrayList<>();
        List<Stock> stocks = stockRepository.findByKospiIdOrderByTradingDayDesc(kospiId);
        for (Stock stock : stocks) {
            StockResponseDto stockData = new StockResponseDto(kospi.getName(), stock.getKospi().getId(), stock.getTradingDay(), stock.getClose(), stock.getOpen(), stock.getHigh(), stock.getLow(), stock.getVolume(), stock.getCap());
            stockTenYear.add(stockData);
        }
        return stockTenYear;
    }

    // 머신러닝
    public List<MachineListResponseDto> getMachineList(String stockName) {
        List<MachineListResponseDto> machineListResponseDtoList = new ArrayList<>();
        List<MachinePredict> machinePredictList = machinePredictRepository.findByNameOrderByCreatedDateDesc(stockName);
        for (MachinePredict machine : machinePredictList) {
            MachineListResponseDto mlR = new MachineListResponseDto(machine.getCreatedDate(), machine.getName(), machine.getCode(), machine.getClose(), machine.getPredClose(), machine.getDiff());
            machineListResponseDtoList.add(mlR);
        }
        return machineListResponseDtoList;
    }

    // 딥러닝
    public List<DeepListResponseDto> getDeepList(String stockName) {
        List<DeepListResponseDto> deepListResponseDtoList = new ArrayList<>();
        List<DeeplearningPredict> deeplearningPredictList = deeplearningRepository.findByNameOrderByCreatedDateDesc(stockName);
        for (DeeplearningPredict deeplearning : deeplearningPredictList) {
            DeepListResponseDto dlR = new DeepListResponseDto(deeplearning.getCreatedDate(), deeplearning.getName(), deeplearning.getCode(), deeplearning.getClose(), deeplearning.getPredClose(), deeplearning.getDiff());
            deepListResponseDtoList.add(dlR);
        }
        return deepListResponseDtoList;
    }


}
