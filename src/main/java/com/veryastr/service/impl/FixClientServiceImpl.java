package com.veryastr.service.impl;

import com.veryastr.common.MsgUtils;
import com.veryastr.service.FixClientService;
import com.veryastr.service.MarketDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quickfix.*;

import static quickfix.field.MsgType.MARKET_DATA_SNAPSHOT_FULL_REFRESH;
import static quickfix.field.MsgType.SECURITY_DEFINITION;

@Service
@Slf4j
@RequiredArgsConstructor
public class FixClientServiceImpl extends BaseFixService implements FixClientService {

    private final MarketDataService marketDataService;

    @Override
    public void sendMarkedDataRequest(String symbol) {
        sessions.forEach((sessionID, session) ->
                session.send(MsgUtils.createMarketDataRequest(symbol)));
    }

    @Override
    public void onLogon(SessionID sessionId) {
        log.info(">> onLogon for session: {}", sessionId);
        Session session = Session.lookupSession(sessionId);
        if (session != null) {
            sessions.putIfAbsent(sessionId, session);
        } else {
            log.warn("Requested session is not found.");
        }
    }

    @Override
    public void fromApp(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        try {
            String type = MessageUtils.getMessageType(message.toString());
            switch (type) {
                case MARKET_DATA_SNAPSHOT_FULL_REFRESH:
                    marketDataService.saveMarketData(message);
                    break;
                case SECURITY_DEFINITION:
                    log.info("SecurityDefinition message: {}", message);
                    break;
                default:
                    log.info("Unhandled message {} of type: {}", message, type);
            }
        } catch (Exception ex) {
            log.debug("Unexpected exception while processing message.", ex);
        }
    }
}
