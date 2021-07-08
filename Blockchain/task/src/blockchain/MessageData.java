package blockchain;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class MessageData implements Serializable {
    private String msgText;
    private byte[] signature;
    private long unique_id;
    private PublicKey public_key;

    public MessageData(String msgText) throws Exception {
        this.msgText = msgText;
        unique_id = Main.blockchain.getNewUniqueID();

        signature = sign(msgText+unique_id, "C:\\Users\\wh\\IdeaProjects\\Blockchain1\\" +
                "Blockchain\\task\\src\\blockchain\\generateMsgKeys\\privateKey");

        public_key = getPublic("C:\\Users\\wh\\IdeaProjects\\Blockchain1\\" +
                "Blockchain\\task\\src\\blockchain\\generateMsgKeys\\publicKey");
    }

    //The method that signs the data using the private key that is stored in keyFile path
    private byte[] sign(String data, String keyFile) throws InvalidKeyException, Exception{
        Signature rsa = Signature.getInstance("SHA1withRSA");
        rsa.initSign(getPrivate(keyFile));
        rsa.update(data.getBytes());
        return rsa.sign();
    }

    private PrivateKey getPrivate(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    public boolean verifySignature(byte[] data, byte[] signature, String keyFile) throws Exception {
        Signature sig = Signature.getInstance("SHA1withRSA");
        sig.initVerify(getPublic(keyFile));
        sig.update(data);

        return sig.verify(signature);
    }

    //Method to retrieve the Public Key from a file
    private PublicKey getPublic(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    @Override
    public String toString(){
        return msgText;
    }

    public long getUnique_id() {
        return unique_id;
    }
}
