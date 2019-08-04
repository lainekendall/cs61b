// Remove all comments that begin with //, and replace appropriately.
// Feel free to modify ANYTHING in this file.
package loa;

import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.Character;

import static loa.Piece.*;
import static loa.Main.*;

/** Represents one game of Lines of Action.
 *  @author  */
class Game {

    /** A new series of Games. */
    Game() {
        _randomSource = new Random();

        _players = new Player[2];
        _input = new BufferedReader(new InputStreamReader(System.in));
        _players[0] = new HumanPlayer(BP, this);
        _players[1] = new MachinePlayer(WP, this);
        _playing = false;
    }

    /** Return the current board. */
    Board getBoard() {
        return _board;
    }

    /** Quit the game. */
    private void quit() {
        System.exit(0);
    }

    /** Return a move.  Processes any other intervening commands as
     *  well.  Exits with null if the value of _playing changes. */
    Move getMove() {
        try {
            boolean playing0 = _playing;
            while (_playing == playing0) {
                prompt();

                String line = _input.readLine();
                if (line == null) {
                    quit();
                }

                line = line.trim();
                if (!processCommand(line)) {
                    Move move = Move.create(line, _board);
                    if (move == null) {
                        error("invalid move: %s%n", line);
                    } else if (!_playing) {
                        error("game not started");
                    } else if (!_board.isLegal(move)) {
                        error("illegal move: %s%n", line);
                    } else {
                        return move;
                    }
                }
            }
        } catch (IOException excp) {
            error(1, "unexpected I/O error on input");
        }
        return null;
    }

    /** Print a prompt for a move. */
    private void prompt() {
        if (_playing) {
            System.out.print(_board.turn().abbrev());
        }
        System.out.print("> ");
        System.out.flush();
    }

    /** Describes a command with up to two arguments. */
    private static final Pattern COMMAND_PATN =
        Pattern.compile("(#|\\S+)\\s*(\\S*)\\s*(\\S*).*");

    /** If LINE is a recognized command other than a move, process it
     *  and return true.  Otherwise, return false. */
    private boolean processCommand(String line) {
        if (line.length() == 0) {
            return true;
        }
        Matcher command = COMMAND_PATN.matcher(line);
        if (command.matches()) {
            switch (command.group(1).toLowerCase()) {
            case "#":
                return true;
            case "manual":
                manualCommand(command.group(2).toLowerCase());
                return true;
            case "auto":
                autoCommand(command.group(2).toLowerCase());
                return true;
            case "seed":
                seedCommand(command.group(2));
                return true;

            // FILL THIS IN
            case "clear":
                // out.println("clear command");
                _board.clear();
                _playing = true;
                return true;
            case "start":
                // out.println("start command");
                _playing = true;
                return true;
            case "dump":
                // out.println("dump command");
                _board.print();
                return true;
            case "set":
                // out.println("set command");
                _playing = false;
                String cr = command.group(2);
                // System.out.println(cr);
                char c = cr.charAt(0);
                // System.out.println(c);
                int col = Character.getNumericValue(c) - 9;
                // System.out.println(col);
                String r = cr.substring(1,2);
                int row = Integer.parseInt(r);
                // System.out.println(row);
                String p = command.group(3);
                Piece piece = Piece.setValueOf(p);
                _board.set(col, row, piece, piece.opposite());
                return true;
            case "quit":
                quit();


            case "help":
                help();
                return true;
            default:
                return false;
            }
        }
        return false;
    }



    // private String playerName(String s) {
    //     if (s.equals("b")) {
    //         return "black";
    //     } else if (s.equals("w")) {
    //         return "white";
    //     } else if (s.equals("e")) {
    //         return "empty";
    //     }
    //     return null;
    // }

    /** Set player PLAYER ("white" or "black") to be a manual player. */
    private void manualCommand(String player) {
        try {
            Piece s = Piece.playerValueOf(player);
            _playing = false;
            _players[s.ordinal()] = new HumanPlayer(s, this);
        } catch (IllegalArgumentException excp) {
            error("unknown player: %s", player);
        }
    }

    /** Set player PLAYER ("white" or "black") to be an automated player. */
    private void autoCommand(String player) {
        try {
            Piece s = Piece.playerValueOf(player);
            _playing = false;
            _players[s.ordinal()] = new MachinePlayer(s, this);
        } catch (IllegalArgumentException excp) {
            error("unknown player: %s", player);
        }
    }

    /** Seed random-number generator with SEED (as a long). */
    private void seedCommand(String seed) {
        try {
            _randomSource.setSeed(Long.parseLong(seed));
        } catch (NumberFormatException excp) {
            error("Invalid number: %s", seed);
        }
    }

    /** Play this game, printing any results. */
    public void play() {
        HashSet<Board> positionsPlayed = new HashSet<Board>();
        _board = new Board();

        while (true) {
            int playerInd = _board.turn().ordinal();
            Move next;
            if (_playing) {
                if (_board.gameOver()) {
                    announceWinner();
                    _playing = false;
                    continue;
                }
                next = _players[playerInd].makeMove();
                assert !_playing || next != null;
            } else {
                getMove();
                next = null;
            }
            if (next != null) {
                assert _board.isLegal(next);
                _board.makeMove(next);
                if (_board.gameOver()) {
                    announceWinner();
                    _playing = false;
                }
            }
        }
    }

    /** Print an announcement of the winner. */
    private void announceWinner() {
        // FIXME
        Piece current = _board.turn();
        Piece lastTurn = current.opposite();
        if (_board.playerWins(lastTurn)) {
            out.println(lastTurn.fullName() + " wins!");
        } else if (_board.playerWins(current)) {
            out.println(current.fullName() + " wins!");
        }
    }

    /** Return an integer r, 0 <= r < N, randomly chosen from a
     *  uniform distribution using the current random source. */
    int randInt(int n) {
        return _randomSource.nextInt(n);
    }

    /** Print a help message. */
    void help() {
        System.out.print("Commands: Commands are whitespace-delimited.");
        System.out.println("Other trailing text on a line is ignored. Comment lines begin with # and are ignored.");
        System.out.println("b");
        System.out.println("board     Display the board, showing row and column designations.");
        System.out.println("autoprint Toggle mode in which the board is printed (as for b) after each AI move.");
        System.out.println("start     Start playing from the current position.");
        System.out.println("uv-xy     A move from square uv to square xy.  Here u and v are column designations (a-h) and v and y are row designations (1-8): ");
        System.out.println("clear     Stop game and return to initial position.");
        System.out.println("seed N    Seed the random number with integer N.");
        System.out.println("auto P    P is white or black; makes P into an AI. Stops game.");
        System.out.println("manual P  P is white or black; takes moves for P from terminal. Stops game.");
        System.out.println("set cr P  Put P ('w', 'b', or empty) into square cr. Stops game.");
        System.out.println("dump      Display the board in standard format.");
        System.out.println("quit      End program.");
        System.out.println("help");
        System.out.println("?         This text.");
    }

    /** The official game board. */
    private Board _board;

    /** The _players of this game. */
    private Player[] _players = new Player[2];

    /** A source of random numbers, primed to deliver the same sequence in
     *  any Game with the same seed value. */
    private Random _randomSource;

    /** Input source. */
    private BufferedReader _input;

    /** True if actually playing (game started and not stopped or finished).
     */
    private boolean _playing;

}
