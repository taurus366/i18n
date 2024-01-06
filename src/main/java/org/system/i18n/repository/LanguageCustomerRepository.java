package org.system.i18n.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.system.i18n.model.entity.LanguageCustomerEntity;
import org.system.i18n.model.entity.LanguageEntity;

import java.util.List;

@Repository
public interface LanguageCustomerRepository extends JpaRepository<LanguageCustomerEntity, Long> {
    LanguageCustomerEntity findBySession(String session);

    @Query(value = "SELECT cust FROM LanguageCustomerEntity cust WHERE YEAR(cust.created) = :year")
    List<LanguageCustomerEntity> findAllSessionByYear(int year);
}
