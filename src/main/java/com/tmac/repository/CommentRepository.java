package com.tmac.repository;

import com.tmac.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CommentRepository extends PagingAndSortingRepository<Comment, String> {
    Page<Comment> findAllByDestId(final String destId, final Pageable pageable);

    Optional<Comment> findTopByDestIdOrderByFloorDesc(final String destId);

    Integer countByDestId(final String destId);

    Integer countByCreater(final String creater);
}
