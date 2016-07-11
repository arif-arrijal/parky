package edu.ejemplo.demo.repositorios;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Created by admin on 7/11/2016.
 */
public class SpringDataJPARepositoryUtils {

    public static PageRequest createSingleSortPageRequest(int page, int size) {
        return new PageRequest(page, size);
    }

    public static PageRequest createSingleSortPageRequest(int page, int size, Sort.Order order) {
        Sort s = new Sort(order);
        return new PageRequest(page, size, s);
    }

    public static PageRequest createSingleSortPageRequest(int page, int size, List<Sort.Order> orders) {
        Sort s = new Sort(orders);
        return new PageRequest(page, size, s);
    }

    public static int getPageIndexZeroBased(int displayStart, int displayLength) {
        return (displayStart / displayLength);
    }

}
