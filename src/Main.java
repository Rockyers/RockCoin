import blockchain.BlockchainAPI;

public class Main {
    private static final BlockchainAPI blockchain = new BlockchainAPI(5);

    public static void main(String[] args) {
        blockchain.add("Block 1");
        blockchain.add("Block 2");
        blockchain.add("Block 3");
        blockchain.add("Block 4");
        blockchain.add("Block 5");
	blockchain.add("Block 6");
	blockchain.add("Block 7");
	blockchain.add("Block 8");
	blockchain.add("Block 9");
	blockchain.add("Block 10");

        print("\n");

        blockchain.mineBlocks();

        print("Valid: " + blockchain.isValid() + "\n");
    }

    private static void print(String string) {
        System.out.print(string);
    }
}
