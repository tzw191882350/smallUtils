package com.ygsoft.test.loopFileUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tuzewen
 * @createTime {@YEAR}年{@MONTH}月{@DAY}日
 */
public final class LoopFileUtilTest {

    /**
     * 文件名集合.
     */
    private static final List<String> fileNameList = new ArrayList<>();

    /**
     * 获取匹配文件名集合.
     */
    public static List<String> getFileNameList(final String filePath) {
        fileNameList.clear();
        traverseFolder2(filePath);
        return fileNameList;
    }

    /**
     * 查找所有文件路径
     * @param filePath
     */
    private static void findFilePath(final String filePath) {
        final int index = filePath.lastIndexOf("\\");
        final String contextName = filePath.substring(index + 1);
        fileNameList.add(contextName);
    }

    /**
     * 递归路径下所有的文件.
     * @param path
     */
    public static void traverseFolder2(final String path) {
        InputStream is = null;
        Reader reader = null;
        BufferedReader bufferedReader = null;
        final File file = new File(path);
        try {
            if (file.exists()) {
                final File[] files = file.listFiles();
                if (null == files || files.length == 0) {
//					System.out.println("文件夹是空的!");
                    return;
                } else {
                    for (final File file2 : files) {
                        if (file2.isDirectory()) {
                            traverseFolder2(file2.getAbsolutePath());
                        } else {
                            is = new FileInputStream(file2);
                            reader = new InputStreamReader(is, StandardCharsets.UTF_8);
                            bufferedReader = new BufferedReader(reader);
                            com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.FILECOUNTS++;
                            absoluteFile(file2, bufferedReader);
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
     * 处理文件
     * @param file2
     * @throws IOException
     */
    private static void absoluteFile(final File file2, final BufferedReader bufferedReader) throws IOException {
        final String filePath = file2.getAbsolutePath();
        com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.title = "";
        com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.isModify = false;
        com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.isComments = false;
        if (filePath.contains("com.ygsoft.gris")
                && filePath.endsWith("Context.java")
                && filePath.contains("service")) {
            findFilePath(filePath);
        }
        if (filePath.endsWith("FileUtils.java")) {
            findFilePath(filePath);
        }

    }
}
