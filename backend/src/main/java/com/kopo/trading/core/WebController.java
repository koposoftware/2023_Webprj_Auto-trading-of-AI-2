package com.kopo.trading.core;

import com.kopo.trading.core.dto.*;
import com.kopo.trading.core.service.ScheduledStockService;
import com.kopo.trading.core.service.StockService;
import com.kopo.trading.core.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WebController {
    private final StockService stockService;
    private final WebService webService;
    private final ScheduledStockService scheduledStockService;

    @Autowired
    public WebController(StockService stockService, WebService webService, ScheduledStockService scheduledStockService) {
        this.stockService = stockService;
        this.webService = webService;
        this.scheduledStockService = scheduledStockService;
    }


    // 10년치 주식 정보 모두 뿌리기
    @PostMapping("/stock")
    public ResponseEntity<List<StockResponseDto>> getStockContent(@RequestBody StockRequest stockRequest) {
        List<StockResponseDto> stockData = webService.getStockList(stockRequest.getName());
        return new ResponseEntity<>(stockData, HttpStatus.OK);
    }

    // 머신러닝 데이터 종목 보여주기
    @PostMapping("/machine")
    public ResponseEntity<List<MachineListResponseDto>> getMachineStockContent(@RequestBody MachineRequestDto machineRequest) {
        List<MachineListResponseDto> machineListResponseDtoList = webService.getMachineList(machineRequest.getName());
        return new ResponseEntity<>(machineListResponseDtoList, HttpStatus.OK);
    }

    @PostMapping("/deep")
    public ResponseEntity<List<DeepListResponseDto>> getDeepStockContent(@RequestBody DeepRequsetDto deepRequest){
        List<DeepListResponseDto> deepListResponseDto = webService.getDeepList(deepRequest.getName());
        return new ResponseEntity<>(deepListResponseDto, HttpStatus.OK);
    }
}
