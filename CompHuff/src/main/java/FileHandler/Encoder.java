package FileHandler;


import java.io.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Scanner;

public class FileWriter implements FileAccess, Serializable {

    private HashMap<Character, byte[]> coded;
    public HashMap<Character, String> codes;
    private String readText = "";

    private int maxBytes = 0;

    private StringBuilder tst;

    private ByteBuffer buffMe;


    public String loadingTextFile() {

        try {

            File read = new File("100kb.txt");
            Scanner reader = new Scanner(read);

            reader.useDelimiter("\\Z");

            readText = reader.next();
            return readText;


        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }


    public void writingCodedBinary(String compressText) {

        try {
            buffMe = ByteBuffer.allocate(2);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            buffMe.putShort((short) coded.keySet().size()); // Number of letters in tree
            output.write(buffMe.array());

            maxBytes += 16;

            for (char chara : coded.keySet()) {
                buffMe = ByteBuffer.allocate(4);
                char c = chara;
                output.write(c);
                output.write((byte) codes.get(chara).length()); //length of the binary.
                buffMe.put(coded.get(chara));

                output.writeBytes(buffMe.array());


                maxBytes += 8 + 8 + 32;

            }


            checkSizeAndBuildBinary(compressText);
            ByteArrayOutputStream encoder = new ByteArrayOutputStream();
            writingLetter(output);

            maxBytes+=32;
            buffMe =  ByteBuffer.allocate(Integer.BYTES);
            buffMe.putInt(maxBytes);
            encoder.write(buffMe.array(),0,4);// the reserved space for the maximum bytes the file will hold.
            encoder.write(output.toByteArray());
            encoder.writeTo(new FileOutputStream("meh.txt"));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void checkSizeAndBuildBinary(String compressText){

        tst = new StringBuilder(Integer.MAX_VALUE-3);

        for (int i = 0; i < compressText.length(); i++) {

            tst.append(codes.get(compressText.charAt(i)));

        }

    }

    public void writingLetter(ByteArrayOutputStream output){

        long binary=0b1;

        ByteBuffer shit;

        for(int i = 0; i < tst.length(); i++){

            if(tst.charAt(i) == '1'){
                binary = (binary << 1)+1;

            }else {
                binary = (binary << 1);
            }

            if( Long.toBinaryString(binary).length()%64 ==0 ){
                shit = ByteBuffer.allocate(8);
                shit.putLong(binary);
                output.writeBytes(shit.array());

                binary=0b1;
                maxBytes+=64;
            } else if(i+1 == tst.length()){
                shit = ByteBuffer.allocate(8);
                shit.putLong(binary);
                output.writeBytes(shit.array());

                maxBytes+=tst.length()%64;

            }


        }

    }


    public void readFile() {

        try {
            ByteArrayInputStream input = new ByteArrayInputStream(new FileInputStream("meh.txt").readAllBytes());
            int binaryCount =  0 ;
            for(byte b: input.readNBytes(4)){
                binaryCount = (binaryCount << 8) +(b & 0xFF);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setCoded(HashMap<Character, byte[]> coded) {

        this.coded = coded;
    }
    public void setCodes(HashMap<Character, String> codes) {

        this.codes = codes;
    }

}
