package com.sistema_boft.repository;

import com.sistema_boft.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    // Busca orçamentos por cliente
    List<Photo> findByClienteId(Long photoId);
}



