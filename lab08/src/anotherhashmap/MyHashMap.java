package anotherhashmap;

import java.util.*;

public class MyHashMap<K, V> {
    private class Node {
        K key;
        V value;
        Node next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private Node[] buckets;
    private int size;
    private final double loadFactor;
    private int threshold;

    private static final int INITIAL_CAPACITY = 16;

    public MyHashMap() {
        this(INITIAL_CAPACITY, 0.75);
    }

    public MyHashMap(int initialCapacity, double loadFactor) {
        this.buckets = (Node[]) new MyHashMap.Node[initialCapacity];  // Casting generic array
        this.loadFactor = loadFactor;
        this.threshold = (int) (initialCapacity * loadFactor);
        this.size = 0;
    }

    private int getIndex(K key) {
        return key == null ? 0 : Math.abs(key.hashCode()) % buckets.length;
    }

    private void resize() {
        Node[] oldBuckets = buckets;
        buckets = (Node[]) new MyHashMap.Node[oldBuckets.length * 2];  // Fix for generic array
        threshold = (int) (buckets.length * loadFactor);
        size = 0;

        for (Node head : oldBuckets) {
            while (head != null) {
                put(head.key, head.value);  // Rehash each node
                head = head.next;
            }
        }
    }

    public void put(K key, V value) {
        if (size >= threshold) {
            resize();
        }

        int index = getIndex(key);
        Node head = buckets[index];

        for (Node node = head; node != null; node = node.next) {
            if (Objects.equals(node.key, key)) {
                node.value = value;
                return;
            }
        }

        Node newNode = new Node(key, value);
        newNode.next = head;
        buckets[index] = newNode;
        size++;
    }

    public V get(K key) {
        int index = getIndex(key);
        Node head = buckets[index];

        for (Node node = head; node != null; node = node.next) {
            if (Objects.equals(node.key, key)) {
                return node.value;
            }
        }
        return null;
    }

    public V remove(K key) {
        int index = getIndex(key);
        Node head = buckets[index];
        Node prev = null;

        for (Node node = head; node != null; prev = node, node = node.next) {
            if (Objects.equals(node.key, key)) {
                if (prev == null) {
                    buckets[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                return node.value;
            }
        }
        return null;
    }

    public boolean containsKey(K key) {
        int index = getIndex(key);
        Node head = buckets[index];

        for (Node node = head; node != null; node = node.next) {
            if (Objects.equals(node.key, key)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void clear() {
        buckets = (Node[]) new MyHashMap.Node[INITIAL_CAPACITY];
        size = 0;
    }

    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (Node head : buckets) {
            for (Node node = head; node != null; node = node.next) {
                keys.add(node.key);
            }
        }
        return keys;
    }

    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("One", 1);
        map.put("Two", 2);
        System.out.println(map.get("One")); // Output: 1
        System.out.println(map.get("Two")); // Output: 2
        map.remove("One");
        System.out.println(map.get("One")); // Output: null
        System.out.println(map.size()); // Output: 1
        map.clear();
        System.out.println(map.size()); // Output: 0
    }
}
