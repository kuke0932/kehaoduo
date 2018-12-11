package com.htcs.kehaoduo.common.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class JsonUtil {

    public static Map<String, Object> json2Map(String jsonStr) {
        if (Strings.isNullOrEmpty(jsonStr)) {
            return null;
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        // 将json字符串转换成jsonObject
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        // 循环找出所有的属性及值
        for (Entry<String, Object> entry : jsonObject.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    /**
     * json 转list
     *
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static <T> List<T> json2List(String jsonStr, Class<T> clazz) {
        if (Strings.isNullOrEmpty(jsonStr)) {
            return null;
        }
        List<T> list = JSONObject.parseArray(jsonStr, clazz);
        return list;
    }

    /**
     * 将list 转换为 json
     *
     * @param list
     * @return
     */
    public static <T> String list2Json(List<T> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        String json = JSONObject.toJSONString(list);
        return json;
    }

    /**
     * 将oject 转换为 json
     *
     * @param object
     * @return
     */
    public static String object2Json(Object object) {
        if (object == null) {
            return null;
        }
        String json = JSONObject.toJSONString(object);
        return json;
    }


    /**
     * json 转been
     *
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static <T> T json2been(String jsonStr, Class<T> clazz) {
        if (Strings.isNullOrEmpty(jsonStr)) {
            return null;
        }
        T obj = JSONObject.parseObject(jsonStr, clazz);
        return obj;
    }


    public static void main(String[] args) {
        String str = "{\"id\":\"de96a0a8-20a4-46ac-a2de-a46abb6c90fd\",\"createTime\":\"2015-08-27T07:09:08.244Z\",\"updateTime\":\"2015-08-27T07:09:08.244Z\",\"version\":1440659348244,\"type\":\"OFFLINE_PAY\",\"status\":\"CREATED\",\"userId\":\"d53ee339-59e9-47e8-8480-745edb423287\",\"bankAccountId\":\"edfa29b2-fd9c-419c-9f20-811b11222be5\",\"accountId\":\"0cabeae6-d8b7-412f-9d5c-d8698365a5af\",\"principal\":10000,\"userFees\":0,\"sumLateFees\":0,\"reduUserFees\":0,\"reduLateFees\":0,\"reduStatus\":0,\"bankCardHolder\":\"银行卡持有人811b11\",\"bankCardNo\":\"7883017024766789\",\"bankNo\":\"305473016119\",\"branchOffice\":\"中国民生银行临沭支行\",\"province\":\"居住的省份811b11222be5\",\"city\":\"临沂市\",\"bankCardVerified\":0,\"kyPayUserType\":1,\"bankAccronym\":\"CMBC\",\"bankName\":\"中国民生银行\"}";
        Map<String, Object> map = JsonUtil.json2Map(str);
        for (Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        String slist = "[{\"name\":\"sEcho\",\"value\":1},{\"name\":\"iColumns\",\"value\":5},{\"name\":\"sColumns\",\"value\":\"\"},{\"name\":\"iDisplayStart\",\"value\":0},{\"name\":\"iDisplayLength\",\"value\":10},{\"name\":\"mDataProp_0\",\"value\":\"id\"},{\"name\":\"mDataProp_1\",\"value\":\"bankCardHolder\"},{\"name\":\"mDataProp_2\",\"value\":\"bankName\"},{\"name\":\"mDataProp_3\",\"value\":\"idCardNO\"},{\"name\":\"mDataProp_4\",\"value\":\"billingRequestId\"}]";
        List<JSONObject> list = JsonUtil.json2List(slist, JSONObject.class);

        System.out.println(list.get(0).getString("name"));

    }
}
