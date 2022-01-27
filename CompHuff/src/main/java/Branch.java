public class Branch implements Node,Comparable<Node>{

    private int weight;

    private Node right;

    private Node left;

    public Branch(int weights,Node r, Node l){

        this.right = r;
        this.left = l;
        this.weight = weights;
    }

    @Override
    public String toString() {
        return weight+"";
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
    public int getWeight() {
        return weight;
    }

    @Override
    public Character getChar() {
        return '0';
    }

    @Override
    public int compareTo(Node o) {
        return this.weight - o.getWeight();
    }
}
