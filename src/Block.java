import utils.StringUtils;

import java.util.Date;

public class Block {
    private String hash;
    private final String previousHash;
    private final String data;
    private final long timeStamp;
    private int nonce;

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        hash = calculateHash();
    }

    public String hash() {
        return hash;
    }

    public String previousHash() {
        return previousHash;
    }

    public String calculateHash() {
        return StringUtils.applySha256(previousHash + timeStamp + nonce + data);
    }

    public void mine(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
    }
}
