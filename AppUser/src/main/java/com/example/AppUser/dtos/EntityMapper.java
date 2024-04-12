package com.example.AppUser.dtos;


import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class EntityMapper<E, D> {

    public D fromBasic(E entity, Class<D> dtoClass) {
        D dto = BeanUtils.instantiateClass(dtoClass);
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public E fromDTO(D dto, Class<E> entityClass) {
        E entity = BeanUtils.instantiateClass(entityClass);
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
