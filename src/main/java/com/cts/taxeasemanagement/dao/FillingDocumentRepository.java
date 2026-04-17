package com.cts.taxeasemanagement.dao;


import com.cts.taxeasemanagement.entity.FillingDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FillingDocumentRepository extends JpaRepository<FillingDocument, Long> {
    List<FillingDocument> findByFilingId(Long filingId);
}
