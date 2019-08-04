package loa;

import static org.junit.Assert.*;
import org.junit.Test;


import static org.junit.Assert.*;
import static loa.Board.*;
import static loa.Direction.*;
import static loa.Piece.*;

public class BoardTest {
/* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(BoardTest.class));
    }

    @Test
    public void outLiers() {
    	Piece[][] oneAway1 = {
            { BP,  BP,  BP,  BP,  EMP, EMP, EMP, EMP },
            { EMP, WP,  WP,  BP,  EMP, EMP, EMP, EMP },
            { EMP, EMP, BP,  BP,  WP,  WP,  EMP, WP  },
            { BP,  WP,  BP,  WP,  WP,  EMP, EMP, EMP },
            { EMP, WP,  WP,  BP,  BP,  BP,  EMP, EMP },
            { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
            { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
            { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP }
        };

        Board b1 = new Board(oneAway1, BP);
        assertFalse(b1.isLegal(Move.create("d4-h4", b1)));
        for (Move move : b1) {
            // System.out.println(move);
            assertTrue(b1.isLegal(move));
        }

        Board b2 = new Board(oneAway1, BP);
        for (Move move : b2) {
            assertTrue(b2.isLegal(move));
        }

        Piece[][] middleGame2 = {
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

    // @Test
    // public void 

}

















