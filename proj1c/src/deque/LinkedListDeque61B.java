package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class LinkedListDeque61B<T> implements Deque61B<T> {

    public class Node {
        public T data;
        public Node prev;
        public Node next;

        public Node (T d) {
            data = d;
            prev = null;
            next = null;
        }
    }

    public int size;
    private final Node head;
    private final Node tail;

    public LinkedListDeque61B() {
        size = 0;
        head = new Node(null);
        tail = new Node(null);
        head.next = tail;
        tail.prev = head;
    }
    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        Node newItem = new Node(x);
        Node tempItem = head.next;
        head.next = newItem;
        newItem.prev = head;
        newItem.next = tempItem;
        tempItem.prev = newItem;
        size += 1;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        Node newItem = new Node(x);
        Node tempItem = tail.prev;
        tempItem.next = newItem;
        newItem.prev = tempItem;
        newItem.next = tail;
        tail.prev = newItem;
        size += 1;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> newArray = new ArrayList<>(size);
        Node current = head.next;
        while (current != tail) {
            newArray.add(current.data);
            current = current.next;
        }
        return newArray;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null; // Handle empty deque
        }

        Node first = head.next;
        Node second = first.next;
        head.next = second;
        second.prev = head;
        size -= 1;
        return first.data;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null; // Handle empty deque
        }

        Node last = tail.prev;
        Node secondLast = last.prev;
        tail.prev = secondLast;
        secondLast.next = tail;
        size -= 1;
        return last.data;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if (size == 0 || index < 0 || index >= size) {
            return null;
        }
        Node current = head.next;
        int i = 0;

        while(current != tail) {
            if (index == i) {
                return current.data;
            }
            i += 1;
            current = current.next;
        }
        return null;
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        if (size == 0 || index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelper(head.next, index);
    }

    public T getRecursiveHelper(Node current, int index) {
        if (current == tail) { // Reached the end
            return null; // Handle out of bounds
        }
        if (index == 0) {
            return current.data;
        }
        return getRecursiveHelper(current.next, index - 1);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node current = head.next;

            @Override
            public boolean hasNext() {
                return current != tail;
            }

            @Override
            public T next() {
                if (current != tail) {
                    T data = current.data;
                    current = current.next;
                    return data;
                }
                throw new NoSuchElementException();
            }
        };
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof LinkedListDeque61B<?> otherSet) {
            if (this.size != otherSet.size) {
                return false;
            }

            for (T x : this) {
                boolean found = false;

                for (Object y : otherSet) {
                    if (x.equals(y)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        Node current = head.next;
        while (current != tail) {
            s.append((current.data).toString());
            if (current.next != tail) {
                s.append(",");
            }

            current = current.next;
        }
        s.append("]");
        return s.toString();
    }

}
