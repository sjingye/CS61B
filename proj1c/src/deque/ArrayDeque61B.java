package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.Math;
import java.util.NoSuchElementException;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private int size;
    private int first;
    private int last;
    private T[] items;
    private static final int INITIAL_CAPACITY = 8;

    public ArrayDeque61B() {
        size = 0;
        first = 0;
        last = 0;
        items = (T[]) new Object[INITIAL_CAPACITY];
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     * In addFirst, we're inserting an item before the current front, so we move the front pointer first and then insert the item.
     */
    @Override
    public void addFirst(T x) {
        int length = items.length;
        if (size == length) {
            resize(2 * length);
        }
        first = Math.floorMod(first - 1, length);
        items[first] = x;
        size += 1;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     * In addLast, we're inserting an item at the current rear, so we insert the item first and then move the rear pointer to the next slot.
     */
    @Override
    public void addLast(T x) {
        int length = items.length;
        if (size == length) {
            resize(2 * length);
        }
        items[last] = x;
        last = Math.floorMod(last + 1, length);
        size += 1;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     *
     *  iterating over the entire length of the array could lead to adding null elements to the list if the deque is not full.
     */
    @Override
    public List<T> toList() {
        int length = items.length;
        List<T> newArray = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            newArray.add(items[Math.floorMod(first + i, length)]);
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
            return null;
        }

        int length = items.length;

        T firstItem = items[first];
        items[first] = null;
        first = Math.floorMod(first + 1, length);
        size -= 1;

        // Shrink the size if necessary
        if (size < length / 4 && length > INITIAL_CAPACITY) {
            resize(length / 2);
        }

        return firstItem;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }

        int length = items.length;

        last = Math.floorMod(last - 1, length);
        T lastItem = items[last];
        items[last] = null;

        size -= 1;

        // Shrink the size if necessary
        if (size < length / 4 && length > INITIAL_CAPACITY) {
            resize(length / 2);
        }

        return lastItem;
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
        int length = items.length;
        if (size == 0 || index < 0 || index >= size) {
            return null;
        }
        return items[Math.floorMod(index + first, length)];
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
        return null;
    }

    public void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];

        for (int i = 0; i < size; i++) {
            newItems[i] = items[Math.floorMod(first + i, items.length)];
        }
        first = 0;
        last = size;
        items = newItems;
    }
    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    public class ArrayIterator implements Iterator<T> {
        private int position;
        public ArrayIterator() {
            position = 0;
        }
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return position < size;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            if (position < size) {
                T Item = items[Math.floorMod(first + position, items.length)];
                position += 1;
                return Item;
            }
            throw new NoSuchElementException();
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof ArrayDeque61B<?> otherSet) {
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
        int length = items.length;
        StringBuilder s = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            s.append((items[Math.floorMod(first + i, length)]).toString());
            if (i != size - 1) {
                s.append(",");
            }
        }
        s.append("]");
        return s.toString();
    }

}
