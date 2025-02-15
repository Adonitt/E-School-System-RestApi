package org.example.schoolmanagementsystem.services.base_services;

@FunctionalInterface
public interface Removable<Tid> {
    public void removeById(Tid id);
}