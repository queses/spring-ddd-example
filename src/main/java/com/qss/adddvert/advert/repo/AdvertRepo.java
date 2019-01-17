package com.qss.adddvert.advert.repo;

import com.qss.adddvert.advert.model.Advert;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AdvertRepo extends PagingAndSortingRepository<Advert, Integer> {
}
