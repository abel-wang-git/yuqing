package com.zkyf.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Administrator on 2017/3/9.
 * 操作类的工具类
 */
public class ClassUtil {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 只要类中有该注解 就返回
     */
    public static List<Class<?>> HasAnnotation(boolean isChild, Class an, String... scanpackage) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        List<Class> list = scanPackage(isChild, scanpackage);
        for (Class c : list) {
            for (Method m : c.getMethods()) {
                if (m.isAnnotationPresent(an)) {
                    classes.add(c);
                }
            }
            if (c.isAnnotationPresent(an)) {
                classes.add(c);
            }
        }
        return classes;
    }

    /**
     * 扫描某个包下具有某个注解的所有类的集合
     */
    public static List<Class<?>> ClassHasAnnotation(boolean isChild, Class an, String... scanpackage) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        List<Class> list = scanPackage(isChild, scanpackage);
        for (Class c : list) {
            c.isAnnotationPresent(an);
            classes.add(c);
        }
        return classes;
    }

    /**
     * 扫描某个包下具有某个注解的所有方法的集合
     */
    public static List<Method> MethodHasAnnotation(boolean isChild, Class an, String... scanpackage) {
        List<Class> list = scanPackage(isChild, scanpackage);
        List<Method> methods = new ArrayList<Method>();
        for (Class c : list) {
            Method[] ma = c.getDeclaredMethods();
            for (Method m : c.getDeclaredMethods()) {
                if (m.isAnnotationPresent(an)) {
                    methods.add(m);
                }
            }
        }
        return methods;
    }

    /**
     * 扫描某个包下的所有类
     *
     * @param isChild     是否扫描子包
     * @param scanpackage 要扫描的包
     */
    public static List<Class> scanPackage(boolean isChild, String... scanpackage) throws SysException {
        List<Class> classes = new ArrayList<Class>();
        // 获取包的名字 并进行替换
        for (String packags : scanpackage) {
            if (!UtilFun.isEmptyString(packags)) throw new SysException("package name is not null");
            String packageDirName = packags.replace('.', '/');
            Enumeration<URL> dirs;
            try {
                dirs = Thread.currentThread().getContextClassLoader().getResources(
                        packageDirName);
                while (dirs.hasMoreElements()) {
                    URL url = dirs.nextElement();
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        // 获取包的物理路径
                        String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                        FindbyFile(packags, filePath, isChild, classes);
                    } else if ("jar".equals(protocol)) {
                        FindbyJar(packags, url, isChild, classes);
                    } else {
                        throw new SysException("解析路径出错");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return classes;
    }

    /**
     * jar包下的类
     */
    private static void FindbyJar(String packageName, URL url, boolean isChile, List<Class> classes) {
        JarFile jar;
        String packageDirName = packageName.replace(".", "/");
        try {
            jar = ((JarURLConnection) url.openConnection()).getJarFile();
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (name.charAt(0) == '/') {
                    name = name.substring(1);
                }
                if (name.startsWith(packageDirName)) {
                    int idx = name.lastIndexOf('/');
                    // 如果以"/"结尾 是一个包
                    if (idx != -1) {
                        // 获取包名 把"/"替换成"."
                        packageName = name.substring(0, idx)
                                .replace('/', '.');
                    }
                    if ((idx != -1) || isChile) {
                        if (name.endsWith(".class") && !entry.isDirectory()) {
                            String className = name.substring(
                                    packageName.length() + 1,
                                    name.length() - 6);
                            try {
                                // 添加到classes
                                classes.add(Class
                                        .forName(packageName + '.'
                                                + className));
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 目录下的类
     */
    private static void FindbyFile(String packageName, String url, boolean isChile, List<Class> classes) throws UnsupportedEncodingException {
        File dir = new File(url);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new SysException("用户定义包名 " + url + " 下没有任何文件");
        }
        File[] dirfiles = dir.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (file.isDirectory())
                        || (file.getName().endsWith(".class"));
            }
        });
        for (File file : dirfiles) {
            if (file.isDirectory()) {
                FindbyFile(packageName + "." + file.getName(), file.getAbsolutePath(), isChile, classes);
            } else {
                String className = file.getName().substring(0,
                        file.getName().length() - 6);
                try {
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
