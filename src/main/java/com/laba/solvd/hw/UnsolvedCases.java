package com.laba.solvd.hw;
import com.laba.solvd.hw.Case.*;
import com.laba.solvd.hw.Exception.ElementNotFoundException;

import java.util.*;

public class UnsolvedCases<T extends Case> implements List<T> {
    private Node head;
    private int size;

    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
            next = null;
        }
    }

    public boolean add(T data) {
        int priority = data.getSeverity();
        Node newNode = new Node(data);
        if (head == null || priority > head.data.getSeverity()) {
            newNode.next = head;
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null && priority <= current.next.data.getSeverity()) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;
        return true;
    }

    public boolean remove(Object obj) {
        if (obj == null) {
            return false;
        }
        Node current = head;
        Node prev = null;
        while (current != null && !current.data.equals(obj)) {
            prev = current;
            current = current.next;
        }
        if (current == null) {
            return false;
        }
        if (current == head) {
            head = current.next;
        } else {
            prev.next = current.next;
        }
        size--;
        return true;
    }

    public boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        Node current = head;
        while (current != null) {
            if (current.data.equals(obj)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public Iterator<T> iterator() {
        return new UnsolvedCasesIterator();
    }

    private class UnsolvedCasesIterator implements Iterator<T> {
        private Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T data = current.data;
            current = current.next;
            return data;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Object[] toArray() {
        Object[] array = new Object[size];
        Node current = head;
        int index = 0;
        while (current != null) {
            array[index] = current.data;
            current = current.next;
            index++;
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    public <E> E[] toArray(E[] arr) {
        if (arr.length < size) {
            arr = (E[]) java.lang.reflect.Array.newInstance(arr.getClass().getComponentType(), size);
        }
        Node current = head;
        int index = 0;
        while (current != null) {
            arr[index] = (E) current.data;
            current = current.next;
            index++;
        }
        if (arr.length > size) {
            arr[size] = null;
        }
        return arr;
    }

    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new ElementNotFoundException("Invalid index: " + index);
        }

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.data;
    }

    @Override
    public T set(int index, T element) {
        return null;
    }

    @Override
    public void add(int index, T element) {

    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }
}