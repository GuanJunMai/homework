package com.lagou.edu.factory;

import com.lagou.edu.annotation.Autowired;
import com.lagou.edu.annotation.Service;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class BeanFactory {

    private static Map<String, Object> map = new HashMap<>();
    private final static String pack = "com.lagou.edu";


    static {
        try {
            Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forPackage(pack))
                    .setScanners(new SubTypesScanner(),
                            new TypeAnnotationsScanner(),
                            new FieldAnnotationsScanner())
                    .filterInputsBy(new FilterBuilder().includePackage(pack)));
            Set<Field> fields = reflections.getFieldsAnnotatedWith(Autowired.class);
            Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Service.class);
            for (Class clazz : classes) {
                Class<?> aClass = Class.forName(clazz.getTypeName());
                Object o = aClass.newInstance();  // 实例化之后的对象
                String name;
                if (clazz.getInterfaces().length > 0){
                     name = clazz.getInterfaces()[0].getTypeName();
                }else {
                    name= clazz.getTypeName();
                }
                map.put(name, o);
            }
            for (Field field : fields) {
                String name = field.getDeclaringClass().getInterfaces()[0].getTypeName();
                String type = field.getType().getTypeName();
                Object obj = map.get(name);
                field.setAccessible(true);
                field.set(obj, map.get(type));
                map.put(name, obj);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    // 任务二：对外提供获取实例对象的接口（根据id获取）
    public static Object getBean(String id) {
        return map.get(id);
    }
}
