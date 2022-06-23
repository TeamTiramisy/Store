package com.dmdev.store.mapper;

public interface Mapper <F, T>{

    T map(F object);
}
