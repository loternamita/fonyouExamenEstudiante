package com.fonyou.pruebatecnica.examen.service;

public interface ModelMapperService {
    <D,E> D convertToDto(E entity, Class<D> dtoClass);

    <D,E> E convertToEntity(D dto, Class<E> entityClass);
}
