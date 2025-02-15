package org.example.schoolmanagementsystem.mappers;

import java.util.List;

public interface SimpleMapper <TEntity,TDto>{
    TDto toDto(TEntity entity);
    TEntity toEntity(TDto dto);
    List<TEntity> toEntityList(List<TDto> dtos);
    List<TDto> toDtoList(List<TEntity> entities);
}
