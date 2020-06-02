package com.veryastr.service;

import com.veryastr.model.MarketDataModel;
import quickfix.Message;

public interface MarketDataService {
    MarketDataModel getMarketData(String symbol);

    void saveMarketData(Message message);
}
