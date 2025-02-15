package org.example.schoolmanagementsystem.services.base_services;

@FunctionalInterface
public interface Modifiable<T, Tid> {

    void modify(Tid id, T entity);
}
