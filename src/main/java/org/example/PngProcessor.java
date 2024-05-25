package org.example;

import java.io.*;

public class PngProcessor extends Thread {

    private String filename;
    private int part;
    private byte[] data;

    public PngProcessor(String filename){
        this.filename = filename;
    }

    @Override
    public void run() {
        try (DataInputStream is = new DataInputStream(new FileInputStream(filename))) {

            int sz = is.readInt();
            data = is.readNBytes(sz);
            int even = is.readInt();
            part = is.readInt();
            int finalNumber = 0;

            for(byte b: data){
                for (int i = 0; i < 8; i ++){
                    finalNumber += (b & ( 1 << i)) >> i;
                }
            }

            if(finalNumber % 2 != even) {
                throw new IOException();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public byte[] getData() {
        return data;
    }

    public int getPart() {
        return part;
    }

    public String getFilename() {
        return filename;
    }
}