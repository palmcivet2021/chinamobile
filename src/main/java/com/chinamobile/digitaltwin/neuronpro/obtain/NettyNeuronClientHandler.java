package com.chinamobile.digitaltwin.neuronpro.obtain;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.LinkedBlockingDeque;

public class NettyNeuronClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 把获取的设备信息存储到队列中
     */
    private LinkedBlockingDeque<String> blockingQueue;
    private String name;

    public NettyNeuronClientHandler(String name, LinkedBlockingDeque<String> queue) {
        this.name = name;
        this.blockingQueue = queue;
    }

    /**
     * 当通道就绪就会触发该方法<br/>
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("channelActive client： " + ctx);
        String req = "{\"action\": \"req_data\", \"bones\": \"all\"}";
        ctx.writeAndFlush(Unpooled.copiedBuffer(req, CharsetUtil.UTF_8));
    }

    /**
     * 当通道有读取事件时，会触发<br/>
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务器回复的消息:" + buf.toString(CharsetUtil.UTF_8));
        String msgInfo = buf.toString(CharsetUtil.UTF_8);

        //把获取的消息放到队列中
        System.out.println("[" + name + "] 的生产者生产内容 : +" + msgInfo);
        blockingQueue.put(msgInfo);
    }

    /**
     * 处理异常, 一般是需要关闭通道<br/>
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
