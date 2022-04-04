package edu.touro.cs.mcon364;

import org.junit.jupiter.api.*;

import java.io.*;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MainTest {
    static PriorityQueue<Integer> queue;

    @org.junit.jupiter.api.Test
    @Order(1)
    void create() {
        queue = new PriorityQueue<>(10);
        queue.enqueue(10, 10);

        assertEquals("[10]", queue.toString());
    }

    @org.junit.jupiter.api.Test
    @Order(2)
    void addSmaller() {
        queue.enqueue(8, 8);
        queue.enqueue(9, 8);
        queue.enqueue(10, 6);

        assertEquals("[10, 8, 9, 10]", queue.toString());
    }

    @org.junit.jupiter.api.Test
    @Order(3)
    void addLarger() {
        queue.enqueue(7, 9);
        queue.enqueue(0, 11);

        assertEquals("[0, 10, 7, 8, 9, 10]", queue.toString());
    }

    @org.junit.jupiter.api.Test
    @Order(4)
    void overflow() {
        queue.enqueue(5, 5);
        queue.enqueue(4, 4);
        queue.enqueue(3, 3);
        queue.enqueue(2, 2);

        assertEquals("[0, 10, 7, 8, 9, 10, 5, 4, 3, 2]", queue.toString());

        assertThrows(IllegalStateException.class, () -> queue.enqueue(1, 7));
        assertThrows(IllegalStateException.class, () -> queue.enqueue(0, 1));
    }

    @org.junit.jupiter.api.Test
    @Order(5)
    void serialization() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("tests/edu/touro/cs/mcon364/object.txt"));
             ObjectInputStream ois = new ObjectInputStream(
                     new FileInputStream("tests/edu/touro/cs/mcon364/object.txt"))) {
            oos.writeObject(queue);
            PriorityQueue<Integer> sameQueue = (PriorityQueue<Integer>) ois.readObject();
            assertEquals(queue, sameQueue);
            queue.dequeue();
            sameQueue.dequeue();
            queue.enqueue(1, 7);
            sameQueue.enqueue(1, 7);
            assertEquals(queue, sameQueue);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    @Order(6)
    void removal() {
        for (int i = 0; i < 10; i++) {
            int x = queue.dequeue();
            assert(x >= 0 && x < 11);
        }

        assertThrows(NoSuchElementException.class, () -> queue.dequeue());
    }
}