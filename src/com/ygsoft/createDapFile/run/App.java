package com.ygsoft.createDapFile.run;

import com.ygsoft.createDapFile.fileType.AbstractQueryAppServiceFileUtils;
import com.ygsoft.createDapFile.fileType.AbstractQueryControllerFileUtils;
import com.ygsoft.createDapFile.fileType.AbstractQueryFacadeFileUtils;
import com.ygsoft.createDapFile.fileType.AppServiceFileUtils;
import com.ygsoft.createDapFile.fileType.QueryControllerFileUtils;
import com.ygsoft.createDapFile.fileType.QueryFacadeFileUtils;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

    	String[] arr = new String[] { "Dkllwh" };
    	for (int i = 0; i < arr.length; i++) {
    		modifyFile(arr[i]);
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
