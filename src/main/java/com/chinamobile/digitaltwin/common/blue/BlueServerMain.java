package com.chinamobile.digitaltwin.common.blue;


import java.io.InputStream;
import java.io.OutputStream;

public class BlueServerMain {
    public static void main(String[] args) {
        final String serverName = "Bluetooth Server Test";
        final String serverUUID = "1000110100001000800000805F9B34FB";  //根据需要自定义

        BlueServer blueServer = new BlueServer(serverUUID, serverName);
        blueServer.setServerListener(new BlueServer.OnServerListener() {

            @Override
            public void onConnected(InputStream inputStream, OutputStream outputStream) {
                System.out.printf("Connected");
                //添加通信代码
            }

            @Override
            public void onDisconnected() {

            }

            @Override
            public void onClose() {

            }

        });

        blueServer.start();
    }
}
