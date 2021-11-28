package com.chinamobile.digitaltwin.neuronpro.obtain;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * TextWebSocketFrame 类型，表示一个文本帧(frame)
 */
public class NeuronWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private LinkedBlockingDeque<String> blockingQueue;
    private String name;

    public NeuronWebSocketFrameHandler(String name, LinkedBlockingDeque<String> queue) {
        this.name = name;
        this.blockingQueue = queue;
    }

    /**
     * 用来接收浏览器发送过来的数据<br/>
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        System.out.println("服务器收到消息 " + msg.text());
        if (msg.text() != null && msg.text().equals("1")) {
            while (true) {
                boolean interrupted = false;
                try {
                    //取出队列中的消息
                    System.out.println("开始从队列中取消息");
                    String msgInfo = blockingQueue.take();
                    System.out.println("取出队列中的消息为：" + msgInfo);
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(msgInfo));
                } catch (InterruptedException ignore) {
                    interrupted = true;
                } finally {
                    if (interrupted) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    /**
     * 当web客户端连接后， 触发方法<br/>
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        //id 表示唯一的值，LongText 是唯一的 ShortText 不是唯一
        System.out.println("handlerAdded 被调用" + ctx.channel().id().asLongText());
        System.out.println("handlerAdded 被调用" + ctx.channel().id().asShortText());
    }

    /**
     * 当web客户端断接连接后的处理<br/>
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        System.out.println("handlerRemoved 被调用" + ctx.channel().id().asLongText());
    }

    /**
     * 发生异常时的处理<br/>
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("异常发生 " + cause.getMessage());
        ctx.close(); //关闭连接
    }
}
