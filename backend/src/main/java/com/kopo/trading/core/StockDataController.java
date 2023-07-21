package com.kopo.trading.core;

import com.kopo.trading.core.dto.MainViewResponseDto;
import com.kopo.trading.core.dto.StockDataResponseDTO;
import com.kopo.trading.core.service.StockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StockDataController {
    private final StockDataService stockService;

    @Autowired
    public StockDataController(StockDataService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/")
    public String getTop5StockData(Model model) {
        List<StockDataResponseDTO> top5StockData = stockService.getTop5StockData();
        List<StockDataResponseDTO> top5UpStockData = stockService.getTop5UpStockData();
        List<StockDataResponseDTO> top5DownStockData = stockService.getTop5DownStockData();
        List<MainViewResponseDto> mainViewResponseDtos = stockService.getMain();

        model.addAttribute("top5StockData", top5StockData);
        model.addAttribute("top5UpStockData", top5UpStockData);
        model.addAttribute("top5DownStockData", top5DownStockData);
        model.addAttribute("mainViewResponseDtos", mainViewResponseDtos);
        return "index";
    }
}
