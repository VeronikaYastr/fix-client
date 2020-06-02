package com.veryastr.controller;

import com.veryastr.model.MarketDataModel;
import com.veryastr.service.MarketDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/market-data")
@RequiredArgsConstructor
@Slf4j
public class MarketDataController {

    private final MarketDataService marketDataService;

    @GetMapping
    public ResponseEntity<MarketDataModel> getMarketData(@RequestParam("symbol") String symbol) {
        return new ResponseEntity<>(marketDataService.getMarketData(symbol), HttpStatus.OK);
    }
}
