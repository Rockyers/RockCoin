package blockchain;

import utils.StringUtils;

import java.util.Date;

public class Block {


    public enum Status {
        RAW,
        MINING,
        MINED;
    }
    private Status status = Status.RAW;

    private String hash;
    private final String simpleHash;
    private String previousHash;
    private final String data;
    private final long timeStamp;
    private String miningString;
    private int nonce;
    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        hash = calculateHash();
        simpleHash = calculateSimpleHash();
    }

    public String hash() {
        return hash;
    }

    public String simpleHash() {
        return simpleHash;
    }

    public int getNonce() {
        return nonce;
    }

    public String previousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String calculateHash() {
        return StringUtils.applySha256(previousHash + timeStamp + nonce + data);
    }

    private String calculateSimpleHash() {
        return StringUtils.applySha256(timeStamp + data);
    }

    public void mine(int difficulty) {
        status = Status.MINING;
        String target = new String(new char[difficulty]).replace('\0', '0');
        boolean delete = false;
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
            if (delete) print("\r");
            print(getNonceString());
            delete = true;
        }
        status = Status.MINED;
        print("\r");
        printf("&gNonce: " + nonce + "; " + hash);
        print("\n");
    }

    public void mineAsync(int difficulty) {
        new Thread(() -> {
            status = Status.MINING;
            String target = new String(new char[difficulty]).replace('\0', '0');
            while (!hash.substring(0, difficulty).equals(target)) {
                nonce++;
                hash = calculateHash();
                miningString = getNonceString();
            }
            StringUtils.backspace(getNonceString().length());
            miningString = StringUtils.color("&gNonce: " + nonce + "; " + hash);
            status = Status.MINED;
        }).start();
    }

    private String getNonceString() {
        return StringUtils.color("&yNonce: " + nonce + "; " + hash);
    }

    public Status getStatus() {
        return status;
    }

    public String getMiningString() {
        return miningString;
    }

    private void printf(String str) {
        System.out.print(StringUtils.color(str));
    }

    private void print(String str) {
        System.out.print(str);
    }
}
