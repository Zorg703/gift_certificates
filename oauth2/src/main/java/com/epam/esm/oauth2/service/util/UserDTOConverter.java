package com.epam.esm.oauth2.service.util;

import org.modelmapper.ModelMapper;


/**
 * The User dto converter.
 * Convert Tag into TagDTO and List<Tag>
 * * to  List<TagDTO> there and back again
 */
public class UserDTOConverter {

    private static ModelMapper modelMapper = new ModelMapper();

    public static <D, T> D map(T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }
}