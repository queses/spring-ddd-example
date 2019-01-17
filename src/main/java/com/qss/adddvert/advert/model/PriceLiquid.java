package com.qss.adddvert.advert.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PriceLiquid {
    @Getter
    private PriceLiquidValue value;
    @Getter @NotNull
    private Date assignedAt;

    public static PriceLiquid create(int value) {
        return PriceLiquid.create(PriceLiquidValue.getForInt(value));
    }

    public static PriceLiquid create(PriceLiquidValue value) {
        return new PriceLiquid(value, new Date());
    }
}
