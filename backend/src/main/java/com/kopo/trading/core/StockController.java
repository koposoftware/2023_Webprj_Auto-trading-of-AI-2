package com.kopo.trading.core;

import com.kopo.trading.core.dto.*;
import com.kopo.trading.core.service.ScheduledStockService;
import com.kopo.trading.core.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/trading")
@RestController
public class StockController {

    private final StockService stockService;
    private final ScheduledStockService scheduledStockService;

    @Autowired
    public StockController(StockService stockService, ScheduledStockService scheduledStockService) {
        this.stockService = stockService;
        this.scheduledStockService = scheduledStockService;
    }


    @GetMapping("/show")
    public StockPriceResponseDto stockPrice(@RequestParam("code") String code) {
        return stockService.getStockHistory(code);
    }

    @GetMapping("/all")
    public List<StockListResponseDto> stockList(@PageableDefault(size = 20) Pageable page) {
        return stockService.getStockAll(page);
    }

    // 토큰 + userid 검증 추가
    @PostMapping("/portfolio/list")
    public List<PortFolioListResponseDto> portfolioList(@RequestHeader("Authorization") String token) {

        log.info("requested");
        return stockService.getUserPortFolio(token.substring(7));
    }

    @GetMapping("/portfolio/profit/recommend")
    public List<PortfolioRecommendResponseDto> profitRecommendMachineLearning(@RequestParam("date") String date) {
        return stockService.recommendProfitMachineLearing(date);
    }

    @GetMapping("/portfolio/profit/recommend/deeplearning")
    public List<PortfolioRecommendResponseDto> profitRecommendDeepLearning(@RequestParam("date") String date) {
        return stockService.recommendProfitDeepLearing(date);
    }

    @PostMapping("/portfolio/loss/recommend")
    public List<PortfolioRecommendResponseDto> lossRecommendMachineLearning() throws Exception {
        return stockService.recommendLossMachineLearing();
    }


    @PostMapping("/portfolio/init")
    public void portfolioInit(@RequestBody PortfolioInitDto portfolioInitDto, @RequestHeader("Authorization") String token) throws Exception {
        stockService.initPortfolio(portfolioInitDto, token.substring(7));
    }


    @PostMapping("/portfolio/state")
    public List<PortfolioStateResponseDto> portfolioState(@RequestHeader("Authorization") String token) throws Exception {
        List<PortfolioStateResponseDto> portfolioStateResponseDtos = stockService.portfolioProfitState(token.substring(7));
        log.info(String.valueOf(portfolioStateResponseDtos.size()));
        return portfolioStateResponseDtos;
    }

    @PostMapping("/portfolio/summary")
    public PortfolioSummaryResponseDto portfolioSummary(@RequestHeader("Authorization") String token) throws Exception {
        return stockService.getPortfolioSummary(token.substring(7));
    }

    @PostMapping("/portfolio/shutdown")
    public void portfolioShutdown(@RequestHeader("Authorization") String token) throws Exception {
        stockService.shutPortfolio(token.substring(7));
    }

    @PostMapping("/portfolio/check")
    public void portfolioCheck(@RequestHeader("Authorization") String token) throws Exception {
        stockService.portfolioCheck(token.substring(7));
    }

    @GetMapping("/test")
    public void updateDate() throws Exception {
        scheduledStockService.predict();
    }

    @PostMapping("/transaction")
    public List<TransactionResponseDto> transactionList(@RequestHeader("Authorization") String token, @RequestBody TransactionRequestDto transactionRequest) throws Exception{
        List<TransactionResponseDto> transactionResponseDtoList = stockService.getTransacitonList(token.substring(7), transactionRequest.getName());
        return transactionResponseDtoList;
    }

}
