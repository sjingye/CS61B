package deque;

import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {
    private final Comparator<T> comparator;

    public MaxArrayDeque61B(Comparator<T> c) {
        comparator = c;
    }

    /**
     * returns the maximum element in the deque as governed by the previously given Comparator. If the MaxArrayDeque61B is empty, simply return null.
     *
     */
    public T max() {
        if (isEmpty()) {
            return null;
        }
        T max = get(0);
        for (T s : this) {
            if (comparator.compare(max, s) < 0) {
                max = s;
            }
        }
        return max;
    }


    /**
     * returns the maximum element in the deque as governed by the parameter Comparator c. If the MaxArrayDeque61B is empty, simply return null.
     * @param c
     * @return
     */
    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T max = get(0);
        for (T s : this) {
            if (c.compare(max, s) < 0) {
                max = s;
            }
        }
        return max;
    }
}
