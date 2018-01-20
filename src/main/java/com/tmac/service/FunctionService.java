package com.tmac.service;

import com.tmac.entity.Function;
import com.tmac.mapper.FunctionMapper;
import com.tmac.repository.FunctionRepository;
import com.tmac.validator.FunctionValidator;
import com.tmac.vo.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Objects;

@Service
public class FunctionService {
    private final FunctionRepository functionRepository;
    private final FunctionValidator functionValidator;

    @Autowired
    public FunctionService(final FunctionRepository functionRepository,
                           final FunctionValidator functionValidator) {
        this.functionRepository = functionRepository;
        this.functionValidator = functionValidator;
    }

    public Page<Function> selectList(final String id,
                                     final String name,
                                     final Integer page, final Integer size) {
        return this.functionRepository
                .findAllByIdContainingAndNameContaining(
                        id,
                        name,
                        new PageRequest(page, size, new Sort("id"))
                );
    }

    public Function save(final Function function) {
        this.functionValidator.validateBeforeSave(function);
        return this.functionRepository.save(function);
    }

    @Modifying
    @Transactional
    public Function modify(final Function function) {
        this.functionValidator.validateBeforeModify(function);
        final Function existFunction = this.functionRepository.findOne(function.getId());
        return this.functionRepository.save(FunctionMapper.mapToModifiedFunction(existFunction, function));
    }

    @Transactional
    public void delete(final String[] ids) {
        Arrays.stream(ids)
                .forEach(functionRepository::delete);
    }

    public Function get(final String id) {
        return this.functionRepository.findOne(id);
    }

    public ValidationResponse idUnique(final String id) {
        return new ValidationResponse(Objects.equals(this.functionRepository.countById(id), 0));
    }

    public ValidationResponse nameUnique(final String name) {
        return new ValidationResponse(Objects.equals(this.functionRepository.countByName(name), 0));
    }
}
