package blockchain;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

import static java.lang.Math.random;

public class Block implements Serializable {
    private static final long serialVersionUID = 1L;

    public long timestamp = new Date().getTime();
    private long timeEnd;
    public double duration;
    private String hash = "0";
    private String prevHash = "";
    public long magicNum = 0;
    public static int numOfZeroes = 3;

    private String str;
    public Block(int id){
        str = id + timestamp + prevHash;
        if(!str.equals("0")) {
            do{
                magicNum = (long)(random() * 1000000000);
                hash = StringUtil.applySha256(str + magicNum);
            }
            while(hash.indexOf(createZeroStr()) != 0);
        }
        timeEnd = new Date().getTime();
        duration = (double)(timeEnd - timestamp)/1000;
    }
    private String createZeroStr(){
        String zeroes = "";
        for(int i = 0; i < numOfZeroes; i++){
            zeroes += "0";
        }
        return zeroes;
    }
    @Override
    public String toString(){
        return hash;
    }

    public void setPrevHash(String str) {
        this.prevHash = str;
    }

    public String getPrevHash() {
        return prevHash;
    }
}
