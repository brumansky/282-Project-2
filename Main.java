// Team Member: Gage Aschenbrenner
// Team Member: Brandon Umansky
// Class:       COMP282
// Assignment:  Project 2

public class Main {

    public static void main(String[] args) {

        TreeMap tree = new TreeMap();
        /*
        tree.put(6, 16);
        tree.put(2, 22);
        tree.put(1, 14);
        tree.put(5, 19);
        tree.put(3, 40);
        tree.put(4, 90);
        tree.put(7, 10);
        tree.put(8, 50);
        tree.put(9, 69);
            */
/*
        tree.put(14, 1);
        tree.put(6, 2);
        tree.put(15, 3);
        tree.put(3, 1);
        tree.put(7, 1);
        tree.put(8, 4);
        tree.put(9, 42);
        tree.put(10, 21);

/*
        tree.put(5, 1);
        tree.put(10, 1);
        tree.put(20, 1);
        tree.put(15, 1);
        tree.put(25, 1);
        tree.put(12, 1);
        tree.put(17, 1);
        tree.put(18, 1);
*/

        for (int i = 1; i < 16; i++) {
            tree.put(i, i);
        }
        tree.print();

    }
}
