package com.qss.adddvert.advert.model;

public enum PriceLiquidValue {
    NORMAL,
    GOOD,
    GREAT;

    static PriceLiquidValue getForInt(int intValue) {
        switch (intValue) {
            case 1: return PriceLiquidValue.GREAT;
            case 2: return PriceLiquidValue.GOOD;
            default: return PriceLiquidValue.NORMAL;
        }
    }
}
