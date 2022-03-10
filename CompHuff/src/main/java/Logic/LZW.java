package Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class compresses and decompresses text using Lempel Ziv Welch algorithm.
 */

public class LZW {

    /**
     * Reads the text, creates the default dictionary and then starts packing it into an Integer based list.
     * @param text text to be compressed
     * @return List with int values
     */
    public List<Integer> compress(String text) {

        int dicSize = 256;

        HashMap<String, Integer> dic = new HashMap<>();

        for (int i = 0; i < 256; i++) {  // Creates the default dictionary, mainly all chars.
            dic.put("" + (char) i, i);
        }

        String chocolate = "";
        List<Integer> code = new ArrayList<>();

        for (char churro : text.toCharArray()) {  // goes through the text

            String chocoChu = chocolate + churro; // Everyone likes chocolate and churroes.

            if (dic.containsKey(chocoChu)) {
                chocolate = chocoChu;
            } else {
                code.add(dic.get(chocolate));  // when a new combination is found it adds the previous to the list.
                dic.put(chocoChu, dicSize++); // new one goes to dictionary.
                chocolate = "" + churro;
            }

        }

        if (!chocolate.equals("")) {
            code.add(dic.get(chocolate));

        }

        return code;


    }

    /**
     * Decompresses given List<Integer> into a String
     *
     * @param codes List of ints to be decompressed
     * @return generated text
     */


    public String decompress(List<Integer> codes) {

        int dicSize = 256;

        HashMap<Integer, String> dic = new HashMap<>();

        for (int i = 0; i < 256; i++) { // creates default dictionary.
            dic.put(i, "" + (char) i);
        }

        String chocolate = "" + (char) (int) codes.remove(0);
        StringBuilder chocolateChurros = new StringBuilder();
        chocolateChurros.append(chocolate);

        for (int code : codes) {  // goes through the given List decoding it by the list of values given.

            String churro = "";

            if (dic.containsKey(code)) {    // it also builds it up
                churro = dic.get(code);
            } else if (code == dicSize) {

                churro = chocolate + chocolate.charAt(0);
            } else {
                System.out.println("shit hits the fan");
            }

            chocolateChurros.append(churro);

            dic.put(dicSize++, chocolate + churro.charAt(0));

            chocolate = churro;
        }


        return chocolateChurros.toString();
    }
}