import blockchain.Block;
import blockchain.Blockchain;

public class Main {
    private static final Blockchain blockchain = new Blockchain();
    private static final int difficulty = 5;

    public static void main(String[] args) throws InterruptedException {
//        blockchain.addBlock(new Block("Block 1", "0"))
//            .addBlock(new Block("Block 2", blockchain.getBlock(blockchain.size() - 1).hash()))
//            .addBlock(new Block("Block 3", blockchain.getBlock(blockchain.size() - 1).hash()))
//            .addBlock(new Block("Block 4", blockchain.getBlock(blockchain.size() - 1).hash()));
//            System.out.println("Is Valid: " + blockchain.isValid(difficulty));
//        System.out.print("Among Us");
//        Thread.sleep(1000);
//        System.out.print("\u001B[A");
//        System.out.print("Joe");
    }

    private static void print(String string) {
        System.out.print(string);
    }
}