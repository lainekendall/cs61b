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
    }

    /** Do not use the new keyword in your tests. You can create
     *  lists using the handy IntList.list method.
     *
     *  Make sure to include test cases involving lists of various sizes
     *  on both sides of the operation. That includes the empty list, which
     *  can be instantiated, for example, with
     *  IntList empty = IntList.list().
     *
     *  Keep in mind that dcatenate(A, B) is NOT required to leave A untouched.
     *  Anything can happen to A.
     */

    @Test
    public void testDcatenate() {
        
        assertEquals(IntList.list(1,2,3,4), IntList.dcatenate(IntList.list(1,2), IntList.list(3,4)));
        assertEquals(IntList.list(1,2), IntList.dcatenate(IntList.list(1,2), IntList.list()));
        assertEquals(IntList.list(1,3,4,5,6), IntList.dcatenate(IntList.list(1), IntList.list(3,4,5,6)));
        assertEquals(IntList.list(1,2,3,4,5,6), IntList.dcatenate(IntList.list(1,2,3,4,5), IntList.list(6)));
        assertEquals(IntList.list(3,4,5,6), IntList.dcatenate(IntList.list(), IntList.list(3,4,5,6)));

    }

    /** Tests that subtail works properly. Again, don't use new.
     *
     *  Make sure to test that subtail does not modify the list.
     */

    @Test
    public void testSubTail() {
        IntList a = IntList.list(1,2,3,4);
        assertEquals(IntList.list(2,3,4), IntList.subTail(a, 1));
        assertEquals(a, IntList.list(1,2,3,4));

        IntList b = IntList.list();
        assertEquals(IntList.list(), IntList.subTail(b, 1));
        assertEquals(b, IntList.list());

        IntList c = IntList.list(1,2,3,4);
        assertEquals(IntList.list(1,2,3,4), IntList.subTail(c, 0));
        assertEquals(c, IntList.list(1,2,3,4));

        IntList d = IntList.list(1,2,3,4);
        assertEquals(IntList.list(), IntList.subTail(d, 4));
        assertEquals(d, IntList.list(1,2,3,4));
    }

    /** Tests that sublist works properly. Again, don't use new.
     *
     *  Make sure to test that sublist does not modify the list.
     */

    @Test
    public void testSublist() {
        IntList a = IntList.list(1,2,3,4);
        assertEquals(IntList.list(), IntList.sublist(a, 4, 1));
        assertEquals(a, IntList.list(1,2,3,4));

        IntList b = IntList.list(1,2,3,4);
        assertEquals(IntList.list(1,2,3,4), IntList.sublist(b, 0, 4));
        assertEquals(b, IntList.list(1,2,3,4));

        IntList c = IntList.list(1,2,3,4);
        assertEquals(IntList.list(2,3), IntList.sublist(c, 1, 2));
        assertEquals(c, IntList.list(1,2,3,4));

        IntList d = IntList.list(1,2,3,4);
        assertEquals(IntList.list(4), IntList.sublist(d, 3, 3));
        assertEquals(d, IntList.list(1,2,3,4));

        IntList e = IntList.list(1,2,3,4);
        assertEquals(IntList.list(), IntList.sublist(e, 3, 0));
        assertEquals(e, IntList.list(1,2,3,4));

        IntList f = IntList.list();
        assertEquals(IntList.list(), IntList.sublist(f, 3, 3));
        assertEquals(f, IntList.list());
    }

    /** Tests that dSublist works properly. Again, don't use new.
     *
     *  As with testDcatenate, it is not safe to assume that list passed
     *  to dSublist is the same after any call to dSublist
     */

    @Test
    public void testDsublist() {

        IntList originalA = IntList.list(1,2,3,4);
        IntList computedA = IntList.dsublist(originalA, 4, 1);
        IntList shouldbeA = IntList.list();
        assertEquals(shouldbeA, computedA);
        //assertEquals(computedA, originalA);

        IntList originalB = IntList.list(1,2,3,4);
        IntList computedB = IntList.dsublist(originalB, 0, 4);
        IntList shouldbeB = IntList.list(1,2,3,4);
        assertEquals(shouldbeB, computedB);
        assertEquals(computedB, originalB);

        IntList originalC = IntList.list(1,2,3,4);
        IntList computedC = IntList.dsublist(originalC, 1, 2);
        IntList shouldbeC = IntList.list(2,3);
        assertEquals(shouldbeC, computedC);
        assertEquals(computedC, originalC);

        IntList originalD = IntList.list(1,2,3,4);
        IntList computedD = IntList.dsublist(originalD, 3, 3);
        IntList shouldbeD = IntList.list(4);
        assertEquals(shouldbeD, computedD);
        assertEquals(computedD, originalD);

        IntList originalE = IntList.list(1,2,3,4);              // should we ignore this case?
        IntList computedE = IntList.dsublist(originalE, 3, 0);
        IntList shouldbeE = IntList.list();
        assertEquals(shouldbeE, computedE);
        // assertEquals(computedE, originalE);

        IntList originalF = IntList.list();
        IntList computedF = IntList.dsublist(originalF, 3, 3);
        IntList shouldbeF = IntList.list();
        assertEquals(shouldbeF, computedF);
        assertEquals(computedF, originalF);

    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(IntListTest.class));
    }
}
