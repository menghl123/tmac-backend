package com.tmac.repository;

import com.tmac.entity.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FunctionRepository extends PagingAndSortingRepository<Function, String> {
    Page<Function> findAllByIdContainingAndNameContaining(final String id, final String name, final Pageable pageable);

    Integer countById(final String id);

    Integer countByName(final String name);

    List<Function> findByName(final String name);
}
