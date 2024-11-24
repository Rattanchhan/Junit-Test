package com.spring_boot.caching.base;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;

@Setter
public class BaseListingRQ {

    private Integer page;
    private Integer size;

    private String query;
    private String sort;
    private String order;

    public Integer getSize() {
        if (size == null)
            return 20;
        return size;
    }

    public String getSort() {
        if (sort == null || sort.isEmpty() || sort.isBlank())
            return "id";
        return sort;
    }

    public String getOrder() {
        if (order == null || order.isEmpty() || order.isBlank() || !List.of("ASC", "DESC", "asc", "desc").contains(order))
            return "DESC";
        return order;
    }

    public Integer getPage() {
        if (page == null || page <= 0)
            return 0;
        return page - 1;
    }

    public Boolean hasQuery() {
        if (query == null || query.isEmpty())
            return Boolean.FALSE;
        return Boolean.TRUE;
    }

    public String getQuery() {
        if (query == null || query.isEmpty())
            return "ALL";
        return query;
    }

    public Pageable getPageable() {
        return PageRequest.of(this.getPage(), this.getSize());
    }

    public Pageable getPageable(String column) {
        Sort sort = Sort.by(Sort.Direction.ASC, column);
        return PageRequest.of(this.getPage(), this.getSize(), sort);
    }

    public Pageable getPageable(String column, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), column);
        return PageRequest.of(this.getPage(), this.getSize(), sort);
    }
}

