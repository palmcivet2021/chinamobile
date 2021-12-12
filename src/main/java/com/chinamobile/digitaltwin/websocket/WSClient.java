/*
package com.chinamobile.digitaltwin.websocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

*/
/**
 * Java版webSocket客户端实现
 *//*


public class WSClient extends WebSocketClient {
    public WSClient(URI serverUri) {
        super(serverUri);
    }

    public WSClient(URI serverUri, Draft protocolDraft){
        super(serverUri,protocolDraft);
    }

    @Override
    public void onOpen(ServerHandshake arg0) {
        System.out.println("------ WSClient onOpen ------");
    }

    @Override
    public void onClose(int arg0, String arg1, boolean arg2) {
        System.out.println("------ MyWebSocket onClose ------");
    }

    @Override
    public void onError(Exception arg0) {
        System.out.println("------ MyWebSocket onError ------");
    }

    @Override
    public void onMessage(String arg0) {
        System.out.println("-------- 接收到服务端数据： " + arg0 + "--------");
    }

    public static void main(String[] arg0) throws URISyntaxException {
        // 创建访问的webSocketServer的URI
        URI uri = new URI("ws://localhost:8080/foot/data");
        WSClient client = new WSClient(uri,new Draft_6455());
        client.connect();
        //保证已完成连接
        while (!client.getReadyState().equals(ReadyState.OPEN)) {}
        //向webSocket服务端发送数据
        client.send("此为要发送的数据内容");
    }
}
*/
