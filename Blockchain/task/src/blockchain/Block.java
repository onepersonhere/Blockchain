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
    private List<MessageData> data;
    private int miner_id;

    private String str;
    public Block(int id, int miner_id) throws InterruptedException {
        this.miner_id = miner_id;
        str = id + timestamp + prevHash;
        if(!str.equals("0")) {
            do{
                magicNum = (long)(random() * 1000000000);
                hash = StringUtil.applySha256(str + magicNum);
            }
            while(hash.indexOf(createZeroStr()) != 0);
        }

        //Thread.sleep(3000); //for testing inputs
        getDataFromList();
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

    private void getDataFromList(){
        data = blockchain.getDataList();
    }

     public String getDataString(){
        if(data.isEmpty()){
            return "\nNo transactions";
        }
        String returnVal = "";
        for(int i = 0; i < data.size(); i++){
            returnVal += "\n" + data.get(i).toString();
        }
        return returnVal;
     }

     public List<MessageData> getData(){
        return data;
     }
}
