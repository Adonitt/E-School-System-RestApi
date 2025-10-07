package org.example.schoolmanagementsystem.mappers;

public interface SimpleMapper<TEntity, TDto> {
    TDto toDto(TEntity entity);

    TEntity toEntity(TDto dto);
}
