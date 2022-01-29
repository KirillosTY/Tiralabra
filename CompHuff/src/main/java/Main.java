import FileHandler.FileOpener;
import Logic.Node;
import Logic.WordCounter;
import jdk.swing.interop.SwingInterOpUtils;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class Main {

    private static WordCounter counter;

    public static void main(String[] args) {


        FileOpener test = new FileOpener();


        count(test.loadingTextFile());
        test.setCoded(counter.getCoded());
        test.readFile();



    }


    public static void count(String text) {

        counter = new WordCounter();

        counter.count(text);

        counter.treeForming();

        counter.binaryCalculations();

    }





}



