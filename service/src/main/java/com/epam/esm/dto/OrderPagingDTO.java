package com.epam.esm.dto;

import java.util.List;

public class OrderPagingDTO {
    private List<OrderDTO> list;
    private Integer size;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<OrderDTO> getList() {
        return list;
    }

    public void setList(List<OrderDTO> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "OrderPagingDTO{" +
                "list=" + list +
                ", size=" + size +
                '}';
    }
}
