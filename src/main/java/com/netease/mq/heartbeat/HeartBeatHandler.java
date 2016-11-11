package com.netease.mq.heartbeat;

import com.netease.mq.heartbeat.listener.HBSendMsgListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by hzwangyujie on 2016/11/10.
 *
 * 心跳发送者
 */
public class HeartBeatHandler {

    private static final Logger logger = LoggerFactory.getLogger(HeartBeatHandler.class);

    /**
     * 监控host
     */
    private String host;

    /**
     * 是否已连接
     */
    private volatile boolean isConnected;

    /**
     * 端口
     */
    private int port;

    /**
     * 心跳间隔
     */
    private long heartBeatInterval;

    private ExecutorService singleThreadPool = Executors.newSingleThreadExecutor(new ThreadFactory() {
        int count = 0;
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("rmq-heartbeat-thread"+(count++));
            return thread;
        }
    });

    @Autowired
    private HBSendMsgListener sendMsgListener;

    public HeartBeatHandler(){}

    public HeartBeatHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 请求心跳包
     */
    public void sendHeartBeat(){
        // 线程钩子，应用停止需要停止卸载
        singleThreadPool.submit(new RequestHeartBeatThread());
    }

    /**
     * 线程轮训发送心跳包
     */
    class RequestHeartBeatThread implements Runnable{

        public void run() {
            while(true){
                // 本次心跳结果
                boolean currentRequest = request();
                //reconnected
                if(!isConnected && currentRequest){
                    sendMsgListener.doListen();
                }
                isConnected =currentRequest;
                // 线程休眠指定间隔
                try {
                    Thread.sleep(heartBeatInterval);
                } catch (InterruptedException e) {
                    logger.error("thread sleep error", e);
                }
            }
        }

        public boolean request(){
            Socket socket = null;
            try {
                socket = new Socket(host, port);
            } catch (IOException e) {
                logger.error("connect to host= {}, port= {} error", host, port);
                return false;
            } finally {
                if(socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        logger.error("socket close error host= {}, port= {} error", host, port, e);
                    }
                }
            }
            return true;
        }
    }

    public long getHeartBeatInterval() {
        return heartBeatInterval;
    }

    public void setHeartBeatInterval(long heartBeatInterval) {
        this.heartBeatInterval = heartBeatInterval;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public static void main(String[] args) {
        HeartBeatHandler handler = new HeartBeatHandler("127.0.0.1", 5672);
        handler.sendHeartBeat();
    }
}
