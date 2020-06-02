package com.veryastr.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class MarketDataModel {
    private String symbol;
    private BigDecimal bid;
    private BigDecimal ask;
}
