package com.dmdev.store.database.repository;

import com.dmdev.store.database.entity.Order;
import com.dmdev.store.database.entity.Status;
import com.dmdev.store.database.entity.User;
import com.dmdev.store.dto.OrderReadDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    public List<Order> findAllByUser(User user);

    public List<Order> findAllByProductContaining(String name);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Order o where (o.status = 'COMPLETED' or o.status = 'REJECTED') and o.dateClose < :date")
    public void deleteDateClose(LocalDate date);

    @Query("select o from Order o where o.status = 'ACCEPTED' and o.user = :user")
    List<Order> findAllByStatusByUserId(User user);
}
