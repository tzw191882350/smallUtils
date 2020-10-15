package com.ygsoft.createDapFile.fileType;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;


public class AbstractQueryAppServiceFileUtils {
	
	private static final String firstUrl = "E:\\南网云化改造\\filetool\\AbstractQueryAppService.txt";
	
	private static final String modifyUrl = "D:\\develop\\gitCode\\NW_electricity\\com.ygsoft.gris.mapp.std.electricity.app\\src\\main\\java\\cn\\csg\\gmp\\fm\\electricity\\appservice\\abs\\";
	
	public static void abstractQueryAppServiceFile(final String contentName) {
    	modifyFileContent(firstUrl, "QueryAppService", contentName);
	}
	
    /**
     * 修改文件内容：字符串逐行替换
     */
    public static boolean modifyFileContent(final File file, final String sufstr, final String newStr) {
        List<String> list = null;
        try {
        	final File newFile = new File(modifyUrl + "Abstract"+ newStr + sufstr + ".java");
            if (newFile.exists()) {
        		System.out.println(newFile + "文件已存在不作处理。");
    			return true;
    		}
            list = FileUtils.readLines(file, "UTF-8");
            for (int i = 0; i < list.size(); i++) {
            	final String line = list.get(i);
            	final String temp = line.replaceAll("abstractname", "Abstract".concat(newStr.concat(sufstr)));
                list.remove(i);
                list.add(i, temp);
            }
            FileUtils.writeLines(newFile, "UTF-8", list, "");
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean modifyFileContent(final String filePath, final String oldstr, final String newStr) {
        return modifyFileContent(new File(filePath), oldstr, newStr);
    }
    
}