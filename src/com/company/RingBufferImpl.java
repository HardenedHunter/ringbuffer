package com.company;

import java.lang.reflect.Array;
import java.util.Iterator;

public class RingBufferImpl<E> implements RingBuffer<E> {

    // Array containing buffer data.
    private final E[] data;

    // Max size of the buffer.
    private final int size;

    // Number of elements that are currently in the buffer.
    private int count;

    // Index of the oldest element.
    private int head;

    @Override
    public int getSize() {
        return size;
    }

    public int getCount() {
        return count;
    }

    @SuppressWarnings("unchecked")
    RingBufferImpl(Class<E> c, int size) {
        if (size <= 0)
            throw new IllegalArgumentException("size <= 0");
        this.size = size;
        this.data = (E[]) Array.newInstance(c, size);
        this.head = 0;
        this.count = 0;
    }

    @Override
    public E poll() {
        if (count > 0) {
            E value = data[head];
            head = (head + 1) % size;
            count--;
            return value;
        }
        return null;
    }

    @Override
    public E peek() {
        if (count > 0)
            return data[head];
        return null;
    }

    @Override
    public void add(E item) {
        int tail = (head + count) % size;
        data[tail] = item;
        if (count < size)
            count++;
        else
            head = (head + 1) % size;
        print();
    }

    public void print() {
        for (E item : this)
            System.out.print(item + " ");
        System.out.println();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int current = head;
            int counter = 0;

            @Override
            public boolean hasNext() {
                return this.counter < count;
            }

            @Override
            public E next() {
                E element = data[this.current];
                this.current = (this.current + 1) % size;
                this.counter++;
                return element;
            }
        };
    }
}

