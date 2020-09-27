package com.cashmachine.repository;

import com.cashmachine.model.OperationCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationCatalogRepository extends JpaRepository<OperationCatalog, Long> {
}
