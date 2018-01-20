package com.tmac.mapper;

import com.tmac.utils.ValidatorUtils;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.stream.Stream;

public class BaseMapper {
    private final MapperFactory mapperFactory;

    protected BaseMapper() {
        this.mapperFactory = new DefaultMapperFactory.Builder().build();
    }

    protected void register(Class<?> typeA, Class<?> typeB) {
        mapperFactory.classMap(typeA, typeB)
                .byDefault()
                .register();
    }

    public <T> T mapLeft(Class<T> targetType, Object... objects) {
        Assert.notNull(targetType, "targetType must not be null");
        ValidatorUtils.rejectIfArrayEmpty(objects, "array is empty");
        final T target = mapperFactory.getMapperFacade().map(objects[0], targetType);
        return mapLeft(target, Arrays.stream(objects).skip(1));
    }

    public <T> T mapLeft(Type<T> targetType, Object... objects) {
        Assert.notNull(targetType, "targetType must not be null");
        ValidatorUtils.rejectIfArrayEmpty(objects, "array is empty");
        final T target = mapperFactory.getMapperFacade()
                .newObject(objects[0], targetType, new MappingContext.Factory().getContext());
        return mapLeft(target, Arrays.stream(objects));
    }

    public <T> T mapLeft(T target, Object... objects) {
        Assert.notNull(target, "targetType must not be null");
        ValidatorUtils.rejectIfArrayEmpty(objects, "array is empty");
        return mapLeft(target, Arrays.stream(objects));
    }

    private <T> T mapLeft(T target, final Stream<Object> stream) {
        stream.forEachOrdered(source -> mapperFactory.getMapperFacade().map(source, target));
        return target;
    }

    public <S, D> D map(S sourceObject, D destinationObject) {
        final MapperFacade mapperFacade = mapperFactory.getMapperFacade();
        mapperFacade.map(sourceObject, destinationObject);
        return destinationObject;
    }

    protected MapperFactory getMapperFactory() {
        return mapperFactory;
    }

}
