import java.util.Comparator;

public interface Node  {

    Node right();

    Node left();

    int getWeight();
    Character getChar();


    int compareTo(Node n);
}
