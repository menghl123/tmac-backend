package com.tmac.utils.jpa.model;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.security.InvalidParameterException;
import java.util.Arrays;

public class DynamicSpecification<T extends Object> implements Specification<T> {

    private final Class<T> thisClass;
    private final Filter[] filters;

    public DynamicSpecification(final Class<T> aClass, final Filter[] filters) {
        this.thisClass = aClass;
        this.filters = filters;
    }

    public Predicate toPredicate(final Root<T> root,
                                 final CriteriaQuery<?> criteriaQuery,
                                 final CriteriaBuilder criteriaBuilder) {
        return Arrays.stream(this.filters)
                .filter(Filter::isPresent)
                .map(filter -> this.createPredicate(root, criteriaBuilder, filter))
                .reduce(criteriaBuilder::and).orElse(null);
    }

    private Predicate createPredicate(final Root root,
                                      final CriteriaBuilder criteriaBuilder,
                                      final Filter filter) {
        Predicate newPredicate = null;
        if (filter.isPresent()) {
            final String compareType = filter.getCompareType();
            final String value = filter.getValue();
            final Path path = root.get(filter.getField());
            switch (compareType) {
                case "=":
                    newPredicate = criteriaBuilder.equal(path, value);
                    break;
                case "<":
                    newPredicate = criteriaBuilder.lessThan(path, value);
                    break;
                case "<=":
                    newPredicate = criteriaBuilder.lessThanOrEqualTo(path, value);
                    break;
                case ">":
                    newPredicate = criteriaBuilder.greaterThan(path, value);
                    break;
                case ">=":
                    newPredicate = criteriaBuilder.greaterThanOrEqualTo(path, value);
                    break;
                case "like":
                    newPredicate = criteriaBuilder.like(path, value);
                    break;
                default:
                    throw new InvalidParameterException("不支持的比较表达式：" + compareType);
            }
        }
        return newPredicate;
    }
}
