// Team Member: Gage Aschenbrenner
// Team Member: Brandon Umansky
// Class:       COMP282
// Assignment:  Project 2

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
}
