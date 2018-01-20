package com.tmac.repository;

import com.tmac.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MenuRepository extends PagingAndSortingRepository<Menu, String> {
    List<Menu> findByName(final String name);

    Integer countByName(final String name);

    List<Menu> findByParentId(final String parentId);

    List<Menu> findById(final String id);

    Integer countById(final String id);

    List<Menu> findAllByParentIdAndFunctionIdInOrderBySortIdAsc(final String parentId, final List<String> functionIds);

    Page<Menu> findAllByParentIdContainingAndNameContainingAndUrlContaining(final String parentId,
                                                                            final String name,
                                                                            final String url, final Pageable pageable);
}
