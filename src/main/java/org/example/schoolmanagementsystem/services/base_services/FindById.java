package org.example.schoolmanagementsystem.services.base_services;

@FunctionalInterface
public interface FindById<T,Tid> {
    T findById(Tid id);
}
