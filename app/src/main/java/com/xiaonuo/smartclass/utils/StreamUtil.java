package com.xiaonuo.smartclass.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/11/23.
 */

public class StreamUtil {
    /**
     * @param is 输入流
     * @return 流的数据  如果错误 返回null
     */
    public static String streamToString(InputStream is) {

        ByteArrayOutputStream bos=new ByteArrayOutputStream();

        byte[] temp=new byte[1024];

        int pos=-1;

        try {
            while((pos=is.read(temp))!=-1){
                bos.write(temp,0,pos);
            }

            return bos.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }
}
