package com.ygsoft.loopFileUtil.fileConst;

import java.util.HashMap;
import java.util.Map;

public class SubsCssPathConst {

	/**
	 * 扫描文件总数.
	 */
	public static int FILECOUNTS = 0;
	/**
	 * 修改文件总数.
	 */
	public static int WRITEFILECOUNTS = 0;
	/**
	 * 匹配文件总数.
	 */
	public static int RIGHTCOUNTS = 0;
	/**
	 * 指定层级.
	 */
	public static final int ZDCJ = 2;
	/**
	 * 初始层级.
	 */
	public static int CSCJ = 1;
	/**
	 * 标题.
	 */
	public static String title = "";
	/**
	 * 记录信息map.
	 */
	public static Map<String, Boolean> infoMap = new HashMap<String, Boolean>();
	public static final StringBuilder modifyFileName = new StringBuilder(); 
	/**
	 * 文件是否需要修改
	 */
	public static boolean isModify = false;
	/**
	 * 是否多行注释
	 */
	public static boolean isComments = false;
	/**
	 * 换CSS相对路径，true是执行，false不执行.
	 */
	public static final boolean INFOENABLE = false;
	/**
	 * 换CSS相对路径，true是执行，false不执行.
	 */
	public static final boolean SUBCSS = false;
	/**
	 * 增加java的Context的注解@service，true是执行，false不执行.
	 */
	public static final boolean SUBJAVA = false;
	/**
	 * 替换js中穿透其他页面的url地址，true是执行，false不执行.
	 */
	public static final boolean SUBURL = false;
	/**
	 * 把所有含getWebAppRoot的行中单引号全部替换成双引号.
	 */
	public static final boolean SUBATOB = false;
	/**
	 * 把framework前端的start.js替换成绝对路径.
	 */
	public static final boolean SUBSTARTJS = false;
	/**
	 * 替换mdmx文件的html路径.
	 */
	public static final boolean SUBMDMXHTML = false;
	/**
	 * 替换单据的html文件中的各种路径.
	 */
	public static final boolean SUBYWDJHTML = false;
	/**
	 * 修改js文件中的引用其他文件的路径.
	 */
	public static final boolean SUBJSPATHJS = false;
	/**
	 * 修改actx文件中的html路径.
	 */
	public static final boolean SUBHTMLPATHACTX = false;
	/**
	 * 查找文件.
	 */
	public static final boolean FINDFILE = false;
	/**
	 * 查找文件路径.
	 */
	public static final boolean FINDFILEPATH = true;
	
	
	/**
	 * 微服务名称.
	 */
	public static final String WFWNAME = "com.ygsoft.gris.mapp.std.construct.finance.web";
	/**
	 * 层级和相对路径对应关系.
	 */
	public static Map<Integer, String> cjDyPath = new HashMap<Integer, String>();

}
