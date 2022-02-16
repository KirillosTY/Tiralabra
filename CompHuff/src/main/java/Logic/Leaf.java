package Logic;

public class Leaf implements Node, Comparable<Node> {

    public Node right;
    public Node left;
    public Character letter;
    private int weight;


    public Leaf(Character chara, Integer weights) {

        this.letter = chara;
        this.weight = weights;
        right = null;
        left = null;
    }


    public void setRight(Node right) {
        this.right = right;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public Character getChar() {
        return this.letter;
    }

    @Override
    public String toString() {
        return letter + " " + weight;
    }


    @Override
    public Node right() {

        return right;
    }

    @Override
    public Node left() {

        return left;
    }

    @Override
    public int compareTo(Node o) {
        return this.weight - o.getWeight();
    }
}
