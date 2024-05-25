package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) throws IOException {

        String filePath = "D:\\КФУ(Java)\\Programming\\KRnumber2\\src\\main\\java\\org\\example\\v21.png";
        File file = new File(filePath);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file);){

            File fileDir = new File("D:\\КФУ(Java)\\Programming\\KRnumber2\\src\\main\\cw2\\v21");
            String[] filenames = fileDir.list();
            ArrayList<PngProcessor> threads = new ArrayList<>();

            if (filenames != null) {
                for (String filename : filenames) {
                    PngProcessor thread = new PngProcessor("D:\\КФУ(Java)\\Programming\\KRnumber2\\src\\main\\cw2\\v21\\" + filename);
                    thread.start();
                    threads.add(thread);
                }
            }
            for (Thread thread:threads){
                try{
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }


            Collections.sort(threads, new Comparator<PngProcessor>() {
                public int compare(PngProcessor thread1, PngProcessor thread2) {
                    if (thread1.getPart() > thread2.getPart()) {
                        return 1;
                    }
                    return -1;
                }
            });

            for (PngProcessor thread:threads){
                fileOutputStream.write(thread.getData());
                fileOutputStream.flush();
            }

        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}