package com.dmdev.store.service;

import com.dmdev.store.database.entity.Category;
import com.dmdev.store.database.entity.Technic;
import com.dmdev.store.database.repository.TechnicRepository;
import com.dmdev.store.dto.TechnicCreateDto;
import com.dmdev.store.dto.TechnicReadDto;
import com.dmdev.store.mapper.TechnicCreateMapper;
import com.dmdev.store.mapper.TechnicReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TechnicService {

    private final TechnicRepository technicRepository;
    private final TechnicReadMapper mapper;
    private final ImageService imageService;
    private final TechnicCreateMapper createMapper;

    public Set<String> findAllCategory() {
        return technicRepository.findAll().stream()
                .map(Technic::getCategory)
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    public List<TechnicReadDto> findAllByCategory(Category value) {
        return technicRepository.findByCategory(value).stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    public Optional<TechnicReadDto> findById(Long id) {
        return technicRepository.findById(id)
                .map(mapper::map);
    }

    public List<TechnicReadDto> findByNameContainingIgnoreCase(String name) {
        return technicRepository.findByNameContainingIgnoreCase(name).stream()
                .map(mapper::map)
                .toList();
    }

    @Transactional
    public TechnicReadDto create(TechnicCreateDto technicCreateDto) {
        return Optional.of(technicCreateDto)
                .map(dto -> {
                    uploadImage(dto.getImage(), technicCreateDto.getCategory().name());
                    return createMapper.map(dto);
                })
                .map(technicRepository::save)
                .map(mapper::map)
                .orElseThrow();
    }

    public Optional<byte[]> findAvatar(Long id) {
        return technicRepository.findById(id)
                .map(Technic::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image, String directory) {
        if (!image.isEmpty()){
            imageService.upload(directory + "/" + image.getOriginalFilename(), image.getInputStream());
        }
    }
}
