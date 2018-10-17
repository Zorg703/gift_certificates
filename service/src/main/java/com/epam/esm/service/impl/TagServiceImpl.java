package com.epam.esm.service.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.dto.PageParamDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.util.TagDTOConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;

/**
 * The type Tag implementation.
 */
@Service
@Transactional
public class TagServiceImpl implements TagService {
    private TagRepository repository;

    public TagServiceImpl(TagRepository repository) {
        this.repository = repository;
    }

    /**
     * Instantiates a new Tag service.
     */

    @Override
    public void create(final TagDTO tagDTO) {
        Tag tag = TagDTOConverter.map(tagDTO, Tag.class);
        repository.create(tag);
    }


    @Override
    public TagDTO getOne(final long id) {
        Optional<Tag> tag = repository.getOne(id);
        return tag.map(tag1 -> TagDTOConverter.map(tag1, TagDTO.class)).orElse(null);
    }

    @Override
    public void delete(final long id) {
        getOne(id);
        repository.delete(id);
    }

    @Override
    public TagDTO getTagByName(final String name) {
        Optional<List<Tag>> tag = repository.getTagByName(name);
        return tag.isPresent() ? TagDTOConverter.map(tag.get().get(0), TagDTO.class) : null;
    }

    @Override
    public List<UserDTO> getTopTags() {
        Optional<List<Object[]>> optionalTags = repository.getTopTag();
        Map<String, List<TagDTO>> map = new HashMap<>();
        List<UserDTO> list = new ArrayList<>();
        if (optionalTags.isPresent()) {
            optionalTags.get().forEach((o) -> {
                String name = (String) o[2];
                TagDTO tagDTO = new TagDTO();
                tagDTO.setId(((BigInteger) o[1]).longValue());
                tagDTO.setName(o[0].toString());
                if (map.get(name) != null) {
                    map.get(name).add(tagDTO);
                } else {
                    List<TagDTO> tags = new ArrayList<>();
                    tags.add(tagDTO);
                    map.put(name, tags);
                }
            });
            map.forEach((key, value) -> {
                UserDTO userDTO = new UserDTO(key, value);
                list.add(userDTO);
            });
        }
        return list;
    }

    @Override
    public List<TagDTO> getAll(PageParamDTO param) {
        Optional<List<Tag>> list = repository.getAll(param.getPage(), param.getSize());
        List<TagDTO> dtoList = new ArrayList<>();
        return list.isPresent() ? TagDTOConverter.mapAll(list.get(), TagDTO.class) : dtoList;
    }
}
