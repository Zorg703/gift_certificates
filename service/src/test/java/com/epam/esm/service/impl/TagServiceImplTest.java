package com.epam.esm.service.impl;

import com.epam.esm.domain.Tag;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.util.TagDTOConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class TagServiceImplTest {
    @Mock
    private TagDTO tagDTO;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    @Before
    public void setUp()  {
        MockitoAnnotations.initMocks(this);
        tagDTO=new TagDTO();
        tagDTO.setName("Super");
        tagDTO.setId(1);
    }

    @Test
    public void getAll() {
        List<TagDTO> list=new ArrayList<>();
        list.add(tagDTO);
        list.add(tagDTO);
        Optional<List<Tag>> tags=Optional.of(TagDTOConverter.mapAll(list,Tag.class));
        when(tagRepository.getAll(1,1)).thenReturn(tags);
        tagService.getAll(1, 1);
        verify(tagRepository).getAll(1,1);
    }

    @Test
    public void getAllNotFound() {
        when(tagRepository.getAll(1,1)).thenReturn(Optional.empty());
        tagService.getAll(1, 1);
        verify(tagRepository).getAll(1,1);
    }

    @Test
    public void getOne() {
        Optional<Tag> tag=Optional.of(TagDTOConverter.map(tagDTO,Tag.class));
        when(tagRepository.getOne(1)).thenReturn(tag);
        tagService.getOne(1);
        verify(tagRepository).getOne(1);
    }

    @Test
    public void getOneNotFound() {
        Optional<Tag> tag=Optional.empty();
        when(tagRepository.getOne(1)).thenReturn(tag);
        tagService.getOne(1);
        verify(tagRepository).getOne(1);
    }

//    @Test
//    public void delete(){
//        doNothing().when(tagRepository).delete(1);
//        tagService.delete(1);
//        verify(tagRepository).delete(1);
//    }

    @Test
    public void create(){
        Optional<Tag> tag=Optional.of(TagDTOConverter.map(tagDTO,Tag.class));
        doNothing().when(tagRepository).create(tag.get());
        tagService.create(tagDTO);
        verify(tagRepository).create(tag.get());
    }

    @Test(expected = ServiceException.class)
    public void createIncorrectInputData(){
        tagDTO.setName("");
        Optional<Tag> tag=Optional.of(TagDTOConverter.map(tagDTO,Tag.class));
        doNothing().when(tagRepository).create(tag.get());
        tagService.create(tagDTO);
        verify(tagRepository).create(tag.get());
    }

    @Test
    public void getTagByName() {
        Optional<Tag> tag=Optional.of(TagDTOConverter.map(tagDTO,Tag.class));
        when(tagRepository.getTagByName("Super")).thenReturn(tag);
        tagService.getTagByName("Super");
        verify(tagRepository).getTagByName("Super");
    }

    @Test
    public void getTagByNameNotFound() {
        when(tagRepository.getTagByName("asd")).thenReturn(Optional.empty());
        tagService.getTagByName("asd");
        verify(tagRepository).getTagByName("asd");
    }

    @Test
    public void getTopTags(){
        List<TagDTO> list=new ArrayList<>();
        list.add(tagDTO);
        list.add(tagDTO);
        Optional<List<Tag>> tags=Optional.of(TagDTOConverter.mapAll(list,Tag.class));
        when(tagRepository.getTopTag()).thenReturn(tags);
        tagService.getTopTags();
        verify(tagRepository).getTopTag();

    }

    @Test
    public void getTopTagsNotFound(){
        when(tagRepository.getTopTag()).thenReturn(Optional.empty());
        tagService.getTopTags();
        verify(tagRepository).getTopTag();

    }
}