package com.veryastr.service.impl;

import com.veryastr.common.FixClientException;
import com.veryastr.config.ConnectorConfig;
import com.veryastr.service.FixClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quickfix.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.InputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectorService {

    private final ConnectorConfig config;
    private final FixClientService fixClientService;

    private SocketInitiator socketInitiator;

    @PostConstruct
    public void init() {
        if (config.getCfg() == null || !config.getCfg().exists()) {
            throw new FixClientException("Config file for connector not found.");
        }
        createConnector();
    }

    @PreDestroy
    public void preDestroy() {
        if (socketInitiator != null) {
            socketInitiator.getSessions().forEach(sessionID -> {
                try {
                    Session session = Session.lookupSession(sessionID);
                    if (session != null) {
                        session.close();
                    } else {
                        log.warn("Requested session is not found.");
                    }
                } catch (Exception e) {
                    log.error("Error while closing session.", e);
                }
            });

            try {
                socketInitiator.stop(true);
            } catch (Exception e) {
                log.error("Error while closing Socket Initializer.", e);
            }
        }
    }

    private void createConnector() {
        try (InputStream inputStream = config.getCfg().getInputStream()) {
            SessionSettings sessionSettings = new SessionSettings(inputStream);
            sessionSettings.setString("SocketConnectHost", config.getSocketConnectHost());
            sessionSettings.setString("SocketConnectPort", config.getSocketConnectPort());

            MessageStoreFactory storeFactory = new FileStoreFactory(sessionSettings);
            SLF4JLogFactory logFactory = new SLF4JLogFactory(sessionSettings);
            MessageFactory messageFactory = new DefaultMessageFactory();

            socketInitiator = new SocketInitiator(fixClientService, storeFactory, sessionSettings, logFactory, messageFactory);
            socketInitiator.start();
        } catch (Exception ex) {
            log.error("Exception while establishing connection to FIX server.", ex);
            throw new FixClientException("Exception while establishing connection to FIX server.", ex);
        }
    }
}
