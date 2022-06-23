package com.dmdev.store.database.repository;

import com.dmdev.store.database.entity.Category;
import com.dmdev.store.database.entity.Technic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnicRepository extends JpaRepository<Technic, Long> {

    public List<Technic> findByCategory(Category category);

    public List<Technic> findByNameContainingIgnoreCase(String name);
}

