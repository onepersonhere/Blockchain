package blockchain;

import java.io.IOException;

public class Miner extends Thread {
    private int miner_id;
    public Miner(int miner_id){
        this.miner_id = miner_id;
    }
    private static boolean printing ;

    @Override
    public void run() {
        //System.out.println("invoked new miner_"+miner_id);
        do{
            Blockchain blockchain = Main.blockchain;
            try {
                generateBlock(miner_id, blockchain);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                if (blockchain.validate()) {
                    FileSerial.write(Main.blockchain, Main.filename);
                    Main.blockchain = blockchain;
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }while(Main.blockchain.hashStorage.size() < Main.finalSize); //how many blocks you want
    }

    private void generateBlock(int miner_id, Blockchain blockchain) throws InterruptedException {
        int blk_id = blockchain.getId() + 1;
        String prevHash = blockchain.getPrevHash();

        Block blk = new Block(blk_id); //block is already created from here
        blk.setPrevHash(prevHash);
        //System.out.println("size: "+blockchain.hashStorage.size() + " current id: " + (blk_id - 1));

        printBlock(blk_id, blk, prevHash, blockchain);
    }
    private synchronized void printBlock(int blk_id, Block blk, String prevHash, Blockchain blockchain) throws InterruptedException {
        if(!printing) {
            if (blockchain.hashStorage.size() <= blk_id - 1) {
                printing = true;
                blockchain.setPrevHash(blk.toString());
                blockchain.hashStorage.add(blk);
                blockchain.setId(blk_id);

                //System.out.println("id is "+blockchain.getId());

                System.out.println("Block:");
                System.out.println("Created by miner # " + miner_id);
                //factors affecting the hash value
                System.out.println("Id: " + blk_id);
                System.out.println("Timestamp: " + blk.timestamp);
                System.out.println("Magic number: " + blk.magicNum);
                System.out.println("Hash of the previous block: \n" + prevHash);
                System.out.println("Hash of the block: \n" + blk);

                //Block Data?

                System.out.println("Block was generating for " + blk.duration + " seconds");

                if(blk.duration < 0.25){
                    Block.numOfZeroes++;
                    System.out.println("N was increased to " + Block.numOfZeroes + "\n");
                }else if(blk.duration >= 0.25 && blk.duration <= 60){
                    System.out.println("N stays the same\n");
                }else {
                    Block.numOfZeroes--;
                    System.out.println("N was decreased by 1\n");
                }
                blockchain.setN(Block.numOfZeroes);
                printing = false;
            } else {
                //System.out.println("miner_"+miner_id+" is blocked");
            }
        }else{
            Thread.sleep(10);
            printBlock(blk_id, blk, prevHash, blockchain);
        }
    }
}
