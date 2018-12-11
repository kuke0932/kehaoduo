package com.htcs.kehaoduo.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class UUIDUtil {
    /**
     * 获得一个UUID
     *
     * @return String UUID
     */
    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        //去掉“-”符号 
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }


    public static String getUUIDWithDateRandom() {
        String dateTime = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        String ranEight = String.format("%08d", new Random().nextInt(99999999));
        return dateTime + uuid + ranEight;
    }

    /**
     * 获得指定数目的UUID
     *
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUUID();
        }
        return ss;
    }
}
