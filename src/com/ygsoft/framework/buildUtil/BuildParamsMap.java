package com.ygsoft.framework.buildUtil;

import com.sun.deploy.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 构建客户端传过来的参数
 */
public class BuildParamsMap {

    /**
     * 参数字符串转参数Map
     * @Param []
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    public static Map<String, String> strToMap(final String paramStr) {
        final Map<String, String> paramMap = new HashMap<>();
        if ("".equals(paramStr)) {
            return paramMap;
        }
        final String[] paramStrArr = paramStr.split("\n");
        final int size = paramStrArr.length;
        for (int i = 0; i < size; i++) {
            final String lineStr = paramStrArr[i];
            if (!"".equals(lineStr)) {
                final String[] strSplitArr = lineStr.split(":", 2);
                final String preStr = strSplitArr[0].trim();
                final String aftStr = strSplitArr[1].trim();
                paramMap.put(preStr, aftStr);
            }
        }
        return paramMap;
    }
}
