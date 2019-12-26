package com.robbad.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @program: tenmallfront
 * @description: redis 缓存工具类
 * @author: niyao
 * @create: 2019-11-20 19:25
 */
@Component
public class RedisUtil {
    /**
     * 检查验证码是否存在
     */
    @Autowired
    public   RedisTemplate redisTemplate;

    public boolean isCodeExist(String phone,String code){
        try {
            ValueOperations<String,String> ops=redisTemplate.opsForValue();
            return  ops.get(phone)!=null&&ops.get(phone).equals(code);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
    /**
     * 检查Phone是否存在
     */

    public boolean isPhoneExist(String phone){
        try {
            ValueOperations<String,String> ops=redisTemplate.opsForValue();
            return  ops.get(phone)!=null;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

//    /**
//     * 验证抢占
//     */
//
//    public boolean isqzExist(int id,String student){
//        try {
//            ValueOperations<String,String> ops=redisTemplate.opsForValue();
//            return  ops.get(id)!=null&&ops.get(id).equals(student);
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//
//    }
//    /**
//     * 删除抢占
//     */
//    public boolean reqzExist(int id,String student){
//        try {
//            ValueOperations<String,String> ops=redisTemplate.opsForValue();
//            ops.decr
//            return  ops.get(id)!=null&&ops.get(id).equals(student);
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//    }
//    /**
//     * 添加抢占
//     * @param
//     * @param
//     */
//    public boolean setqzExist(int id,String student){
//        try {
//            ValueOperations<String,String> ops=redisTemplate.opsForValue();
//            return  ops.get(id)!=null&&ops.get(id).equals(student);
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//
//    }
    /**
     * 设置验证码
     * @param phone
     * @param code
     */
    public void setCode(String phone,String code){
        ValueOperations<String,String> ops=redisTemplate.opsForValue();
        ops.set(phone,code);
        redisTemplate.expire(phone,2, TimeUnit.MINUTES);//过期时间2分钟
    }
}