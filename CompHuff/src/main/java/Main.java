import FileHandler.Decoder;
import FileHandler.Encoder;
import Logic.HuffmanGenerator;
import Logic.LZW;

public class Main {

    private static HuffmanGenerator counter;

    public static void main(String[] args) {


        Encoder test = new Encoder();

        Decoder boo = new Decoder();

        double time= System.nanoTime();
        /* String inputFromFile = test.loadingTextFile("500kb.txt");
        if(inputFromFile.matches("^\\p{ASCII}*")){

        } else {
            System.out.println("File contains disallowed letters");
        }
        count(inputFromFile);
        test.setCoded(counter.getCoded());
        test.setCodes(counter.getCodes());
        System.out.println(counter.getCodes());

        test.writingCodedBinary(inputFromFile,"meh.txt");
        boo.readFile("meh.txt");
        System.out.println((System.nanoTime()-time)/1e9+" koko krääsä");
        */

        LZW bobo = new LZW();


        System.out.println(bobo.decompress(bobo.compress(test.loadingTextFile("500kb.txt"))));

    }


    public static void count(String text) {
        double time= System.nanoTime();
        counter = new HuffmanGenerator();
        counter.count(text);
        System.out.println((System.nanoTime()-time)/1e9+" laskenta");
        time= System.nanoTime();
        counter.treeForming();
        System.out.println((System.nanoTime()-time)/1e9+" puun muodostus");
        time= System.nanoTime();
        counter.binaryCalculations();
        System.out.println((System.nanoTime()-time)/1e9+" binaarit");
    }


}



