// Remove all comments that begin with //, and replace appropriately.
// Feel free to modify ANYTHING in this file.
package loa;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.Collections;
import java.util.AbstractMap.SimpleEntry;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/** An automated Player.
 *  @author Laine D Kendall
 */
class MachinePlayer extends Player {

    /** A MachinePlayer that plays the SIDE pieces in GAME. */
    MachinePlayer(Piece side, Game game) {
        super(side, game);
        // FILL IN
        // clearMap();
    }

    // or make this move (key) --> int (value)
    HashMap<Integer, ArrayList<Move>> moveMap = new HashMap<Integer, ArrayList<Move>>();

    DefaultMutableTreeNode gameTree = new DefaultMutableTreeNode();

    @Override
    Move makeMove() {
        // return null;  // REPLACE WITH IMPLEMENTATION
        int rand = getGame().randInt(30);
        int i = 0;
        for (Move move : getBoard()) {
            i += 1;
            if (i == rand) {
                System.out.println(side().abbrev().toUpperCase() + "::" + move);
                return move;
            }
        }
        return null;
    }


    // must check if the move is legal first.
    // @Override
    // Move makeMove() {
    //     // return null;  // REPLACE WITH IMPLEMENTATION
    //     System.out.println("make move 1");
    //     evalBoard(getBoard(), gameTree, 1, false);
    //     System.out.println("make move 2");
    //     int max = Integer.MIN_VALUE;
    //     for (int i = 0; i < gameTree.getChildCount(); i++) {
    //         TreeNode child = gameTree.getChildAt(i);
    //         DefaultMutableTreeNode childD = (DefaultMutableTreeNode) child;
    //         Object moveObject = childD.getUserObject();
    //         int moveValue = Math.abs((int) moveObject);
    //         if (moveValue > max) {
    //             max = moveValue;
    //         }
    //     }
    //     return traverseTree(gameTree, max);
    // }

    Move traverseTree(DefaultMutableTreeNode T, int k) {
        if (T.getDepth() == 0) {
            return null;
        } else {
            Object obj = T.getUserObject();
            int value = (int) ((SimpleEntry) obj).getKey();
            if (value == k) {
                return (Move) ((SimpleEntry) obj).getValue();
            }
        }
        return null;
    }

    // ArrayList<Move> list = getMaxList();
    //     if (list == null) {
    //         // System.out.println("make move list is null");
    //         return null;
    //     }
    //     Move move = list.get(0);
    //     // System.out.println("make move 3");
    //     clearMap();
    //     System.out.println(getBoard().turn().abbrev().toUpperCase() + "::" + move);
    //     return move; // returns the first move from the lsit of best moves.

    ArrayList<Move> getMaxList() {
        Set<Integer> set = moveMap.keySet();
        for (int i = 0; i < set.size(); i++) {
            int max = Collections.max(set);
            // System.out.println("max is: " + max);
            ArrayList<Move> list = moveMap.get(max);
            if (list.size() != 0) {
                return list;
            }
            set.remove(max);
        }
        return null;
    }

    void clearMap() {
        // System.out.println("clear map");
        moveMap.put(0, new ArrayList<Move>());
        moveMap.put(10, new ArrayList<Move>());
        moveMap.put(-10, new ArrayList<Move>());

    }

    // void clearTree() {
    //     int[] list = {0, 10, -10};
    //     for (int x : list) {
    //         SimpleEntry<Integer, ArrayList<Move>> entry = new SimpleEntry<Integer, ArrayList<Move>>(eval, );
    // }

    // puts all legal moves into a MOVEMAP for BOARD.turn().
    static void evalBoard(Board board, DefaultMutableTreeNode gameTree, int depth, boolean opponent) {
        System.out.println("evalBoard called ");
        if (depth == 0) {
            return;
        }
        Board boardCopy = new Board(board);
        for (Move move : board) {
            if (board.isLegal(move)) {
                System.out.println("LEGAL move in evalBoard(): " + move);
                int eval = evalMove(boardCopy, move, board.turn());
                if (opponent) {
                    eval = - eval;
                }
                DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(new SimpleEntry<Move, Integer>(move, eval));
                gameTree.add(newChild);
                boardCopy.makeMove(move);
                evalBoard(boardCopy, newChild, depth - 1, !opponent);
            } else {
                System.out.println("ERROR: move is illegal in evalBoard(): " + move);
            }
        }
    }

    static void evalBoard(Board board, HashMap<Integer, ArrayList<Move>> moveMap) {
        // System.out.println("evalBoard called ");
        for (Move move : board) {
            if (board.isLegal(move)) {
                // System.out.println("LEGAL move in evalBoard(): " + move);
                ArrayList<Move> list = moveMap.get(evalMove(board, move, board.turn()));
                int eval = evalMove(board, move, board.turn());
                
                // gameTree.add(move);
                // list.add(move);
            } else {
                // System.out.println("ERROR: move is illegal in evalBoard(): " + move);
            }
        }
    }

    // OTHER METHODS AND FIELDS HERE.
    static int evalMove(Board board, Move move, Piece side) {
        // System.out.println("evalMove called");
        if (isWinningMove(board, move, side)) {
            return 10;
        } else if (isWinningMove(board, move, side.opposite())) {
            return -10;
        } else {
            // if 
            return 0;
        }
    }

    static int percentageInPile(Board board, Piece side) {
        return 9;
    }

    // boolean piecesContiguous(Piece side) {
    //     // return false; // FIXME
    //     // iterate thru pieces
    //     // once you find one of side, then you follow the paths it creates. 
    //     HashSet<Point> totalContiguous = new HashSet<Point>();
    //     // System.out.println("pieces contiguous called on " + side);
    //     Point first = findFirst(side);
    //     checkSurrounding(side, first, totalContiguous);
    //     // System.out.println(totalContiguous.size());
    //     return countPieces(side) == totalContiguous.size();
    // }

    // Point findFirst(Piece side) {
    //     for (int r = 1; r <= M; r++) {
    //         for (int c = 1; c <= M; c++) {
    //             if (get(c, r) == side) {
    //                 // System.out.println("first found: " + c + "::" + r);
    //                 return new Point(c, r);
    //             }
    //         }
    //     }
    //     // System.out.println("null calse in first find");
    //     return null;
    // }

    // // add point to totalContiguous, check all surrounding, if not in total contig
    // // then call checksurrounding on those points recursively.
    // void checkSurrounding(Piece side, Point point, HashSet<Point> totalContiguous) {
    //     // System.out.println("piece called");
    //     totalContiguous.add(point);
    //     // System.out.println("check surrounding on " + point);
    //     ArrayList<Point> crList = new ArrayList<Point>();
    //     Direction dir = NOWHERE;
    //     while (dir != NW) {
    //         // System.out.println(dir);
    //         dir = dir.succ();
    //         int c = (int) point.getX() + dir.dc;
    //         int r = (int) point.getY() + dir.dr;
    //         if ((Move.inBounds(c, r)) && (get(c, r) == side)) {
    //             crList.add(new Point(c, r));
    //         }
    //     }
    //     for (Point cr : crList) {
    //         if (!totalContiguous.contains(cr)) {
    //                     checkSurrounding(side, cr, totalContiguous);
    //                 }
    //     }
    // }

    // private int countPieces(Piece side) {
    //     int numPieces = 0;
    //     for (int r = 1; r <= M; r++) {
    //         for (int c = 1; c <= M; c++) {
    //             if (get(c, r) == side) {
    //                 numPieces++;
    //             }
    //         }
    //     }
    //     // System.out.println(numPieces);
    //     return numPieces;
    // }


    // returns iff the move is winning for side. 
    static boolean isWinningMove(Board board, Move move, Piece side) {
        // System.out.println("is winning called on move:" + move + "::" + side);
        board.makeMove(move);
        boolean winning = false;
        if (board.playerWins(side)) {
            // System.out.println("is winning move: true");
            return true;
        }
        board.retract();
        return winning;
    }


    

    int numPiecesTouching(Board board) {
        return 0;
    }
}









