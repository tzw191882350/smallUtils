package com.ygsoft.test.loopFileUtil;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * 替换css路径.
 * @author tuzewen <br>
 */
@SuppressWarnings("all")
public class SubsCssPath {

	/**
	 * line.
	 */
	public static String line;
	
	/**
	 * 批量修改文件.
	 * @param args
	 * @throws Exception 
	 */
	public static void run() {
		final StringBuilder xdPath = new StringBuilder();
		// 初始层级路径
//		String path = "E:\\git\\gris.finance\\com.ygsoft.gris.mapp.std.construct.finance.web" 
//				+ "\\src\\main\\resources\\META-INF\\resources";
//		final String path = "E:\\tianqin\\gris.finance\\com.ygsoft.gris.mapp.std.construct.finance\\src\\main\\java\\com\\ygsoft\\gris\\construction";
		// tuzewen的path
		// 替换business的@service路径
//		String path = "E:\\tianqin\\gris.business.framework\\com.ygsoft.gris.mapp.business.framework\\src\\main\\java\\com\\ygsoft\\gris\\business\\framework";
		// 替换css的路径finace
//		String path = "E:\\tianqin\\gris.finance\\com.ygsoft.gris.mapp.std.construct.finance.web\\src\\main\\resources\\META-INF\\resources";
		// 替换css的路径finace
//		String path = "E:\\tianqin\\gris.framework\\com.ygsoft.gris.component.std.construct.framework.web\\src\\main\\resources\\META-INF\\resources";
		// 替换js中html的路径
//		String path = "E:\\tianqin\\gris.finance\\com.ygsoft.gris.mapp.std.construct.finance.web\\src\\main\\resources\\META-INF\\resources";
		//E:\tianqin\gris.framework\com.ygsoft.gris.component.std.construct.framework.web\src\main\resources\META-INF\resources
		// 替换js中html的路径
//		final String path = "E:\\tianqin\\gris.framework\\com.ygsoft.gris.component.std.construct.framework.web\\src\\main\\resources\\META-INF\\resources";
		// 修改mdmx文件中html路径
//		final String path = "E:\\tianqin\\gris.finance\\com.ygsoft.gris.mapp.std.construct.finance\\src\\main\\resources\\META-INF\\config";
		// 单据的html文件中的各种路径
//		final String path = "E:\\tianqin\\gris.finance\\com.ygsoft.gris.mapp.std.construct.finance.web\\src\\main\\resources\\META-INF\\resources";
		// 单据的actx文件中的各种路径
//		final String path = "E:\\tianqin\\gris.finance\\com.ygsoft.gris.mapp.std.construct.finance\\src\\main\\resources\\META-INF\\config";
//		final String path = "E:\\eclipse6.1";
		// 查找文件内容路径
//		final String path = "C:\\Users\\tuzewen\\Desktop\\有迹可循\\历史";
		final String path = "E:\\eclipse6.0";
		com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.CSCJ = countString(path, "\\");
		for (int i = com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.CSCJ + 3; i < 100; i++) {
			xdPath.append("../");
			com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.cjDyPath.put(i, xdPath.toString());
		}
		traverseFolder2(path);
		System.out.println("所有修改文件:\n" + com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.modifyFileName.toString());
		System.out.println("共修改文件:" + com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.WRITEFILECOUNTS + "个");
		System.out.println("共匹配文件:" + com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.RIGHTCOUNTS + "个");
		System.out.println("共扫描文件:" + com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.FILECOUNTS + "个");
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
							reader = new InputStreamReader(is, "UTF-8");
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
		final int lastNameIndex = filePath.lastIndexOf("\\") + 1;
		final StringBuilder info = new StringBuilder();
		com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.title = "";
		com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.isModify = false;
		com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.isComments = false;
		if (com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.SUBCSS) {
			info.append("准备修改文件的css");
			subCssMethod(filePath, file2, bufferedReader);
		}
		if (com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.SUBJAVA) {
			info.append("准备修改文件的@Service注解");
			subJavaMethod(filePath, file2, bufferedReader);
		}
		if (com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.SUBURL) {
			info.append("准备修改文件的穿透的url地址");
			subUrlpathMethod(filePath, file2, bufferedReader);
		}
		if (com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.SUBATOB) {
			info.append("准备修改文件的单引号变双引号");
			subAtoBMethod(filePath, file2, bufferedReader);
		}
		if (com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.SUBSTARTJS) {
			info.append("准备修改文件的start.js路径");
			subStartPathMethod(filePath, file2, bufferedReader);
		}
		if (com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.SUBMDMXHTML) {
			info.append("准备修改文件的html路径");
			subMdmxHtml(filePath, file2, bufferedReader);
		}
		if (com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.SUBYWDJHTML) {
			info.append("准备修改文件的各种引用路径");
			subYwdjHtml(filePath, file2, bufferedReader);
		}
		if (com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.SUBJSPATHJS) {
			info.append("准备修改js文件中的引用其他文件的路径");
			subJsPath(filePath, file2, bufferedReader);
		}
		if (com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.SUBHTMLPATHACTX) {
			info.append("准备修改actx文件中的html路径");
			subActxPath(filePath, file2, bufferedReader);
		}
		if (com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.FINDFILE) {
			info.append("扫描待匹配文件的路径");
			findFile(filePath, file2, bufferedReader);
		}
		if (com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.FINDFILEPATH && filePath.contains("com.ygsoft.gris") && filePath.endsWith("Context.java")
				&& filePath.contains("service")) {
			info.append("扫描待匹配文件的路径");
			findFilePath(filePath, file2, bufferedReader);
		}
		
//		info.append("--" + filePath.substring(lastNameIndex, filePath.length()) + SubsCssPathConst.title + "\n");
		if (com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.INFOENABLE) {
			System.out.println(info);
		}
		
	}
	
	/**
	 * 批量替换Css方法.
	 * @throws IOException 
	 */
	private static void subCssMethod(final String filePath, final File file2, final BufferedReader bufferedReader) throws IOException {
		// 层级 //alarmManageGZKZBJUI3,FinaGeneYSReport
		if (filePath.indexOf(".html") > -1) {
//		if (filePath.indexOf(".html") > -1 && (filePath.indexOf("zwmxb35.html") > -1)) {
			//if (filePath.indexOf("html") > -1) {
			final int cj = countString(filePath, "\\");
			System.out.print("层级cj:" + cj + "--");
			// 如果层级大于初始层级+指定层级才做转换
			if (cj > com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.CSCJ + com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.ZDCJ) {
				System.out.println(filePath);
				final StringBuilder sb = new StringBuilder();
				while ((line = bufferedReader.readLine()) != null) {
					if (line.contains("/themes") && !line.contains("/themes/default")) {
						line = line.replace("/themes", "/themes/default");
					}
					if (line.contains("ecp.starter.js")) {
						line = line.replace("/grm/ecp/webcore/scripts/ecp.starter.js", com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.cjDyPath.get(cj) + "ecp/webcore/scripts/ecp.starter.js?mapp.name=" + com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.WFWNAME);
					}
					// 已经存在../的个数
					final int existCjpathNum = countString(line, "../");
					// 已经存在的../字符串
					final StringBuilder existCjpathStr = new StringBuilder();
					if (existCjpathNum > 0 && line.indexOf("link") > -1) {
						for (int i = 0; i < existCjpathNum; i++) {
							existCjpathStr.append("../");
						}
						line = line.replace(existCjpathStr, com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.cjDyPath.get(cj));
						sb.append(line + "\n");
						continue;
					}
					if (line.contains("style.css") || line.contains("style-template.css") 
							|| line.contains("font-awesome.min.css") || line.contains("font-custom.css")
							|| line.contains("icomoon.css")) {
						line = line.replace("/grm/ecp/webcore", com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.cjDyPath.get(cj) + "assets/std");
					} else if (line.contains("style-ecp-plugins.css") || line.contains("style-ecp.css")) {
						line = line.replace("/grm/ecp/webcore", com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.cjDyPath.get(cj) + "assets/necp");
					}
					
					sb.append(line + "\n");
				}
				write(sb.toString(), file2);
			}
		}
	}
	
	/**
	 * 批量替换Java方法.
	 * @throws IOException 
	 */
	private static void subJavaMethod(final String filePath, final File file2, final BufferedReader bufferedReader) throws IOException {
		// 给所有不存在@Service的Context添加@Service
		if (filePath.indexOf(".java") > -1 && filePath.indexOf("Context") > -1) {
//		if (filePath.indexOf(".java") > -1 && filePath.indexOf("Context") > -1 && filePath.indexOf("DkllwhContext") > -1) {
			boolean flag = true;
			boolean interfaceFlag = false;
			final StringBuilder sb = new StringBuilder();
			int subIndex = 0;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.indexOf("interface") > -1) {
					interfaceFlag = true;
					break;
				}
				if (sb.indexOf("importservice") == -1 && line.startsWith("import")) {
					sb.append("importservice");
					subIndex = sb.indexOf("importservice");
				}
				final int serIndex = line.indexOf("@Service");
				if (serIndex > -1) {
					flag = false;
				}
				if (line.startsWith("public") && line.indexOf("class") > -1) {
					if (flag) {
						// 没有@Service注解但是引入了包就不重复导入了
						if (sb.indexOf("stereotype.Service") > -1) {
							sb.replace(subIndex, subIndex + 13, "");
						} else {
							sb.replace(subIndex, subIndex + 13, "import org.springframework.stereotype.Service;\n");
						}
						sb.append("@Service\n");
					} else {
						sb.replace(subIndex, subIndex + 13, "");
					}
					sb.append(line + "\n");
				} else {
					sb.append(line + "\n");
				}
			}
			if (!interfaceFlag) {
				write(sb.toString(), file2);
			}
		}
	}
	
	/**
	 * 修改文件url地址方法.
	 * @throws IOException 
	 */
	private static void subUrlpathMethod(final String filePath, final File file2, final BufferedReader bufferedReader) throws IOException {
		// 给所有不存在@Service的Context添加@Service
		if (filePath.indexOf(".js") > -1) {
//		if (filePath.indexOf(".js") > -1 && filePath.indexOf("batchSetEditor") > -1) {
			final boolean flag = true;
			boolean jjUtilFlag = false;
			boolean needReplace = false;
			String jjUtil = "";
			final StringBuilder sb = new StringBuilder();
			final int subIndex = 0;
			while ((line = bufferedReader.readLine()) != null) {
//				if (line.indexOf("jjgk.common.utils") > -1) {//jjgk.common.utils
				if (line.indexOf("jjgk.gccw.utils") > -1) {//jjgk.gccw.utils
					jjUtilFlag = true;
				}
				if ("".equals(jjUtil)) {
					if (line.indexOf("jjgkUtil") > -1) {
						jjUtil = "jjgkUtil";
					}
					if (line.indexOf("jjUtil") > -1) {
						jjUtil = "jjUtil";
					}
					if (line.indexOf("cstrUtil") > -1) {
						jjUtil = "cstrUtil";
					}
					if (line.indexOf("jjUtils") > -1) {
						jjUtil = "jjUtils";
					}
				}
				if (line.indexOf("/grm/construction") > -1 && line.indexOf("//") == -1 && line.indexOf(".html") > -1 && !"".equals(jjUtil)) {
					needReplace = true;
					final int grm = line.indexOf("/grm");
					int realPath = line.indexOf("/", grm);
					realPath = line.indexOf("/", realPath + 1);
					realPath = line.indexOf("/", realPath + 1);
					realPath = line.indexOf("/", realPath + 1);
					sb.append(line.substring(0, grm - 1));
					sb.append(jjUtil + ".getWebAppRoot() + ");
					if (line.indexOf("'") > -1) {
						sb.append("'");
					} else {
						sb.append("\"");
					}
					if (line.indexOf("construction/framework") > -1 || line.indexOf("construction/basic") > -1) {
						sb.append("/assets/components/constructframeworkweb");
						System.out.println("\nframework的文件:" + file2.getName());
					}
					sb.append(line.substring(realPath, line.length()) + "\n");
				} else {
					sb.append(line + "\n");
				}
			}
			if (jjUtilFlag && needReplace) {
				write(sb.toString(), file2);
				needReplace = false;
			}
		}
	}
	
	/**
	 * 修改所有单引号错误.
	 * @throws IOException 
	 */
	private static void subAtoBMethod(final String filePath, final File file2, final BufferedReader bufferedReader) throws IOException {
		// 给所有不存在@Service的Context添加@Service
		if (filePath.indexOf(".js") > -1) {
//		if (filePath.indexOf(".js") > -1 && filePath.indexOf("GybmEditor") > -1) {
			final boolean flag = true;
			boolean needReplace = false;
			final String jjUtil = "";
			final StringBuilder sb = new StringBuilder();
			final int subIndex = 0;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.indexOf("getWebAppRoot") > -1 && line.indexOf("'") > -1 && line.indexOf("\"") > -1) {
					needReplace = true;
					sb.append(line.replaceAll("'", "\"")).append("\n");
				} else {
					sb.append(line + "\n");
				}
			}
			if (needReplace) {
				write(sb.toString(), file2);
				needReplace = false;
			}
		}
	}
	
	/**
	 * 修改所有单引号错误.
	 * @throws IOException 
	 */
	private static void subStartPathMethod(final String filePath, final File file2, final BufferedReader bufferedReader) throws IOException {
		// 给所有不存在@Service的Context添加@Service
//		if (filePath.indexOf(".js") > -1) {
		if (filePath.indexOf(".opx") > -1) {
			System.out.println(filePath);
		}
//		if (filePath.indexOf(".html") > -1 && filePath.indexOf("showYwdjUI3") > -1) {
//			boolean flag = true;
//			boolean needReplace = false;
//			String jjUtil = "";
//			final StringBuilder sb = new StringBuilder();
//			int subIndex = 0;
//			while ((line = bufferedReader.readLine()) != null) {
//				if (line.indexOf("../ecp/webcore/scripts/ecp.starter.js") > -1) {
//					needReplace = true;
//					sb.append(line.substring(0, line.indexOf("src"))).append("src=\"/grm/ecp/webcore/scripts/ecp.starter.js?")
//						.append(line.substring(line.indexOf("mapp"), line.length()));
//				} else {
//					sb.append(line + "\n");
//				}
//			}
//			if (needReplace) {
//				write(sb.toString(), file2);
//				needReplace = false;
//			}
//		}
	}
	
	/**
	 * 修改mdmx文件中html路径.
	 * @throws IOException 
	 */
	private static void subMdmxHtml(final String filePath, final File file2, final BufferedReader bufferedReader) throws IOException {
		// 给所有不存在@Service的Context添加@Service
		if (filePath.indexOf(".mdmx") > -1) {
//		if (filePath.indexOf(".mdmx") > -1 && filePath.indexOf("gris.ywdj_gccw.cbxwzckd") > -1) {
			boolean start = false;
			boolean needWrite = false;
			final StringBuilder sb = new StringBuilder();
			while ((line = bufferedReader.readLine()) != null) {
				if (line.indexOf("\"formats\"") > -1) {
					start = true;
				} else if(start && line.indexOf("]") > -1) {
					start = false;
				}
				if (start && line.indexOf("source") > -1 && line.indexOf("constructfinanceweb") == -1) {
					needWrite = true;
					sb.append(line.substring(0, line.indexOf("/"))).append("/gris/mapp/constructfinanceweb").append(line.substring(getIndexByTime(line, "/", 3), line.length()));
				}else {
					sb.append(line);
				}
				sb.append("\n");
			}
			if (needWrite) {
				write(sb.toString(), file2);
			}
		}
	}
	
	/**
	 * 修改单据html文件中各种路径.
	 * @throws IOException 
	 */
	private static void subYwdjHtml(final String filePath, final File file2, final BufferedReader bufferedReader) throws IOException {
		// 给所有不存在@Service的Context添加@Service
		if (filePath.indexOf(".html") > -1) {
//		if (filePath.indexOf(".html") > -1 && filePath.indexOf("ywdj_rkhzspdUI3") > -1) {
			final int cj = countString(filePath, "\\");
			// 是否UI3.0
			boolean isUiThree = false;
			// 是否注释内容
			boolean isComment = false;
			// 是否已经修改过
			boolean isModify = false;
			final boolean start = false;
			final boolean needWrite = false;
			final StringBuilder sb = new StringBuilder();
			while ((line = bufferedReader.readLine()) != null) {
				if (line.indexOf("<title>") > -1) {
					com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.title = "--" + line.substring(line.indexOf(">") + 1, line.indexOf("/") - 1);
				}
				if (line.indexOf("平台ECP3.0通用样式") > -1) {
					isUiThree = true;
				}
				if (line.indexOf("<!--") == -1) {
					isComment = true;
				}
				if (line.indexOf("-->") == -1) {
					isComment = false;
				}
				if (line.indexOf("../../../") == -1) {
					isModify = true;
				}
				if (!isComment) {
					// 说明是UI3.0
					if (isUiThree) {
//						if (line.toLowerCase().indexOf("style=\"djbody\"") > -1) {
//							isYwdj = true;
//						}
						if (line.indexOf("main.min.css") > -1 || line.indexOf("icomoon.css") > -1) {
							sb.append(line.replaceAll("/grm/ecp/webcore", com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.cjDyPath.get(cj) + "assets/necp"));
						} else if(line.indexOf("generalentity.css") > -1) {
							sb.append(line.replaceAll("/grm", com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.cjDyPath.get(cj) + "assets/components"));
						} else {
							sb.append(line);
						}
						sb.append("\n");
					} else {
//						if (line.toLowerCase().indexOf("classid=") > -1 && line.toLowerCase().indexOf("gris.ywdj") > -1) {
//							isYwdj = true;
//						}
						if(line.indexOf("com.ygsoft.ecp.skin.ui_bill_3.css") > -1) {
							sb.append(line.replaceAll("/grm", com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.cjDyPath.get(cj) + "assets/components"));
						} else if(line.indexOf("com.ygsoft.ecp.initiator.js") > -1) {
							sb.append(line.replaceAll("/grm/ecp", com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.cjDyPath.get(cj) + "assets/necp"));
						} else if(line.indexOf("/grm/construction") > -1) {
							if (line.indexOf("/grm/construction/framework") > -1) {
								sb.append(line.replaceAll("/grm/construction/framework", com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.cjDyPath.get(cj) + "assets/components/constructframeworkweb"));
							} else {
								sb.append(line.substring(0, line.indexOf("/grm"))).append("/gris/mapp/constructfinanceweb").append(line.substring(getIndexByTime(line, "/", 4)));
							}
						} else {
							sb.append(line);
						}
						sb.append("\n");
					}
				} else {
					sb.append(line + "\n");
				}
			}
			write(sb.toString(), file2);
		}
	}

	/**
	 * 准备修改js文件中的引用其他文件的路径.
	 * @throws IOException 
	 */
	private static void subJsPath(final String filePath, final File file2, final BufferedReader bufferedReader) throws IOException {
		if (filePath.indexOf(".js") > -1 && com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.WRITEFILECOUNTS < 2000) {
//		if (filePath.indexOf(".js") > -1 && filePath.indexOf("com.ygsoft.gris.construction.framework.baseGldxListPage.Controller") > -1) {
			boolean start = false;
			boolean hasCommonUtil = false;
			final String commonUtil = "$.ecp.construction.commonUtil.getWebAppRoot";
			final String util = "EcpVersion.webCtxPath";
			final StringBuilder sb = new StringBuilder();
			while ((line = bufferedReader.readLine()) != null) {
				if (line.contains("/*")) {
					com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.isComments = true;
				}
				if (com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.isComments) {
					sb.append(line);
				} else {
					if(line.indexOf("jsImport") > -1 && !line.contains("//")) {
						start = true;
						sb.append("$.ecp.jsImport([");
						line = line.replace("$.ecp.jsImport([", "");
					}
					if (line.contains("com.ygsoft.gris.construction.commonUtil.js")) {
						hasCommonUtil = true;
					}
					if (start) {
						line = line.trim();
						if (line.contains(util) || !line.contains("/")) {
							sb.append(line);
						} else {
							if(line.indexOf("/grm/construction") > -1) {
								if (line.indexOf("/grm/construction/framework") > -1) {
									modify(sb, util + " + " + line.replaceAll("/grm/construction/framework", "assets/components/constructframeworkweb"));
								} else {
									modify(sb, util + " + " + line.substring(0, line.indexOf("/grm"))).append("gris/mapp/constructfinanceweb").append(line.substring(getIndexByTime(line, "/", 4)));
								}
							} else if(line.indexOf("gris.ui.datafield.KmSelector.js") > -1) {
								modify(sb, util + " + \"assets/components/constructframeworkweb/util/com.ygsoft.gris.construction.hskmUtil.js\"");
								if (line.contains(",")) {
									sb.append(",");
								}
								sb.append(" // 新核算科目控件");
							} else if(line.indexOf("/grm/business/framework") > -1) {
								modify(sb, util + " + " + line.replaceAll("/grm/business/framework", "assets/components/businessframeworkweb"));
							} else {
								modify(sb, util + " + " + line.replace("/grm/", ""));
							}
						}
					} else if(hasCommonUtil && line.contains(".html") && line.contains("/") && !line.contains("//") && line.contains("\"/grm")
							&& !line.contains("getWebAppRoot")) {
						if (line.contains("'")) {
							line = line.replaceAll("'", "\"");
						}
						if (line.contains("/framework")) {
							modify(sb, line.substring(0, line.indexOf("\"/grm"))).append(commonUtil + "() + ").append("\"/gris/mapp/constructfinanceweb").append(line.substring(getIndexByTime(line, "/", 4)));
						} else {
							modify(sb, line.substring(0, line.indexOf("\"/grm"))).append(commonUtil + "(\"yw\") + ").append("\"/gris/mapp/constructfinanceweb").append(line.substring(getIndexByTime(line, "/", 4)));
						}
					} else {
						sb.append(line);
					}
					if (start && line.indexOf("]") > -1) {
						start = false;
					}
				}
				sb.append("\n");
				if (line.contains("*/")) {
					com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.isComments = false;
				}
			}
			if (com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.isModify) {
				write(sb.toString(), file2);
			}
		}
	}
	
	/**
	 * 准备修改actx文件中的html路径.
	 * @throws IOException 
	 */
	private static void subActxPath(final String filePath, final File file2, final BufferedReader bufferedReader) throws IOException {
		if (filePath.indexOf(".actx") > -1) {
//		if (filePath.indexOf(".actx") > -1 && filePath.indexOf("gris.ywdj_gccw.costreb_approve_gzjfpcjejyCostrebBeforeapprove") > -1) {
			final boolean start = false;
			final StringBuilder sb = new StringBuilder();
			while ((line = bufferedReader.readLine()) != null) {
				if(line.indexOf("/grm/construction") > -1) {
					if (line.indexOf("/grm/construction/framework") > -1) {
						sb.append(line);
					} else {
						sb.append(line.substring(0, line.indexOf("/grm"))).append("/gris/mapp/constructfinanceweb").append(line.substring(getIndexByTime(line, "/", 4)));
					}
				} else if(line.indexOf("/grm/business/framework") > -1) {
					sb.append(line);
				} else {
					sb.append(line);
				}
				sb.append("\n");
			}
			write(sb.toString(), file2);
		}
	}
	
	/**
	 * 根据文件内容查找文件.
	 * @param filePath
	 * @param file2
	 * @param bufferedReader
	 * @throws IOException
	 */
	private static void findFile(final String filePath, final File file2, final BufferedReader bufferedReader) throws IOException {
		if (filePath.contains(".sql") || filePath.contains(".txt")) {
			boolean start = false;
			final StringBuilder sb = new StringBuilder();
			while ((line = bufferedReader.readLine()) != null) {
				final String containStr = "loop";
				if(line.toLowerCase().indexOf(containStr) > -1) {
					com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.RIGHTCOUNTS++;
					start = true;
					break;
				}
			}
			if (start) {
				System.out.println("---------------------->" + filePath);
			}
		}
	}
	
	/**
	 * 查找所有文件路径
	 * @param filePath
	 * @param file2
	 * @param bufferedReader
	 * @throws IOException
	 */
	private static void findFilePath(final String filePath, final File file2, final BufferedReader bufferedReader) throws IOException {
		final int index = filePath.lastIndexOf("\\");
		System.out.println(filePath.substring(index + 1, filePath.length()));
	}
	
	/**
	 * 字符串中字母出现的次数.
	 * @param str
	 * @param s
	 */
	private static int countString(String str,final String s) {
        int count = 0;
        for(int i= 0; i<=str.length(); i++){
            if(str.indexOf(s) == i){
                str = str.substring(i+1,str.length());
                i = 0;
                count++;
            }
        }
        return count;
    }
	
	/**
	 * 一个字符在指定字符串中第N次出现的位置.
	 * @param str
	 * @param s
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
	 * 修改文件.
	 * @param cont
	 * @param dist
	 * @return
	 */
	private static void write(final String cont, final File dist) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(dist));
			com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.WRITEFILECOUNTS++;
			com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.modifyFileName.append(dist.getName() + "\n");
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
	
	/**
	 * 修改
	 */
	private static StringBuilder modify(final StringBuilder sb, final String str) {
		com.ygsoft.loopFileUtil.fileConst.SubsCssPathConst.isModify = true;
		sb.append(str);
		return sb;
	}
}
