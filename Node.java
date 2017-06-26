// Team Member: Gage Aschenbrenner
// Team Member: Brandon Umansky
// Class:       COMP282
// Assignment:  Project 2

import java.util.Stack;

public class Node<K extends Comparable, V>{
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
}
