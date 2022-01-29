package Logic;

import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.Calendar;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * This class handles the forming of the Huffman tree
 */

public class WordCounter {
    private HashMap<Character, Integer> wordsCounted;

    private HashMap<Character, String> coded;
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
            String p = "0";

            binaryCalculations(node.left(),  p);
        }

        if (node.right() != null) {
            String p = "1";
            binaryCalculations(node.right(), p);
        }


    }

    public void binaryCalculations(Node node, String code) {

        if (node.right() == null && node.left() == null) {

            coded.put(node.getChar(), code);
        }


        if (node.left() != null) {


            String pass = code +"0";
            binaryCalculations(node.left(), pass);
        }

        if (node.right() != null) {

            String pass = code +"1";
            binaryCalculations(node.right(), pass);
        }


    }


    public HashMap<Character, Integer> getWordsCounted() {
        return wordsCounted;
    }

    public HashMap<Character, String> getCoded() {
        return coded;
    }

    public PriorityQueue<Node> getListedLetters() {
        return listedLetters;
    }



}
