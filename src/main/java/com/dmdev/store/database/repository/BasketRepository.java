package com.dmdev.store.database.repository;

import com.dmdev.store.database.entity.Basket;
import com.dmdev.store.database.entity.Technic;
import com.dmdev.store.database.entity.User;
import com.dmdev.store.dto.BasketReadDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {

    public Optional<Basket> findByUserAndTechnic(User user, Technic technic);
}
