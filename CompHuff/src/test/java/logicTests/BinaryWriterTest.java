package logicTests;

import FileHandler.BinaryWriter;
import Logic.HuffmanGenerator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BinaryWriterTest {

    static BinaryWriter testCoder;

    static String path;

    static HuffmanGenerator wC;

    @BeforeAll
    public static void startFile() {
        path = "testSet.txt";

        File delete = new File(path);
        if (delete != null) {
            delete.delete();
        }
        testCoder = new BinaryWriter();
        wC = new HuffmanGenerator();
        wC.count("NFTs are a scam. adding some lorem ipsum would help a little");
        wC.treeForming();
        wC.binaryCalculations();


        try {

            PrintStream writeOut = new PrintStream(new FileOutputStream(path, true));
            writeOut.print("NFTs are a scam. adding some lorem ipsum would help a little");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        testCoder = new BinaryWriter();

    }

    @AfterAll
    public static void deleteExtra() {

        File delete = new File(path);
        if (delete != null) {
            delete.delete();
        }
    }

    @Test
    public void failureToLoadfile() {

        assertFalse(false, testCoder.loadingTextFile("iodwaawpdiwop"));
    }

    @Test
    public void loadingFile() {

        assertEquals(testCoder.loadingTextFile(path), "NFTs are a scam. adding some lorem ipsum would help a little");


    }

    @Test
    public void binaryCode() {

        testCoder.setCoded(wC.getCoded());
        testCoder.setCodes(wC.getCodes());
        testCoder.writingCodedBinary("NFT", "testSetBin.txt");
        assertEquals("101001101000111000", testCoder.getTst().toString());
    }

    @Test
    public void binaryCodeLonger() {

        testCoder.setCoded(wC.getCoded());
        testCoder.setCodes(wC.getCodes());
        testCoder.writingCodedBinary("NFTsgn.Twww", "testSetBin.txt");
        assertEquals("101001101000111000100011101111110101110110111000111001111001111001", testCoder.getTst().toString());
    }

}
