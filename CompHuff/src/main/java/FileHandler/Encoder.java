package FileHandler;


import java.io.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Scanner;


/**
 * This class handles the encoding of the text based on the huffman tree generated.
 *
 */

public class Encoder implements FileAccess, Serializable {

    private HashMap<Character, byte[]> coded;
    public HashMap<Character, String> codes;

    private int maxBytes = 0;

    private StringBuilder tst;

    private ByteBuffer buffMe;

    /**
     * This loads the text file and transforms it to the text. On failure prints out file not found.
     * @param path the path where the file is read from
     * @return String representation of the text read from the file.
     */

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

    /**
     *This method takes a string and starts to encode it with the generated Huffman tree,
     * and writes only the huffman tree, before passing the rest of the encoding to other methods.
     * It uses two byteArrayStreams to so that adding the final bit count to the start
     * will be possible after all conversion is done. First 64 bits to tell the length of the file.
     * Then 16 bits to tell how many letters will be read the next. Letters are read first
     * followed by their length and frequency.
     * the character and length both have 8 bits, while the frequency is 32 bits.
     *
     *
     * @param compressText  text to be encoded.
     * @param writePath  path where to write the text file.
     */


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

                buffMe.put(coded.get(chara)); // Frequency of the character
                output.writeBytes(buffMe.array());
                maxBytes += 8 + 8 + 32;

            }

            checkSizeAndBuildBinary(compressText); // builds the string
            writingLetter(output); // Transforms the string
            finalWriterEncoder(output, writePath); //Writes it to file.

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Builds the binary representation of the encoded text in string.
     * @param compressText text to be encoded
     */

    public void checkSizeAndBuildBinary(String compressText){

        tst = new StringBuilder(0);

        for (int i = 0; i < compressText.length(); i++) {

            tst.append(codes.get(compressText.charAt(i)));

        }

    }

    /**
     * Handles the final writing where the maximum bits are added to the start of the file.
     * Then writes the file with the given output stream to the location given.
     * @param output bits to be written.
     * @param writePath location and file name to be written.
     */

    public void finalWriterEncoder(ByteArrayOutputStream output, String writePath){

        try {
            maxBytes+=32;
            buffMe =  ByteBuffer.allocate(Integer.BYTES);
            buffMe.putInt(maxBytes);
            ByteArrayOutputStream encoder = new ByteArrayOutputStream();
            encoder.write(buffMe.array(), 0, 4);// the reserved space for the maximum bytes the file will hold.
            encoder.write(output.toByteArray()); //the tree and encoded text.
            encoder.writeTo(new FileOutputStream(writePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Transforms the binary string to actual binary and writes to the output stream.
     * handles bit shifting. A 1 bit is added to start of every 64 bits to be written, to not lose any Zeroes.
     * @param output  takes a ByteArrayOutputStream that writes the bits to file.
     */

    public void writingLetter(ByteArrayOutputStream output){

        long binary=0b1; // the 64 bit is reserved to make sure some 0 bits are not lost.

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
