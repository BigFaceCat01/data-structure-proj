package com.hxb.structure.util;

import com.hxb.structure.exception.UtilException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author create by huang xiao bao
 * @date 2019-04-20 17:24:51
 */
public class BeanConvertUtils {

    /**
     * 转换单类
     * @param source 源对象
     * @param target 目标类型
     * @param <T> 类型
     * @return 转换结果
     */
    public static <T> T convert(Object source,Class<T> target){
        if(Objects.isNull(source)){
            return null;
        }
        return convertList(Collections.singletonList(source),target).get(0);
    }

    /**
     * 转换列表
     * @param sources 源列表
     * @param target 目标类型
     * @param <T> 类型
     * @return 结果列表
     */
    public static <T> List<T> convertList(List sources,Class<T> target){
        if(Objects.isNull(sources) || sources.isEmpty()){
            return Collections.emptyList();
        }

        List<Field> fields = fieldIntersection(sources.get(0).getClass(),target);

        List<T> results = new ArrayList<>(sources.size());
        try {
            for (Object source : sources) {
                T res = target.newInstance();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Object value = field.get(source);
                    //注意这里getDeclaredField的NoSuchFieldException异常不会发生,因为一开始调用了fieldIntersection方法
                    Field targetField = target.getDeclaredField(field.getName());
                    //两者类型相同才进行赋值
                    if(targetField.getType() == field.getType()) {
                        targetField.setAccessible(true);
                        targetField.set(res, value);
                    }
                }
                results.add(res);
            }
            return results;
        } catch (InstantiationException e) {
            throw new UtilException("target must have a constructor which has no argument",e);
        } catch (IllegalAccessException e) {
            throw new UtilException("access of constructor which has no argument must be public",e);
        }catch (NoSuchFieldException e){
            //以下语句不会执行到,因为一开始调用了fieldIntersection方法
            throw new UtilException(e.getMessage(),e);
        }
    }


    private BeanConvertUtils(){}

    /**
     * 获得两个target类与origin类的共同字段，以target为基准
     * @param origin origin
     * @param target target
     * @return 共同字段
     */
    private static List<Field> fieldIntersection(Class<?> origin, Class<?> target){
        Field[] fields = target.getDeclaredFields();
        List<Field> commonField = new ArrayList<>(fields.length);
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            try {
                commonField.add(origin.getDeclaredField(f.getName()));
            } catch (NoSuchFieldException e) {

            }
        }
        return commonField;
    }
}
