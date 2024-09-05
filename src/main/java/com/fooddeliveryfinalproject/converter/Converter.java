package com.fooddeliveryfinalproject.converter;


import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface Converter<E, M> {
    // Converts model object to entity object
    E convertToEntity(M model, E entity);
    // Converts entity object to model object
    M convertToModel(E entity, M model);

    // Converts list of model objects to list of entity objects
    default List<E> convertToEntityList(List<M> models, Supplier<E> entitySupplier) {
        return models.stream()
                .map(model -> convertToEntity(model, entitySupplier.get()))
                .collect(Collectors.toList());
    }

    // Converts list of entity objects to list of model objects
    default List<M> convertToModelList(List<E> entities, Supplier<M> modelSupplier) {
        return entities.stream()
                .map(entity -> convertToModel(entity, modelSupplier.get()))
                .collect(Collectors.toList());
    }
}
