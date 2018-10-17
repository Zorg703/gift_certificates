package com.epam.esm.service.impl;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.GiftCertificateState;
import com.epam.esm.domain.Tag;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.GiftCertificatePagingDTO;
import com.epam.esm.dto.PageParamDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.GiftCertificateDTOConverter;
import com.epam.esm.util.TagDTOConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * The Gift certificate service implementation.
 */
@Transactional
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private GiftCertificateRepository repository;
    private TagRepository tagRepository;

    /**
     * Instantiates a new Gift certificate service.
     */
    public GiftCertificateServiceImpl(GiftCertificateRepository repository, TagRepository tagRepository) {
        this.repository = repository;
        this.tagRepository = tagRepository;
    }

    @Override
    public void update(final long id, final GiftCertificateDTO giftCertificate) {
        GiftCertificateDTO certificateDB = getOne(id);
        if (certificateDB != null) {
            giftCertificate.setId(id);
            Set<TagDTO> dtoList = giftCertificate.getTags();
            giftCertificate.setTags(checkCertificateList(dtoList));
            GiftCertificate certificate = GiftCertificateDTOConverter.map(giftCertificate, GiftCertificate.class);
            repository.update(certificate);
        }
        else{
            throw new ServiceException("Object not found");
        }
    }

    @Override
    public void create(final GiftCertificateDTO giftCertificate) {
        Set<TagDTO> dtoList = giftCertificate.getTags();
        if (dtoList != null && !dtoList.isEmpty()) {
            giftCertificate.setTags(checkCertificateList(dtoList));
        }
        GiftCertificate certificate = GiftCertificateDTOConverter.map(giftCertificate, GiftCertificate.class);
        certificate.setState(GiftCertificateState.INACTIVE);
        repository.create(certificate);
    }


    @Override
    public List<GiftCertificateDTO> getAll(PageParamDTO param) {
        Optional<List<GiftCertificate>> certificates = repository.getAll(param.getPage(), param.getSize());
        List<GiftCertificateDTO> dtoList = new ArrayList<>();
        return certificates.map(giftCertificates -> GiftCertificateDTOConverter.mapAll(giftCertificates, GiftCertificateDTO.class)).orElse(dtoList);
    }


    @Override
    public GiftCertificateDTO getOne(final long id) {
        Optional<GiftCertificate> certificate = repository.getOne(id);
        return certificate.map(giftCertificate -> GiftCertificateDTOConverter.map(giftCertificate, GiftCertificateDTO.class)).orElse(null);
    }


    @Override
    public void delete(final long id) {
        repository.delete(id);
    }

    @Override
    public GiftCertificatePagingDTO getGiftCertificatesWithConditions(List<String> tags, PageParamDTO param, String text, String sort) {
        Map<Integer, Optional<List<GiftCertificate>>> map = repository.getGiftCertificatesByTextAndTags(tags, param.getPage(), param.getSize(), text,sort);
        Integer size=null;
        GiftCertificatePagingDTO giftCertificatePagingDTO = new GiftCertificatePagingDTO();
        Optional<List<GiftCertificate>> certificates = Optional.empty();
        for (Map.Entry<Integer, Optional<List<GiftCertificate>>> entry : map.entrySet()) {
            size = entry.getKey();
            certificates = entry.getValue();
        }
        List<GiftCertificateDTO> dtoList = new ArrayList<>();
        if (certificates.isPresent()){
            dtoList = GiftCertificateDTOConverter.mapAll(certificates.get(), GiftCertificateDTO.class);
        }
        giftCertificatePagingDTO.setList(dtoList);
        giftCertificatePagingDTO.setSize(Long.valueOf(size));
        return giftCertificatePagingDTO;
    }


    @Override
    public void changePrice(long id, GiftCertificateDTO giftCertificate) {
        Optional<GiftCertificate> certificate = repository.getOne(id);
        if (certificate.isPresent()) {
            GiftCertificate current = certificate.get();
            if (giftCertificate.getPrice().doubleValue() > 0) {
                current.setPrice(giftCertificate.getPrice());
                repository.update(current);
            } else {
                throw new ServiceException("Incorrect price");
            }
        }
    }

    @Override
    public List<GiftCertificateDTO> checkCertificatesList(List<GiftCertificateDTO> dtoList) {
        List<GiftCertificateDTO> list=new ArrayList<>();
        for (GiftCertificateDTO certificate:dtoList) {
            if(getOne(certificate.getId())!=null){
                list.add(certificate);
            }
        }
        return list;
    }

    private Set<TagDTO> checkCertificateList(Set<TagDTO> set) {
        Set<TagDTO> dtoSet = new HashSet<>();
        for (TagDTO tagDto : set) {
            Tag tag = TagDTOConverter.map(tagDto, Tag.class);
            Optional<List<Tag>> tagd = tagRepository.getTagByName(tag.getName());
            if (tagd.isPresent()) {
                tagDto = TagDTOConverter.map(tagd.get().get(0), TagDTO.class);
            } else {
                tagDto = TagDTOConverter.map(tag, TagDTO.class);
            }
            dtoSet.add(tagDto);
        }
        return dtoSet;
    }
}
