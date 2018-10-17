package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.GiftCertificatePagingDTO;
import com.epam.esm.dto.PageParamDTO;

import java.util.List;

/**
 * The interface Gift certificate service.
 * Gets data from controller and pass then in to repository
 */
public interface GiftCertificateService extends CRDService<GiftCertificateDTO> {
    /**
     * Update.
     *
     * @param id              the id
     * @param giftCertificate the gift certificate
     */
    void update(final long id, final GiftCertificateDTO giftCertificate);


    /**
     * Gets gift certificate with parameters.
     *
     * @param text  the text
     * @param tags   the tags
     * @param sort  the sort
     * @param param the param
     * @return the gift certificate with parameters
     */


    GiftCertificatePagingDTO getGiftCertificatesWithConditions(List<String> tags, PageParamDTO param, String text, String sort);
    /**
     * Change price.
     *
     * @param id              the id
     * @param giftCertificate the gift certificate
     */
    void changePrice(long id, GiftCertificateDTO giftCertificate);


    List<GiftCertificateDTO> checkCertificatesList(List<GiftCertificateDTO> dtoList);
}
