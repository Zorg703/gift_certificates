package com.epam.esm.dto;

import java.util.List;

public class GiftCertificatePagingDTO {
    private Long size;
    private List<GiftCertificateDTO> list;

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public List<GiftCertificateDTO> getList() {
        return list;
    }

    public void setList(List<GiftCertificateDTO> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "GiftCertificatePagingDTO{" +
                "size=" + size +
                ", list=" + list +
                '}';
    }
}
