package com.epam.esm.repository;

import com.epam.esm.domain.GiftCertificate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Gift certificate repository.
 */
public interface GiftCertificateRepository extends Repository<GiftCertificate> {
    /**
     * Update.
     *
     * @param giftCertificate the gift certificate
     */
    void update(GiftCertificate giftCertificate);

    Map<Integer, Optional<List<GiftCertificate>>> getGiftCertificatesByTextAndTags(List<String> tags, Integer page, Integer size, String text, String sort);
}
