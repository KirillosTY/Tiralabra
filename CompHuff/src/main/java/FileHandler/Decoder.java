package FileHandler;

import Logic.LZW;
import Logic.Leaf;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Decoder {

    public ArrayList<Integer> listLZW;
    public Leaf treeFormed;
    private int maxBytes = 0;
    private short numberOfLeaves = 0;
    private ByteBuffer buffMe;
    private StringBuilder binaryFormer;
    private StringBuilder textFormer;

    public boolean workDone = true;

    public Decoder() {
        textFormer = new StringBuilder();
        treeFormed = new Leaf(null, 0);
        listLZW = new ArrayList<>();

    }

    public StringBuilder getTextFormer() {

        return textFormer;
    }

    /**
     * This opens the encoded file and starts to decode it.
     *
     * @param pathToFile path from where the file is read from.
     */


    public void readFileLZW(String pathToFile, String pathToWrite) {
        LZW decomp = new LZW();
        listLZW = new ArrayList<>();
        workDone = false;
        try {
            ByteArrayInputStream input = new ByteArrayInputStream(new FileInputStream(pathToFile).readAllBytes());
            buffMe = ByteBuffer.allocate(4);
            buffMe.put(input.readNBytes(4));
            if (buffMe.getInt(0) != 0b10000000000000000000000000000001) {
                throw new IOException("The fuck are you feeding me");
            }
            while (input.available() > 0) {
                buffMe = ByteBuffer.allocate(4);
                buffMe.put(input.readNBytes(4));
                listLZW.add(buffMe.getInt(0));
            }
            textFormer.append(decomp.decompress(listLZW));
            writeTextToFile(pathToWrite);

            input.close();

        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public void readFileHuff(String pathToFile, String pathToWrite) {

        buffMe = ByteBuffer.allocate(20);
        workDone = false;
        try {

            ByteArrayInputStream input = new ByteArrayInputStream(new FileInputStream(pathToFile).readAllBytes());
            maxBytes = 32;
            for (byte b : input.readNBytes(4)) {
                maxBytes = (maxBytes << 8) + (b & 0xFF); // first 32 bits tells if its done by huff compression.
            }
            if (maxBytes != 0b11111111111111111111111111111110) {
                throw new IOException("Why are you trying to feed me some random shit?");
            }
            numberOfLeaves = buffMe.put(input.readNBytes(2)).getShort(0);
            for (int i = 0; i < numberOfLeaves; i++) {
                buffMe = ByteBuffer.allocate(4);
                char c = (char) input.readNBytes(1)[0]; // char
                byte length = input.readNBytes(1)[0]; // lenght of the char
                buffMe.put(input.readNBytes(4)); // binary of the char.

                int inInt = buffMe.getInt(0);
                StringBuilder addZeros = new StringBuilder();
                String binary = Integer.toBinaryString(inInt);

                if (binary.length() < length) { //0 are added to the front the approriate amount to match length.
                    for (int z = 0; z < (length - binary.length()); z++) {
                        addZeros.append(0);
                    }
                    addZeros.append(binary);
                } else {
                    addZeros.append(binary.substring(binary.length() - length));
                }

                formTree(treeFormed, c, addZeros.toString(), -1); // Builds a leaf into the tree with the given binary.

            }
            formText(input);
            writeTextToFile(pathToWrite);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Builds a leaf into the tree with the given parameters. If a branch is not available to move through it creates it and then passes through it.
     *
     * @param leaf      Leaf to be moved through
     * @param character Character to be written
     * @param binary    Binary String to be followed to created the leaf.
     * @param index     Length of the String to know when to stop.
     */
    public void formTree(Leaf leaf, char character, String binary, int index) {


        index++;

        if (binary.length() == index) {
            leaf.letter = character;

            return;
        }

        if (binary.charAt(index) == '0') {
            if (leaf.left == null) {
                Leaf nLeaf = new Leaf(null, 0);
                leaf.left = nLeaf;
                formTree(nLeaf, character, binary, index);
            } else {
                formTree((Leaf) leaf.left, character, binary, index);
            }
        } else {

            if (leaf.right == null) {

                Leaf nLeaf = new Leaf(null, 0);
                leaf.right = nLeaf;
                formTree(nLeaf, character, binary, index);
            } else {
                formTree((Leaf) leaf.right, character, binary, index);
            }

        }


    }

    /**
     * Decodes the binary string to actual text by using the generated tree. Every 64th bit is ignored, because it is a fill bit
     * to make sure 0 are not lost. This method only receives the input after the bits concerning byte count and Huffman tree are read.
     *
     * @param input encoded file.
     * @throws IOException if you are not good this will pop out. Definently not a programming error. Take some responsibility.
     */

    public void formText(ByteArrayInputStream input) throws IOException {

        binaryFormer = new StringBuilder();
        Leaf tempLeaf = treeFormed;


        while (input.available() > 0) {

            buffMe = ByteBuffer.allocate(8);
            buffMe.put(input.readNBytes(8));

            binaryFormer.append(Long.toBinaryString(buffMe.getLong(0)).substring(1));


        }
        binaryFormer.append(" ");


        for (int i = 0; i < binaryFormer.length(); i++) {

            if (tempLeaf.letter != null) {
                textFormer.append(tempLeaf.letter);

                tempLeaf = treeFormed;


            }
            if (binaryFormer.charAt(i) == '0') {

                tempLeaf = (Leaf) tempLeaf.left;
            } else if (binaryFormer.charAt(i) == '1') {

                tempLeaf = (Leaf) tempLeaf.right;
            }
        }


    }

    /**
     * Takes the decoded text and writes it to file.
     *
     * @param pathToWriteTo location of where to write the file.
     */
    public void writeTextToFile(String pathToWriteTo) {
        try {

            PrintStream writeOut = new PrintStream(new FileOutputStream(pathToWriteTo, true));
            writeOut.print(textFormer);

            writeOut.close();
            textFormer = new StringBuilder();
            workDone = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public int getMaxBytes() {
        return maxBytes;
    }

    public short getNumberOfLeaves() {
        return numberOfLeaves;
    }

    public Leaf getTreeFormed() {
        return treeFormed;
    }
}
