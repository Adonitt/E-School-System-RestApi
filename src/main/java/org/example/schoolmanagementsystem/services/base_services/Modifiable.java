package org.example.schoolmanagementsystem.services.base_services;

@FunctionalInterface
public interface Modifiable<T, Tid> {

    T modify(Tid id, T entity);
}
