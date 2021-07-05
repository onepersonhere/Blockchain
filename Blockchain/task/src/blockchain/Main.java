package blockchain;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        for(int i = 0; i < 5; i++){
            Blockchain.generateBlock();
        }
        System.out.println(Blockchain.validate());
    }
}
