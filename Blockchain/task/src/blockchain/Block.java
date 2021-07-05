package blockchain;

import java.util.Date;

public class Block {
    private String hash = "0";
    private String prevHash = "";
    public Block(String str){
        if(!str.equals("0")) {
            hash = StringUtil.applySha256(str);
        }
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
