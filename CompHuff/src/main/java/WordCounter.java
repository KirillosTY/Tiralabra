import com.sun.jdi.ByteValue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.function.BinaryOperator;

public class WordCounter {

    private HashMap<Character,Integer> wordsCounted;
    private HashMap<Character,String> coded;

    private PriorityQueue<Node> listedLetters;



    public WordCounter(){

        wordsCounted = new HashMap<>();
        coded = new HashMap<>();
        listedLetters = new PriorityQueue<>();

    }

    public void count(String a){




        for (int i = 0; i < a.length(); i++){

            wordsCounted.put(a.charAt(i),wordsCounted.getOrDefault(a.charAt(i),0)+1);

        }

        for(char c: wordsCounted.keySet()){

            listedLetters.offer(new Leaf(c,wordsCounted.get(c)));

        }

    }




    public void treeForming(){

        while(listedLetters.size()>1 ) {

            Node first =listedLetters.peek();
            listedLetters.poll();
            Node second =listedLetters.peek();
            listedLetters.poll();
            //System.out.println(second.getChar()+" "+ first.getChar()+ "  "+(first.getWeight()+second.getWeight()));


            Branch b = new Branch((first.getWeight()+second.getWeight()), second, first);

            listedLetters.add(b);
        }

        Node list = listedLetters.poll();
        print2D(list);
        binaryCalculations(list);
        System.out.println(coded);

    }
    public void binaryCalculations(Node node){


        if(node.left()!=null){

            binaryCalculations(node.left(),"0");
        }

        if (node.right()!=null){

            binaryCalculations(node.right(),"1");
        }

    }

    public void binaryCalculations(Node node, String code) {
        if(node.right()== null && node.left()==null){
            coded.put(node.getChar(),code);
            System.out.println(node.getChar());
        }


        if(node.left()!=null){
            String temp = code;
            binaryCalculations(node.left(),temp+="0");
        }

        if (node.right()!=null){
            String temp = code;
            binaryCalculations(node.right(),temp+="1");
        }



    }
    static final int COUNT = 5;

    static void print2DUtil(Node root, int space)
    {
        // Base case
        if (root == null)
            return;

        // Increase distance between levels
        space += COUNT;

        // Process right child first
        print2DUtil(root.right(), space);

        // Print current node after space
        // count
        System.out.print("\n");
        for (int i = COUNT; i < space; i++)
            System.out.print(" ");
        System.out.print(root + "\n");

        // Process left child
        print2DUtil(root.left(), space);
    }

    // Wrapper over print2DUtil()
    static void print2D(Node root)
    {
        // Pass initial space count as 0
        print2DUtil(root, 0);
    }


}
