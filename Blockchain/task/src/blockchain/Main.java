package blockchain;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static Blockchain blockchain = new Blockchain();
    public static int size;
    public static int finalSize;
    public static final String filename = "C:\\Users\\wh\\IdeaProjects\\Blockchain1\\Blockchain\\task\\src\\blockchain\\bc.blockchain";
    public static Message message = new Message();
    public static int iniID;
    private static final int NUM_OF_BLOCKS = 15;

    //start with 100 vc
    //miner adds miner_id into blockchain???
    //blocks store info about miner who created the block

    //to check amt of vc a person have:
    //check all of his transactions and all of the transactions to him
    //reject transaction if person tries to spend more money than he has.
    //method to return amount of coins a person has.

    public static void main(String[] args) throws InterruptedException {
        blockchain = Main.ifFileEmpty(blockchain); //create/load blockchain
        iniID = blockchain.getId();
        size = blockchain.blockStorage.size();
        finalSize = size + NUM_OF_BLOCKS; //how many blocks you want

        Block.numOfZeroes = blockchain.getN(); //set N value
         //only 1 instance of system.in

        for (int i = 0; i < 9; i++) { //amount of miners
            Miner miner = new Miner(i + 1);
            miner.setName("miner_" + (i + 1));
            miner.start();
        }

        Thread.sleep(12000); //prevent main from exiting while other threads are running...
    }

    public static Blockchain ifFileEmpty(Blockchain blockchain){
        try {
            File myObj = new File(filename);
            if (myObj.length() != 0) {
                blockchain = (Blockchain) FileSerial.read(filename);
            }else{
                blockchain.setId(0);
                blockchain.setPrevHash("0");
                //System.out.println("new Blockchain");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return blockchain;
    }

    //Stop concurrent printing
    public static synchronized AtomicBoolean printBlock(int blk_id, Block blk, String prevHash, Blockchain blockchain,
                                                 AtomicBoolean printing, int miner_id) throws InterruptedException {
        if(!printing.get()) { //not true
            if (blockchain.blockStorage.size() <= blk_id - 1) {
                printing.set(true);
                blockchain.setPrevHash(blk.toString());
                blockchain.blockStorage.add(blk);
                blockchain.setId(blk_id);

                //System.out.println("id is "+blockchain.getId());

                System.out.println("Block:");
                System.out.println("Created by: miner # " + miner_id);
                System.out.println("miner #"+miner_id+" gets 100 VC");
                //factors affecting the hash value
                System.out.println("Id: " + blk_id);
                System.out.println("Timestamp: " + blk.timestamp);
                System.out.println("Magic number: " + blk.magicNum);
                System.out.println("Hash of the previous block: \n" + prevHash);
                System.out.println("Hash of the block: \n" + blk);

                System.out.println("Block data: "+blk.getDataString());
                clearData(blockchain);
                stopDataCollection();
                //Block Data?
                System.out.println("Block was generating for " + blk.duration + " seconds");

                if(blk.duration < 0.01){
                    Block.numOfZeroes++;
                    System.out.println("N was increased to " + Block.numOfZeroes + "\n");
                }else if(blk.duration >= 0.01 && blk.duration <= 5){
                    System.out.println("N stays the same\n");
                }else {
                    Block.numOfZeroes--;
                    System.out.println("N was decreased by 1\n");
                }
                blockchain.setN(Block.numOfZeroes);
                printing.set(false);
                //blockchain.validate();
            } else {
                //System.out.println("miner_"+miner_id+" is blocked");
            }
        }else{
            printBlock(blk_id, blk, prevHash, blockchain, printing, miner_id);
        }
        return printing;
    }
    private static void clearData(Blockchain blockchain){
        //System.out.println(blockchain.getId() + " " + Main.iniID);
        if(blockchain.getId() != Main.iniID + 1) {
            //System.out.println("data cleared!");
            blockchain.setData(new ArrayList<>()); //clear data
        }
    }
    private static void stopDataCollection(){
        if(Main.blockchain.blockStorage.size() == Main.finalSize) {
            //System.out.println("Message terminated");
            Main.message.terminate();
        }
    }
}
