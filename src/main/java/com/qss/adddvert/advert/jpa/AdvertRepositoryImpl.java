package com.qss.adddvert.advert.jpa;

import com.qss.adddvert.advert.model.Advert;
import com.qss.adddvert.advert.repo.AdvertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class AdvertRepositoryImpl extends SimpleJpaRepository<Advert, Integer> implements AdvertRepository {
    @Autowired
    public AdvertRepositoryImpl(EntityManager entityManager) {
        super(Advert.class, entityManager);
    }

    @Override
    public List<Advert> findByTitle(String title) {
        return this.findAll((root, query, builder) -> {
            return builder.equal(root.get("title"), title);
        });
    }

    @Override
    public List<Advert> findByContactEmail(String email) {
        return this.findAll((root, query, builder) -> {
            return builder.equal(root.get("contact").get("email"), email);
        });
    }

    @Override
    public List<Advert> findByContactPhone(String phone) {
        return this.findAll((root, query, builder) -> {
            root.join("contact").fetch("phoneNumbers");
            return builder.equal(root.join("contact").joinList("phoneNumbers").get("number"), phone);
        });
    }
}
