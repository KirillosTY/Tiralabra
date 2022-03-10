package logicTests;

import Logic.LZW;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class LZWTest {

    static List<Integer> dicDefault;

    static LZW lempelZivWelch;

    @BeforeAll
    static void init(){

        dicDefault = new ArrayList<>();

        lempelZivWelch = new LZW();
        dicDefault = lempelZivWelch.compress("Samuel el jackson got paid 10k extra for every mf he said on tv");
    }

    @Test
    public void containsRightChars(){


        List<Integer> counted = lempelZivWelch.compress("count m");
        List<Integer> included = Arrays.asList( (int) 'c', (int) 'o', (int) 'u', (int) 'n', (int) 't', (int) 'm');
        for(int letter: included){
                assertTrue(counted.contains(letter));


        }

    }

    @Test
    public void decompressingHappens(){

        String decoded = lempelZivWelch.decompress(dicDefault);
        assertEquals("Samuel el jackson got paid 10k extra for every mf he said on tv", decoded);


    }

}
