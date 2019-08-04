// Remove all comments that begin with //, and replace appropriately.
// Feel free to modify ANYTHING in this file.
package loa;

import ucb.junit.textui;
import org.junit.Test;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.Collections;

import static org.junit.Assert.*;
import static loa.Board.*;
import static loa.Direction.*;
import static loa.Piece.*;
import static loa.MachinePlayer.*;



/** The suite of all JUnit tests for the loa package.
 *  @author Laine D Kendall
 */
public class MachinePlayerTest {

    /** Run the JUnit tests in the loa package. Add xxxTest.class entries to
     *  the arguments of runClasses to run other JUnit tests. */
    public static void main(String[] ignored) {
        textui.runClasses(MachinePlayerTest.class);
    }

    @Test
    public void evalMoveTest() {
    	Board b1 = new Board(oneAway1, BP);
    	assertEquals(10, MachinePlayer.evalMove(b1, Move.create(1, 4, 1, 2, b1), b1.turn()));
    	Board b2 = new Board(oneAway1, WP);
    	assertEquals(0, MachinePlayer.evalMove(b2, Move.create(8, 3, 8, 2, b2), b2.turn()));
    	Board b3 = new Board(oneAway2, WP);
    	assertEquals(-10, MachinePlayer.evalMove(b3, Move.create(1, 7, 1, 4, b3), b3.turn()));
    }

    static Piece[][] oneAway1 = {
        { BP,  BP,  BP,  BP,  EMP, EMP, EMP, EMP },
        { EMP, WP,  WP,  BP,  EMP, EMP, EMP, EMP },
        { EMP, EMP, BP,  BP,  WP,  WP,  EMP, WP  },
        { BP,  WP,  BP,  WP,  WP,  EMP, EMP, EMP },
        { EMP, WP,  WP,  BP,  BP,  BP,  EMP, EMP },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP }
	};

	static Piece[][] oneAway2 = {
        { BP,  BP,  BP,  BP,  EMP, EMP, EMP, EMP },
        { EMP, WP,  WP,  BP,  EMP, EMP, EMP, EMP },
        { EMP, EMP, BP,  BP,  WP,  WP,  EMP, WP  },
        { BP,  WP,  BP,  WP,  WP,  EMP, EMP, EMP },
        { EMP, WP,  WP,  BP,  BP,  BP,  EMP, EMP },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP }
	};

    @Test 
    public void evalBoardTest() {
        HashMap<Integer, ArrayList<Move>> moveMap = new HashMap<Integer, ArrayList<Move>>();
        moveMap.put(0, new ArrayList<Move>());
        moveMap.put(10, new ArrayList<Move>());
        moveMap.put(-10, new ArrayList<Move>());
        Board b1 = new Board(oneAway1, BP);
        // b1.print();
        assertEquals(BP, b1.get("d3"));
        assertEquals(WP, b1.get("d4"));
        assertEquals(BP, b1.turn());
        assertFalse(b1.isLegal(Move.create("d4-h4", b1)));
        // assertTrue(b1.isLegal(Move.create("d3-d8", b1)));
        MachinePlayer.evalBoard(b1, moveMap);
        // printMap(moveMap);

    }

    public void printMap(HashMap<Integer, ArrayList<Move>> moveMap) {
        System.out.print("10: ");
        printArrayList(moveMap.get(10));

        System.out.print("0: ");
        printArrayList(moveMap.get(0));

        System.out.print("-10: ");
        printArrayList(moveMap.get(-10));
    }

    public void printArrayList(ArrayList<Move> list) {
        for (Move move : list) {
            System.out.println(move);
        }
    }








}