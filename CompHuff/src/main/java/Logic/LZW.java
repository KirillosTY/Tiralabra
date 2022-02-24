package Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LZW {
    public List<Integer> compress(String text) {

        int dicSize = 256;

        HashMap<String, Integer> dic = new HashMap<>();

        for (int i = 0; i < 256; i++) {
            dic.put("" + (char) i, i);
        }

        String chocolate = "";
        List<Integer> code = new ArrayList<>();

        for (char churro : text.toCharArray()) {

            String chocoChu = chocolate + churro;

            if (dic.containsKey(chocoChu)) {
                chocolate = chocoChu;
            } else {
                code.add(dic.get(chocolate));
                dic.put(chocoChu, dicSize++);
                chocolate = "" + churro;
            }

        }

        if (!chocolate.equals("")) {
            code.add(dic.get(chocolate));

        }

        return code;


    }


    public String decompress(List<Integer> codes){

        int dicSize = 256;

        HashMap<Integer,String> dic = new HashMap<>();

        for (int i = 0; i <256 ; i++) {
            dic.put(i,""+ (char) i);
        }

        String chocolate = ""+ (char)(int) codes.remove(0);
        StringBuilder  chocolateChurros = new StringBuilder();
        chocolateChurros.append(chocolate);

        for(int code: codes){

            String churro = "";

            if(dic.containsKey(code)){
                churro = dic.get(code);
            } else if( code == dicSize){

                churro = chocolate + chocolate.charAt(0);
            } else {
                System.out.println("shit hits the fan");
            }

            chocolateChurros.append(churro);

            dic.put(dicSize++,chocolate + churro.charAt(0));

            chocolate = churro;
        }


        return chocolateChurros.toString();
    }
}