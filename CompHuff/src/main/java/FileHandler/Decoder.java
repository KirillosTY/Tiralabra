package FileHandler;

import Logic.Leaf;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.PriorityQueue;

public class Decoder {

    private int maxBytes = 0;
    private short numberOfLeaves = 0;

    private ByteBuffer buffMe;
    private StringBuilder binaryFormer;
    private StringBuilder textFormer;
    public  Leaf treeFormed;

    public StringBuilder getTextFormer() {

        return textFormer;
    }

    public Decoder() {
        textFormer = new StringBuilder();
        treeFormed = new Leaf(null, 0);
    }

    public void readFile(String pathToFile) {

        buffMe = ByteBuffer.allocate(20);

        try {
            ByteArrayInputStream input = new ByteArrayInputStream(new FileInputStream(pathToFile).readAllBytes());
            maxBytes = 32;
            for (byte b : input.readNBytes(4)) {
                maxBytes = (maxBytes << 8) + (b & 0xFF);
            }
            numberOfLeaves = buffMe.put(input.readNBytes(2)).getShort(0);
            for (int i = 0; i < numberOfLeaves; i++) {
                buffMe = ByteBuffer.allocate(4);
                char c = (char) input.readNBytes(1)[0];
                byte length = input.readNBytes(1)[0];
                buffMe.put(input.readNBytes(4));

                int inShort = buffMe.getInt(0);
                StringBuilder addZeros = new StringBuilder();
                String binary = Integer.toBinaryString(inShort);

                if (binary.length() < length) {
                    for (int z = 0; z < (length - binary.length()); z++) {
                        addZeros.append(0);
                    }
                    addZeros.append(binary);
                } else {
                    addZeros.append(binary.substring(binary.length() - length));
                }

                formTree(treeFormed, c, addZeros.toString(), -1);


            }
            formText(input);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

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
        writeTextToFile("");

    }

    public void writeTextToFile(String pathToWriteTo) {
        try {

            PrintStream writeOut = new PrintStream(new FileOutputStream("Transformed.txt", true));
            writeOut.print(textFormer);


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
