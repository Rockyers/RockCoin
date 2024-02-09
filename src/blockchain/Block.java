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
    private long startTime;

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        hash = calculateHash(nonce);
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

    public String calculateHash(int lNonce) {
        return StringUtils.applySha256(previousHash + timeStamp + lNonce + data);
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
            hash = calculateHash(nonce);
            if (delete) print("\r");
            print(getNonceString(nonce, hash));
            delete = true;
        }
        status = Status.MINED;
        print("\r");
        printf("&gNonce: " + nonce + "; " + hash);
        print("\n");
    }

    public void mineAsync(int difficulty) {
        status = Status.MINING;
        String target = new String(new char[difficulty]).replace('\0', '0');
        startTime = System.currentTimeMillis();
        new Thread(() -> {
            while (!hash.substring(0, difficulty).equals(target)) {
                nonce++;
                hash = calculateHash(nonce);
                miningString = getNonceString(nonce, hash);
            }
            miningString = " &gComplete  &w| &g" + hash + " &w| &g" + nonce;
            status = Status.MINED;
        }).start();
    }

    public void mineMultiAsync(int difficulty) {
        status = Status.MINING;
        String target = new String(new char[difficulty]).replace('\0', '0');
        startTime = System.currentTimeMillis();
        new Thread(() -> {
            int localNonce = 0;
            String localHash;
            while (status == Status.MINING) {
                localNonce += 2;
                localHash = calculateHash(localNonce);
                miningString = getNonceString(localNonce, localHash);
                if (localHash.substring(0, difficulty).equals(target)) {
                    finish(localHash, localNonce);
                    break;
                }
            }
        }).start();

        new Thread(() -> {
            int localNonce = 1;
            String localHash;
            while (status == Status.MINING) {
                localNonce += 2;
                localHash = calculateHash(localNonce);
                miningString = getNonceString(localNonce, localHash);
                if (localHash.substring(0, difficulty).equals(target)) {
                    finish(localHash, localNonce);
                    break;
                }
            }
        }).start();
    }

    private void finish(String hash, int nonce) {
        this.hash = hash;
        this.nonce = nonce;
        status = Status.MINED;
        miningString = " &gComplete  &w| &g" + hash + " &w| &g" + nonce;
    }

    private String getNonceString(int nonce, String hash) {
        float time = (System.currentTimeMillis() - startTime) / 1000f;
        String space = " ".repeat(Math.max(10 - String.valueOf(nonce).length(), 1));
        return " &yMining... &w| &y" + hash + " &w| &y" + nonce + space + "&w| &p" + time + "s";
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
