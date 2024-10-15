package com.fooddeliveryfinalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"pageable", "totalElements", "totalPages", "last", "size", "number", "sort", "numberOfElements", "first", "empty"})
public class PageDto<T> {
    private List<T> content;

    private int pageNo;

    private int pageSize;

    private long totalElements;

    private int totalPages;

    private boolean empty;

    public PageDto(Page<T> page) {
        this.content = page.getContent();
        this.pageNo = page.getTotalPages();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.empty = page.isEmpty();
    }
}
