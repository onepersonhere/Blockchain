package blockchain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Blockchain {
    private static int id = 0;
    private static String prevHash = "0";
    private static List<Block> hashStorage = new ArrayList<>();

    public static void generateBlock(){
        System.out.println("Block:");
        id++;
        System.out.println("Id: " + id);
        long timeStamp = new Date().getTime();
        System.out.println("Timestamp: " + timeStamp);
        System.out.println("Hash of the previous block: \n" + prevHash);

        Block hash = new Block(id + timeStamp + prevHash);
        hash.setPrevHash(prevHash);
        System.out.println("Hash of the block: \n" + hash + "\n");

        hashStorage.add(hash);
        prevHash = hash.toString();
    }
    public static boolean validate(){
        for(int i = id - 1; i >= 1; i--){
            if(!hashStorage.get(i).getPrevHash().contains(hashStorage.get(i-1).toString())){
                return false;
            }
            //System.out.println(hashStorage.get(i).getPrevHash() + " " + hashStorage.get(i-1).toString());
        }
        return true;
    }
}
