package blockchain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

public class Miner extends Thread {
    private int miner_id;
    private int current_VC = 100; //initial
    public Miner(int miner_id){
        this.miner_id = miner_id;
    }
    private AtomicBoolean printing = new AtomicBoolean(false);
    @Override
    public void run() {
        //System.out.println("invoked new miner_"+miner_id);

        do{
            Blockchain blockchain = Main.blockchain;
            try {
                generateBlock(miner_id, blockchain);
                //System.out.println(blockchain.validate());
                FileSerial.write(Main.blockchain, Main.filename);
                Main.blockchain = blockchain;

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }while(Main.blockchain.blockStorage.size() < Main.finalSize); //how many blocks you want
    }

    private void generateBlock(int miner_id, Blockchain blockchain) throws InterruptedException {
        int blk_id = blockchain.getId() + 1;
        String prevHash = blockchain.getPrevHash();

        Block blk = new Block(blk_id,miner_id); //block is already created from here

        if(blockchain.validate()) {
            current_VC += 100;
            startIfAlive();
            blk.setPrevHash(prevHash);
            //System.out.println("size: "+blockchain.hashStorage.size() + " current id: " + (blk_id - 1));

            printing = Main.printBlock(blk_id, blk, prevHash, blockchain, printing, miner_id);
        }else{
            System.out.println("Invalid Blockchain!!!");
        }
    }

    private void startIfAlive(){
        if(!Main.message.isAlive()){
            try {
                Main.message.start();
                //System.out.println("Message Thread Started");
            }catch(IllegalThreadStateException ignored){}
        }
    }

    public int getCurrent_VC() {
        return current_VC;
    }
}
