package blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Blockchain implements Serializable {
    private static final long serialVersionUID = 1L;

    public int id = 0;
    public String prevHash;
    private List<Block> hashStorage = new ArrayList<>();

    public void generateBlock(){
        System.out.println("Block:");
        id++;

        //factors affecting the hash value
        System.out.println("Id: " + id);

        Block blk = new Block(id);

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

    @Override
    public String toString(){
        String str = "last id: " + id + "\nprevHash: " + prevHash + "\n";
        str += hashStorage.toString() + "\n";
        return str;
    }
}
