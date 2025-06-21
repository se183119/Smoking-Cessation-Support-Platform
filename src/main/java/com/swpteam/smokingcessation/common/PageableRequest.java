package com.swpteam.smokingcessation.common;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageableRequest {

    @Min(value = 0, message = "PAGE_NO_MIN")
    Integer page;

    @Min(value = 1, message = "PAGE_SIZE_MIN")
    @Max(value = 100, message = "PAGE_SIZE_MAX")
    Integer size;

    Sort.Direction direction = Sort.Direction.ASC;

    String sortBy = "id";

    public static Pageable getPageable(PageableRequest request) {
        int page = Objects.nonNull(request.getPage()) ? request.getPage() : 0;
        int size = Objects.nonNull(request.getSize()) ? request.getSize() : 20;
        Sort.Direction direction = Objects.nonNull(request.getDirection()) ? request.getDirection() : Sort.Direction.ASC;
        String sortBy = Objects.nonNull(request.getSortBy()) ? request.getSortBy() : "id";

        return PageRequest.of(page, size, direction, sortBy);
    }
}
