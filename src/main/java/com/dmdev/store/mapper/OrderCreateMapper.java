package com.dmdev.store.mapper;

import com.dmdev.store.database.entity.Order;
import com.dmdev.store.database.entity.User;
import com.dmdev.store.database.repository.UserRepository;
import com.dmdev.store.dto.OrderCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderCreateMapper implements Mapper<OrderCreateDto, Order> {

    private final UserRepository userRepository;

    @Override
    public Order map(OrderCreateDto object) {
        return Order.builder()
                .product(object.getProduct())
                .user(getUser(object.getUserId()))
                .dateRegistration(object.getDateRegistration())
                .dateClose(object.getDateClose())
                .status(object.getStatus())
                .total(object.getTotal())
                .build();
    }

    public User getUser(Long userId) {
        return Optional.ofNullable(userId)
                .flatMap(userRepository::findById)
                .orElse(null);
    }

}
