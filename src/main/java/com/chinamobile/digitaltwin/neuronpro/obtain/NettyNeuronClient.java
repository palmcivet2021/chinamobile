package com.chinamobile.digitaltwin.neuronpro.obtain;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * 动捕设备接数据收客户端<br/>
 *
 * @author lichunxia
 */
public class NettyNeuronClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyNeuronClient.class);

    private LinkedBlockingDeque<String> blockingQueue;
    private String name;

    public NettyNeuronClient(String name, LinkedBlockingDeque<String> queue) {
        this.name = name;
        this.blockingQueue = queue;
    }

    /**
     * 开始获取动捕设备数据<br/>
     *
     * @throws Exception
     */
    public void startObtainData(String ip, int port) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建客户端启动对象
            Bootstrap bootstrap = new Bootstrap();
            //设置线程组
            bootstrap.group(group);
            //设置客户端通道的实现类(反射)
            bootstrap.channel(NioSocketChannel.class);
            //设置日志输出
            bootstrap.handler(new LoggingHandler(LogLevel.INFO));
            //设置自己的处理器
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(new NettyNeuronClientHandler(name, blockingQueue));
                }
            });

            LOGGER.debug("动捕设备接收数据客户端 ok..");

            //启动客户端去连接服务器端
            //关于 ChannelFuture 要分析，涉及到netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
            //给关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
