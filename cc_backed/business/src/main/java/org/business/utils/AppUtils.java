package org.business.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class AppUtils {
    public static <T> PageableResponse<T> buildPageableResponse(List<T> payloadData,
                                                                Page<?> paginationInfo) {
        PageableResponse<T> data = new PageableResponse<>();
        int pageSize = paginationInfo.getSize();
        int pageNumber = paginationInfo.getNumber();
        int totalPages = paginationInfo.getTotalPages();
        long totalElementCount = paginationInfo.getTotalElements();

        data.setCurrentPage(paginationInfo.getNumber());
        data.setNextPage(totalPages - 1 > pageNumber ? (pageNumber + 1) : pageNumber);
        data.setPageSize(pageSize);
        data.setTotalPageCount(totalPages);
        data.setPayload(payloadData);
        data.setTotalElementsCount(totalElementCount);

        return data;
    }

    public static Pageable enhancePageable(Pageable pageable) {
        if (Sort.unsorted().equals(pageable.getSort())) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, "id"));
        }
        return pageable;
    }
}
