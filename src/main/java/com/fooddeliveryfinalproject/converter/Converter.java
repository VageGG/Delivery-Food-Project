package com.fooddeliveryfinalproject.converter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface Converter<E, M> {
    // Converts model object to entity object
    E convertToEntity(M model, E entity);
    // Converts entity object to model object
    M convertToModel(E entity, M model);

    default List<M> convertToModelList(Iterable<E> entities, M model) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(entity -> convertToModel(entity, model))
                .collect(Collectors.toList());
    }
}
