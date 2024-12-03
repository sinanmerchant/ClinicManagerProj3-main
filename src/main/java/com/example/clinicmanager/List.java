package com.example.clinicmanager;

import java.util.Iterator;

/**
 * A generic List class that dynamically grows and manages objects of any type.
 * 
 * @author Sinan Merchant + Varun Bondugula
 *
 * @param <E> The type of objects that this list holds.
 */
public class List<E> implements Iterable<E> {
    private E[] objects;
    private int size; // Number of elements in the list
    private static final int INITIAL_CAPACITY = 4;
    private static final int INCREASE_SIZE = 4;
    private static final int NOT_FOUND = -1;

    /**
     * Default constructor that initializes the list with an initial capacity of 4.
     */
    @SuppressWarnings("unchecked")
    public List() {
        objects = (E[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Finds the index of a specific element in the list.
     *
     * @param e The element to search for.
     * @return The index of the element if found, or -1 if not found.
     */
    private int find(E e) {
        for (int i = 0; i < size; i++) {
            if (objects[i].equals(e)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Grows the list when it reaches full capacity.
     */
    @SuppressWarnings("unchecked")
    private void grow() {
        E[] newObjects = (E[]) new Object[objects.length + INCREASE_SIZE];
        for (int i = 0; i < size; i++) {
            newObjects[i] = objects[i];
        }
        objects = newObjects;
    }

    /**
     * Checks if the list contains a specific element.
     *
     * @param e The element to search for.
     * @return True if the element is found, false otherwise.
     */
    public boolean contains(E e) {
        return find(e) != NOT_FOUND;
    }

    /**
     * Adds a new element to the list and grows the list if needed.
     *
     * @param e The element to be added.
     */
    public void add(E e) {
        if (size == objects.length) {
            grow();
        }
        objects[size] = e;
        size++;
    }

    /**
     * Removes a specified element from the list and shifts the remaining elements.
     *
     * @param e The element to be removed.
     */
    public void remove(E e) {
        int index = find(e);
        if (index != NOT_FOUND) {
            for (int i = index; i < size - 1; i++) {
                objects[i] = objects[i + 1];
            }
            objects[size - 1] = null;
            size--;
        }
    }

    /**
     * Checks if the list is empty.
     *
     * @return True if the list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the current size of the list.
     *
     * @return The number of elements in the list.
     */
    public int size() {
        return size;
    }

    /**
     * Gets the element at the specified index.
     *
     * @param index The index of the element.
     * @return The element at the specified index.
     */
    public E get(int index) {
        if (index >= 0 && index < size) {
            return objects[index];
        }
        throw new IndexOutOfBoundsException("Index out of bounds");
    }

    /**
     * Sets the element at the specified index.
     *
     * @param index The index where the element will be placed.
     * @param e The element to set.
     */
    public void set(int index, E e) {
        if (index >= 0 && index < size) {
            objects[index] = e;
        } else {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    /**
     * Returns the index of a specified element.
     *
     * @param e The element to search for.
     * @return The index of the element if found, or -1 if not found.
     */
    public int indexOf(E e) {
        return find(e);
    }

    /**
     * Returns an iterator to iterate through the list.
     *
     * @return An iterator for the list.
     */
    @Override
    public Iterator<E> iterator() {
        return new ListIterator();
    }

    /**
     * Private class implementing the Iterator interface for iterating over the list.
     */
    private class ListIterator implements Iterator<E> {
        private int current = 0;

        /**
         * Checks if there is a next element in the list.
         *
         * @return {@code true} if there is a next element, {@code false} otherwise.
         */
        @Override
        public boolean hasNext() {
            return current < size;
        }

        /**
         * Returns the next element in the list.
         *
         * @return The next element in the list.
         * @throws RuntimeException if there are no more elements to return.
         */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new RuntimeException("No more elements");
            }
            return objects[current++];
        }
    }
}