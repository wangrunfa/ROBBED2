package com.robbad.util;

import java.security.MessageDigest;
import java.util.Map;

public class MD5 {
    public static Map<String, Object> getMD5(String str) {
        try {
            String MD5 = "";
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = str.getBytes();
            byte[] digest = md5.digest(bytes);

            for (int i = 0; i < digest.length; i++) {
                //摘要字节数组中各个字节的"十六进制"形式.
                int j = digest[i];
                j = j & 0x000000ff;
                String s1 = Integer.toHexString(j);

                if (s1.length() == 1) {
                    s1 = "0" + s1;
                }
                MD5 += s1;
            }
            return WebTools.returnData(MD5,0);
        } catch (Exception e) {
            return WebTools.returnData("密码加密错误",1);
        }
    }

}
