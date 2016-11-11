package com.example.lyw.simplehttpserver;

/**
 * Created by LYW on 2016/10/27.
 */

public class WebConfiguration {

    /**
     * 端口
     */
    private int port;
    /**
     * 最大监听数
     */
    private int maxParalles;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxParalles() {
        return maxParalles;
    }

    public void setMaxParalles(int maxParalles) {
        this.maxParalles = maxParalles;
    }
}
