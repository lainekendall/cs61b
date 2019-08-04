import java.util.List;
import java.util.ArrayList;

public class IntDList {

    private DNode _front, _back;

    public IntDList() {
        _front = _back = null;
    }

    public IntDList(Integer... values) {
        _front = _back = null;
        for (int val : values) {
            insertBack(val);
        }
    }

    public int getFront() {
        return _front._val;
    }

    /** Returns the last item in the IntDList. */
    public int getBack() {
        return _back._val;
    }

    /** Return value #I in this list, where item 0 is the first, 1 is the
     *  second, ...., -1 is the last, -2 the second to last.... */
    public int get(int i) {
        if (i >= 0) {
            int item = 0;
            DNode firstNodePointer = this._front;
            if ((this._back == null) && (this._front == null)) { // length = 0
                return item;
            }
            while (firstNodePointer != null) {
                if (i == 0) {
                    item = firstNodePointer._val;
                }
                i -= 1;
                firstNodePointer = firstNodePointer._next;
            }
            return item;
        } else {
            int item = 0;
            DNode lastNodePointer = this._back;
            if ((this._back == null) && (this._front == null)) { // length = 0
                return item;
            }
            while (lastNodePointer != null) {
                if (i == -1) {
                    item = lastNodePointer._val;
                }
                i += 1;
                lastNodePointer = lastNodePointer._prev;
            }
            return item;
        }
    }

    /** The length of this list. */
    public int size() {
        if ((this._back == null) && (this._front == null)) { // length = 0
            return 0;
        } else if (this._front._next == null) {
            return 1;
        } else {
            DNode first = this._front;
            int len = 1;
            while (first._next != null) {
                first = first._next;
                len +=1;
            }
            return len;
        }
    }

    /** Adds D to the front of the IntDList. */
    public void insertFront(int d) {
        if ((this._back == null) && (this._front == null)) { // length = 0
            this._front = this._back = new DNode (null, d, null);
        } else { 
            DNode nextdNode = this._front;
            DNode newNode = new DNode (null, d, nextdNode);
            this._front._prev = newNode;
            this._front = newNode;
        }
    }

    /** Adds D to the back of the IntDList. */
    public void insertBack(int d) { // assume intdlist is null, null
        if ((this._back == null) && (this._front == null)) { // length = 0
            this._front = this._back = new DNode (null, d, null);
        } else { 
            DNode previousNode = this._back;
            DNode newNode = new DNode (previousNode, d, null);
            this._back._next = newNode;
            this._back = newNode;
        }
    }

    /** Removes the last item in the IntDList and returns it.
     * This is an extra challenge problem. */
    public int deleteBack() {
        // Your code here
        if ((this._back == null) && (this._front == null)) { // length = 0
            return 0;
        } else if (this._front._next == null) { // if length is 1
            DNode deletedNode = this._back;
            int deletedNodeValue = deletedNode._val;
            this._front = this._back = null;
            return deletedNodeValue;
        } else { // if length is > 1
            DNode deletedNode = this._back;
            int deletedNodeValue = deletedNode._val;
            DNode secondToLast = this._back._prev;
            secondToLast._next = null;
            this._back = secondToLast;
            return deletedNodeValue;
        }
    }    
/*  1. store last deleted node in variable (to be returned later)
    2. delete second to last node's next pointer (change to null)
    3. change this's back pointer to second to last
    }

    /** Returns a string representation of the IntDList in the form
     *  [] (empty list) or [1, 2], etc. 
     * This is an extra challenge problem. */
    public String toString() {
        // Your code here
        ArrayList<Integer> result = new ArrayList<Integer>();
        if ((this._back == null) && (this._front == null)) { // length = 0
            return result.toString();
        } else {
            DNode firstNode = this._front;
            while (firstNode != null) { // check to see if the list is empty
                result.add(firstNode._val); // add value to our new list
                firstNode = firstNode._next; // traverse along list
            }
        }
        return result.toString();
    }

    /* DNode is a "static nested class", because we're only using it inside
     * IntDList, so there's no need to put it outside (and "pollute the
     * namespace" with it. */
    private static class DNode {
        protected DNode _prev;
        protected DNode _next;
        protected int _val;

        private DNode(int val) {
            this(null, val, null);
        }

        private DNode(DNode prev, int val, DNode next) {
            _prev = prev;
            _val = val;
            _next = next;
        }
    }

}
