package com.chinamobile.digitaltwin.websocket;

import com.chinamobile.digitaltwin.insole.bean.Insole;
import com.chinamobile.digitaltwin.insole.service.InsoleService;
import com.chinamobile.digitaltwin.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@ServerEndpoint(value = "/foot/data")
@Component("wsserver")
public class WSServer {

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("有新连接加入::"+session.getId());
        WSMap.put(session.getId(),this);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("有一连接关闭");
        WSMap.remove(session.getId());

        WSServer wsServer = WSMap.get(session.getId());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     *            客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("服务端收到客户端的消息:"+message);
        log.debug("服务端收到客户端[{}]的消息:{}", session.getId(), message);
        //业务处理：将消息存数据库
        Insole insole = new Insole();
        insole.setAd1(1816);
        insole.setAd2(1816);
        insole.setAd3(1816);
        insole.setAd4(1816);
        insole.setAd5(1816);
        insole.setAd6(1816);
        insole.setAd7(1816);
        insole.setAd8(1816);

        insole.setAcceleration_x(0.186);
        insole.setAcceleration_y(-0.065);
        insole.setAcceleration_z(-0.983);

        insole.setAngle_x(-175.682);
        insole.setAngle_y(-10.393);
        insole.setAngle_z(9.135);

        insole.setAngular_speed_x(0.0);
        insole.setAngular_speed_y(0.061);
        insole.setAngular_speed_z(0.0);

        insole.setMagnetic_strength_x(-27.0);
        insole.setMagnetic_strength_y(-140.0);
        insole.setMagnetic_strength_z(329.0);

        Insole insole2 = new Insole();
        insole2.setAd1(2000);
        insole2.setAd2(2000);
        insole2.setAd3(2000);
        insole2.setAd4(2000);
        insole2.setAd5(2000);
        insole2.setAd6(2000);
        insole2.setAd7(2000);
        insole2.setAd8(2000);

        insole2.setAcceleration_x(0.111);
        insole2.setAcceleration_y(-0.0665);
        insole2.setAcceleration_z(-0.988);

        insole2.setAngle_x(-175.682);
        insole2.setAngle_y(-9.393);
        insole2.setAngle_z(9.185);

        insole2.setAngular_speed_x(0.0);
        insole2.setAngular_speed_y(0.091);
        insole2.setAngular_speed_z(0.0);

        insole2.setMagnetic_strength_x(-27.0);
        insole2.setMagnetic_strength_y(-150.0);
        insole2.setMagnetic_strength_z(321.0);

        List<Insole> insoleList = new LinkedList<>();
        insoleList.add(insole);
        insoleList.add(insole2);


        InsoleService insoleService = SpringContextUtil.getBean(InsoleService.class);
        //采集数据到MongoDB数据库
        //insoleService.insert(insole);
        insoleService.insert(insoleList);

    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session toSession) {
        log.info("服务端给客户端发送消息{}",message);
    }

    /**
     * 连接出错
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        WSMap.remove(session.getId());
        error.printStackTrace();
    }
}
