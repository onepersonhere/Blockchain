package blockchain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static java.lang.Math.random;

public class Block implements Serializable {
    private static final long serialVersionUID = 1L;

    private static Blockchain blockchain = Main.blockchain; //ini blkchn
    public long timestamp = new Date().getTime();
    private long timeEnd;
    public double duration;
    private String hash = "0";
    private String prevHash = "";
    public long magicNum = 0;
    public static int numOfZeroes; //ini N
    private String data;

    private String str;
    public Block(int id) throws InterruptedException {
        str = id + timestamp + prevHash;
        if(!str.equals("0")) {
            do{
                magicNum = (long)(random() * 1000000000);
                hash = StringUtil.applySha256(str + magicNum);
            }
            while(hash.indexOf(createZeroStr()) != 0);
        }

        //Thread.sleep(1000); //for testing inputs
        data = getdata();
        timeEnd = new Date().getTime();
        duration = (double)(timeEnd - timestamp)/1000; // in seconds
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

    private String getdata(){
        List<String> list = blockchain.getData();

        String returnVal = "";
        for(int i = 0; i < list.size(); i++){
            returnVal += "\n" + list.get(i);
        }
        return returnVal;
    }

     public String getData(){
        if(data.isEmpty()){
            return "no messages";
        }
        return data;
     }
}
