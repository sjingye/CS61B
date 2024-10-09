import java.util.ArrayList;
import java.util.List;

public class UnionFind {
    // TODO: Instance variables
    private final int[] parent;
    private final int[] size;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
        parent = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        return size[find(v)];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        if (parent[v] == v) {
            return -size[v];
        }
        /*
        * the advantage is when call find method, this can apply full path compression.
        * /
        int root = find(v);
        if (root == v) {
            return -size[root];
        }
        */
        return parent[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        if (v < 0 || v >= parent.length) {
            throw new IllegalArgumentException("Invalid input: " + v);
        }
        int p = v;
        // Find the root of v
        while (parent[p] != p) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }

        return p;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        int root1 = find(v1);
        int root2 = find(v2);

        // root1 and root2 are connected
        if (root1 == root2) {
            return;
        }

        if (size[root1] > size[root2]) {
            parent[root2] = root1;
            size[root1] += size[root2];
            size[root2] = 0;
        } else {
            parent[root1] = root2;
            size[root2] += size[root1];
            size[root1] = 0;
        }
    }

}
