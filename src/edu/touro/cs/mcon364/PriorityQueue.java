package edu.touro.cs.mcon364;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class PriorityQueue<T> implements Serializable {
    private transient TreeMap<Integer, LinkedList<T>> store;
    private transient int capacity, size = 0, highestPriority = Integer.MIN_VALUE;
    private static final long serialVersionUID = 42L;

    PriorityQueue(int maximumSize) {
        store = new TreeMap<>();
        capacity = maximumSize;
    }

    /**
     * Adds an item to the back of its priority.
     *
     * @param item     the element to add
     * @param priority the element's priority
     * @throws IllegalStateException if the queue is at maximum size
     */
    void enqueue(T item, int priority) {
        if (size == capacity)
            throw new IllegalStateException("The queue is already full.");

        store.putIfAbsent(priority, new LinkedList<>());
        store.get(priority).add(item);
        highestPriority = Math.max(highestPriority, priority);
        size++;
    }

    /**
     * Retrieves the next item from the queue.
     *
     * @return the next element in the queue
     * @throws NoSuchElementException if the queue has no items in it
     */
    T dequeue() {
        if (size == 0)
            throw new NoSuchElementException("The queue is empty.");

        T el = store.get(highestPriority).remove();
        if (store.get(highestPriority).isEmpty()) {
            Integer i = store.lowerKey(highestPriority);
            highestPriority = i != null ? i : Integer.MIN_VALUE;
        }
        size--;

        return el;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(capacity);
        s.writeInt(store.keySet().size());
        for (Map.Entry<Integer, LinkedList<T>> q : store.entrySet()) {
            s.writeInt(q.getValue().size());
            for (T t : q.getValue()) {
                s.writeObject(t);
            }
            s.writeInt(q.getKey());
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();

        capacity = s.readInt();
        store = new TreeMap<>();
        size = 0;

        for (int i = 0; i < s.readInt(); i++) {
            LinkedList<T> queue = new LinkedList<>();
            for (int j = 0; j < s.readInt(); j++) {
                queue.add((T) s.readObject());
                size++;
            }
            store.put(s.readInt(), queue);
        }

        highestPriority = store.lastKey();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        for (LinkedList<T> q : store.descendingMap().values()) {
            for (T t : q) {
                s.append(t).append(", ");
            }
        }
        s.setLength(s.length() - 2);
        return s.append(']').toString();
    }
}
