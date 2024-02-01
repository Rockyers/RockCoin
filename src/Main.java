import blockchain.Block;
import blockchain.Blockchain;

public class Main {
    private static final Blockchain blockchain = new Blockchain();
    private static final int difficulty = 5;

    public static void main(String[] args) throws InterruptedException {
        blockchain.addBlock(new Block("Block 1", "0"), difficulty)
            .addBlock(new Block("Block 2", blockchain.getBlock(blockchain.size() - 1).hash()), difficulty)
            .addBlock(new Block("Block 3", blockchain.getBlock(blockchain.size() - 1).hash()), difficulty)
            .addBlock(new Block("Block 4", blockchain.getBlock(blockchain.size() - 1).hash()), difficulty);
        System.out.println("Is Valid: " + blockchain.isValid(difficulty));
    }

    private static void print(String string) {
        System.out.print(string);
    }
}