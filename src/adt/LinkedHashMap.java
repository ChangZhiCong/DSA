package adt;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author Cheong Wei Zhe
 * @author Chang Zhi Cong
 * @author Lee Wai Xian
 * @param <K>
 * @param <V>
 *
 */
public class LinkedHashMap<K, V> implements MapInterface<K, V> {

    private Entry<K, V>[] buckets;
    private static final int INITIAL_CAPACITY = 100;
    private static final double LOAD_FACTOR = 0.75;
    private int size = 0;

    private Entry<K, V> head, tail;

    private class Entry<K, V> implements MapEntryInterface<K, V> {

        private K key;
        private V value;
        private Entry<K, V> next;
        private Entry<K, V> prev, nextOrder;

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
                    existing.value = value;
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

        while (current != null) {
            if (current.key.equals(key)) {

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
