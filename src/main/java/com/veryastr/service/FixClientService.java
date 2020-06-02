package com.veryastr.service;

import quickfix.Application;

public interface FixClientService extends Application {
    void sendMarkedDataRequest(String symbol);
}
