package com.dmdev.store.service;

import com.dmdev.store.database.entity.Basket;
import com.dmdev.store.database.entity.Technic;
import com.dmdev.store.database.entity.User;
import com.dmdev.store.database.repository.BasketRepository;
import com.dmdev.store.database.repository.TechnicRepository;
import com.dmdev.store.database.repository.UserRepository;
import com.dmdev.store.dto.BasketCreateDto;
import com.dmdev.store.dto.BasketReadDto;
import com.dmdev.store.dto.TechnicReadDto;
import com.dmdev.store.dto.UserReadDto;
import com.dmdev.store.mapper.BasketCreateMapper;
import com.dmdev.store.mapper.BasketReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasketService {

    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final TechnicRepository technicRepository;
    private final BasketCreateMapper mapper;
    private final BasketReadMapper readMapper;
    private final UserService userService;
    private final TechnicService technicService;

    public List<TechnicReadDto> findByUserIdBasket(String email){
        UserReadDto userReadDto = userService.findByEmail(email).orElseThrow();
        return technicService.findByUserIdBasket(userReadDto.getId());

    }

    @Transactional
    public Optional<BasketReadDto> create(Long id, String email){
        UserReadDto userReadDto = userService.findByEmail(email).orElseThrow();
        Optional<BasketReadDto> maybeBasket = findByUserAndTechnic(userReadDto.getId(), id);

        if (maybeBasket.isEmpty()) {
            BasketCreateDto basketCreateDto = BasketCreateDto.builder()
                    .usersId(userReadDto.getId())
                    .technicId(id)
                    .build();

            return Optional.of(basketCreateDto)
                    .map(mapper::map)
                    .map(basketRepository::save)
                    .map(readMapper::map);
        } else {
            return Optional.empty();
        }
    }

    public Optional<BasketReadDto> findByUserAndTechnic(Long userId, Long technicId){
        Optional<User> maybeUser = userRepository.findById(userId);
        Optional<Technic> maybeTechnic = technicRepository.findById(technicId);

        if (maybeUser.isPresent() && maybeTechnic.isPresent()){
            return basketRepository.findByUserAndTechnic(maybeUser.orElseThrow(), maybeTechnic.orElseThrow())
                    .map(readMapper::map);
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public boolean delete(String username, Long id) {
        User user = userRepository.findByEmail(username).orElseThrow();
        Technic technic = technicRepository.findById(id).orElseThrow();

        return basketRepository.findByUserAndTechnic(user, technic)
                .map(basket -> {
                    basketRepository.delete(basket);
                    basketRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
