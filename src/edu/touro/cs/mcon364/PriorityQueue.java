package edu.touro.cs.mcon364;

import java.util.*;

public class PriorityQueue<T> {
    private final LinkedList<ObjectWithPriority<T>> STORE;
    private final int CAPACITY;

    PriorityQueue(int maximumSize) {
        STORE = new LinkedList<>();
        CAPACITY = maximumSize;
    }

    /**
     * Adds an item to the back of its priority.
     * @param item the element to add
     * @param priority the element's priority
     * @throws IllegalStateException if the queue is at maximum size
     */
    void enqueue(T item, int priority) {
        if (STORE.size() == CAPACITY)
            throw new IllegalStateException("The queue is already full.");

        boolean added = false;

        ListIterator<ObjectWithPriority<T>> it = STORE.listIterator();
        while (it.hasNext()) {
            if (it.next().PRIORITY < priority) {
                it.previous();
                it.add(new ObjectWithPriority<>(priority, item));
                added = true;
                break;
            }
        }

        if (!added) {
            STORE.add(new ObjectWithPriority<>(priority, item));
        }
    }

    /**
     * Retrieves the next item from the queue.
     * @return the next element in the queue
     * @throws NoSuchElementException if the queue has no items in it
     */
    T dequeue() {
        if (STORE.isEmpty())
            throw new NoSuchElementException("The queue is empty.");

        return STORE.remove().ELEMENT;
    }

    @Override
    public String toString() {
        Iterator<ObjectWithPriority<T>> it = STORE.iterator();
        StringBuilder out = new StringBuilder("[");
        while (it.hasNext()) {
            out.append(it.next().ELEMENT.toString()).append(", ");
        }

        out.setLength(out.length()-2);
        return out.append("]").toString();
    }

    private static class ObjectWithPriority<T> {
        public final int PRIORITY;
        public final T ELEMENT;

        public ObjectWithPriority(int i, T e) {
            PRIORITY = i;
            ELEMENT = e;
        }
    }
}
