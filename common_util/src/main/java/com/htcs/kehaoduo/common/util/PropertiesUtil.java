package com.htcs.kehaoduo.common.util;

import com.htcs.kehaoduo.common.Common;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * <p>
 * 最简单的属性文件读取工具类
 * </p>
 *
 * @author GuoYF
 * @version 1.0
 */
public class PropertiesUtil {

    /**
     * 默认属性集合（文件在Constants中配置）
     */
    private static Properties defaultProp = null;
    /**
     * 所有读取过的属性集合
     * 文件名 <-> 属性集合
     */
    private static Map<String, Properties> allProps = new HashMap<String, Properties>();

    // 初始化默认的属性集合
    static {
        init();
    }

    public static void init() {
        if (defaultProp == null) {
            defaultProp = loadProperties(Common.FILE_NAME_PROP);
            allProps.put(Common.FILE_NAME_PROP, defaultProp);
        }
    }

    /**
     * 读取属性文件，并将读出来的属性集合添加到【allProps】当中
     * 如果该属性文件之前已读取过，则直接从【allProps】获得
     */
    public static Properties getProperties(String fileName) {
        if (fileName == null || "".equals(fileName)) {
            if (defaultProp == null) {
                init();
            }
            return defaultProp;
        } else {
            Properties prop = allProps.get(fileName);
            if (prop == null) {
                prop = loadProperties(fileName);
                allProps.put(fileName, prop);
            }

            return prop;
        }
    }

    /**
     * 解析属性文件，将文件中的所有属性都读取到【Properties】当中
     */
    private static Properties loadProperties(String fileName) {
        Properties prop = new Properties();
        InputStream ins = null;
        ins = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
        if (ins == null) {
            System.err.println("Can not find the resource!");
        } else {
            try {
                prop.load(ins);
            } catch (IOException e) {
                System.err.println("An error occurred when reading from the input stream, " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println("The input stream contains a malformed Unicode escape sequence, " + e.getMessage());
            }
        }
        return prop;
    }

    /**
     * 从指定的属性文件中获取某一属性值
     * 如果属性文件不存在该属性则返回 null
     */
    public static String getProperty(String fileName, String name) {
        return getProperties(fileName).getProperty(name);
    }

    /**
     * 从默认的属性文件中获取某一属性值
     * 如果属性文件不存在该属性则返回 null
     */
    public static String getProperty(String name) {
        return getProperties(null).getProperty(name);
    }
}
