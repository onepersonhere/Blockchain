package blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Blockchain implements Serializable {
    public static int id = 0;
    private static String prevHash = "0";
    private static List<Block> hashStorage = new ArrayList<>();

    public void generateBlock(){
        System.out.println("Block:");
        id++;

        //factors affecting the hash value
        System.out.println("Id: " + id);

        Block blk = new Block();

        System.out.println("Timestamp: " + blk.timestamp);
        System.out.println("Magic number: " + blk.magicNum);
        System.out.println("Hash of the previous block: \n" + prevHash);

        blk.setPrevHash(prevHash);
        System.out.println("Hash of the block: \n" + blk);

        System.out.println("Block was generating for " + blk.duration + " seconds\n");
        hashStorage.add(blk);
        prevHash = blk.toString();
    }
    public boolean validate(){
        for(int i = id - 1; i >= 1; i--){
            if(!hashStorage.get(i).getPrevHash().contains(hashStorage.get(i-1).toString())){
                return false;
            }
            //System.out.println(hashStorage.get(i).getPrevHash() + " " + hashStorage.get(i-1).toString());
        }
        return true;
    }
}
