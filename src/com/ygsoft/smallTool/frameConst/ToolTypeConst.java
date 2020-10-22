package com.ygsoft.smallTool.frameConst;

import java.util.HashMap;
import java.util.Map;

/**
 * 工具类型常量类.
 * @author tuzewen
 *
 */
public final class ToolTypeConst {

    /**
     * 字符串处理工具
     */
    public static final String[] STRTOOLTYPE = new String[]{
            "---请选择---",
            "1.将java中sql与plsql中sql进行转换",
            "2.根据VO或者PO生成POVO转换类",
            "3.获取每行某一位置字符串",
            "4.一行与多行转换",
            "5.根据字段信息自动生成参考模型"
    };

    /**
     * 字符串处理工具
     */
    public static final String[] SQLTOOLTYPE = new String[]{
            "---请选择---",
            "1.常用sql合集"
    };
    
    /**
     * 代码生成工具
     */
    public static final String[] BUILDCODETYPE = new String[]{
            "---请选择---",
            "1.快速生成Context,QueryContext"
    };

    /**
     * 代码生成工具
     */
    public static final String[] LOOPFILETYPE = new String[]{
            "---请选择---",
            "1.扫描指定文件夹下的文件",
            "2.按dap平台规范修改html和js"
    };

    /**
     * 工具类型加说明.
     */
    public static final Map<String, String> TOOLTYPEMAP = new HashMap<String, String>();
    
    /**
     * 代码生成工具类型加说明.
     */
    public static final Map<String, String> BUILDCODETYPEMAP = new HashMap<String, String>();

    static {
    	/**
    	 * "1.将java中sql与plsql中sql进行转换"
    	 * "2.根据VO或者PO生成POVO转换类"
    	 * "3.获取每行某一位置字符串"
    	 * "4.灵活多行合并成一行"
    	 * "5.根据字段信息自动生成参考模型"
    	 *
    	 */
        TOOLTYPEMAP.put("1", "将java中sql与plsql中sql进行转换");
        TOOLTYPEMAP.put("2", "根据VO或者PO中的变量及get，set方法生成transfer中的转换方法");
        TOOLTYPEMAP.put("3", "根据标识符，获取指定标识符后的字符串，并用逗号拼接");
        TOOLTYPEMAP.put("4", "将多行字符串合并成一行,或者将一行字符串拆分成多行");
        TOOLTYPEMAP.put("5", "填写字段信息,自动生成参考模型(支持多个)");
        /**
         * 1.对应路径生成Context,Role,Dao,QueryContext,QueryRole,QueryDao
         */
        BUILDCODETYPEMAP.put("1", "对应路径生成Context,Role,Dao,QueryContext,QueryRole,QueryDao");
        
    }


}
