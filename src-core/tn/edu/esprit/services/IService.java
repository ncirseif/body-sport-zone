package tn.edu.esprit.services;

import java.util.List;

public interface IService<T> {
    void ajouter(T t);
    void modifier(T t);
    void supprimer(int id);
    T getOne(T t);
    List<T> getAll(T t);
}
