package com.chinamobile.digitaltwin.util;

import java.io.File;
import java.io.InputStream;

public class CmdUtil {

    public static void exec(String cmd){
        Runtime run = Runtime.getRuntime();
        try{
            File file = new File("command.bat");
            //String tt = "cmd /c conda activate pytorch-py37 && cd  D:\\project\\PycharmProject\\braindecode\\ && python torchtest.py";
            //String tt = "cmd /c D: && conda activate pytorch-py37 && python D:\\project\\PycharmProject\\braindecode\\torchtest.py";
            String tt = "cmd /c D: && python D:\\project\\PycharmProject\\pythonDemo\\demo_mysql_test.py";
            Process process = run.exec("cmd.exe /c start " + tt);
            InputStream in = process.getInputStream();
            /*while (in.read() != -1){
                System.out.println(in.read());
            }*/

            byte[] b = new byte[100];
            in.read(b);
            System.out.println(new String(b));

            in.close();
            process.waitFor();
        }catch (Exception e){
            e.printStackTrace();
        }


        /*String cmd1 = "cmd /c conda activate pytorch-py37";
        String cmd2 = "cmd /c cd  D:\\project\\PycharmProject\\braindecode\\";
        String cmd3 = "cmd /c python torchtest.py";
        String[] arrs={cmd1,cmd1,cmd1};

        try{
            for(String str : arrs){
                Runtime.getRuntime().exec(str);
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/

    }


    public static void main(String[] args){
        String cmd = "D:\\mat.bat";
        CmdUtil.exec(cmd);
    }


}
