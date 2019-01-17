package com.qss.adddvert.advert.model;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class AdPrice {
    @Getter @Valid
    private PriceLiquid liquid;
    @Getter @Min(0)
    private double value;

    public AdPrice(double value, PriceLiquid liquid) {
        this.value = value;
        this.liquid = liquid;
    }

    public AdPrice changeLiquid (PriceLiquid liquid) {
        return new AdPrice(this.value, liquid);
    }
}
