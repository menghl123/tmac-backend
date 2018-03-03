package com.tmac.dynamic;

import com.tmac.dynamic.model.DynamicSpecification;
import com.tmac.dynamic.model.QueryParameters;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public interface DynamicRepository{
//public interface DynamicRepository<T extends Object, TIdType extends Serializable> extends JpaRepository<T, TIdType>, JpaSpecificationExecutor<T> {
//
//    default Page<T> findAll(QueryParameters queryParameters, Class<T> aClass) {
//        DynamicSpecification<T> spec = new DynamicSpecification<>(aClass, queryParameters.getFilters());
//
//        Sort sort = null;
//        if (queryParameters.getSorts() != null && queryParameters.getSorts().length > 0) {
//            List<Sort.Order> orders = new ArrayList<>();
//            for (com.tmac.dynamic.model.Sort s : queryParameters.getSorts()) {
//                orders.add(new Sort.Order("DESC".equalsIgnoreCase(s.getOrder()) ? Sort.Direction.DESC : Sort.Direction.ASC,
//                        s.getField()));
//            }
//            sort = new Sort(orders);
//        }
//        Pageable pageable = new PageRequest(queryParameters.getPageIndex(),
//                queryParameters.getPageSize(),
//                sort);
//
//        return this.findAll(spec, pageable);
//    }
}
