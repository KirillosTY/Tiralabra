package logicTests;

import FileHandler.BinaryWriter;
import FileHandler.Decoder;
import Logic.HuffmanGenerator;
import Logic.Node;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DecoderTest {


    static Decoder testCoder;

    static String path;

    static HuffmanGenerator wC;

    @BeforeAll
    public static void startFile() {
        path = "decoderTest.txt";


        File delete = new File(path);
        if (delete != null) {
            delete.delete();
        }

        BinaryWriter createFile = new BinaryWriter();
        testCoder = new Decoder();
        wC = new HuffmanGenerator();
        wC.count("NFTs are a scam. adding some lorem ipsum would help a little");
        wC.treeForming();
        wC.binaryCalculations();
        createFile.setCodes(wC.getCodes());
        createFile.setCoded(wC.getCoded());
        createFile.writingCodedBinary("NFTs are a scam. adding some lorem ipsum would help a little", "decoderTest.txt");
        try {

            PrintStream writeOut = new PrintStream(new FileOutputStream(path, true));
            writeOut.print("NFTs are a scam. adding some lorem ipsum would help a little");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        testCoder.readFileHuff("decoderTest.txt", "testDone.txt");
    }

    @Test
    public void loadingFileAndLeavesMatch() {

        assertEquals(testCoder.getNumberOfLeaves(), wC.getCodes().keySet().size());
    }


    @Test
    public void testingTree() {


        assertTrue(same(testCoder.treeFormed, wC.headOfTree));
    }

    public boolean same(Node node1, Node node2) {


        if (node1.getChar() == null && node2.getChar() != null) {
            return false;
        } else if (node1.getChar() != null && node2.getChar() == null) {
            return false;
        } else if (node1.left() == node2.left() && node1.right() == node2.right()) {
            return true;
        }

        return (same(node1.left(), node2.left()) && same(node1.right(), node2.right()));

    }
}
