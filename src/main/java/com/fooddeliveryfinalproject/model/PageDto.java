package com.fooddeliveryfinalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties({"pageable", "totalElements", "totalPages", "last", "size", "number", "sort", "numberOfElements", "first", "empty"})
public class PageDto<T> {
    private List<T> content;

    private boolean empty;

    private int number;

    private int size;

    public PageDto(Page<T> page) {
        this.content = page.getContent();
        this.empty = page.isEmpty();
        this.number = page.getNumber();
        this.size = page.getSize();
    }
}
