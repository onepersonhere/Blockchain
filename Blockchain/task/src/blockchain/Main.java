package blockchain;

import java.io.IOException;
import java.io.File;

public class Main {
    public static Blockchain blockchain = new Blockchain();
    public static int size;
    public static int finalSize;
    public static final String filename = "C:\\Users\\wh\\IdeaProjects\\Blockchain1\\Blockchain\\task\\src\\blockchain\\bc.blockchain";

    //while creating one block, message receive will be stored into the next block
    public static void main(String[] args) throws InterruptedException {
        blockchain = Main.ifFileEmpty(blockchain);
        size = blockchain.hashStorage.size();
        finalSize = size + 5;

        Block.numOfZeroes = blockchain.getN(); //set N value

        for (int i = 0; i < 9; i++) { //amount of miners
            Miner miner = new Miner(i + 1);
            miner.setName("miner_" + (i + 1));
            miner.start();
        }

        Thread.sleep(5000);
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
}
