import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T>{

    public  class Node {
        public T item;
        public Node prev;
        public Node next;

        public Node(T i) {
            this.item = i;
            this.prev = null;
            this.next = null;
        }
    }

    public int size;
    public Node head;
    public Node tail;

    public LinkedListDeque61B() {
        size = 0;
        head = new Node(null); // Sentinel node (no data)
        tail = new Node(null); // Sentinel node (no data)
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
        Node newNode = new Node(x);
        Node first = head.next;

        head.next = newNode;
        first.prev = newNode;
        newNode.next = first;
        newNode.prev = head;

        size += 1;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        Node newNode = new Node(x);
        Node last = tail.prev;

        last.next = newNode;
        tail.prev = newNode;
        newNode.next = tail;
        newNode.prev = last;

        size += 1;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>(size);
        Node p = head.next;

        while(p != tail) {
            returnList.add(p.item);
            p = p.next;
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
        if (size == 0) return null;

        Node first = head.next;
        Node second = first.next;

        head.next = second;
        second.prev = head;
        size -= 1;
        return first.item;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (size == 0) return null;

        Node last = tail.prev;
        Node secondLast = last.prev;

        tail.prev = secondLast;
        secondLast.next = tail;

        size -= 1;
        return last.item;
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
        Node p = head.next;
        int i = 0;

        while(p != tail) {
            if (i == index) {
                return p.item;
            }
            i += 1;
            p = p.next;
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
        return getRecursiveHelper(head.next, index);
    }

    public T getRecursiveHelper(Node node, int index) {
        if (node == tail || index < 0) {
            return null;
        } else if (index == 0) {
            return node.item;
        } else {
            return getRecursiveHelper(node.next, index - 1);
        }
    }

}
