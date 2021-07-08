package blockchain.generateMsgKeys;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class Run {
    public static void run(String[] args) { //rename to main to avoid conflict with tests
        GenerateKeys gk;
        try {
            gk = new GenerateKeys(1024);
            gk.createKeys();
            gk.writeToFile("C:\\Users\\wh\\IdeaProjects\\Blockchain1\\Blockchain\\task\\src\\blockchain\\generateMsgKeys\\publicKey", gk.getPublicKey().getEncoded());
            gk.writeToFile("C:\\Users\\wh\\IdeaProjects\\Blockchain1\\Blockchain\\task\\src\\blockchain\\generateMsgKeys\\privateKey", gk.getPrivateKey().getEncoded());
            System.out.println("Success!");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}
