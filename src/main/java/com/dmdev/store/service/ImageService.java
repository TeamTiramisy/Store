package com.dmdev.store.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.*;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${app.image.bucket:C:/Users/37529/IdeaProjects/Store/image}")
    private final String path;

    @SneakyThrows
    public void upload(String image, InputStream content){
        Path fullImagePath = Path.of(path, image);

        try(content) {
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath, content.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        }
    }

    @SneakyThrows
    public Optional<byte[]> get(String imagePath) {
        Path imageFullPath = Path.of(path, imagePath);

        return Files.exists(imageFullPath)
                ? Optional.of(Files.readAllBytes(imageFullPath))
                : Optional.empty();
    }
}