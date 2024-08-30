/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

/**
 *
 * @author Cheong Wei Zhe
 * @param <T>
 */
public interface ListInterface<T> {
    void add(T newItem);
    T get(int index); 
    void remove(int index); 
    int size();
    boolean isEmpty(); 
}
