/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adt;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedHashMap<K, V> implements MapInterface<K, V> {

    private Entry<K, V>[] buckets;
    private static final int INITIAL_CAPACITY = 100;
    private static final double LOAD_FACTOR = 0.75;
    private int size = 0;

    // Head and tail pointers for the linked list to maintain insertion order
    private Entry<K, V> head, tail;

    // Inner class Entry representing a key-value pair in the LinkedHashMap
    private class Entry<K, V> implements MapEntryInterface<K, V> {

        private K key;
        private V value;
        private Entry<K, V> next; // For hash bucket linked list
        private Entry<K, V> prev, nextOrder; // For insertion order linked list

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
            this.nextOrder = null;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }
    }

    public LinkedHashMap() {
        this.buckets = (Entry<K, V>[]) new Entry[INITIAL_CAPACITY];
        this.head = this.tail = null;
    }

    @Override
    public void put(K key, V value) {
        if ((double) size >= LOAD_FACTOR * buckets.length) {
            resize();
        }

        int bucketIndex = getBucketIndex(key);
        Entry<K, V> existing = buckets[bucketIndex];

        if (existing == null) {
            Entry<K, V> newEntry = new Entry<>(key, value);
            buckets[bucketIndex] = newEntry;
            addEntryToOrderList(newEntry);
            size++;
        } else {
            while (existing != null) {
              if (existing.key.equals(key)) {
                  existing.value = value; // Update the value if the key exists
                  return;
              }
                if (existing.next == null) {
                    Entry<K, V> newEntry = new Entry<>(key, value);
                    existing.next = newEntry;
                    addEntryToOrderList(newEntry);
                    size++;
                    break;
                }
                existing = existing.next;
            }
        }
    }

    @Override
    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        Entry<K, V> bucket = buckets[bucketIndex];
        while (bucket != null) {
            if (bucket.key.equals(key)) {
                return bucket.value;
            }
            bucket = bucket.next;
        }
        return null;
    }

    @Override
    public boolean remove(K key) {
        int bucketIndex = getBucketIndex(key);
        Entry<K, V> current = buckets[bucketIndex];
        Entry<K, V> previous = null;
        
        // Shift Forward after removing the entity
        while (current != null) {
            if (current.key.equals(key)) {
                
                // If the remove element is the first element inside the bucket
                if (previous == null) {
                    buckets[bucketIndex] = current.next;
                } else {
                    previous.next = current.next;
                }
                removeEntryFromOrderList(current);
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean containsKey(K key) {
        int bucketIndex = getBucketIndex(key);
        Entry<K, V> entry = buckets[bucketIndex];
        while (entry != null) {
            if (entry.key.equals(key)) {
                return true;
            }
            entry = entry.next;
        }
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        for (Entry<K, V> bucket : buckets) {
            Entry<K, V> entry = bucket;
            while (entry != null) {
                if (entry.value.equals(value)) {
                    return true;
                }
                entry = entry.next;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        buckets = (Entry<K, V>[]) new Entry[INITIAL_CAPACITY];
        head = tail = null;
        size = 0;
    }

    @Override
    public K[] keySet() {
        K[] keys = (K[]) new Object[size];
        int index = 0;
        Entry<K, V> current = head;
        while (current != null) {
            keys[index++] = current.key;
            current = current.nextOrder;
        }
        return keys;
    }

    @Override
    public V[] values() {
        V[] values = (V[]) new Object[size];
        int index = 0;
        Entry<K, V> current = head;
        while (current != null) {
            values[index++] = current.value;
            current = current.nextOrder;
        }
        return values;
    }

    @Override
    public Iterable<MapEntryInterface<K, V>> entrySet() {
        return () -> new LinkedHashMapIterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Entry<K, V> current = head;
        while (current != null) {
            sb.append(current.value).append("\n");
            current = current.nextOrder;
        }
        return sb.toString();
    }

    @Override
    public void sortByAscending() {
        if (head != null) {
            head = mergeSort(head, Comparator.comparing(entry -> entry.key.toString()));
            updateTail();
        }
    }

    @Override
    public void sortByDescending() {
        if (head != null) {
            head = mergeSort(head, Comparator.comparing(entry -> entry.key.toString(), Comparator.reverseOrder()));
            updateTail();
        }
    }

    private void addEntryToOrderList(Entry<K, V> newEntry) {
        if (head == null) {
            head = tail = newEntry;
        } else {
            tail.nextOrder = newEntry;
            newEntry.prev = tail;
            tail = newEntry;
        }
    }

    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode() % buckets.length);
    }

    private void resize() {
        Entry<K, V>[] oldBuckets = buckets;
        buckets = (Entry<K, V>[]) new Entry[oldBuckets.length * 2];
        head = tail = null;
        size = 0;

        for (Entry<K, V> bucket : oldBuckets) {
            Entry<K, V> entry = bucket;
            while (entry != null) {
                put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }

    private void removeEntryFromOrderList(Entry<K, V> entry) {
        if (entry.prev != null) {
            entry.prev.nextOrder = entry.nextOrder;
        } else {
            head = entry.nextOrder;
        }
        if (entry.nextOrder != null) {
            entry.nextOrder.prev = entry.prev;
        } else {
            tail = entry.prev;
        }
    }

    private Entry<K, V> mergeSort(Entry<K, V> head, Comparator<Entry<K, V>> comparator) {
        if (head == null || head.nextOrder == null) {
            return head;
        }

        Entry<K, V> middle = getMiddle(head);
        Entry<K, V> nextOfMiddle = middle.nextOrder;

        middle.nextOrder = null;

        Entry<K, V> left = mergeSort(head, comparator);
        Entry<K, V> right = mergeSort(nextOfMiddle, comparator);

        return sortedMerge(left, right, comparator);
    }

    private Entry<K, V> sortedMerge(Entry<K, V> left, Entry<K, V> right, Comparator<Entry<K, V>> comparator) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }

        Entry<K, V> result;
        if (comparator.compare(left, right) <= 0) {
            result = left;
            result.nextOrder = sortedMerge(left.nextOrder, right, comparator);
            if (result.nextOrder != null) {
                result.nextOrder.prev = result;
            }
        } else {
            result = right;
            result.nextOrder = sortedMerge(left, right.nextOrder, comparator);
            if (result.nextOrder != null) {
                result.nextOrder.prev = result;
            }
        }
        return result;
    }

    private Entry<K, V> getMiddle(Entry<K, V> head) {
        if (head == null) {
            return head;
        }

        Entry<K, V> slow = head, fast = head.nextOrder;
        while (fast != null && fast.nextOrder != null) {
            slow = slow.nextOrder;
            fast = fast.nextOrder.nextOrder;
        }
        return slow;
    }

    private void updateTail() {
        tail = head;
        while (tail != null && tail.nextOrder != null) {
            tail = tail.nextOrder;
        }
    }

    private class LinkedHashMapIterator implements Iterator<MapEntryInterface<K, V>> {

        private Entry<K, V> current;

        public LinkedHashMapIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public MapEntryInterface<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            MapEntryInterface<K, V> entry = current;
            current = current.nextOrder;
            return entry;
        }
    }
}