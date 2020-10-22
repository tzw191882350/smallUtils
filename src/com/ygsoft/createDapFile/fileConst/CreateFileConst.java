package com.ygsoft.createDapFile.fileConst;

import java.io.File;
import java.io.IOException;

/**
 * 创建文件工具常量类.
 */
public final class CreateFileConst {

    /**
     * 生成文件的路径.
     */
    public static final String CREATEFILEPATH = "D:\\ideaworkspace\\smallUtils\\src\\com\\ygsoft\\createDapFile\\testFile\\";

    /**
     * 作者.
     */
    public static final String AUTHOR = "tuzewen";

    /**
     * 获取模板文件路径.
     */
    public static final String getReadfilepath() {
        try {
            return new File("").getCanonicalPath() + "\\source\\";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}
