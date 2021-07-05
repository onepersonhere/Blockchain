package blockchain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.io.File;

public class Main {
    private static final String filename = "C:\\Users\\wh\\IdeaProjects\\Blockchain1\\Blockchain\\task\\src\\blockchain\\bc.blockchain";
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Enter how many zeros the hash must start with: ");
        Scanner scanner = new Scanner(System.in);
        Block.numOfZeroes = scanner.nextInt();

        Blockchain blockchain = new Blockchain();
        blockchain = ifFileEmpty(blockchain);

        for(int i = 0; i < 5; i++){
            blockchain.generateBlock();
            FileSerial.write(blockchain, filename);
        }

        System.out.println(blockchain.validate());
    }

    private static Blockchain ifFileEmpty(Blockchain blockchain){
        try {
            File myObj = new File(filename);
            if (myObj.length() != 0) {
                blockchain = (Blockchain) FileSerial.read(filename);
            }else{
                blockchain.id = 0;
                blockchain.prevHash = "0";
                //System.out.println("new Blockchain");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return blockchain;
    }
}
