package Logic;

public interface Node {

    Node right();

    Node left();

    int getWeight();

    Character getChar();


    int compareTo(Node n);

    void setLeft(Node first);

    void setRight(Node second);
}
