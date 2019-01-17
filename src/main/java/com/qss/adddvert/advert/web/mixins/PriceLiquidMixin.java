package com.qss.adddvert.advert.web.mixins;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.qss.adddvert.advert.model.PriceLiquidValue;

import javax.xml.crypto.Data;

public abstract class PriceLiquidMixin {
    @JsonCreator
    public static void create(PriceLiquidValue value) {}
//    // Or, to use with constructor:
//    @JsonCreator
//    public PriceLiquidMixin(PriceLiquidValue value) {}

    @JsonProperty(access = JsonProperty.Access.READ_ONLY) abstract Data getAssignedAt();
}
