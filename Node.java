// Team Member: Gage Aschenbrenner
// Team Member: Brandon Umansky
// Class:       COMP282
// Assignment:  Project 2

import javafx.scene.Parent;

import java.util.Stack;

import java.util.concurrent.atomic.AtomicBoolean;

public class Node<K extends Comparable, V> {

    private K key;
    private V value;
    private Node<K, V> right;
    private Node<K, V> left;
    private boolean isRed;

    Node(K keyarg, V valuearg) {

        key = keyarg;
        value = valuearg;
        left = null;
        right = null;
        isRed = true;
    }

    Node(K keyarg, V valuearg, boolean red) {

        key = keyarg;
        value = valuearg;
        left = null;
        right = null;
        isRed = red;
    }

    public V put(K keyarg, V valarg, AtomicBoolean replaced) {

        Node parent = this;
        Node tmp = null;
        replaced.set(false);
        V result = null;


        int diff = keyarg.compareTo(this.key);

        if (diff < 0 && parent.left != null) {
            tmp = parent.left;
            if (tmp.isFourNode())
                tmp.recolor();
        } else if (diff > 0 && parent.right != null) {
            tmp = parent.right;
            if (tmp.isFourNode())
                tmp.recolor();
        } else {
            tmp = parent;
            parent = null;
        }

        if (parent == null)
            tmp.isRed = false;

        else if (parent != null) {
            if (parent.isFourNode()) {
                parent.recolor();
                this.isRed = false;
            }
        }

        do {
            diff = keyarg.compareTo(tmp.key);
            if (diff == 0) { // replacement!
                replaced.set(true);
                result = (V) tmp.value;
                tmp.value = valarg;

            } else if (diff < 0) { //left hand direction
                if (tmp.left == null) { // add a new item
                    tmp.left = new Node<>(keyarg, valarg);

                    //fix linear four node
                    if (tmp.isRed) {
                        if (parent.left.equals(tmp)) {
                            parent.rotateRight();
                        } else {
                            tmp.rotateRight();
                            parent.rotateLeft();
                        }
                    }
                    return null;
                } else {
                    // fix four node
                    if (tmp.left.isFourNode()) {
                        tmp.left.recolor();

                        if (tmp.isRed)
                            tmp = fixRecolor(parent, tmp, true);
                    }

                    if (tmp.isFourNode() && parent.isRed == false) {
                        tmp.recolor();
                    }

                    this.isRed = false;

                    parent = tmp;
                    tmp = tmp.left;
                }
            } else {  // right hand direction
                if (tmp.right == null) { // add a new item
                    tmp.right = new Node<>(keyarg, valarg);

                    //fix linear four node
                    if (tmp.isRed) {
                        if (parent.right.equals(tmp)) {
                            parent.rotateLeft();
                        } else {
                            tmp.rotateLeft();
                            parent.rotateRight();
                        }
                    }
                    return null;
                } else {
                    //fix four node
                    if (tmp.right.isFourNode()) {
                        tmp.right.recolor();

                        if (tmp.isRed) {
                            tmp = fixRecolor(parent, tmp, false);
                        }
                    }

                    if (tmp.isFourNode() && parent.isRed == false) {
                        tmp.recolor();
                    }

                    this.isRed = false;

                    parent = tmp;
                    tmp = tmp.right;
                }
            }
        } while (diff != 0 && tmp != null);
        return result;
    }

    private Node fixRecolor(Node parent, Node tmp, boolean isLeft) {

        if (isLeft) {
            if (parent.left.equals(tmp)) {
                parent.rotateLeft();
                return parent;
            } else {
                tmp.rotateRight();
                parent.rotateLeft();
                return parent.right;
            }
        } else {

            if (parent.right.equals(tmp)) {
                parent.rotateLeft();
                return parent;
            } else {
                tmp.rotateLeft();
                parent.rotateRight();

                return parent;
            }
        }
    }

    public V remove(K key, AtomicBoolean removed) {

        Node current = this;
        Node parent = null;
        V deletedValue = rootCases(current, removed);

        if (deletedValue != null)
            return deletedValue;

        while (current != null) {
            int diff = key.compareTo(current.key);
            System.out.println("PARENT" + parent);
            System.out.println("CURRENT " + current);
            System.out.println("DIFF " + diff);
            if (diff < 0) {
                parent = current;
                current = fix2node(current, current.left);
            } else if (diff > 0) {
                parent = current;
                current = fix2node(current, current.right);
            } else {
                //System.out.println("Current: " + current);
                //System.out.println("Parent: " + parent);
                //System.out.println("Root: " + this);
                if (parent.left.key.compareTo(current.key) < 0)
                    parent.left = null;
                else if (parent.right.key.compareTo(current.key) > 0)
                    parent.right = null;
                removed.set(true);
                break;
            }
        }
        return deletedValue;
    }

    public Node fix2node(Node current, Node dest) {

        if (dest.isTwoNode()) {
            if (!current.isRed) {
                if (dest.equals(current.left)) {
                    current.rotateLeft();
                    current = current.left;

                    System.out.println("TEST");
                } else {
                    current.rotateRight();
                    current = dest;
                }
            }
        }
        else if (dest.equals(current.left)) {
            System.out.println(current + "CURRENT");
            if (current.right.isTwoNode()) {
                current.isRed = true;
                dest.isRed = false;
                current.right.isRed = false;
            } else if (!(current.right.left == null || !current.right
                    .left.isRed)) {
                current.right.rotateRight();
            }
            current.rotateLeft();
        } else {
            if (current.left.isTwoNode()) {
                current.isRed = true;
                dest.isRed = false;
                current.left.isRed = false;
            } else if (!(current.left.right == null || !current.left
                    .right.isRed)) {
                current.left.rotateLeft();
            }
            current.rotateRight();
        }
        current = dest;
        return current;
    }

    public boolean containsKey(K keyarg) {

        Node tmp = this;
        int difference;

        while (tmp != null) {
            difference = keyarg.compareTo(tmp.key);
            if (difference == 0)
                return true;
            else if (difference > 0) {
                tmp = tmp.right;
            } else {
                tmp = tmp.left;
            }
        }
        return false;
    }

    public void print() {

        if (this == null)
            return;
        Stack<Node> stack = new Stack<Node>();
        Node node = this;

        while (node != null) {
            stack.push(node);
            node = node.left;
        }

        while (stack.size() > 0) {
            node = stack.pop();
            System.out.println(node);
            if (node.right != null) {
                node = node.right;
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
        }
    }

    public boolean containsValue(V valuearg) {

        Stack<Node> tree = new Stack<>();

        Node tmp = this;

        if (tmp.value.equals(valuearg))
            return true;

        while (tmp != null) {
            tree.push(tmp);
            tmp = tmp.left;
        }

        while (tree.size() > 0) {

            tmp = tree.pop();

            if (tmp.value.equals(valuearg))
                return true;

            if (tmp.right != null) {
                tmp = tmp.right;

                while (tmp != null) {
                    tree.push(tmp);
                    tmp = tmp.left;
                }
            }
        }
        return false;
    }

    public V get(K keyarg) {

        Node tmp = this;
        int difference;

        while (tmp != null) {
            difference = keyarg.compareTo(tmp.key);
            if (difference == 0)
                return (V) tmp.value;
            else if (difference > 0) {
                tmp = tmp.right;
            } else {
                tmp = tmp.left;
            }

        }
        return null;
    }

    private void rotateRight() {

        Node tmp = right;
        right = left;

        if (right != null) {
            left = right.left;
            right.left = right.right;
            right.right = tmp;
        }
        K ktmp = key;
        key = right.key;
        right.key = ktmp;
        V vtmp = value;
        value = right.value;
        right.value = vtmp;
    }

    private void rotateLeft() {

        Node tmp = left;
        left = right;

        if (left != null) {
            right = left.right;
            left.right = left.left;
            left.left = tmp;
        }
        K ktmp = key;
        key = left.key;
        left.key = ktmp;
        V vtmp = value;
        value = left.value;
        left.value = vtmp;
    }

    private void recolor() {

        this.isRed = true;
        this.left.isRed = false;
        this.right.isRed = false;
    }

    private boolean isFourNode() {

        if (this.right != null && this.left != null)
            return (this.right.isRed && this.left.isRed);
        return false;
    }

    private boolean isTwoNode() {

        if (this.isRed) return false;
        else if (this.right == null && this.left == null)
            return true;
        else if (this.right != null && this.left != null) {
            if (!(this.right.isRed) && !(this.left.isRed))
                return true;
        }
        return false;
    }

    // In order successor
    private Node successorOf(Node parent) {

        Node temp = null;

        if (parent.right != null)
            temp = parent.right;
        else return parent;

        while (temp.left != null)
            temp = temp.left;
        return temp;
    }

    private Node successorsParent(Node parent) {

        Node temp = null;

        if (parent.right != null) {
            temp = parent.right;
            if (temp.left == null)
                return parent;
        } else return parent;

        while (temp.left != null) {
            temp = temp.left;
            if (temp.left.left == null)
                break;
        }
        return temp;
    }

    private void reLink(Node successor) {

        if (successor.right != null)
            successor = successor.right;
    }

    private V rootCases(Node current, AtomicBoolean removed) {

        V deletedValue = null;

        if (current.left == null && current.right == null) {
            deletedValue = (V) current.value;
            removed.set(true);
            return deletedValue;
        } else if (current.left.isTwoNode() && current.right.isTwoNode()) {
            current.left.isRed = false;
            current.right.isRed = false;
        }
        return deletedValue;
    }

    private void swapNodes(Node current, Node successor) {

        V tempVal = (V) current.value;
        K tempKey = (K) current.key;
        current.value = successor.value;
        current.key = successor.key;
        successor.key = tempKey;
        successor.value = tempVal;
    }

    private boolean isRightChild(Node parent) {

        if (this.key.compareTo(parent.key) < 0)
            return false;
        return true;
    }

    @Override
    public String toString() {

        return "Key: " + key + "\tValue: " + value + "\tRed: " + isRed;
    }
}
