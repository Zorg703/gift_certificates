package com.epam.esm.service;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.dto.PageParamDTO;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag service.
 * Gets data from controller and pass then in to repository
 */
public interface TagService extends CRDService<TagDTO> {
    /**
     * Gets tag by name.
     *
     * @param name the name
     * @return the tag by name
     */
    TagDTO getTagByName(final String name);

    /**
     * Gets top tags.
     *
     * @return the top tags
     */
    List<UserDTO> getTopTags();


    List<TagDTO> getAll(PageParamDTO param);

}
