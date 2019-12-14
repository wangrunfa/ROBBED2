package com.robbad.util;

import java.util.Random;
import java.util.UUID;

/**
 * RandomNumberUtil
 * 随机数工具类
 */
public class RandomNumberUtil {
    /**
     * randomUUID
     * 32位UUID 随机数
     * @return
     */
 public static String randomUUID(){
     return  UUID.randomUUID().toString().replace("-", "").toLowerCase();
 }
    /**
     * randomVerificationCode
     * 随机验证码
     * @return
     */
    public static StringBuffer randomVerificationCode(int number){
        char[] ch= "0123456789".toCharArray();
        StringBuffer random = new StringBuffer();
        Random r = new Random();
        for(int i=0;i<number;i++){
            random.append(ch[r.nextInt(ch.length)]);
        }
        return random;
    }

}
