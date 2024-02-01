package blockchain;

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
        boolean delete = false;
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
            if (delete) StringUtils.backspace(getNonceString().length());
            print(getNonceString());
            delete = true;
        }
        StringUtils.backspace(getNonceString().length());
        printf("&gNonce: " + nonce + "; " + hash + "\n");
    }

    public void mineAsync(int difficulty) {
        new Thread(() -> {
            String target = new String(new char[difficulty]).replace('\0', '0');
            boolean delete = false;
            while (!hash.substring(0, difficulty).equals(target)) {
                nonce++;
                hash = calculateHash();
                print(getNonceString());
                if (delete) StringUtils.backspace(getNonceString().length());
                delete = true;
            }
            printf("&gNonce: " + nonce + "; " + hash);
        }).start();
    }

    private String getNonceString() {
        return StringUtils.color("&yNonce: " + nonce + "; " + hash);
    }

    private void printf(String str) {
        System.out.print(StringUtils.color(str));
    }

    private void print(String str) {
        System.out.print(str);
    }
}
