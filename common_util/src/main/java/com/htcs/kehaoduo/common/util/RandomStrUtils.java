package com.htcs.kehaoduo.common.util;

import java.util.Random;

/**
 * @author zhaokaiyuan
 */
public class RandomStrUtils {

    private static String salt = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    private static String saltNumber = "1234567890";

    public static <T> T isNullAThenB(T a, T b) {
        return a == null ? b : a;
    }

    /**
     * 生成一个随机字符串
     *
     * @param length 要生成的字符串的位数
     * @return
     */
    public static String generateStr(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(salt.charAt(random.nextInt(62)));
        }
        return sb.toString();
    }


    /**
     * 生成一个随机字符串
     *
     * @param length 要生成的字符串的位数
     * @return
     */
    public static String generateOnlyNumber(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(saltNumber.charAt(random.nextInt(10)));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String str = generateStr(6);
        System.out.println(str);
    }
}
