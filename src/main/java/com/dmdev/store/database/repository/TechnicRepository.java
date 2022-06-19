package com.dmdev.store.database.repository;

import com.dmdev.store.database.entity.Technic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicRepository extends JpaRepository<Technic, Long> {
}
