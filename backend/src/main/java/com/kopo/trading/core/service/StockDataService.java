package com.kopo.trading.core.service;

import com.kopo.trading.core.dto.MainViewResponseDto;
import com.kopo.trading.core.dto.StockDataResponseDTO;
import com.kopo.trading.core.repository.MainViewRepository;
import com.kopo.trading.core.repository.StockDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockDataService {
    private final StockDataRepository stockDataRepository;
    private final MainViewRepository mainViewRepository;

    DecimalFormat df = new DecimalFormat("#,###.##");

    @Autowired
    public StockDataService(StockDataRepository stockDataRepository, MainViewRepository mainViewRepository) {
        this.stockDataRepository = stockDataRepository;
        this.mainViewRepository = mainViewRepository;
    }

    public List<StockDataResponseDTO> getTop5StockData() {
        List<Object[]> result = stockDataRepository.findTop5StockData();
        List<StockDataResponseDTO> stockDataList = new ArrayList<>();

        for (Object[] obj : result) {
            String kospiName = (String) obj[0];
            String code = (String) obj[1];
            String close = df.format(((BigDecimal) obj[2]).setScale(2, BigDecimal.ROUND_HALF_UP));
            String changedRate = df.format(((BigDecimal) obj[3]).setScale(2, BigDecimal.ROUND_HALF_UP)) + "%";
            StockDataResponseDTO stockDataDTO = new StockDataResponseDTO(kospiName, code, close, changedRate);
            stockDataList.add(stockDataDTO);
        }

        return stockDataList;
    }

    public List<StockDataResponseDTO> getTop5UpStockData() {
        List<Object[]> result = stockDataRepository.findTop5UpStockData();
        List<StockDataResponseDTO> stockDataList = new ArrayList<>();

        for (Object[] obj : result) {
            String kospiName = (String) obj[0];
            String code = (String) obj[1];
            String close = df.format(((BigDecimal) obj[2]).setScale(2, BigDecimal.ROUND_HALF_UP));
            String changedRate = df.format(((BigDecimal) obj[3]).setScale(2, BigDecimal.ROUND_HALF_UP)) + "%";
            StockDataResponseDTO stockDataDTO = new StockDataResponseDTO(kospiName, code, close, changedRate);
            stockDataList.add(stockDataDTO);
        }

        return stockDataList;
    }

    public List<StockDataResponseDTO> getTop5DownStockData() {
        List<Object[]> result = stockDataRepository.findTop5DownStockData();
        List<StockDataResponseDTO> stockDataList = new ArrayList<>();

        for (Object[] obj : result) {
            String kospiName = (String) obj[0];
            String code = (String) obj[1];
            String close = df.format(((BigDecimal) obj[2]).setScale(2, BigDecimal.ROUND_HALF_UP));
            String changedRate = df.format(((BigDecimal) obj[3]).setScale(2, BigDecimal.ROUND_HALF_UP)) + "%";
            StockDataResponseDTO stockDataDTO = new StockDataResponseDTO(kospiName, code, close, changedRate);
            stockDataList.add(stockDataDTO);
        }

        return stockDataList;
    }

    public List<MainViewResponseDto> getMain(){
        List<Object[]> result = mainViewRepository.findMain();
        List<MainViewResponseDto> mainviewList = new ArrayList<>();

        for(Object[] obj : result){
            String category = (String) obj[0];
            String price = df.format(((BigDecimal) obj[1]).setScale(2, BigDecimal.ROUND_HALF_UP));
            String status = df.format(((BigDecimal) obj[2]).setScale(2, BigDecimal.ROUND_HALF_UP)) + "%";
            MainViewResponseDto mainViewResponseDto = new MainViewResponseDto(category, price, status);
            mainviewList.add(mainViewResponseDto);
        }
        return mainviewList;
    }
}
