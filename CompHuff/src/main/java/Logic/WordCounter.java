package Logic;

import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * This class handles the forming of the Huffman tree
 */

public class WordCounter {
    private HashMap<Character, Integer> wordsCounted;

    private HashMap<Character, CharSequence> coded;
    private PriorityQueue<Node> listedLetters;

    public WordCounter() {

        wordsCounted = new HashMap<>();
        coded = new HashMap<>();
        listedLetters = new PriorityQueue<>();

    }


    /**
     *  Counts all the characters of the text and adds them to a map, then moves the data to a  PriorityQueue.
     *
     * @param text
     */

    public void count(String text) {


        for (int i = 0; i < text.length(); i++) {

            wordsCounted.put(text.charAt(i), wordsCounted.getOrDefault(text.charAt(i), 0) + 1);

        }

        for (char c : wordsCounted.keySet()) {

            listedLetters.offer(new Leaf(c, wordsCounted.get(c)));

        }

    }

    /**
     * This creates forms the huffman via PriorityQueue.
     */
    public void treeForming() {

        while (listedLetters.size() > 1) {

            Node first = listedLetters.poll();

            Node second = listedLetters.poll();

             //System.out.println(second.getChar()+" "+ first.getChar()+ "  "+(first.getWeight()+second.getWeight()));



            Node tree = new Leaf(null, (first.getWeight() + second.getWeight()));

            tree.setLeft(first);
            tree.setRight(second);

            listedLetters.add(tree);
        }



    }

    /**
     *
     * This handles the binary value each char will have.
     */

    public void binaryCalculations() {

        Node node = listedLetters.poll();

        if (node.left() != null) {

            binaryCalculations(node.left(), "0");
        }

        if (node.right() != null) {

            binaryCalculations(node.right(), "1");
        }

    }

    public void binaryCalculations(Node node, String code) {
        if (node.right() == null && node.left() == null) {
            coded.put(node.getChar(), code);
        }


        if (node.left() != null) {
            String temp = code;
            binaryCalculations(node.left(), temp += "0");
        }

        if (node.right() != null) {
            String temp = code;
            binaryCalculations(node.right(), temp += "1");
        }


    }

    public HashMap<Character, Integer> getWordsCounted() {
        return wordsCounted;
    }

    public HashMap<Character, CharSequence> getCoded() {
        return coded;
    }

    public PriorityQueue<Node> getListedLetters() {
        return listedLetters;
    }

    /*
    static void print2DUtil(Node root, int space) {
        // Base case
        if (root == null) {
            return;
        }

        // Increase distance between levels
        space += COUNT;

        // Process right child first
        print2DUtil(root.right(), space);

        // Print current node after space
        // count
        System.out.print("\n");
        for (int i = COUNT; i < space; i++) {
            System.out.print(" ");
            System.out.print(root + "\n");
        }

        // Process left child
        print2DUtil(root.left(), space);
    }

    // Wrapper over print2DUtil()
    static void print2D(Node root) {
        // Pass initial space count as 0
        print2DUtil(root, 0);
    }
*/


}
