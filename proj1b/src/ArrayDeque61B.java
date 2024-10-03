import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T> {
    public int size;
    public T[] items;
    public int nextFirst;
    public int nextLast;

    public ArrayDeque61B() {
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 0;
    }
    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        int length = items.length;

        if (length == size) {
            resize(2 * size);
        }

        nextFirst = Math.floorMod(nextFirst - 1, length);
        items[nextFirst] = x;
        size += 1;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        int length = items.length;

        if (length == size) {
            resize(2 * size);
        }

        nextLast = Math.floorMod(nextLast + 1, length);
        items[nextLast] = x;
        size += 1;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        int length = items.length;
        List<T> returnList = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            returnList.set(i, items[i]);
        }
        return returnList;
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
        nextFirst = Math.floorMod(nextFirst + 1, length);
        T first = items[nextFirst];
        items[nextFirst] = null;
        size -= 1;

        if (size < length / 4 && length > 15) {
            resize(length / 4);
        }

        return first;
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
        nextLast = Math.floorMod(nextLast - 1, length);
        T last = items[nextLast];
        items[nextLast] = null;
        size -= 1;

        if (size < length / 4 && length > 15) {
            resize(length / 4);
        }
        return last;
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

        if (index < 0 || index > length - 1) {
            return null;
        }
        return items[index];
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
        int length = items.length;

        for (int i = 0; i < capacity; i++) {
            newItems[i] = items[Math.floorMod(i + nextFirst - 1, length)];
        }
        items = newItems;
        nextFirst = 0;
        nextLast = size;
    }

}
