import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

/** HW #8, Optional Problem 5b.
 *  @author
 */
public class Inversions {

    /** A main program for testing purposes.  Prints the number of inversions
     *  in the sequence ARGS. */
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(Inversions.class));
        // System.out.println(inversions(Arrays.asList(args)));
    }

    /** Return the number of inversions of T objects in ARGS. */
    public static <T extends Comparable<? super T>> int inversions(List<T> args)
    {
        return sort(args, 0);
    }

    public static <T extends Comparable<? super T>> int sort(List<T> array, int k) {
        int numInv = 0;
        // List<T> result = new ArrayList<T>();
        for (int farthest = 1; farthest < array.size(); farthest++) {
            // System.out.print("farthest");
          for (int j = farthest; j > 0; j--) {
            // printList(array);
            // System.out.print("j");
            if (array.get(j).compareTo(array.get(j-1)) < 0) {
                numInv += 1;
              T right = array.remove(j);
              T left = array.remove(j-1);
              array.add(j - 1, right);
              array.add(j, left);
            } else {
                // System.out.print("break");
              break;
            }
          }
        }
        return numInv;
    }

    public static <T extends Comparable<? super T>> void printList(List<T> A) {
          for (T a : A) {
            System.out.print(a + "  ");
          }
          System.out.println("::::");
        }

    @Test
    public void basicTest() {
        System.out.print(1);
        int[] array = 
            {19, 30, 8, 15, 3, 10, 6, 12, 4, 5};
        List<Integer> list = new ArrayList<Integer>();
        for (int x : array) {
            list.add(x);
        }
        System.out.print(2);
        int[] arraySorted = 
            {3, 4, 5, 6, 8, 10, 12, 15, 19, 30};
            List<Integer> listSorted = new ArrayList<Integer>();
        for (int x : arraySorted) {
            listSorted.add(x);
        }
        assertEquals(33, sort(list, 0));
        assertEquals(0, sort(listSorted, 0));
        assertArrayEquals(list.toArray(), listSorted.toArray());
    }


}
