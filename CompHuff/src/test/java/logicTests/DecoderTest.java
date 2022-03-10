package logicTests;

import FileHandler.Decoder;
import FileHandler.BinaryWriter;
import Logic.Node;
import Logic.HuffmanGenerator;
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

    static HuffmanGenerator WC;

    @BeforeAll
    public  static  void startFile(){
        path = "decoderTest.txt";



        File delete = new File(path);
        if(delete!= null){
            delete.delete();
        }

        BinaryWriter createFile = new BinaryWriter();
        testCoder = new Decoder();
        WC = new HuffmanGenerator();
        WC.count("NFTs are a scam. adding some lorem ipsum would help a little");
        WC.treeForming();
        WC.binaryCalculations();
        createFile.setCodes(WC.getCodes());
        createFile.setCoded(WC.getCoded());
        createFile.writingCodedBinary("NFTs are a scam. adding some lorem ipsum would help a little","decoderTest.txt");
        try {

            PrintStream writeOut = new PrintStream(new FileOutputStream(path, true));
            writeOut.print("NFTs are a scam. adding some lorem ipsum would help a little");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        testCoder.readFile("decoderTest.txt");
    }

    @Test
    public void loadingFileAndLeavesMatch(){

        assertEquals(testCoder.getNumberOfLeaves(), WC.getCodes().keySet().size());
    }


    @Test
    public void testingTree(){


        assertTrue(same(testCoder.treeFormed,WC.headOfTree));
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
