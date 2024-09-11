import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        // TODO: Fill in this function.
        int sum = 0;
        for (int i : L) {
            sum += i;
        }
        return sum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        // TODO: Fill in this function.
        List<Integer> list = new ArrayList<>();

        for (int i : L) {
            if (i % 2 == 0) {
                list.add(i);
            }
        }
        return list;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        // TODO: Fill in this function.
        List<Integer> list = new ArrayList<>();

        for (int i : L1) {
            if (L1.contains(i) && L2.contains(i)) {
                list.add(i);
            }
        }

        return list;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        // TODO: Fill in this function.
        int count = 0;
        for(String s : words) {
            int length = s.length();

            for (int i = 0; i < length; i++) {
                if (s.charAt(i) == c) {
                    count += 1;
                }
            }
        }
        return count;
    }
}
