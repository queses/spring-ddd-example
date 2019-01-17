package com.qss.adddvert.advert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qss.adddvert.advert.model.PriceLiquid;
import com.qss.adddvert.advert.web.mixins.PriceLiquidMixin;
import com.qss.adddvert.core.ModuleRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdvertModule implements ModuleRegistrar {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void register() {
        addJacksonMixins();
    }

    private void addJacksonMixins() {
        objectMapper.addMixIn(PriceLiquid.class, PriceLiquidMixin.class);
    }
}
