package com.robbad.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: tenmallfront
 * @description: 工具类
 * @author: lixuqiang
 * @create: 2019-11-23 14:08:41
 */
public class WebTools {

    /**
     * 返回数据
     * @param object 返回的数据
     * @param code code
     * @return map集合
     */
    public static Map<String,Object> returnData(Object object,Integer code){
        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        if(code == 0){
            map.put("data",object);
        }else{
            map.put("message",object);
        }
        return map;
    }

    /**
     * 对象转化为map
     * @param o 对象
     * @return 转化成功的map
     * @throws IllegalAccessException 非法访问异常
     */
    public static Map<String,Object> objectToMap(Object o) throws IllegalAccessException {
        if(null == o){
            return null;
        }
        Map<String,Object> map = new HashMap<>();
        Field[] declaredFields = o.getClass().getDeclaredFields();
        for (Field field :declaredFields) {
            // （此处如果不设置 无法获取对象的私有属性）
            field.setAccessible(true);
            map.put(field.getName(),field.get(o));
        }
        return map;
    }

    /**
     * 保存session参数
     * @param name 名称
     * @param value 值
     */
    public static void setSession(String name,Object value) {
        RequestAttributes requestAttributes= RequestContextHolder.currentRequestAttributes();
        if(requestAttributes != null) {
            requestAttributes.setAttribute(name, value, RequestAttributes.SCOPE_SESSION);
        }
    }

    /**
     * 获取session参数
     * @param name 名称
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSession(String name) {
        RequestAttributes requestAttributes= RequestContextHolder.currentRequestAttributes();
        if(requestAttributes!=null) {
            return (T) requestAttributes.getAttribute(name, RequestAttributes.SCOPE_SESSION);
        }
        return null;
    }

    /**
     * 取最大值
     * @param oneNumber
     * @param twoNumber
     * @return
     */
    public static Integer  takeTheMaximum(Integer oneNumber,Integer twoNumber){
        if(oneNumber>=twoNumber){
            return oneNumber;
        }
        return twoNumber;
    }

}
