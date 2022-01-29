package FileHandler;



import Logic.Node;
import jdk.swing.interop.SwingInterOpUtils;

import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Scanner;

public class FileOpener implements FileAccess,Serializable {

    private HashMap<Character,String> coded;
    private String readText = "";

    private double maxBytes = 0;
    public String loadingTextFile(){

        try {

            File read = new File("testText.txt");
            Scanner reader = new Scanner(read);

            reader.useDelimiter("\\Z");

            readText = reader.next();
            return readText;


        } catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }


    



    public void writingCodedBinary(String compressText){

        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            boolean isMoreText = true;


            byte[] b = new byte[8];
            output.write(b, 0, 8); // the reserved space for the maximum bytes the file will hold.
            maxBytes = +64;

            byte bSmall = (byte) coded.keySet().size(); // Number of letters in tree
            output.write(bSmall);
            maxBytes += 8;

            byte[] fromShort = new byte[2];
            for (char chara : coded.keySet()) {
                char c = chara;
                output.write(c);
                output.write((byte) coded.get(chara).length());

                int sizeN = output.size();
                short codes = Short.valueOf(coded.get(chara), 2).byteValue();

                fromShort[0] = (byte) (codes & 0xff);
                fromShort[1] = (byte) ((codes >> 8) & 0xff);

                output.writeBytes(fromShort);


                maxBytes += 8 + 8 + 16;


            }

            StringBuilder tst = new StringBuilder();


            for (int i = 0; i < compressText.length(); i++) {

                tst.append(coded.get(compressText.charAt(i)));
                maxBytes+= coded.get(compressText.charAt(i)).length();
            }


            output.writeTo(new FileOutputStream("meh.txt"));

        }catch (Exception e){
            e.printStackTrace();
        }


    }


    public void readFile(){

        try {
            ByteArrayInputStream output = new ByteArrayInputStream(new FileInputStream("meh.txt").readAllBytes());
            System.out.println(Arrays.toString(Files.readAllBytes(Path.of("meh.txt"))));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void setCoded(HashMap<Character, String> coded){

        this.coded = coded;
    }

}
