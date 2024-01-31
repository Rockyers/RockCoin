import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class Blockchain {
    private final ArrayList<Block> blockchain = new ArrayList<>();

    public Blockchain addBlock(Block block, int difficulty) {
        blockchain.add(block);
        block.mine(difficulty);
        return this;
    }

    public Block getBlock(int index) {
        return blockchain.get(index);
    }

    public int size() {
        return blockchain.size();
    }

    public String getJson() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
    }

    public boolean isValid(int difficulty) {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);

            if (!currentBlock.hash().equals(currentBlock.calculateHash()))
                return false;

            if (!previousBlock.hash().equals(currentBlock.previousHash()))
                return false;

            if (!currentBlock.hash().substring(0, difficulty).equals(hashTarget))
                return false;
        }

        return true;
    }
}
