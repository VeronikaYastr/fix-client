package com.veryastr.service.impl;

import com.veryastr.common.FixClientException;
import com.veryastr.common.MsgUtils;
import com.veryastr.model.MarketDataModel;
import com.veryastr.service.MarketDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quickfix.Message;
import quickfix.field.MDEntryPx;
import quickfix.field.MDEntryType;
import quickfix.field.NoMDEntries;
import quickfix.field.Symbol;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MarketDataServiceImpl implements MarketDataService {

    private Map<String, MarketDataModel> marketData = new HashMap<>();

    @Override
    public MarketDataModel getMarketData(String symbol) {
        MarketDataModel model = marketData.get(symbol);
        if (model == null) {
            throw new FixClientException("No market data found for " + symbol);
        }
        return model;
    }

    @Override
    public void saveMarketData(Message message) {
        log.info("Saving market data: {}", message);
        MarketDataModel dataModel = new MarketDataModel();
        message.getGroups(NoMDEntries.FIELD).forEach(group -> {
            int type = MsgUtils.getIntField(group, MDEntryType.FIELD).orElse(-1);
            BigDecimal value = MsgUtils.getDecimalField(group, MDEntryPx.FIELD).orElse(BigDecimal.ZERO);
            switch (type) {
                case 0:
                    dataModel.setBid(value);
                    break;
                case 1:
                    dataModel.setAsk(value);
                    break;
                default:
                    log.warn("Invalid entry type: {}", type);
                    break;
            }
        });

        MsgUtils.getStrField(message, Symbol.FIELD).ifPresent(s -> {
            dataModel.setSymbol(s);
            marketData.put(s, dataModel);
        });
    }
}
