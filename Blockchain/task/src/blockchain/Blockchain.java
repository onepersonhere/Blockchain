package blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Blockchain implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id = 0;
    private String prevHash;
    public List<Block> blockStorage = new ArrayList<>();
    private int N = 0;

    private static List<MessageData> data = new ArrayList<>();

    private long current_unique_id = 0;
    private static final long max_id = 1000000000;

    public boolean validate(){
        //reject the messages with identifier less than
        //the prev identifier in the block

        //check that every message has an identifier greater
        //than the prev identifier
        for(int i = id - 1; i >= 1; i--){
            if(!blockStorage.get(i).getPrevHash().contains(blockStorage.get(i-1).toString())){
                return false;
            }
            //System.out.println(hashStorage.get(i).getPrevHash() + " " + hashStorage.get(i-1).toString());
            if(blockStorage.get(i).getData().size() == 1){
                if (blockStorage.get(i).getData().get(0).getUnique_id() <=
                        blockStorage.get(i-1).getData().
                                get(blockStorage.get(i-1).getData().size()-1).getUnique_id()){
                    return false;
                }
            }else if(blockStorage.get(i).getData().size() > 1) {
                for (int j = blockStorage.get(i).getData().size() - 1; j >= 1; j--) {
                    if (blockStorage.get(i).getData().get(j).getUnique_id() <=
                            blockStorage.get(i).getData().get(j-1).getUnique_id()){
                        return false;
                    }
                }
            }

        }
        return true;
    }

    @Override
    public String toString(){
        String str = "last id: " + id + "\nprevHash: " + prevHash + "\n";
        str += blockStorage.toString() + "\n";
        return str;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    public void setN(int n) {
        N = n;
    }

    public int getN() {
        return N;
    }

    public void setData(List<MessageData> data) {
        //System.out.println("data set to: "+data.toString());
        Blockchain.data = data; //reset
    }

    public List<MessageData> getDataList() {
        //System.out.println(data.toString());
        return data;
    }

    public void addData(MessageData data){
        Blockchain.data.add(data);
        //System.out.println(this.data.toString());
    }

    public long getNewUniqueID(){
        long new_id = (long) (Math.random() * max_id + current_unique_id);
        current_unique_id = new_id;

        return new_id;
    }
}
