package org.business.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PageableResponse<T> {
    private List<T> payload;
    private int currentPage;
    private int totalPageCount;
    private int pageSize;
    private int nextPage;
    private long totalElementsCount;
}
