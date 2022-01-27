public class Leaf implements Node,Comparable<Node> {

    private Character letter;
    private int weight;
    public   Node right;

    public Node left;

    public int getWeight() {
        return weight;
    }

    @Override
    public Character getChar() {
        return this.letter;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    @Override
    public String toString() {
        return  letter +
                ", weight=" + weight;
    }

    public Leaf(Character chara, Integer weights){

        this.letter = chara;
        this.weight = weights;
    }

    @Override
    public Node right() {
        return null;
    }

    @Override
    public Node left() {
        return null;
    }

    @Override
    public int compareTo(Node o) {
        return this.weight-o.getWeight();
    }
}
