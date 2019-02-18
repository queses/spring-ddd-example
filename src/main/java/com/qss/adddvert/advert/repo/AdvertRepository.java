package com.qss.adddvert.advert.repo;

import com.qss.adddvert.advert.model.Advert;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

@NoRepositoryBean
public interface AdvertRepository extends PagingAndSortingRepository<Advert, Integer> {
    List<Advert> findByTitle (String title);
    List<Advert> findByContactEmail (String email);
    List<Advert> findByContactPhone (String phone);
}
