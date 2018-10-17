package com.epam.esm.repository;

import com.epam.esm.domain.Tag;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag repository.
 */
public interface TagRepository extends Repository<Tag> {
    /**
     * Gets tag by name.
     *
     * @param name the name
     * @return the tag by name
     */
    Optional<List<Tag>> getTagByName(String name);

    /**
     * Gets top tag.
     *
     * @return the top tag
     */
    Optional<List<Object[]>> getTopTag();
}
