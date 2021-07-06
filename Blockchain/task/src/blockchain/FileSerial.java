package blockchain;

import java.io.*;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileSerial {
    private static ReadWriteLock lock = new ReentrantReadWriteLock();
    static Lock writelock = lock.writeLock();
    public static synchronized void write(Object obj, String fileName) throws IOException, InterruptedException {

        try {
            writelock.lock();
            FileOutputStream fos = new FileOutputStream(fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            try{oos.writeObject(obj);} catch (ConcurrentModificationException e){
                Thread.sleep(10);
                oos.writeObject(obj);
            }
            //System.out.println(obj.toString());
            oos.close();
        }finally {
            writelock.unlock();
        }
    }
    public static Object read(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        //System.out.println(obj.toString());
        ois.close();
        return obj;
    }
}
