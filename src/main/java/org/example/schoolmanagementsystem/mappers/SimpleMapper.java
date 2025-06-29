package org.example.schoolmanagementsystem.mappers;

import java.util.List;

public interface SimpleMapper <TEntity,TDto>{
    TDto toDto(TEntity entity);
    TEntity toEntity(TDto dto);
}
