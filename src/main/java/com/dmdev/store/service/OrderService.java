package com.dmdev.store.service;

import com.dmdev.store.database.entity.Order;
import com.dmdev.store.database.entity.User;
import com.dmdev.store.database.repository.OrderRepository;
import com.dmdev.store.database.repository.UserRepository;
import com.dmdev.store.dto.OrderCreateDto;
import com.dmdev.store.dto.OrderReadDto;
import com.dmdev.store.dto.TechnicReadDto;
import com.dmdev.store.mapper.OrderCreateMapper;
import com.dmdev.store.mapper.OrderReadMapper;
import com.dmdev.store.mapper.TechnicReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dmdev.store.database.entity.Status.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BasketService basketService;
    private final TechnicReadMapper technicMapper;
    private final OrderCreateMapper orderCreateMapper;
    private final OrderReadMapper readMapper;

    @Transactional
    public OrderReadDto create(String email, Integer[] amounts) {
        List<TechnicReadDto> orders = getOrder(email, amounts);
        Long id = userRepository.findByEmail(email)
                .map(User::getId)
                .orElseThrow();

        Integer total = getTotalOrder(orders);
        String product = getProductOrder(orders);

        OrderCreateDto orderCreateDto = create(id, total, product);

        Order order = orderCreateMapper.map(orderCreateDto);
        orderRepository.save(order);
        OrderReadDto orderReadDto = readMapper.map(order);

        if (orderReadDto.getId() > 0){
            basketService.delete(id);
        }

        return orderReadDto;
    }

    private List<TechnicReadDto> getOrder(String email, Integer[] amounts){
        List<TechnicReadDto> baskets = basketService.findByUserIdBasket(email);
        List<TechnicReadDto> orders = new ArrayList<>();

        for (int i = 0; i < baskets.size(); i++) {
            TechnicReadDto order = technicMapper.copy(baskets.get(i), amounts[i]);
            orders.add(order);
        }
        return orders;
    }

    private Integer getTotalOrder(List<TechnicReadDto> orders){
        return orders.stream()
                .map(order -> (order.getPrice() * order.getAmount()))
                .reduce(Integer::sum).orElseThrow();
    }

    private String getProductOrder(List<TechnicReadDto> orders){
        List<String> products = orders.stream()
                .map(order -> (order.getName() + " - " + order.getAmount())).toList();

        String product = String.join(" , ", products);

        return product;
    }

    private OrderCreateDto create(Long id, Integer total, String product){
        return OrderCreateDto.builder()
                .product(product)
                .userId(id)
                .dateRegistration(LocalDate.now())
                .dateClose(LocalDate.parse("1900-01-01"))
                .status(PROCESSING)
                .total(total)
                .build();
    }

    public List<OrderReadDto> findAllByUser(String username) {
        User user = userRepository.findByEmail(username).orElseThrow();
        return orderRepository.findAllByUser(user).stream()
                .map(readMapper::map)
                .toList();
    }

    public List<OrderReadDto> findAllByUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return orderRepository.findAllByUser(user).stream()
                .map(readMapper::map)
                .toList();
    }

    @Transactional
    public boolean delete(Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    orderRepository.delete(order);
                    return true;
                })
                .orElse(false);
    }

    public List<OrderReadDto> findAllByProduct(String name) {
        return orderRepository.findAllByProductContaining(name).stream()
                .map(readMapper::map)
                .toList();
    }

    public List<OrderReadDto> findAll() {
        return orderRepository.findAll().stream()
                .map(readMapper::map)
                .toList();
    }

    @Transactional
    public void deleteDateClose(LocalDate date){
        orderRepository.deleteDateClose(date);
    }

    @Transactional
    public Optional<OrderReadDto> updateAccept(Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setStatus(ACCEPTED);
                    return order;
                })
                .map(orderRepository::saveAndFlush)
                .map(readMapper::map);
    }

    @Transactional
    public Optional<OrderReadDto> updateReject(Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setStatus(REJECTED);
                    return order;
                })
                .map(orderRepository::saveAndFlush)
                .map(readMapper::map);
    }

    @Transactional
    public Optional<OrderReadDto> updateCompleted(Long id) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setStatus(COMPLETED);
                    order.setDateClose(LocalDate.now());
                    return order;
                })
                .map(orderRepository::saveAndFlush)
                .map(readMapper::map);
    }

    public List<OrderReadDto> findAllByStatusByUserId(String username) {
        User user = userRepository.findByEmail(username).orElseThrow();
        return orderRepository.findAllByStatusByUserId(user).stream()
                .map(readMapper::map)
                .toList();
    }
}
