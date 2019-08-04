import static org.junit.Assert.*;
import org.junit.Test;

public class IntListTest {

    /** Example test that verifies correctness of the IntList.list static
     *  method. The main point of this is to convince you that
     *  assertEquals knows how to handle IntLists just fine.
     */

    @Test
    public void testList() {
        IntList one = new IntList(1, null);
        IntList twoOne = new IntList(2, one);
        IntList threeTwoOne = new IntList(3, twoOne);

        IntList x = IntList.list(3, 2, 1);
        assertEquals(threeTwoOne, x);
        
        IntList L1 = IntList.list(1, 2, 3, 4, 5),
            L2 = IntList.list(1, 2, 3);
        assertEquals(false, L1.equals(L2));

        IntList L3 = IntList.list(1, 2, 3),
            L4 = IntList.list(1, 2, 3, 4);
        assertEquals(false, L3.equals(L4));

        IntList L7 = IntList.list(1, 2, 3),
            L8 = IntList.list();
        assertEquals(false, L7.equals(L8));
    }

    @Test
    public void testdSquareList() {
        IntList L = IntList.list(1, 2, 3);
        IntList.dSquareList(L);
        assertEquals(IntList.list(1, 4, 9), L);
    }

    /*  Do not use the "new" keyword in your tests. You can create
     *  lists using the handy IntList.list method.
     *
     *  Make sure to include test cases involving lists of various sizes
     *  on both sides of the operation. That includes the empty list, which
     *  can be instantiated, for example, with
     *  IntList empty = IntList.list().
     */

    @Test
    public void testSquareListRecursive() {
        IntList L1 = IntList.list(1, 2, 3);
        IntList L2 = IntList.squareListRecursive(L1);
        assertEquals(IntList.list(1, 4, 9), L2);
        assertEquals(IntList.list(1, 2, 3), L1);
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(IntListTest.class));
    }
}
