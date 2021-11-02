package com.chinamobile.foot.jni;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class SockerClient {

    public static char[] getChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array();
    }

    public static void main(String[] args){
        String server = "127.0.0.1";
        int port = 7001;
        //port = 8800;  //脑电端口

        try {
            //向server的port端口发出客户请求
            Socket socket = new Socket(server, port);


            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String info = br.readLine();
            while (info != null){
                System.out.println("socket data"+info);
                info = br.readLine();
            }

            //由Socket对象得到输出流，并构造PrintWriter对象
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            //由Socket对象得到输入流，并构造相应的BufferedReader对象
            InputStream inputStream = null;
            byte[] bytes = new byte[1024];
            int i = 1;
            while (true){
                inputStream = socket.getInputStream();
                inputStream.read(bytes);
                System.out.println("7001.data"+i+"="+new String(bytes));
                i++;
            }

            /*InputStream is=socket.getInputStream();
            byte[] bys = new byte[1024];
            int len = is.read(bys);
            String str = new String(bys,0,len);
            String str1 = new String(bys, "utf-8");

            //BufferedReader bis=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //String str = bis.readLine();
            System.out.println("7001.data="+str);

            
            //释放
            socket.close();

            StringBuilder sb = new StringBuilder();
            for (byte b : bys) {
                if(b > -1){
                    if(b == 0 || b == 1){
                        sb.append(" ");
                    }else {
                        sb.append((char) b);
                    }
                }
            }
            System.out.println("7001.str="+sb.toString());*/

            //char[] chars = getChars(bys);
            //str = String.valueOf(chars);
            //System.out.println("7001.str="+str);




        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
