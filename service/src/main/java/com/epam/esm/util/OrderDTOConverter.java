package com.epam.esm.util;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDTOConverter {
    private static ModelMapper modelMapper = new ModelMapper();

    /**
     * Map one entity.
     *
     * @param <D>      the type parameter
     * @param <T>      the type parameter
     * @param entity   the entity
     * @param outClass the out class
     * @return the d
     */
    public static <D, T> D map(T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    /**
     * Map all list entities.
     *
     * @param <D>        the type parameter
     * @param <T>        the type parameter
     * @param entityList the entity list
     * @param outCLass   the out c lass
     * @return the list
     */
    public static <D, T> List<D> mapAll(List<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }
}
