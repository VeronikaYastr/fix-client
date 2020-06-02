package com.veryastr.common;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import quickfix.FieldMap;
import quickfix.FieldNotFound;
import quickfix.Message;
import quickfix.field.*;
import quickfix.fix42.MarketDataRequest;

import java.math.BigDecimal;
import java.util.Optional;

import static java.lang.String.format;
import static quickfix.field.SubscriptionRequestType.SNAPSHOT;

@Slf4j
@UtilityClass
public class MsgUtils {

    private static int mdReqID = 1;
    private static final String ERR_MSG = "Invalid tag {}.";

    public static Message createMarketDataRequest(String symbol) {
        MarketDataRequest marketDataRequest = new MarketDataRequest(
                new MDReqID(format("FixClient-%s", mdReqID++)),
                new SubscriptionRequestType(SNAPSHOT), //263
                new MarketDepth(1) //264, 1 = top of book
        );
        addEntryTypes(marketDataRequest);

        if (symbol == null) {
            marketDataRequest.set(new NoRelatedSym(0));
            return marketDataRequest;
        }

        MarketDataRequest.NoRelatedSym instrument = new MarketDataRequest.NoRelatedSym();
        instrument.set(new Symbol(symbol));

        marketDataRequest.addGroup(instrument);
        return marketDataRequest;
    }

    private void addEntryTypes(MarketDataRequest marketDataRequest) {
        MarketDataRequest.NoMDEntryTypes group = new MarketDataRequest.NoMDEntryTypes(); //267

        group.set(new MDEntryType(MDEntryType.BID));
        marketDataRequest.addGroup(group);
        group.set(new MDEntryType(MDEntryType.OFFER));
        marketDataRequest.addGroup(group);
    }

    public static Optional<String> getStrField(FieldMap map, int tag) {
        String result = null;
        try {
            result = map.isSetField(tag) ? map.getString(tag) : null;
        } catch (FieldNotFound ex) {
            log.error(ERR_MSG, tag, ex);
        }
        return Optional.ofNullable(result);
    }

    public static Optional<Integer> getIntField(FieldMap map, int tag) {
        Integer result = null;
        try {
            result = map.isSetField(tag) ? map.getInt(tag) : null;
        } catch (FieldNotFound ex) {
            log.error(ERR_MSG, tag, ex);
        }
        return Optional.ofNullable(result);
    }

    public static Optional<BigDecimal> getDecimalField(FieldMap map, int tag) {
        BigDecimal result = null;
        try {
            result = map.isSetField(tag) ? map.getDecimal(tag) : null;
        } catch (FieldNotFound ex) {
            log.error(ERR_MSG, tag, ex);
        }
        return Optional.ofNullable(result);
    }
}
