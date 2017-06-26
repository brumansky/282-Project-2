// Team Member: Gage Aschenbrenner
// Team Member: Brandon Umansky
// Class:       COMP282
// Assignment:  Project 2

public class TreeMap {

    private int size;
    private Node root;

    TreeMap() {

        size = 0;
        root = null;
    }

    public boolean isEmpty() { return (size > 0) ? false : true; }

    public int size() { return size; }
}
