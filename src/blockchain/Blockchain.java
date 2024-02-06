package blockchain;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import utils.StringUtils;

import java.util.ArrayList;

public class Blockchain {
    private final ArrayList<Block> blockchain = new ArrayList<>();

    public Blockchain addBlock(Block block) {
        String addingText = StringUtils.color("&yAdding block " + (blockchain.size()) + "...");
        System.out.print(addingText);
        blockchain.add(block);
        System.out.print("\r");
        printlnf("&gAdded block " + (blockchain.indexOf(block)) + "    ");
        return this;
    }

    public boolean allMined() {
        return blockchain.stream().allMatch((block) -> block.getStatus() == Block.Status.MINED);
    }

    public String getProgressString() {
        return null;
    }

    public void mineBlocks(int difficulty) {
        for (Block b : blockchain)
            b.mineAsync(difficulty);

        while (!allMined()) {
            System.out.print("Waiting...\r");
        }

        for (int i = 0; i < blockchain.size() - 1; i++) {
            blockchain.get(i + 1).setPreviousHash(blockchain.get(i).simpleHash());
        }

        System.out.println("Done.      ");
    }

    private void printlnf(String s) {
        System.out.println(StringUtils.color(s));
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

    public Blockchain fromJson(String json) {
        blockchain.clear();
        blockchain.addAll(new GsonBuilder().create().fromJson(json, new TypeToken<ArrayList<Block>>(){}.getType()));
        return this;
    }

    public boolean isValid(int difficulty) {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        boolean pass = true;
        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);

            if (!currentBlock.hash().equals(currentBlock.calculateHash())) {
                printlnf("&rBlock " + i + " | Cached hash (" + currentBlock.hash() + ") does not match calculated hash (" + currentBlock.calculateHash() + ") (nonce: " + currentBlock.getNonce() + ")");
                pass = false;
            }

            if (!previousBlock.simpleHash().equals(currentBlock.previousHash())) {
                printlnf("&rBlock " + i + " | Hash of block " + (i - 1) + " (" + previousBlock.hash() + ") Does match previous hash " + "(" + currentBlock.previousHash() + ")");
                pass = false;
            }

            if (!currentBlock.hash().substring(0, difficulty).equals(hashTarget)) {
                printlnf("&rBlock " + i + " | Not mined");
                pass = false;
            }
        }

        return pass;
    }
}
