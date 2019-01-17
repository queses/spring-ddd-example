package com.qss.adddvert.advert.model;

import com.qss.adddvert.core.model.AbstractAggregateRoot;
import com.qss.adddvert.core.errors.ListShouldNotBeEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Advert extends AbstractAggregateRoot {
    @Getter
    private Integer id;
    @Getter @Size(min = 8)
    private String title;
    @Getter @Setter @Valid
    private AdContact contact;
    @Getter @Valid
    private AdPrice price;
    @Getter @Valid
    private List<AdPrice> oldPrices;
    @Getter
    private int views;

    public Advert(String title, AdContact contact, AdPrice price) {
        this.title = title;
        this.contact = contact;
        this.price = price;
        this.views = 0;
        this.oldPrices = new ArrayList<>();
    }

    public void addPrice(AdPrice price) {
        this.oldPrices.add(this.price);
        this.price = price;
    }

    public void addView() {
        this.views++;
    }
}
