package com.tmac.mapper;

import com.tmac.entity.Function;

public class FunctionMapper {
    public static Function mapToModifiedFunction(final Function existFunction, final Function function) {
        existFunction.setName(function.getName());
        return existFunction;
    }
}
