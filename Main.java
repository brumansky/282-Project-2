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
        tree.print();
        System.out.println("Size: " + tree.size());
/*
        System.out.println("Size: " + tree.size());
        System.out.println("isEmpty: " + tree.isEmpty());
        System.out.println("Get: " + tree.get(9));
        System.out.println("ContainsKey: " + tree.containsKey(9));
        System.out.println("ContainsValue: " + tree.containsValue(9));
        System.out.println("ContainsValue: " + tree.containsValue(50));
        System.out.println("Clear: ");
        tree.clear();
        tree.print();
*/

        for (int i = 5; i >= 1; i--) {
            tree.put(i, i);
        }
        tree.print();
        System.out.println("Size: " + tree.size());

        tree.remove(1);
        tree.remove(3);
        tree.remove(4);
        tree.print();
        System.out.println("Size: " + tree.size());


    }
}
