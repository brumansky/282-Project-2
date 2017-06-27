// Team Member: Gage Aschenbrenner
// Team Member: Brandon Umansky
// Class:       COMP282
// Assignment:  Project 2

import java.util.concurrent.atomic.AtomicBoolean;

public class TreeMap<K extends Comparable, V>{

    private int size;
    private Node root;

    public TreeMap() {

        size = 0;
        root = null;
    }

    public boolean isEmpty() { return (size > 0) ? false : true; }

    public int size() { return size; }

    public void clear() { root = null; }

    public V put(K key, V val) {

        if (root == null) {
            root = new Node<K, V>(key, val);
            size++;
            return null;
        }
        else {
            AtomicBoolean replaced = new AtomicBoolean(false);
            V result = (V)root.put(key, val, replaced);
            if (!replaced.get())
                size++;
            return result;
        }
    }

    public void print() {
        root.print();
        System.out.println("Size: " + size);
    }
}
