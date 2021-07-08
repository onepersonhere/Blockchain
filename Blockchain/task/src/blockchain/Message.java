package blockchain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class Message extends Thread{
    //during search of a block
    private volatile boolean running = true;

    @Override
    public void run() {
        //sends message to blockchain (Main ver)
        //prefer using id?
        //Store message into a list until able a new block is created
        //block can be edited by magic numbers?

        while(running){
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            Blockchain blockchain = Main.blockchain;
            try {
                String str = readLineTimeout(br, 1000);
                blockchain.addData(new MessageData(str));
            } catch (IOException | TimeoutException ignored) { } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void terminate() {
        running = false;
    }

    public void restart() {
        running = true;
    }

    public String readLineTimeout(BufferedReader reader, long timeout) throws TimeoutException, IOException {
        long start = System.currentTimeMillis();

        while (!reader.ready()) {
            if (System.currentTimeMillis() - start >= timeout)
                throw new TimeoutException();

            // optional delay between polling
            try { Thread.sleep(50); } catch (Exception ignore) {}
        }

        return reader.readLine(); // won't block since reader is ready
    }
}
