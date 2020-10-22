package com.ygsoft.test.loopFileUtil;

import com.ygsoft.smallTool.loopFileUtil.LoopFileUtil;

/**
 * @author tuzewen
 * @createTime {@YEAR}年{@MONTH}月{@DAY}日
 */
public class TestMain {

    public static void main(String[] args) {
        final String line = "<script data-main=\"dkllwh\" src=\"../../ecp/webcore/scripts/ecp.starter.js......\">";
        final String preStr = line.substring(line.indexOf("../"), line.lastIndexOf("../") + 3);
        System.out.println(preStr);
    }


}
