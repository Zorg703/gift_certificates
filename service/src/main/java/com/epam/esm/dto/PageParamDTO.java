package com.epam.esm.dto;

import javax.validation.constraints.Min;
import java.util.Objects;


/**
 * The type Page param dto.
 */
public class PageParamDTO {
    @Min(1)
    private Integer size;
    @Min(1)
    private Integer page;

    /**
     * Gets size.
     *
     * @return the size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * Sets size.
     *
     * @param size the size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * Gets page.
     *
     * @return the page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * Sets page.
     *
     * @param page the page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageParamDTO that = (PageParamDTO) o;
        return Objects.equals(size, that.size) &&
                Objects.equals(page, that.page);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, page);
    }

    @Override
    public String toString() {
        return "PageParamDTO{" +
                "size=" + size +
                ", page=" + page +
                '}';
    }
}
