package Logic;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * This class handles the forming of the Huffman tree
 */

public class HuffmanGenerator {
    public Node headOfTree;
    private HashMap<Character, Integer> wordsCounted;
    private HashMap<Character, String> codes;
    private HashMap<Character, byte[]> coded;
    private PriorityQueue<Node> listedLetters;

    public HuffmanGenerator() {

        wordsCounted = new HashMap<>();
        coded = new HashMap<>();
        listedLetters = new PriorityQueue<>();
        codes = new HashMap<>();
    }


    /**
     * Counts all the characters of the text and adds them to a map, then moves the data to a  PriorityQueue.
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
     * This handles the start of the methods that handles the  binary value which each char will have.
     */

    public void binaryCalculations() {

        headOfTree = listedLetters.poll();

        if (headOfTree.left() != null) {


            binaryCalculations(headOfTree.left(), 0b0, "0");
        }

        if (headOfTree.right() != null) {


            binaryCalculations(headOfTree.right(), 0b1, "1");
        }


    }

    /**
     * @param node       leaf to be searched through
     * @param javaIsShit current bit combination
     * @param bit        contains current bit representation as String.
     */

    public void binaryCalculations(Node node, int javaIsShit, String bit) {

        if (node.right() == null && node.left() == null) {
            ByteBuffer b = ByteBuffer.allocate(4);
            b.putInt(javaIsShit);
            codes.put(node.getChar(), bit);
            coded.put(node.getChar(), b.array());
        }


        if (node.left() != null) {

            int binary = (javaIsShit << 1);

            binaryCalculations(node.left(), binary, bit + "0");
        }

        if (node.right() != null) {
            int binary = (javaIsShit << 1) + 1;

            binaryCalculations(node.right(), binary, bit + "1");
        }


    }


    public HashMap<Character, Integer> getWordsCounted() {
        return wordsCounted;
    }

    public HashMap<Character, byte[]> getCoded() {
        return coded;
    }

    public HashMap<Character, String> getCodes() {
        return codes;
    }

    public PriorityQueue<Node> getListedLetters() {
        return listedLetters;
    }


}
