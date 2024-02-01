package blockchain;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import utils.StringUtils;

import java.util.ArrayList;

public class Blockchain {
    private final ArrayList<Block> blockchain = new ArrayList<>();

    public Blockchain addBlock(Block block) throws InterruptedException {
        String addingText = StringUtils.color("&yAdding block " + (blockchain.size() + 1) + "...");
        System.out.print(addingText);
        blockchain.add(block);
        Thread.sleep(350);
        StringUtils.backspace(addingText.length());
        printlnf("&gAdded block " + (blockchain.indexOf(block) + 1) + "    ");
        return this;
    }

    public boolean allMined() {

    }

    public void mineBlocks(int difficulty) {

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
