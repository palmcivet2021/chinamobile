package com.chinamobile.digitaltwin.websocket;

import org.apache.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class MyWebSocketClient extends WebSocketClient {
    Logger logger = Logger.getLogger(MyWebSocketClient.class);

    public MyWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    public MyWebSocketClient(URI serverUri, Draft protocolDraft){
        super(serverUri,protocolDraft);
    }

    @Override
    public void onOpen(ServerHandshake arg0) {
// TODO Auto-generated method stub
        System.out.println("------ MyWebSocket onOpen ------");
    }

    @Override
    public void onClose(int arg0, String arg1, boolean arg2) {
// TODO Auto-generated method stub
        System.out.println("------ MyWebSocket onClose ------");
    }

    @Override
    public void onError(Exception arg0) {
// TODO Auto-generated method stub
        System.out.println("------ MyWebSocket onError ------");
    }

    @Override
    public void onMessage(String arg0) {
// TODO Auto-generated method stub
        System.out.println("-------- 接收到服务端数据： " + arg0 + "--------");
    }

    public static void main(String[] arg0) throws URISyntaxException {
        // 此处的WebSocket服务端URI，上面服务端第2点有详细说明
        URI uri = new URI("ws://localhost:8080/foot/data");
        MyWebSocketClient myClient = new MyWebSocketClient(uri,new Draft_6455());
        myClient.connect();
        while (!myClient.getReadyState().equals(ReadyState.OPEN)) {
        }
        // 往websocket服务端发送数据
        myClient.send("此为要发送的数据内容");
    }
}
