package logicTests;

import Logic.HuffmanGenerator;
import Logic.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class HuffmanGeneratorTest {

    private HuffmanGenerator testC;

    @BeforeEach
    public void initialize() {
        testC = new HuffmanGenerator();


    }

    @Test
    public void noExtraLetters() {

        String sentence = "abcde";

        testC.count(sentence);


        assertEquals(5, testC.getListedLetters().size());


    }

    @Test
    public void mostLettersWithLeastBinary() {
        String sentence = "I dont like this at all hjelp";

        testC.count(sentence);

        testC.treeForming();
        testC.binaryCalculations();


        int numbers = 100;
        char lowestChar = 'z';

        for (Character c : testC.getCoded().keySet()) {


            if (numbers > testC.getCodes().get(c).length()) {

                numbers = testC.getCodes().get(c).length();


                lowestChar = c;
            }

        }

        assertEquals(' ', lowestChar);
    }

    @Test
    public void leastLettersWithMostBinary() {
        String sentence = "this generator better be right";

        testC.count(sentence);

        testC.treeForming();

        testC.binaryCalculations();
        int numbers = 0;
        char lowestChar = 'z';

        for (Character c : testC.getCoded().keySet()) {

            if (numbers < testC.getCodes().get(c).length()) {

                numbers = testC.getCodes().get(c).length();
                lowestChar = c;
            }

        }


        assertTrue(('s' == lowestChar || 'n' == lowestChar || 'a' == lowestChar));
    }


    @Test
    public void sameLengthCharsDifferentLettersSameTrees() {
        String sentence = "aaaabbbccceed";

        testC.count(sentence);

        testC.treeForming();

        Node first = testC.getListedLetters().poll();


        HuffmanGenerator testCTwo = new HuffmanGenerator();

        sentence = "ccctxxxbbbeec";

        testCTwo.count(sentence);

        testCTwo.treeForming();

        Node second = testCTwo.getListedLetters().poll();

        assert first != null;
        assertTrue(same(first, second));


    }

    @Test
    public void differentLengthTextDifferentLettersSameTrees() {
        String sentence = "aaaabbbccceed";

        testC.count(sentence);

        testC.treeForming();

        Node first = testC.getListedLetters().poll();
        HuffmanGenerator testCTwo = new HuffmanGenerator();

        sentence = "zzzzxxxbbbeeeadwawaedaec";

        testCTwo.count(sentence);

        testCTwo.treeForming();

        Node second = testCTwo.getListedLetters().poll();

        assert first != null;
        assertFalse(same(first, second));


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
