package com.ygsoft.createDapFile.fileType;


import com.ygsoft.createDapFile.fileConst.CreateFileConst;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AbstractQueryAppServiceFileUtils {
	
	private static final String firstUrl = CreateFileConst.getReadfilepath() + "AbstractQueryAppService.txt";
	
	private static final String MODIFYURL = CreateFileConst.CREATEFILEPATH;

	public static void abstractQueryAppServiceFile(final String contentName) {
    	modifyFileContent(firstUrl, "QueryAppService", contentName);
	}
	
    /**
     * 修改文件内容：字符串逐行替换
     */
    public static boolean modifyFileContent(final File file, final String sufstr, final String newStr) {
        List<String> list = null;
        try {
        	final File newFile = new File(MODIFYURL + "abs\\Abstract"+ newStr + sufstr + ".java");
            if (newFile.exists()) {
        		System.out.println(newFile + "文件已存在不作处理。");
    			return true;
    		}
            list = FileUtils.readLines(file, "UTF-8");
            for (int i = 0; i < list.size(); i++) {
            	final String line = list.get(i);
            	String temp = line.replaceAll("abstractname", "Abstract".concat(newStr.concat(sufstr)));
                temp = temp.replaceAll("yourname", CreateFileConst.AUTHOR);
                list.remove(i);
                list.add(i, temp);
            }
            FileUtils.writeLines(newFile, "UTF-8", list, "\n");
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean modifyFileContent(final String filePath, final String oldstr, final String newStr) {
        return modifyFileContent(new File(filePath), oldstr, newStr);
    }
    
}