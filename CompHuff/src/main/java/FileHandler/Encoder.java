package FileHandler;


import java.io.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Scanner;

public class Encoder implements FileAccess, Serializable {

    private HashMap<Character, byte[]> coded;
    public HashMap<Character, String> codes;

    private int maxBytes = 0;

    private StringBuilder tst;

    private ByteBuffer buffMe;


    public String loadingTextFile(String path) {

        try {

            File read = new File(path);
            Scanner reader = new Scanner(read);

            reader.useDelimiter("\\Z");

            return reader.next();


        } catch (Exception e) {
            System.out.println("File not found");
        }


        return null;
    }


    public void writingCodedBinary(String compressText, String writePath) {

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
            writingLetter(output);
            finalWriterEncoder(output, writePath);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void finalWriterEncoder(ByteArrayOutputStream output, String writePath){

       try {
           maxBytes+=32;
           buffMe =  ByteBuffer.allocate(Integer.BYTES);
           buffMe.putInt(maxBytes);
           ByteArrayOutputStream encoder = new ByteArrayOutputStream();
           encoder.write(buffMe.array(), 0, 4);// the reserved space for the maximum bytes the file will hold.
           encoder.write(output.toByteArray());
           encoder.writeTo(new FileOutputStream(writePath));
       } catch (IOException e) {
           e.printStackTrace();
       }
    }

    public void checkSizeAndBuildBinary(String compressText){

        tst = new StringBuilder(0);

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


    public void setCoded(HashMap<Character, byte[]> coded) {

        this.coded = coded;
    }
    public void setCodes(HashMap<Character, String> codes) {

        this.codes = codes;
    }

    public int getMaxBytes() {
        return maxBytes;
    }

    public StringBuilder getTst() {
        return tst;
    }

    public ByteBuffer getBuffMe() {
        return buffMe;
    }

}
