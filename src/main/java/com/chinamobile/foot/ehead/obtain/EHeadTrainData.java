package com.chinamobile.foot.ehead.obtain;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EHeadTrainData {
    private static int maxLen = 48;
    public static int[] trainData = new int[maxLen];
    private static Map<Integer, Integer> keyMap = new HashMap();

    public static Map<Integer, Integer> imap = new HashMap<>();

    static {
        for (int i = 0; i < maxLen; i++) {
            trainData[i] = (i + 1) % 4;
            if (trainData[i] == 0) {
                trainData[i] = 4;
            }
        }

        imap.put(0, 0);
        imap.put(1, 0);
        imap.put(2, 0);
        imap.put(3, 0);
    }


    public static int getTrainData() {
        Random random = new Random();
        //生成0-47内的数字m
        int i = random.nextInt(maxLen);
        //int i = random.nextInt() * (47-0+1);

        while (keyMap.containsKey(i) && keyMap.keySet().size() < maxLen) {
            i = random.nextInt(maxLen);
        }

        int data = trainData[i];
        keyMap.put(i, i);
        if (keyMap.keySet().size() == maxLen) {
            //data = 0;
            keyMap.clear();
        }


        return data;
    }


}
