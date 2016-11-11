package com.example.lyw.simplehttpserver;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by LYW on 2016/10/31.
 */

public class ServerToolKits {

    public static final String readLine(InputStream in) throws IOException {
        int c1 = 0;
        int c2 = 0;
        StringBuffer sb = new StringBuffer();
        while (c2 != -1&&!(c1 == '\r'&& c2 == '\n')){
            c1 = c2;
            c2 = in.read();
            sb.append((char)c2);
        }
            if (sb.length() == 0){
                return null;
            }
        return sb.toString();
    }
}
