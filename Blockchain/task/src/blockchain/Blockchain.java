package blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Blockchain implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id = 0;
    private String prevHash;
    public List<Block> hashStorage = new ArrayList<>();
    private int N = 0;

    private static List<String> data = new ArrayList<>();

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

    public void setData(List<String> data) {
        //System.out.println("data set to: "+data.toString());
        Blockchain.data = data; //reset
    }

    public List<String> getData() {
        //System.out.println(data.toString());
        return data;
    }

    public void addData(String data){
        Blockchain.data.add(data);
        //System.out.println(this.data.toString());
        //verdict: Not deleting correctly, must input to end.
    }
}
