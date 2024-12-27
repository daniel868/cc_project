package org.business.utils;

import org.business.pojo.ReservationDto;
import org.springframework.data.domain.Page;

import java.util.List;

public class AppUtils {
    public static <T> PageableResponse<T> buildPageableResponse(List<T> payloadData,
                                                                Page<?> paginationInfo) {
        PageableResponse<T> data = new PageableResponse<>();
        int pageSize = paginationInfo.getSize();
        int pageNumber = paginationInfo.getNumber();
        int totalPages = paginationInfo.getTotalPages();

        data.setCurrentPage(paginationInfo.getNumber());
        data.setNextPage(totalPages - 1 > pageNumber ? (pageNumber + 1) : pageNumber);
        data.setPageSize(pageSize);
        data.setTotalPageCount(totalPages);
        data.setPayload(payloadData);
        return data;
    }
}
