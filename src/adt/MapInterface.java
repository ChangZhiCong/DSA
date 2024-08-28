/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package adt;

/**
 *
 * @author user
 * @param <K>
 * @param <V>
 */
public interface MapInterface<K, V>{

    void put(K key, V value);

    V get(K key);

    boolean remove(K key);

    boolean isEmpty();

    boolean containsKey(K key);

    boolean containsValue(V value);

    K[] keySet();
    
    V[] values();
        
//    void sortByAscending();
//
//    void sortByDescending();
    
    void clear();
    
    int getSize();
    
    Iterable<MapEntryInterface<K, V>> entrySet();
}
