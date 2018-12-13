package com.michalchmielewski;

import java.io.*;

public class Frame {

    public int[] frameValues;
    private int[] correctValues;
    private int w;
    private int k;
    private int emptyIndex;

    public int getW() {
        return w;
    }

    public int getK() {
        return k;
    }

    public int getWK() {
        return w * k;
    }

    public int getEmptyIndex() {
        return emptyIndex;
    }

    public int getFrameValue(int i) {
        return frameValues[i];
    }

    public int getCorrectValue(int i) {
        return correctValues[i];
    }

    public Frame(String inputFileName) {

        File file = new File(inputFileName);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String[] firstLine = bufferedReader.readLine().split(" ");

            w = Integer.parseInt(firstLine[0]);
            k = Integer.parseInt(firstLine[1]);

            frameValues = new int[w * k];
            String[] line;
            for (int i = 0; i < w; i++) {
                line = bufferedReader.readLine().split(" ");
                for (int j = 0; j < k; j++) {
                    frameValues[i * k + j] = Integer.parseInt(line[j]);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setEmptyIndex();
        setCorrectValues();
    }

    private void setCorrectValues() {
        correctValues = new int[w * k];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < k; j++) {
                correctValues[i * k + j] = i * k + j + 1;
            }
        }
        correctValues[w * k - 1] = 0;
    }

    private void setEmptyIndex() {
        emptyIndex = 0;
        for (int i : frameValues) {
            if(i==0) {
                return;
            }
            else
                emptyIndex++;
        }
    }
}