package com.ygsoft.smallTool.loopFileUtil;

import com.ygsoft.framework.buildUtil.BuildParamsMap;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 替换css路径.
 * @author tuzewen <br>
 */
public class LoopFileUtil {

	/**
	 * line.
	 */
	private static String line;
	/**
	 * 执行方法类型
	 */
	private static String type;
	/**
	 * 配置参数
	 */
	private static Map<String, String> paramMap;
	/**
	 * 返回文件名或路径
	 */
	private static StringBuilder result;
	/**
	 * 日志
	 */
    private static StringBuilder info;
	/**
	 * 扫描文件总数
	 */
	private static int viewFileCounts;
	/**
	 * 写入文件总数
	 */
	private static int writeFileCounts;
	/**
	 * 匹配文件总数
	 */
	private static int compareFileCounts;
    /**
     * 文件是否需要修改
     */
    private static boolean isModify;
	/**
	 * 循环文件.
	 * @param methodType,params 方法类型和参数
	 */
	public static String loopFile(final String methodType, final String params) {
		initParam(methodType, params);
		traverseFolder2(paramMap.get("filePath"));
//		System.out.println("所有修改的文件:" + SubsCssPathConst.modifyFileName.toString());
        result.append("共修改文件:" + writeFileCounts + "个\n");
		result.append("共匹配文件:" + compareFileCounts + "个\n");
		result.append("共扫描文件:" + viewFileCounts + "个");
		return result.toString();
	}

	/**
	 * 初始化变量
	 */
	private static void initParam(final String methodType, final String params) {
		result = new StringBuilder();
		info = new StringBuilder();
		type = methodType;
		paramMap = BuildParamsMap.strToMap(params);
		viewFileCounts = 0;
        writeFileCounts = 0;
		compareFileCounts = 0;
        isModify = false;
	}
	
	/**
	 * 递归路径下所有的文件.
	 * @param path
	 */
	private static void traverseFolder2(final String path) {
		InputStream is;
		Reader reader;
		BufferedReader bufferedReader = null;
		final File file = new File(path);
		try {
			if (file.exists()) {
				final File[] files = file.listFiles();
				if (null == files || files.length == 0) {
					return;
				} else {
					for (final File file2 : files) {
						if (file2.isDirectory()) {
							traverseFolder2(file2.getAbsolutePath());
						} else {
							is = new FileInputStream(file2);
							reader = new InputStreamReader(is, StandardCharsets.UTF_8);
							bufferedReader = new BufferedReader(reader);
							viewFileCounts++;
                            recursiveFile(file2, bufferedReader);
							bufferedReader.close();
						}
					}
				}
			}
		} catch (final IOException e) {
			
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 遍历目录下的所有文件
	 */
	private static void recursiveFile(final File file2, final BufferedReader bufferedReader) throws IOException {
		final String filePath = file2.getAbsolutePath();
		com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.title = "";
		com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.isComments = false;
		final int typeInt = Integer.parseInt(type);
		switch (typeInt) {
			case 1:
				findAllCompareFile(filePath, file2);
				break;
			case 2:
				updateFileByDap(file2, bufferedReader);
				break;
			default:
				System.out.println();
		}
		if (com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.INFOENABLE) {
			System.out.println(info);
		}
		
	}

    /**
	 * 查找所有匹配文件
	 * @param filePath 文件路径
	 * @param file2 正在读的文件
	 */
	private static void findAllCompareFile(final String filePath, final File file2) {
		// 文件类型和文件名过滤后添加
		if (filterFileNameAndType(file2)) {
            setResult(filePath, file2);
		}
	}

	/**
	 * 按dap平台规范修改文件(html+js).
	 * @throws IOException 读写文件异常
	 */
	private static void updateFileByDap(final File file2, final BufferedReader bufferedReader) throws IOException {
        // 文件类型和文件名符合条件才改
		if (filterFileNameAndType(file2)) {
		    // 文件内容
		    final StringBuilder fileContent = new StringBuilder();
			while ((line = bufferedReader.readLine()) != null) {
			    if (file2.getName().contains(".html")) {
                    updateDapHtml(fileContent);
                } else if(file2.getName().contains(".js")) {
			        if  (line.contains("C5.use(")) {
			            break;
                    }
                    if (line.contains("require(")) {
                        isModify = true;
                        appendLn(fileContent, "C5.use([\"ecp.start\"], function(){");
                        fileContent.append(line);
                    } else {
                        fileContent.append(line);
                    }
                }
				fileContent.append("\n");
			}
            if(file2.getName().contains(".js") && isModify) {
                fileContent.append("});");
            }
			write(fileContent.toString(), file2);
		}
	}

	/**
	 * dap修改html文件内容
     * @Param [fileContent] 修改的文件内容
	 */
	private static void updateDapHtml(final StringBuilder fileContent) {
        if(line.contains("<script") && line.contains("ecp.starter.js")) {
            isModify = true;
            // line = <script data-main="dkllwh" src="../../ecp/webcore/scripts/ecp.starter.js......">
            // 第一个引号下标
            final int firQuotes = line.indexOf("\"");
            //line中的../
            final String preStr = line.substring(line.indexOf("../"), line.lastIndexOf("../") + 3);
            final String jsName = line.substring(firQuotes + 1, line.indexOf("\"", firQuotes + 1));
            fileContent.append("<script type=”text/javascript” src=”" + preStr + "cui5/comtop.c5.core.js”></script>\n");
            fileContent.append("<script type=”text/javascript” src=”" + preStr + "config.js”></script>\n");
            fileContent.append("<script type=”text/javascript” src=”" + jsName.replace(".js", "") + ".js”></script>");
        } else {
            fileContent.append(line);
        }
    }

	/**
	 * 过滤文件类型和文件名.
	 * @return boolean 文件名和文件类型同时满足才返回true
	 */
	private static boolean filterFileNameAndType(final File file2) {
		final String fileName = file2.getName();
		final String[] fileNameFilter = paramMap.get("fileNameFilter").split(",");
		final int size =  fileNameFilter.length;
		final String fileType = paramMap.get("fileTypeFilter");
		final String[] fileTypeFilter = fileType.split(",");
		final int typeSize =  fileTypeFilter.length;
		boolean typeCompare = true;
		if (!fileType.contains("*.*")) {
			// 比较文件类型是否满足
			for (int i = 0; i < typeSize; i++) {
				if (!fileName.endsWith(fileTypeFilter[i].trim().replace("*", ""))) {
					typeCompare = false;
					break;
				}
			}
		}
		if (typeCompare) {
			// 如果文件类型满足，则继续比较文件名称是否满足
			Boolean nameCompare = true;
			for (int i = 0; i < size; i++) {
				if (!fileName.contains(fileNameFilter[i].trim())) {
					nameCompare = false;
					break;
				}
			}
			if (nameCompare) {
				compareFileCounts++;
				return true;
			}
		}
		return typeCompare;
	}

	/**
     * 设置返回内容.
	 */
    private static void setResult(final String filePath, final File file2) {
        final String showAbsolutePath = paramMap.get("showAbsolutePath").trim();
        if ("true".equals(showAbsolutePath)) {
            appendLn(result, filePath);
        } else {
            appendLn(result, file2.getName());
        }
    }

	/**
	 * 字符串中字母出现的次数.
	 */
	private static int countString(String str,final String s) {
        int count = 0;
        for(int i= 0; i<=str.length(); i++){
            if(str.indexOf(s) == i){
                str = str.substring(i+1);
                i = 0;
                count++;
            }
        }
        return count;
    }

    /**
     * 追加并换行
     */
    private static void appendLn(final StringBuilder sb, final String str) {
	    sb.append(str + "\n");
    }

	/**
	 * 一个字符在指定字符串中第N次出现的位置.
	 */
	private static int getIndexByTime(final String str,final String s, final int time) {
        final int count = countString(str, s);
        if (time > count || count == 0) {
        	return -1;
        }
        int index = 0;
        for(int i = 0; i < time; i++){
        	if (i == 0) {
        		index = str.indexOf(s, index);
        	} else {
        		index = str.indexOf(s, index + 1);
        	}
        }
        return index;
    }
	
	/**
	 * 写入文件.
	 * @param cont 需要写入的内容
	 * @param dist 文件
	 */
	private static void write(final String cont, final File dist) {
		if (!isModify) {
		    return;
        }
	    BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(dist));
            writeFileCounts++;
			com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.modifyFileName.append(dist.getName() + "\n");
            isModify = false;
            setResult(dist.getAbsolutePath(), dist);
			writer.write(cont);
			writer.flush();
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
