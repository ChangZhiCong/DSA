package adt;

/**
 *
 * @author Cheong Wei Zhe
 * @param <T>
 */
public class ArrayList<T> implements ListInterface<T> {

    private Object[] array;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;

    public ArrayList() {
        array = new Object[DEFAULT_CAPACITY];
    }

    private boolean isFull() {
        return size == array.length;
    }

    private void doubleArray() {
        if (isFull()) {
            int newSize = array.length * 2;
            Object[] newArray = new Object[newSize];
            // Copy elements to the new array
            for (int i = 0; i < array.length; i++) {
                newArray[i] = array[i];
            }
            array = newArray;  // Assign the new array back to the original array reference
        }
    }

    @Override
    public void add(T item) {
        doubleArray();  // Ensure there is space before adding
        array[size++] = item;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) array[index];
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(array, index + 1, array, index, numMoved);
        }
        array[--size] = null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
