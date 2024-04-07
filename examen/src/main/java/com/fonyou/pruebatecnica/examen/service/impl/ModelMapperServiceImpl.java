package com.fonyou.pruebatecnica.examen.service.impl;

import com.fonyou.pruebatecnica.examen.config.ModelMapperConfig;
import com.fonyou.pruebatecnica.examen.service.ModelMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelMapperServiceImpl implements ModelMapperService {

    private final ModelMapperConfig modelMapper;

    public ModelMapperServiceImpl(ModelMapperConfig modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public <D, E> D convertToDto(E entity, Class<D> dtoClass) {
        return modelMapper.modelMapper().map(entity, dtoClass);
    }

    @Override
    public <D, E> E convertToEntity(D dto, Class<E> entityClass) {
        return modelMapper.modelMapper().map(dto, entityClass);
    }
}
