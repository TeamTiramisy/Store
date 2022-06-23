package com.dmdev.store.service;

import com.dmdev.store.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@IT
@RequiredArgsConstructor
class ImageServiceTest {

    private final ImageService imageService;

    @ParameterizedTest
    @ValueSource(strings = {"PHONE/iPhone13256.png", "PHONE/Huawei9.png","TV/SonyXR.png"})
    void getParamTest(String path){
        Optional<byte[]> bytes = imageService.get(path);
        assertTrue(bytes.isPresent());
    }

    @Test
    void getTest(){
        Optional<byte[]> bytes = imageService.get("PHONE/iPhone16256.png");
        assertFalse(bytes.isPresent());
    }

}