// Team Member: Gage Aschenbrenner
// Team Member: Brandon Umansky
// Class:       COMP282
// Assignment:  Project 2

import java.util.Stack;

import java.util.concurrent.atomic.AtomicBoolean;

public class Node<K extends Comparable, V> {

    private K key;
    private V value;
    private Node<K , V> right;
    private Node<K , V> left;
    private boolean isRed;

    Node (K keyarg, V valuearg) {

        key = keyarg;
        value = valuearg;
        left = null;
        right = null;
        isRed = true;
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

    public V put(K key, V val, AtomicBoolean replaced) {

        Node<K, V> parent = null;
        Node<K, V> temp = this;

        if (parent == null)
            temp.isRed = false;

        V result = null;
        int diff = -1;

        replaced.set(false);

        while (temp != null) {
            diff = key.compareTo(temp.key);
            if (diff == 0) { // replacement
                replaced.set(true);
                result = temp.value;
                temp.value = val;
            } else if (diff < 0) { // left direction
                if (temp.left == null) {
                    left = new Node<>(key, val);
                    return null;
                } else {
                    parent = temp;
                    temp = temp.left;
                }
            } else { // right direction
                if (temp.right == null) {
                    right = new Node<>(key, val);
                    return null;
                } else {
                    parent = temp;
                    temp = temp.right;
                }
            }
        }
        return result;
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

    @Override
    public String toString() {

        return "Key: " + key + "\tValue :" + value;
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
        left = right.left;
        right.left = right.right;
        right.right = tmp;
        K ktmp = key;
        key = right.key;
        right.key = ktmp;
        V vtmp = value;
        value = right.value;
        right.value = vtmp;

    }

    private void leftRotate() {
        Node tmp = left;
        left = right;
        right = left.right;
        left.right = left.left;
        left.left = tmp;
        K ktmp = key;
        key = left.key;
        left.key = ktmp;
        V vtmp = value;
        value = left.value;
        left.value = vtmp;
    }
}
