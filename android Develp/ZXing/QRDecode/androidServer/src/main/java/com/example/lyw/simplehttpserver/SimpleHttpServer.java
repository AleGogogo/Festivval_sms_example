package com.example.lyw.simplehttpserver;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



/**
 * Created by LYW on 2016/10/27.
 */

public class SimpleHttpServer {
    private boolean isEnable;
    private WebConfiguration webConfiguration;
    private ServerSocket socket;
    private ExecutorService threadPool;
    private static final String TAG = "SimpleHttpServer";

    public SimpleHttpServer(WebConfiguration webConfiguration) {
        this.webConfiguration = webConfiguration;
        //线程池避免了线程频繁的创建和销毁
        threadPool = Executors.newCachedThreadPool();
    }

    /**
     * 启动服务器
     */
    public void startAsync() {
        isEnable = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                doProSync();
            }
        }).start();
    }


    /**
     * 停止服务器
     */
    public void stopAsync() throws IOException {
        if (!isEnable){
            return;
        }
        isEnable = false;

        socket.close();

        socket = null;
    }

    /**
     * 同步监听服务器
     */
    private void doProSync() {

        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress
                    (webConfiguration.getPort());
            socket = new ServerSocket();

            socket.bind(inetSocketAddress);

            while (isEnable){
                //监听到一个请求
                Log.d(TAG, "开始检测~~~~");
                final Socket remoteSocket = socket.accept();
                threadPool.submit(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "accept a remote socket");
                       onAcceptRemotePeer(remoteSocket);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "socket 被打断" );
        }

    }

    private void onAcceptRemotePeer(Socket remoteSocket) {
        try {
       //    remoteSocket.getOutputStream().write("configurations,connection successful!".getBytes());
            InputStream in = remoteSocket.getInputStream();
            String headLine = null;
            while ((headLine = ServerToolKits.readLine(in))!=null){
                   if (headLine.equals("\r\n")){
                       break;
                   }
                Log.d(TAG, "onAcceptRemotePeer: "+headLine);
            }

        } catch (IOException e) {
            Log.e(TAG, e.toString() );
        }
    }
}
