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

    Node (K keyarg, V valuearg, boolean red) {

        key = keyarg;
        value = valuearg;
        left = null;
        right = null;
        isRed = red;
    }

    public V put (K keyarg, V valarg, AtomicBoolean replaced) {
        Node grandparent = null;
        Node parent = this;
        Node tmp = null;

        //if (parent == null)
        //    tmp.isRed = false;
        // this would make every node black always true

        V result = null;
        int diff = keyarg.compareTo(parent.key);;

        if (diff < 0 && parent.left != null)
            tmp = parent.left;
        else if (diff > 0 && parent.right != null)
            tmp = parent.right;
        else {
            tmp = parent;
        }

        if (this.right != null && this.left != null) {
            if (this.right.isRed && this.left.isRed)
                this.recolor();
        }

        while (diff != 0 && tmp != null) {
            diff =  keyarg.compareTo(tmp.key);

            if (diff == 0) {
                replaced.set(true);
                result = (V) tmp.value;
                tmp.value = valarg;
            } else if (diff < 0) {

                //Don't step on a four node!!!
                if (parent.right != null) {
                    if (tmp.isRed && parent.right.isRed) {
                        parent.recolor();

                        // fixes case when recolored and parent is red
                        if (grandparent != null) {
                            if (grandparent.isRed) {
                                if (keyarg.compareTo(this.key) < 0)
                                    this.rotateRight();
                                else
                                    this.rotateLeft();
                            }
                            //TODO fix this ^ so it doesn't rotate root, not sure how though

                            //root is always black
                            this.isRed = false;
                        }
                    }
                }

                if (tmp.left == null) {
                    tmp.left = new Node<>(keyarg,valarg);

                    //Red nodes can't have red children!!!
                    if (tmp.isRed) {
                        if (tmp.equals(parent.right))
                            tmp.rotateLeft();

                        parent.rotateRight();
                    }
                    return null;
                } else {
                    grandparent = parent;
                    parent = tmp;
                    tmp = tmp.left;
                }
            } else {

                //Don't step on a four node!!!
                if (parent.left != null) {
                    if (tmp.isRed && parent.left.isRed) {
                        parent.recolor();

                        //fixes case when recolored and parent is red
                        if (grandparent != null) {
                            if (grandparent.isRed) {
                                if (keyarg.compareTo(this.key) < 0)
                                    this.rotateRight();
                                else
                                    this.rotateLeft();
                            }
                            //root is always black
                            this.isRed = false;
                        }
                    }
                }

                if (tmp.right == null) {
                    tmp.right = new Node<>(keyarg,valarg);
                    if (tmp.isRed) {

                        if (tmp.equals(parent.left))
                            tmp.rotateRight();

                        parent.rotateLeft();
                    }
                    return null;
                } else {
                    grandparent = parent;
                    parent = tmp;
                    tmp = tmp.right;
                }
            }
        }
        return result;
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

    @Override
    public String toString() {

        return "Key: " + key + "\tValue: " + value + "\tRed: " + isRed;
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

    private boolean isFourNode() { return (this.right.isRed && this.left.isRed); }
}
