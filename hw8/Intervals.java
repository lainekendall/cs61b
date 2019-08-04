import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
/** HW #8, Problem 3.
 *  @author
  */

public class Intervals {
    /* Note: there is some ambiguity about whether the intervals are closed
     * (include end points) or open (don't include end points).  Here, I 
     * assume the former. */

    /** Assuming that INTERVALS contains two-element arrays of integers,
     *  <x,y> with x <= y, representing intervals of ints, this returns a
     *  count of the number of intervals that X stabs (i.e. that x belongs to.
     */
    public static int coveredLength(List<int[]> intervals) {
        int N = intervals.size();
        ArrayList<int[]> ends = new ArrayList<>();
        for (int[] i : intervals) {
            ends.add(new int[] { i[0], -1 } );
            ends.add(new int[] { i[1], 1 } );
        }
        Collections.sort(ends, new EndpointComparator());
        int n, start, total;
        n = total = 0;
        start = 0;
        for (int[] intvl : ends) {
            if (n == 0) {
                start = intvl[0];
            }
            n -= intvl[1];
            if (n == 0) {
                total += intvl[0] - start;
            }
        }
        return total;
    }

    /** Lexicographic ordering of 2-element arrays. */
    static class EndpointComparator implements Comparator<int[]> {
        @Override
        public int compare(int[] i0, int[] i1) {
            if (i0[0] == i1[0]) {
                return i0[1] - i1[1];
            } else {
                return i0[0] - i1[0];
            }
        }
    }

    /** Performs a basic functionality test on the stabbingCount method. */
    @Test
    public void basicTest() {
        int[][] intervals = {
            {19, 30},  {8, 15}, {3, 10}, {6, 12}, {4, 5},
        };
        assertEquals(23, coveredLength(Arrays.asList(intervals)));
    }

    /** Runs provided JUnit test. ARGS is ignored. */
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(Intervals.class));
    }

// }
// public class Intervals {
//     /** Assuming that INTERVALS contains two-element arrays of integers,
//      *  <x,y> with x <= y, representing intervals of ints, this returns the
//      *  total length covered by the union of the intervals. */
//     public static int coveredLength(List<int[]> intervals) {
//         // REPLACE WITH APPROPRIATE STATEMENTS.
//       sortIntervals((ArrayList<int[]>) intervals);

//         List<int[]> result = new ArrayList<int[]>();
//         for (int i = 0; i < intervals.size() - 1; i += 2) {
//             int[] interval1 = intervals.get(i);
//             int[] interval2 = intervals.get(i+1);
//             int[] sorted = merge(interval1, interval2);
//             int[] merged1 = putTogether(interval1, interval2);
//             int[] merged2 = putTogether(interval2, interval1);
//             if (sorted.equals(merged1) || (sorted.equals(merged2))) {
//                 result.add(interval1);
//                 result.add(interval2);
//             } else {
//                 int[] concat = new int[] {sorted[0], sorted[3]};
//                 result.add(concat);
//             }
//         }
//         return countLength(result);
//     }
//     // sort by first item (ie lower limit)
//     // public static List<int[]> sortIntervals(List<int[]> intervals) {
//     //     List<int[]> result = new ArrayList<int[]>();
//     //     int currMin = intervals.get(0)[0];
//     //     for (int[] x : intervals) {
//     //         if (x[0] < currMin) {
//     //             currMin = x[0];
//     //         }
//     //     }
//     //     return result;
//     // }

//     public static void sortIntervals(ArrayList<int[]> intervals) {
//         for (int farthest = 1; farthest < intervals.size(); farthest++) {
//             // System.out.print("farthest");
//           for (int j = farthest; j > 0; j--) {
//             // printList(intervals);
//             // System.out.print("j");
//             if (intervals.get(j)[0] < intervals.get(j-1)[0]) {
//               int[] right = intervals.remove(j);
//               int[] left = intervals.remove(j-1);
//               intervals.add(j - 1, right);
//               intervals.add(j, left);
//             } else {
//                 // System.out.print("break");
//               break;
//             }
//           }
//         }
//     }


//     public static int max(ArrayList<int[]> intervals) {
//         int currMax = intervals.get(0)[1];
//         for (int i = 1 ; i < intervals.size(); i++) {
//           currMax = Math.max(intervals.get(i)[1], currMax);
//         }
//         return 0;
//     }

//     public static int min(ArrayList<int[]> intervals) {
//         int currMin = intervals.get(0)[0];
//         for (int i = 1 ; i < intervals.size(); i++) {
//             currMin = Math.min(intervals.get(i)[0], currMin);
//         }
//         return 0;
//     }


//     public static int countLength(List<int[]> intervals) {
//         int sum = 0;
//         int min = min((ArrayList<int[]>) intervals);
//         int max = max((ArrayList<int[]>) intervals);
//         for (int x = min; x <= max; x++) {
//           for (int[] interval : intervals) {
//               if ((x > interval[0]) && (x < interval[1])) {
//                 sum++;
//               }
//           }
//         }
//         return sum;
//     }
//     public static int[] putTogether(int[] A, int[] B) {
//         int[] result = new int[4];
//         result[0] = A[0];
//         result[1] = A[1];
//         result[2] = B[0];
//         result[3] = B[1];
//         return result;
//     }

//     public static int[] merge(int[] A, int[] B) {
//       int[] result = new int[A.length + B.length];
//       int b = 0,
//           a = 0;
//       for (int x = 0; x < result.length; x ++) {
//         if (a >= A.length) {
//           while (b < B.length) {
//             result[x] = B[b];
//             x++;
//             b++;
//           }
//           return result;
//         } else if (b >= B.length) {
//           while (a < A.length) {
//             result[x] = A[a];
//             x++;
//             a++;
//           }
//           return result;
//         }
//         if (A[a] < B[b]) {
//           result[x] = A[a];
//           a++;
//         } else {
//           result[x] = B[b];
//           b++;
//         }
//       }
//       return result;
//     }

//     /** Performs a basic functionality test on the stabbingCount method. */
//     @Test
//     public void sortIntervalsTest() {
//         int[][] intervals = {
//             {19, 30},  {8, 15}, {3, 10}, {6, 12}, {4, 5},
//         };
//         ArrayList<int[]> intervalsList = new ArrayList<int[]>();
//         for (int[] x : intervals) {
//           intervalsList.add(x);
//         }
//         sortIntervals(intervalsList);
//         assertArrayEquals(new int[] {3, 10}, intervalsList.get(0));
//         assertArrayEquals(new int[] {8, 15}, intervalsList.get(3));
//         assertEquals(30, max(intervalsList));
//         assertEquals(3, min(intervalsList));
//     }

//     @Test
//     public void intervalsTest() {
//       int[][] intervals = {
//             {19, 30},  {8, 15}, {3, 10}, {6, 12}, {4, 5},
//         };
//         ArrayList<int[]> intervalsList = new ArrayList<int[]>();
//         for (int[] x : intervals) {
//           intervalsList.add(x);
//         }
//       assertEquals(23, coveredLength(intervalsList));
//     }

//     /** Runs provided JUnit test. ARGS is ignored. */
//     public static void main(String[] args) {
//         System.exit(ucb.junit.textui.runClasses(Intervals.class));
//     }

}
