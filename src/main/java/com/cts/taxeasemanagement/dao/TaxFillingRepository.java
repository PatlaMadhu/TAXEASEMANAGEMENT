package com.cts.taxeasemanagement.dao;



import com.cts.taxeasemanagement.entity.TaxFilling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxFillingRepository extends JpaRepository<TaxFilling, Long> {
    // Spring Data JPA automatically traverses the 'taxpayer' entity to find its 'id'
    List<TaxFilling> findByTaxpayerId(Long taxpayerId);
}
