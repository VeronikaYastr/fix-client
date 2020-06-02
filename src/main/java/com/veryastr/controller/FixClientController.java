package com.veryastr.controller;

import com.veryastr.service.FixClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FixClientController {

    private final FixClientService fixClientService;

    @PostMapping(value = "/market-data-request")
    public void sendMarketDataRequest(@RequestParam("symbol") String symbol) {
        fixClientService.sendMarkedDataRequest(symbol);
    }
}
