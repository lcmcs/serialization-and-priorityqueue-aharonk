package edu.touro.cs.mcon364;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class PriorityQueue<T> implements Serializable {
    private LinkedList<ObjectWithPriority<T>> store;
    private int capacity;
    private static final long serialVersionUID = 42L;

    PriorityQueue(int maximumSize) {
        store = new LinkedList<>();
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
        if (store.size() == capacity)
            throw new IllegalStateException("The queue is already full.");

        boolean added = false;

        ListIterator<ObjectWithPriority<T>> it = store.listIterator();
        while (it.hasNext()) {
            if (it.next().PRIORITY < priority) {
                it.previous();
                it.add(new ObjectWithPriority<>(priority, item));
                added = true;
                break;
            }
        }

        if (!added) {
            store.add(new ObjectWithPriority<>(priority, item));
        }
    }

    /**
     * Retrieves the next item from the queue.
     *
     * @return the next element in the queue
     * @throws NoSuchElementException if the queue has no items in it
     */
    T dequeue() {
        if (store.isEmpty())
            throw new NoSuchElementException("The queue is empty.");

        return store.remove().ELEMENT;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(capacity);
        s.writeObject(store);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        capacity = s.readInt();
        store = (LinkedList<ObjectWithPriority<T>>) s.readObject();
    }

    @Override
    public String toString() {
        Iterator<ObjectWithPriority<T>> it = store.iterator();
        StringBuilder out = new StringBuilder("[");
        while (it.hasNext()) {
            out.append(it.next().ELEMENT.toString()).append(", ");
        }

        out.setLength(out.length() - 2);
        return out.append("]").toString();
    }

    private static class ObjectWithPriority<T> implements Serializable {
        public final int PRIORITY;
        public final T ELEMENT;

        public ObjectWithPriority(int i, T e) {
            PRIORITY = i;
            ELEMENT = e;
        }
    }
}
