// Remove all comments that begin with //, and replace appropriately.
// Feel free to modify ANYTHING in this file.
package loa;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Formatter;
import java.util.NoSuchElementException;

import java.util.regex.Pattern;
import java.awt.Point;
import java.util.HashSet;

import static loa.Piece.*;
import static loa.Direction.*;

/** Represents the state of a game of Lines of Action.
 *  @author
 */
class Board implements Iterable<Move> {

    /** Size of a board. */
    static final int M = 8;

    /** Pattern describing a valid square designator (cr). */
    static final Pattern ROW_COL = Pattern.compile("^[a-h][1-8]$");

    /** A Board whose initial contents are taken from INITIALCONTENTS
     *  and in which the player playing TURN is to move. The resulting
     *  Board has
     *        get(col, row) == INITIALCONTENTS[row-1][col-1]
     *  Assumes that PLAYER is not null and INITIALCONTENTS is MxM.
     *
     *  CAUTION: The natural written notation for arrays initializers puts
     *  the BOTTOM row of INITIALCONTENTS at the top.
     */
    Board(Piece[][] initialContents, Piece turn) {
        initialize(initialContents, turn);
    }

    /** A new board in the standard initial position. */
    Board() {
        clear();
    }

    /** A Board whose initial contents and state are copied from
     *  BOARD. */
    Board(Board board) {
        copyFrom(board);
    }

    /** Set my state to CONTENTS with SIDE to move. */
    void initialize(Piece[][] contents, Piece side) {
        _moves.clear();
        _currentPieces = new Piece[M][M];

        for (int r = 1; r <= M; r += 1) {
            for (int c = 1; c <= M; c += 1) {
                set(c, r, contents[r - 1][c - 1]);
            }
        }
        _turn = side;
    }

    /** Set me to the initial configuration. */
    void clear() {
        initialize(INITIAL_PIECES, BP);
    }

    /** Set my state to a copy of BOARD. */
    void copyFrom(Board board) {
        if (board == this) {
            return;
        }
        _moves.clear();
        _moves.addAll(board._moves);
        _turn = board._turn;
        initialize(board._currentPieces, _turn);

    }

    /** Return the contents of column C, row R, where 1 <= C,R <= 8,
     *  where column 1 corresponds to column 'a' in the standard
     *  notation. */
    Piece get(int c, int r) {
        return _currentPieces[r - 1][c - 1];

    }

    /** Return the contents of the square SQ.  SQ must be the
     *  standard printed designation of a square (having the form cr,
     *  where c is a letter from a-h and r is a digit from 1-8). */
    Piece get(String sq) {
        return get(col(sq), row(sq));
    }

    /** Return the column number (a value in the range 1-8) for SQ.
     *  SQ is as for {@link get(String)}. */
    static int col(String sq) {
        if (!ROW_COL.matcher(sq).matches()) {
            throw new IllegalArgumentException("bad square designator");
        }
        return sq.charAt(0) - 'a' + 1;
    }

    /** Return the row number (a value in the range 1-8) for SQ.
     *  SQ is as for {@link get(String)}. */
    static int row(String sq) {
        if (!ROW_COL.matcher(sq).matches()) {
            throw new IllegalArgumentException("bad square designator");
        }
        return sq.charAt(1) - '0';
    }

    /** Set the square at column C, row R to V, and make NEXT the next side
     *  to move, if it is not null. */
    void set(int c, int r, Piece v, Piece next) {
        _currentPieces[r - 1][c - 1] = v;
        if (next != null) {
            _turn = next;
        }
    }

    /** Print the board. */ 
    void print() {
        System.out.println("===");
        for (int r = M; r >= 1; r--) {
            System.out.print("    ");
            for (int c = 1; c <= M; c++) {
                Piece p = get(c, r);
                System.out.print(p.abbrev() + " ");
            }
            System.out.println();
        }
        System.out.println("Next move: " + turn().fullName());
        System.out.println("===");
    }

    /** Set the square at column C, row R to V. */
    void set(int c, int r, Piece v) {
        set(c, r, v, null);
    }

    /** Assuming isLegal(MOVE), make MOVE. */
    void makeMove(Move move) {
        assert isLegal(move);
        _moves.add(move);
        Piece replaced = move.replacedPiece();
        int c0 = move.getCol0(), c1 = move.getCol1();
        int r0 = move.getRow0(), r1 = move.getRow1();
        if (replaced != EMP) {
            set(c1, r1, EMP);
        }
        set(c1, r1, move.movedPiece());
        set(c0, r0, EMP);
        _turn = _turn.opposite();
    }

    /** Retract (unmake) one move, returning to the state immediately before
     *  that move.  Requires that movesMade () > 0. */
    void retract() {
        assert movesMade() > 0;
        Move move = _moves.remove(_moves.size() - 1);
        Piece replaced = move.replacedPiece();
        int c0 = move.getCol0(), c1 = move.getCol1();
        int r0 = move.getRow0(), r1 = move.getRow1();
        Piece movedPiece = move.movedPiece();
        set(c1, r1, replaced);
        set(c0, r0, movedPiece);
        _turn = _turn.opposite();
    }

    /** Return the Piece representing who is next to move. */
    Piece turn() {
        return _turn;
    }

    /** Return true iff MOVE is legal for the player currently on move.
     *  We already know that the move the starting and ending positions are in bounds
     *  and that starting position doesn't equal the ending position and
     *  that the direction of move is valid. */
    boolean isLegal(Move move) {
        // return move != null; // FIXME
        if (move == null) {
            // System.out.println("move is null");
            return false;
        } else if (move.movedPiece() != turn()) {
            return false;
        } else if (move.length() != pieceCountAlong(move)) {
            // System.out.println("move.len is: " + move.length() + "dne pieceCountAlong: " + pieceCountAlong(move));
            return false;
        } 
        return !blocked(move);
    }

    /** Return a sequence of all legal moves from this position. */
    Iterator<Move> legalMoves() {
        return new MoveIterator();
    }

    @Override
    public Iterator<Move> iterator() {
        return legalMoves();
    }

    /** Return true if there is at least one legal move for the player
     *  on move. */
    public boolean isLegalMove() {
        return iterator().hasNext();
    }

    /** Return true iff either player has all her pieces continguous. */
    boolean gameOver() {
        return piecesContiguous(BP) || piecesContiguous(WP);
    }

    /** Return true iff SIDE wins. */
    boolean playerWins(Piece piece) {
        return piecesContiguous(piece);
    }

    /** Return true iff SIDE's pieces are continguous. */
    boolean piecesContiguous(Piece side) {
        HashSet<Point> totalContiguous = new HashSet<Point>();
        Point first = findFirst(side);
        checkSurrounding(side, first, totalContiguous);
        return countPieces(side) == totalContiguous.size();
    }

    /** Find the first piece of SIDE. */
    Point findFirst(Piece side) {
        for (int r = 1; r <= M; r++) {
            for (int c = 1; c <= M; c++) {
                if (get(c, r) == side) {
                    return new Point(c, r);
                }
            }
        }
        return null;
    }

    /** add point to totalContiguous, check all surrounding, if not in total contig
     *  then call checksurrounding on those points recursively. */
    void checkSurrounding(Piece side, Point point, HashSet<Point> totalContiguous) {
        totalContiguous.add(point);
        ArrayList<Point> crList = new ArrayList<Point>();
        Direction dir = NOWHERE;
        while (dir != NW) {
            dir = dir.succ();
            int c = (int) point.getX() + dir.dc;
            int r = (int) point.getY() + dir.dr;
            if ((Move.inBounds(c, r)) && (get(c, r) == side)) {
                crList.add(new Point(c, r));
            }
        }
        for (Point cr : crList) {
            if (!totalContiguous.contains(cr)) {
                        checkSurrounding(side, cr, totalContiguous);
                    }
        }
    }

    /** Counts the number of pieces total on board for SIDE. */
    private int countPieces(Piece side) {
        int numPieces = 0;
        for (int r = 1; r <= M; r++) {
            for (int c = 1; c <= M; c++) {
                if (get(c, r) == side) {
                    numPieces++;
                }
            }
        }
        return numPieces;
    }

    /** Return the total number of moves that have been made (and not
     *  retracted).  Each valid call to makeMove with a normal move increases
     *  this number by 1. */
    int movesMade() {
        return _moves.size();
    }

    @Override
    public boolean equals(Object obj) {
        Board b = (Board) obj;
        return b.hashCode() == hashCode();
    }


    @Override
    public int hashCode() {
        // return 0; // FIXME
        String board = "";
        for (int r = 1; r <= M; r++) {
            for (int c = 1; c <= M; c++) {
                board.concat(get(c, r).abbrev());
            }
        }
        return board.hashCode();
    }

    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("===%n");
        for (int r = M; r >= 1; r -= 1) {
            out.format("    ");
            for (int c = 1; c <= M; c += 1) {
                out.format("%s ", get(c, r).abbrev());
            }
            out.format("%n");
        }
        out.format("Next move: %s%n===", turn().fullName());
        return out.toString();
    }

    /** Return the number of pieces in the line of action indicated by MOVE. */
    int pieceCountAlong(Move move) {
        // return 1;  // FIXME
        // int movedVertical = move.getRow1() - move.getRow0();
        // int movedHorizontal = move.getCol1() - move.getCol0();
        // return Math.max(movedVertical, movedHorizontal);
        // System.out.println(move.getCol0() + "::" + move.getRow0() + "::" + move.getDirection());
        return pieceCountAlong(move.getCol0(), move.getRow0(), move.getDirection());
    }

    /** Return the number of pieces in the line of action in direction DIR and
     *  containing the square at column C and row R. */
    int pieceCountAlong(int c, int r, Direction dir) {
        // return 1;  // FIXME
        int numPieces = 0;
        int col = c;
        int row = r;
        while (Move.inBounds(col, row)) {
            if (get(col, row) != EMP) {
                    numPieces += 1;
                }
            col += dir.dc;
            row += dir.dr;
        }
        col = c - dir.dc;
        row = r - dir.dr;
        while (Move.inBounds(col, row)) {
            if (get(col, row) != EMP) {
                    numPieces += 1;
                }
            col -= dir.dc;
            row -= dir.dr;
        }
        return numPieces;
    }

    /** Assuming MOVE is not null, return true iff MOVE is blocked by an opposing piece or by a
     *  friendly piece on the target square. */
    private boolean blocked(Move move) {
        // return false;  // FIXME

        if (move.movedPiece() == move.replacedPiece()) {
            return true;
        }
        return (hasOpposingPiece(move.getCol0(), move.getRow0(), move.getDirection(), move.length()));
    }

    /** Checks to see if there is an opposing piece in the spaces you move. */
    boolean hasOpposingPiece(int c, int r, Direction dir, int spaces) {
        for (int i = 1; i <= spaces; i++) {
            if ((Move.inBounds(c, r)) && (get(c, r) == _turn.opposite())) {
                    return true;
                }
            c += dir.dc;
            r += dir.dr;
        }
        
        return false;
    }

    /** The standard initial configuration for Lines of Action. */
    static final Piece[][] INITIAL_PIECES = {
        { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP }
    };

    /** List of all unretracted moves on this board, in order. */
    private final ArrayList<Move> _moves = new ArrayList<>();
    /** Current side on move. */
    private Piece _turn;

    // FILL IN
    private Piece[][] _currentPieces = INITIAL_PIECES;

    /** An iterator returning the legal moves from the current board. */
    private class MoveIterator implements Iterator<Move> {
        /** Current piece under consideration. */
        private int _c, _r;
        /** Next direction of current piece to return. */
        private Direction _dir;
        /** Next move. */
        private Move _move;

        /** A new move iterator for turn(). */
        MoveIterator() {
            _c = 1; _r = 1; _dir = N;
            incr();
        }

        @Override
        public boolean hasNext() {
            return _move != null;
        }

        @Override
        public Move next() {
            if (_move == null) {
                throw new NoSuchElementException("no legal move");
            }

            Move move = _move;
            // System.out.println("here");
            incr();
            return move;
        }

        @Override
        public void remove() {
        }

        /** Get the next legal piece. */
        private void nextPiece() {
            while ((Move.inBounds(_c, _r)) && (get(_c, _r) != Board.this.turn())) {
                // System.out.println("next piece called on " + _c + "::" + _r);
                advanceCoordinates();
            }
        }
        /** Advance to the next legal coordinates. */
        private void advanceCoordinates() {
            _c += 1;
            if (_c > M) {
                _c = 1;
                _r += 1;
            }
            if (_r > M) {
                _move = null;
                return;
            }
        }

        /** Advance to the next legal move. */
        private void incr() {
            // FIXME
            _move = null;
            while (_move == null) {
                // System.out.println("incr on " + _c + "::" + _r);
                if (!Move.inBounds(_c, _r)) {
                    // System.out.println("out of bounds");
                    _move = null;
                    return;
                } if (get(_c, _r) != Board.this.turn()) {
                    nextPiece();
                }
                // now get(_c, _r) is equal to our next piece
                while ((_move == null) && (_dir != null)) {
                    // make move starting at spot and ending at dir.dr * piecescountalong
                    // System.out.println("dir is " + _dir);
                    // System.out.println(Board.this.pieceCountAlong(_c, _r, _dir));
                    _move = Move.create(_c, _r, Board.this.pieceCountAlong(_c, _r, _dir), _dir,
                           Board.this);
                    if (!isLegal(_move)) {
                        // System.out.println("move is illegal in incr(): " + _move);
                        _move = null;
                    }
                    // System.out.println("move is: " + _move);
                    _dir = _dir.succ();
                    // _dir = _dir.succ();
                    if (_move != null) {
                        // System.out.println("move is not null, return");
                        return;
                    }
                }
                advanceCoordinates();
                nextPiece();
                _dir = N;
            }
        }
    }
}
