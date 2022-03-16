package com.lagou.edu.test;

import com.lagou.edu.annotation.Autowired;
import com.lagou.edu.annotation.Service;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * @author 应癫
 */
public class IoCTest {

    private final static String pack = "com.lagou.edu";

    @Test
    public void testIoC() throws Exception {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(pack))
                .setScanners(new SubTypesScanner(),
                        new TypeAnnotationsScanner(),
                        new FieldAnnotationsScanner())
                .filterInputsBy(new FilterBuilder().includePackage(pack)));
//        Reflections reflections = new org.reflections.Reflections("com.lagou.edu");
        Set<Field> fields = reflections.getFieldsAnnotatedWith(Autowired.class);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Service.class);
        for (Class clazz : classes) {
            Object o = clazz.newInstance();  // 实例化之后的对象
            String name = clazz.getInterfaces()[0].getTypeName();
            System.out.println("Class Found:" + name);
        }
        for (Field field : fields) {
            String name = field.getDeclaringClass().getInterfaces()[0].getTypeName();
            System.out.println("Field Found:" + name);
        }
    }
}
