package com.cts.taxeasemanagement.dao;

import com.cts.taxeasemanagement.entity.Taxpayer;
import com.cts.taxeasemanagement.entity.TaxpayerDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaxpayerDocumentRepository extends JpaRepository<TaxpayerDocument, Long> {
    List<TaxpayerDocument> findByTaxpayer(Taxpayer taxpayer);
}
