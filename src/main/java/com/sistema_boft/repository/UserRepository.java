package com.sistema_boft.repository;


import com.sistema_boft.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Busca orçamentos por cliente
    List<User> findByClienteId(Long printorderId);
}
