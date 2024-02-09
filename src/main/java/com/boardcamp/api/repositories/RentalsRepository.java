package com.boardcamp.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boardcamp.api.models.RentalsModel;

@Repository
public interface RentalsRepository extends JpaRepository<RentalsModel, Long> {

    List<RentalsModel> findAllByGameId(Long gameId);
    
}
