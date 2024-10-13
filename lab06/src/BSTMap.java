import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.Stack;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>  {

    public class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;

        public Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private int size;
    private Node root;

    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Null keys are not allowed");
        }
        root = put(root, key, value);
    }
    public Node put(Node node, K key, V value) {
        if (node == null) {
            size += 1;
            return new Node(key, value);
        }
        int compare = key.compareTo(node.key);
        if (compare < 0) {
            node.left = put(node.left, key, value);
        } else if (compare > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }

        return node;
    }
    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null keys are not allowed");
        }

        Node n = get(root, key);
        if (n == null) {
            return null;
        }
        return n.value;
    }
    public Node get(Node node, K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null keys are not allowed");
        }

        if (node == null) {
            return null;
        }
        int compare = key.compareTo(node.key);

       if (compare < 0) {
            return get(node.left, key);
        } else if (compare > 0){
            return get(node.right, key);
        } else {
           return node;
       }
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null keys are not allowed");
        }

        return get(root, key) != null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }
    public int size(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.left) + size(node.right);
    }
    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        Stack<Node> stack = new Stack<>();

        if (root == null) {
            return set;
        }
        stack.push(root);

        while(!stack.empty()) {
            Node current = stack.pop();
            set.add(current.key);

            if (current.right != null) {
                stack.push(current.right);
            }

            if (current.left != null) {
                stack.push(current.left);
            }
        }

        return set;
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }
    private class BSTMapIterator implements Iterator<K> {
        private Stack<Node> stack = new Stack<>();
        public BSTMapIterator() {
            pushLeft(root);
        }
        private void pushLeft(Node node) {
            while(node != null) {
                stack.push(node);
                node = node.left;
            }
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
            return !stack.isEmpty();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public K next() {
            if (!hasNext()) {
                throw new IllegalStateException("No more elements");
            }
            Node current = stack.pop();
            K k = current.key;

            if (current.right != null) {
                pushLeft(current.right);
            }
            return k;
        }
    }
}
