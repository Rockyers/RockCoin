package blockchain;

import utils.StringUtils;

public class BlockchainAPI {
    private final Blockchain blockchain;
    private final int difficulty;

    public BlockchainAPI(int difficulty) {
        blockchain = new Blockchain();
        blockchain.addBlock(new Block("Initial Block", "0"));
        this.difficulty = difficulty;
    }

    public void add(String data) {
        StringUtils.hideCursor();
        String previousHash = blockchain.getBlock(blockchain.size() - 1).simpleHash();
        blockchain.addBlock(new Block(data, previousHash));
        StringUtils.showCursor();
    }

    public void mineBlocks() {
        StringUtils.hideCursor();
        blockchain.mineBlocks(difficulty);
        StringUtils.showCursor();
    }

    public boolean isValid() {
        return blockchain.isValid(difficulty);
    }
}