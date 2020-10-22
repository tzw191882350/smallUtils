package com.ygsoft.createDapFile.run;

import com.ygsoft.createDapFile.fileType.AbstractQueryAppServiceFileUtils;
import com.ygsoft.createDapFile.fileType.AbstractQueryControllerFileUtils;
import com.ygsoft.createDapFile.fileType.AbstractQueryFacadeFileUtils;
import com.ygsoft.createDapFile.fileType.AppServiceFileUtils;
import com.ygsoft.createDapFile.fileType.QueryControllerFileUtils;
import com.ygsoft.createDapFile.fileType.QueryFacadeFileUtils;
import com.ygsoft.test.loopFileUtil.LoopFileUtilTest;

import java.util.List;

/**
 * Hello world!
 *
 */
public class DapRun {
    public static void main( String[] args ) {
    	final String contextPath = "D:\\ideaworkspace\\constructfinance\\fms-project-finacial-mgt\\gmp-fm-constructfinance-app\\com\\ygsoft";
		final List<String> contextNameList = LoopFileUtilTest.getFileNameList(contextPath);
    	for (int i = 0; i < contextNameList.size(); i++) {
//			final String preName = contextNameList.get(i).replaceAll("QueryContext.java", "").replaceAll("Context.java", "");
			final String preName = contextNameList.get(i).replaceAll("FileUtils.java", "").replaceAll("FileUtils.java", "");
    		modifyFile(preName);
		}
    }

	private static void modifyFile(String name) {
		AppServiceFileUtils.appServiceFile(name);
		AbstractQueryAppServiceFileUtils.abstractQueryAppServiceFile(name);
		QueryControllerFileUtils.queryControllerFile(name);
		AbstractQueryControllerFileUtils.abstractQueryControllerFile(name);
		QueryFacadeFileUtils.queryFacadeFile(name);
		AbstractQueryFacadeFileUtils.abstractQueryFacadeFile(name);
	}

}
