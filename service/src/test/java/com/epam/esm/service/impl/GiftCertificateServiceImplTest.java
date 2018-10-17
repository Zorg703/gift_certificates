package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.util.GiftCertificateDTOConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GiftCertificateServiceImplTest {
    private GiftCertificateDTO giftCertificateDTO;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        giftCertificateDTO=new GiftCertificateDTO();
        giftCertificateDTO.setName("New certificate");
        giftCertificateDTO.setDuration(10);
        giftCertificateDTO.setPrice(new BigDecimal(10));
        Set<TagDTO> list=new HashSet<>();
        TagDTO tagDTO=new TagDTO();
        tagDTO.setName("TAG");
        list.add(tagDTO);
        giftCertificateDTO.setTags(list);
    }

    @Test
    public void getAll() {
        List<GiftCertificateDTO> giftCertificates=new ArrayList<>();
        giftCertificates.add(giftCertificateDTO);
        giftCertificates.add(giftCertificateDTO);
        giftCertificates.add(giftCertificateDTO);
        Optional<List<GiftCertificate>> list=Optional.of(GiftCertificateDTOConverter.mapAll(giftCertificates,GiftCertificate.class));
        when(giftCertificateRepository.getAll(1,1)).thenReturn(list);
        giftCertificateService.getAll(1,1);
        verify(giftCertificateRepository).getAll(1,1);
    }
    @Test
    public void getOne(){
        Optional<GiftCertificate> certificate=Optional.of(GiftCertificateDTOConverter.map(giftCertificateDTO,GiftCertificate.class));
        when(giftCertificateRepository.getOne(1)).thenReturn(certificate);
        giftCertificateService.getOne(1);
        verify(giftCertificateRepository).getOne(1);
        assertNotNull(giftCertificateService.getOne(1));

    }
    @Test
    public void delete(){
        doNothing().when(giftCertificateRepository).delete(1);
        giftCertificateService.delete(1);
        verify(giftCertificateRepository).delete(1);
    }

    @Test
    public void create(){
        Optional<GiftCertificate> certificate=Optional.of(GiftCertificateDTOConverter.map(giftCertificateDTO,GiftCertificate.class));
        doNothing().when(giftCertificateRepository).create(certificate.get());
        giftCertificateService.create(giftCertificateDTO);
        verify(giftCertificateRepository).create(certificate.get());
    }

    @Test
    public void  getGiftCertificatesWithConditions(){
        List<GiftCertificateDTO> giftCertificates=new ArrayList<>();
        giftCertificates.add(giftCertificateDTO);
        giftCertificates.add(giftCertificateDTO);
        giftCertificates.add(giftCertificateDTO);
        Optional<List<GiftCertificate>> list=Optional.of(GiftCertificateDTOConverter.mapAll(giftCertificates,GiftCertificate.class));
        when(giftCertificateRepository.getByParameters("","","",1,1)).thenReturn(list);
        giftCertificateService.getGiftCertificateWithParameters("","","",1,1);
        verify(giftCertificateRepository).getByParameters("","","",1,1);
    }

    @Test
    public void getGiftCertificatesByText(){
        List<GiftCertificateDTO> giftCertificates=new ArrayList<>();
        giftCertificates.add(giftCertificateDTO);
        giftCertificates.add(giftCertificateDTO);
        giftCertificates.add(giftCertificateDTO);
        Optional<List<GiftCertificate>> list=Optional.of(GiftCertificateDTOConverter.mapAll(giftCertificates,GiftCertificate.class));
        when(giftCertificateRepository.getByPartOfDescriptionOrName("",1,1)).thenReturn(list);
        giftCertificateService.getGiftCertificatesByText("",1,1);
        verify(giftCertificateRepository).getByPartOfDescriptionOrName("",1,1);
    }

    @Test
    public void getGiftCertificateWithParameters(){
        List<GiftCertificateDTO> giftCertificates=new ArrayList<>();
        giftCertificates.add(giftCertificateDTO);
        giftCertificates.add(giftCertificateDTO);
        giftCertificates.add(giftCertificateDTO);
        Optional<List<GiftCertificate>> list=Optional.of(GiftCertificateDTOConverter.mapAll(giftCertificates,GiftCertificate.class));
        when(giftCertificateRepository.getGiftCertificatesWithConditions(null,1,1, text)).thenReturn(list);
        giftCertificateService.getGiftCertificatesWithConditions(null,1,1);
        verify(giftCertificateRepository).getGiftCertificatesWithConditions(null,1,1, text);
    }

    @Test
    public void changePrice(){
        Optional<GiftCertificate> certificate=Optional.of(GiftCertificateDTOConverter.map(giftCertificateDTO,GiftCertificate.class));

        doNothing().when(giftCertificateRepository).update(certificate.get());
        giftCertificateService.changePrice(certificate.get().getId(),giftCertificateDTO);
        verify(giftCertificateRepository).update(certificate.get());
    }
}
