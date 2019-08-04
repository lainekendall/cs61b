// Remove all comments that begin with //, and replace appropriately.
// Feel free to modify ANYTHING in this file.
package loa;

import ucb.junit.textui;
import org.junit.Test;
import java.util.Iterator;

import static org.junit.Assert.*;
import static loa.Board.*;
import static loa.Direction.*;
import static loa.Piece.*;



/** The suite of all JUnit tests for the loa package.
 *  @author Laine D Kendall
 */
public class UnitTest {

    /** Run the JUnit tests in the loa package. Add xxxTest.class entries to
     *  the arguments of runClasses to run other JUnit tests. */
    public static void main(String[] ignored) {
        textui.runClasses(UnitTest.class);
        textui.runClasses(MachinePlayerTest.class);
        textui.runClasses(BoardTest.class);
    }

    /** Testing if the two get methods in Board work correctly. */
    @Test
    public void boardGetTest() {
    	Board b = new Board();
    	// switch each argument
    	assertEquals(b.get(1, 2).fullName(), "white");
    	assertEquals(b.get(8, 6).fullName(), "white");
    	assertEquals(b.get(3, 8).fullName(), "black");
    	assertEquals(b.get(4, 4).fullName(), "empty");
    	assertEquals(b.get(1, 1).fullName(), "empty");

    	assertEquals(b.get("a4").fullName(), "white");
    	assertEquals(b.get("f8").fullName(), "black");
    	assertEquals(b.get("e1").fullName(), "black");
    	assertEquals(b.get("a4").fullName(), "white");
    	assertEquals(b.get("d5").fullName(), "empty");
    	assertEquals(b.get("a1").fullName(), "empty");
    }
    @Test
    public void pieceCountAlongTest() {
        Board b = new Board();
        assertEquals(2, b.pieceCountAlong(2, 1, N));
        assertEquals(6, b.pieceCountAlong(2, 1, W));
        assertEquals(6, b.pieceCountAlong(1, 5, N));
        assertEquals(2, b.pieceCountAlong(8, 4, E));
    }

    @Test
    public void hasOpposingPieceTest() {
        Board b = new Board();
        assertFalse(b.hasOpposingPiece(2, 1, N, 7));
        assertTrue(b.hasOpposingPiece(1, 2, E, 3));
        assertFalse(b.hasOpposingPiece(2, 1, E, 4));
    }


    @Test
    public void directionTest() {
        assertEquals(NE, Move.direction(1, 1, 3, 3));
        assertEquals(N, Move.direction(3, 1, 3, 3));
        assertEquals(NW, Move.direction(3, 1, 1, 3));
        assertEquals(W, Move.direction(4, 4, 1, 4));
        assertEquals(S, Move.direction(4, 4, 4, 1));
        assertEquals(SW, Move.direction(4, 4, 1, 1));
        assertEquals(SE, Move.direction(4, 4, 8, 1));
        assertEquals(E, Move.direction(4, 4, 8, 4));
        assertEquals(NOWHERE, Move.direction(4, 4, 4, 4));
        assertEquals(NOWHERE, Move.direction(-1, 9, 0, 20));

        assertEquals(true, Move.isDirection(1, 1, 3, 3));
        assertEquals(true, Move.isDirection(3, 1, 3, 3));
        assertEquals(true, Move.isDirection(3, 1, 1, 3));
        assertEquals(true, Move.isDirection(4, 4, 1, 4));
        assertEquals(true, Move.isDirection(4, 4, 4, 1));
        assertEquals(true, Move.isDirection(4, 4, 1, 1));
        assertEquals(true, Move.isDirection(4, 4, 8, 1));
        assertEquals(true, Move.isDirection(4, 4, 1, 4));
        assertEquals(false, Move.isDirection(4, 4, 4, 4));
        assertEquals(false, Move.isDirection(1, 9, 0, 20));
    }

    @Test
    public void isLegalTest() {
        Board b = new Board();
        assertTrue(b.isLegal(Move.create("d1-d3", b)));
        assertFalse(b.isLegal(Move.create("d1-f1", b)));
        assertFalse(b.isLegal(Move.create("b1-b5", b)));
        assertFalse(b.isLegal(Move.create("a5-c5", b)));
        assertFalse(b.isLegal(Move.create("h1-h1", b)));
        assertFalse(b.isLegal(Move.create("b1-c2", b)));
        b.makeMove(Move.create("d1-d3", b)); // black
        b.makeMove(Move.create("a2-c4", b)); // white
        assertFalse(b.isLegal(Move.create("d1-d3", b)));
        assertFalse(b.isLegal(Move.create("d1-f1", b)));
        assertFalse(b.isLegal(Move.create("b1-b5", b)));
        assertFalse(b.isLegal(Move.create("a5-c5", b)));
        assertFalse(b.isLegal(Move.create("h1-h1", b)));
        assertFalse(b.isLegal(Move.create("b1-b1", b)));
        assertFalse(b.isLegal(Move.create("d3-b5", b)));
        // jump a friendly piece 
        assertTrue(b.isLegal(Move.create("b1-e4", b))); // blacks turn
        b.makeMove(Move.create("b1-e4", b));
        // kill an opposing piece
        assertTrue(b.isLegal(Move.create("a4-e4", b))); // whites turn
        b.makeMove(Move.create("a4-e4", b));
        // out of bounds moves
        assertFalse(b.isLegal(Move.create("c1-c9", b)));
        assertFalse(b.isLegal(Move.create("z1-c3", b)));
        // moving an empty piece
        assertFalse(b.isLegal(Move.create("a1-a2", b)));
        assertFalse(b.isLegal(Move.create("a1-a3", b)));
        // moving the opposite color
        assertFalse(b.isLegal(Move.create("a3-d3", b))); // blacks move
        assertFalse(b.isLegal(Move.create("a3-d3", b)));
        // trying to kill a friendly piece
        assertFalse(b.isLegal(Move.create("c1-g1", b))); // blacks
        // trying to jump an opposing piece
        assertFalse(b.isLegal(Move.create("d3-a5", b)));
        // moving in a non-straight line
        assertFalse(b.isLegal(Move.create("c1-d3", b)));
        assertFalse(b.isLegal(Move.create("c1-d3", b)));
        assertFalse(b.isLegal(Move.create("c1-a2", b)));
        // jumping multiple of your own pieces (OK)
        assertTrue(b.isLegal(Move.create("f1-b1", b)));
    }

    @Test
    public void moveIteratorTest() {
        Board b = new Board();
        Iterator<Move> moveIt = b.iterator();
        assertEquals(Move.create("b1-b3", b), moveIt.next());
        assertTrue(moveIt.hasNext());
        assertEquals(Move.create("b1-d3", b), moveIt.next());
        assertEquals(Move.create("b1-h1", b), moveIt.next());

        // next piece
        assertEquals(Move.create("c1-c3", b), moveIt.next());
        assertEquals(Move.create("c1-e3", b), moveIt.next());
        assertEquals(Move.create("c1-a3", b), moveIt.next());
        for (int i = 6; i < 36; i++) {
            // System.out.println(moveIt.next());
            moveIt.next();
        }
        assertFalse(moveIt.hasNext());
        // System.out.println("+++===:::===+++     ++++...]]]]]");
        b.makeMove(Move.create("g8-a8", b));
        // b.print();
        // for (Move move : b) {
        //     System.out.println(move);
        // }
    }

    @Test
    public void boardEqualsTest() {
        Board b1 = new Board();
        Board b2 = new Board();
        Board b3 = new Board();
        assertEquals(b1, b2);
        b1.makeMove(Move.create("d1-d3", b1));
        b2.makeMove(Move.create("d1-d3", b2));
    }

    // @Test 
    public void printBoardTest() {
        Board b1 = new Board();
        b1.print();
    }

    @Test
    public void piecesContiguousTest() {
        Board b = new Board();
        assertFalse(b.piecesContiguous(BP));

        Board bEnd1 = new Board(endGame1, BP);
        assertTrue(bEnd1.piecesContiguous(BP));
        assertFalse(bEnd1.piecesContiguous(WP));

        Board bEnd2 = new Board(endGame2, BP);
        assertTrue(bEnd2.piecesContiguous(BP));
        assertFalse(bEnd2.piecesContiguous(WP));

        Board bEnd3 = new Board(endGame3, BP);
        assertTrue(bEnd3.piecesContiguous(BP));
        assertTrue(bEnd3.piecesContiguous(WP));

        Board bMiddle1 = new Board(middleGame1, BP);
        assertFalse(bMiddle1.piecesContiguous(WP));
        assertFalse(bMiddle1.piecesContiguous(BP));

        Board bMiddle2 = new Board(middleGame2, BP);
        assertFalse(bMiddle2.piecesContiguous(WP));
        assertFalse(bMiddle2.piecesContiguous(BP));
    }

    static Piece[][] endGame1 = {
        { EMP,  EMP, EMP, BP,  EMP, EMP, EMP, EMP },
        { EMP,  WP,  WP,  BP,  EMP, EMP, EMP, EMP },
        { EMP,  EMP, BP,  BP,  WP,  WP,  EMP, WP  },
        { EMP,  WP,  BP,  WP,  WP,  EMP, EMP, EMP },
        { EMP,  BP,  WP,  BP,  BP,  BP,  EMP, EMP },
        { EMP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP }
    };

    static Piece[][] endGame2 = {
        { BP,  BP, BP, BP,  EMP, EMP, EMP, EMP },
        { BP,  WP,  WP,  BP,  EMP, EMP, EMP, EMP },
        { EMP,  EMP, BP,  BP,  WP,  WP,  EMP, WP  },
        { EMP,  WP,  BP,  WP,  WP,  EMP, EMP, EMP },
        { EMP,  BP,  WP,  BP,  BP,  BP,  EMP, EMP },
        { EMP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP }
    };

    static Piece[][] endGame3 = {
        { BP,  BP, BP, BP,  BP, BP, BP, BP },
        { BP,  BP, BP, BP,  BP, BP, BP, BP },
        { BP,  BP, BP, BP,  BP, BP, BP, BP },
        { BP,  BP, BP, BP,  BP, BP, BP, BP },
        { BP,  BP, BP, BP,  BP, BP, BP, BP },
        { BP,  BP, BP, BP,  BP, BP, BP, BP },
        { BP,  BP, BP, BP,  BP, BP, BP, BP },
        { BP,  BP, BP, BP,  BP, BP, BP, WP },
    };

    static Piece[][] middleGame1 = {
        { BP,  BP, BP, BP,  EMP, EMP, EMP, EMP },
        { BP,  WP,  WP,  BP,  EMP, EMP, EMP, EMP },
        { EMP,  EMP, BP,  BP,  WP,  WP,  EMP, WP  },
        { EMP,  WP,  BP,  WP,  WP,  EMP, EMP, EMP },
        { EMP,  BP,  WP,  BP,  BP,  BP,  EMP, EMP },
        { EMP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP,  EMP, EMP, EMP, EMP, EMP, EMP, BP }
    };

    static Piece[][] middleGame2 = {
        { BP,  BP, BP, BP,  EMP, EMP, EMP, EMP },
        { BP,  WP,  WP,  BP,  EMP, EMP, EMP, EMP },
        { EMP,  EMP, BP,  BP,  WP,  WP,  EMP, WP  },
        { EMP,  WP,  BP,  WP,  WP,  EMP, EMP, EMP },
        { EMP,  BP,  WP,  BP,  BP,  BP,  EMP, EMP },
        { EMP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP,  EMP, EMP, BP, EMP, EMP, EMP, EMP },
        { EMP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP }
    };

}


