package org.example.schoolmanagementsystem.services.base_services;

@FunctionalInterface
public interface Addable<T> {
    T add(T entity);
}
