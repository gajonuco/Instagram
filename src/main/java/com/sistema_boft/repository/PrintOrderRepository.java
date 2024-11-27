package com.sistema_boft.repository;

import com.sistema_boft.model.PrintOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrintOrderRepository extends JpaRepository<PrintOrderRepository, Long> {

    // Busca orçamentos por cliente
    List<PrintOrder> findByClienteId(Long printorderId);
}
