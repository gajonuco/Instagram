package com.sistema_boft.repository;

import com.sistema_boft.model.Photo;
import com.sistema_boft.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    // Busca orçamentos por cliente
    List<Photo> findByUser(User user);

        // Deleta fotos associadas a um usuário
        void deleteByUser(User user);
}



