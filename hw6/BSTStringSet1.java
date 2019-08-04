public class BSTStringSet1 implements StringSet {

	private class Node {
        /** String stored by this node. */
        private String label;

        /** left node in the linked list. */
        private Node left;

        /** right node in the linked list. */
        private Node right;

        /** Creates a new node containing the given string SP. */
        public Node(String sp, Node right, Node left) {
            label = sp;
            this.right = right;
            this.left = left;
        }

        public void setRight(Node n) {
        	this.right = n;
        }

        public void setLeft(Node n) {
        	this.left = n;
        }

        public String label() {
        	return this.label;
        }

        public Node right() {
        	return this.right;
        }
        public Node left() {
        	return this.left;
        }
    }

	BSTStringSet1() {
		front = new Node("dummy", null, null);
	}

	BSTStringSet1(String s) {
		front = new Node(s, null, null);
	}

	private Node front;

	public boolean contains(String s) {
		return true;
	}

	public void put(String s) {
	}
}
