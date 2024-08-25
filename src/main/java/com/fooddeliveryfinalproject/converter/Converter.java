package com.fooddeliveryfinalproject.converter;

public interface Converter<E, M> {
    // Converts model object to entity object
    E convertToEntity(M model, E entity);
    // Converts entity object to model object
    M convertToModel(E entity, M model);
}
