package com.veryastr.service.impl;

import lombok.extern.slf4j.Slf4j;
import quickfix.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class BaseFixService implements Application {

    Map<SessionID, Session> sessions = new HashMap<>();

    @Override
    public void onCreate(SessionID sessionId) {
        log.info(">> onCreate for session: {}", sessionId);
        Session session = Session.lookupSession(sessionId);
        if (session != null) {
            sessions.put(sessionId, session);
        } else {
            log.warn("Requested session is not found.");
        }
    }

    @Override
    public void onLogout(SessionID sessionId) {
        log.info(">> onLogout for session: {}", sessionId);
        sessions.remove(sessionId);
    }

    @Override
    public void toAdmin(Message message, SessionID sessionId) {
        log.info(">> toAdmin for session: {} with message {}", sessionId, message);
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        log.info("<< fromAdmin for session: {} with message {}", sessionId, message);
    }

    @Override
    public void toApp(Message message, SessionID sessionId) throws DoNotSend {
        log.info(">> toApp for session: {} with message {}", sessionId, message);
    }
}
