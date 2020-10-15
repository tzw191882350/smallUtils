package com.ygsoft.smallTool.buildCodeUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 生成代码工具.
 * @author tuzewen
 *
 */
public class BuildCodesUtil {

	/*************************************************************************************************************/
	// 使用此工具需要事先创建PO,VO
	// 修改下列变量名和下面的static块
	// MAIN = "ebdkq"; // 主
	// AUXILIARY = "construct.fundsmanager"; // 辅
	// com.ygsoft.gris.ebdkq.construct.fundsmanager.impl
	private static String main; // 主
	private static String auxiliary; // 辅
	private static String prename; 
	// E:\\eclipse6.0\\src\\com\\ygsoft\\gris\\
	// E:\\gc2.0plus6.1\\src\\com\\ygsoft\\gris\\
	private static String rootpath; // 根目录
	private static String projectPath; // 中间目录
	private static final String VOPOPATH = ""; // vopo很可能会创建新的目录,如果创建了就要修改这个值,否则为空
	private static String gnenglishname; // 功能英文名
	private static String gnchinesename; // 功能中文名
	private static String anthorname; // 作者
	private static final Boolean INFOENABLE = false; // 是否需要打印地址日志
	private static final Boolean NEEDQUERY = true; // 是否需要创建查询类接口
	private static final Boolean ONLYNEEDQUERY = true; // 是否只需要创建查询类
//	private static final Boolean NEEDBASEMETHOD = true; // 是否需要保存和删除的方法
	private static final Boolean NEEDPAGER = false; // 查询是否需要分页
	
	/*************************************************************************************************************/
	private static final String N = "\n";
	private static final String BIAS = "\\";
	private static final String DOT = ".";
	private static final String JAVA = "java";
	private static String DOTPOVO = DOT + VOPOPATH;
	
	private static final String QUERY = "Query";
	private static final String CONTEXT = "Context";
	private static final String ROLE = "Role";
	private static final String DAO = "Dao";
	private static final int IMPLANDINTER = 6; //接口和实现类的个数
	
	private static String filePath = "";
	private static String fileName = "";
	/*************************************************************************************************************/
	private static String now = "";
	private static String name = "";
	private static List<StringBuilder> config = new ArrayList<StringBuilder>();
	
	// config配置各个类文件生成路径
	static {
		
		StringBuilder detailConfig = new StringBuilder();
		// detailConfig有四个参数第二个第三个参数控制地址第四个控制文件名后缀
		// 两个地址分别对应地址E:\eclipse6.0\com.ygsoft.gris.construction.contract.{②}\src\com\ygsoft\gris\construction\contract\{②}\{③}
		detailConfig.append("I,service,context,Context");
		config.add(detailConfig); // IContext
		detailConfig = new StringBuilder();
		detailConfig.append("I,service,role,Role");
		config.add(detailConfig); // IRole
		detailConfig = new StringBuilder();
		detailConfig.append("I,data,service,Dao");
		config.add(detailConfig); // IDao
		detailConfig = new StringBuilder();
		detailConfig.append(",impl,context,Context");
		config.add(detailConfig); // Context
		detailConfig = new StringBuilder();
		detailConfig.append(",impl,role,Role");
		config.add(detailConfig); // Role
		detailConfig = new StringBuilder();
		detailConfig.append(",data,internal\\impl,Dao");
		config.add(detailConfig);
		// transfer 7
		detailConfig = new StringBuilder();
		detailConfig.append(",impl,model\\" + VOPOPATH + ",Transfer");
		config.add(detailConfig); 
		// PO
		detailConfig = new StringBuilder();
		detailConfig.append(",data,model,PO");
		config.add(detailConfig);
		// VO
		detailConfig = new StringBuilder();
		detailConfig.append(",service,model,VO");
		config.add(detailConfig);
	}
	
	/**
	 * 调试方法.
	 */
	private static void debugger() {
		final StringBuilder sb = new StringBuilder();
		buildPOVOStr(sb, "VO");
		System.out.println(sb);
	}
	
	/**
	 * 自动生成context,role,dao,querycontext,queryrole,querydao
	 * @param params
	 */
	public static void begin(final Map<String, String> params) {
		final Boolean debuger = false;
		final String special = "2";
		if (debuger) {
			debugger();
			return;
		}
		// 1.自动生成应用分离转换接口和实现类
		if (special.equals("1")) {
			name = "InvoicePoolExecService";
			specialOne();
		} else {
			/**
			 * "workspacePath: E:\\eclipse6.0\n"
			 *  + "projectPath: \\src\\com\\ygsoft\\gris\\\n"
             *  + "groupId: construction\n"
             *  + "detailId: bidding\n"
             *  + "author: tuzewen\n"
             *  + "projectEnglishName: Gccbgj\n"
             *  + "projectName: 工程成本归集\n",
			 */
			main = params.get("groupId");
			rootpath = params.get("workspacePath");
			projectPath = params.get("projectPath");
			gnenglishname = params.get("projectEnglishName");
			gnchinesename = params.get("projectName");
			anthorname = params.get("author");
			prename = "com.ygsoft.gris." + main + "." + auxiliary;
			
			if (!ONLYNEEDQUERY) {
				// 创建普通类接口
				for (int i = 0; i < IMPLANDINTER; i++) {
					final String[] detConfig = config.get(i).toString().split(",");
					now = detConfig[0] + detConfig[3];
					// com.ygsoft.gris.construction.contract...
					filePath = projectPath + prename + DOT + detConfig[1] + projectPath + main + BIAS + auxiliary.replace(".", BIAS) + BIAS + detConfig[1] + BIAS + detConfig[2] + BIAS;
					fileName = detConfig[0] + gnenglishname + detConfig[3] + DOT + JAVA;
					if (INFOENABLE) {
						System.out.println(filePath + fileName);
					}
					createFile();
				}
			}
			// 创建查询类接口
			if (NEEDQUERY) {
				for (int i = 0; i < IMPLANDINTER; i++) {
					final String[] detConfig = config.get(i).toString().split(",");
					now = detConfig[0] + detConfig[3];
					filePath = rootpath + prename + DOT + detConfig[1] + projectPath + main + BIAS + auxiliary.replace(".", BIAS) + BIAS + detConfig[1] + BIAS + detConfig[2] + BIAS;
					fileName = detConfig[0] + gnenglishname + QUERY + detConfig[3] + DOT + JAVA;
					if (INFOENABLE) {
						System.out.println(filePath + fileName);
					}
					createFile();
				}
			}
			// 创建roleconst
			createOhterFile(5);
			// 创建transfer
			createOhterFile(6);
			// 创建PO
			createOhterFile(7);
			// 创建VO
			createOhterFile(8);
			
			System.out.println("使用此工具需要事先创建PO,VO");
			System.out.println("创建完文件之后资源管理器和暂挂的变更里面看不到创建的文件");
			System.out.println("需要在资源管理器选中你新建文件的项目右键选择[刷新]");
			System.out.println("设置NEEDQUERY属性为false即可不创建查询类接口" + N);
			System.out.println("设置INFOENABLE属性为true即可启用地址日志打印" + N);
// 			// E:\eclipse6.0\com.ygsoft.gris.construction.contract.service\src\com\ygsoft\gris\construction\contract\service\context
// 			filePath = rootpath + prename + DOT + SERVICE + MIDDLEPATH + MAIN + BIAS + AUXILIARY + "\\service\\context";
// 			fileName = "I" + gnenglishname + CONTEXT + DOT + JAVA;
		}
	}

	private static void specialOne() {
		final String basePath = "E:\\180704_ECP_plus6.0_ecp5.1\\com.ygsoft.gris.construction.framework";
		filePath = basePath + ".service\\src\\com\\ygsoft\\gris\\construction\\framework\\service\\context\\";
		fileName = "I" + name + ".java";
		now = "1.1";
		createFile();
		filePath = basePath + ".impl\\src\\com\\ygsoft\\gris\\construction\\framework\\impl\\context";
		fileName = name + ".java";
		createFile();
	}
	
	/**
	 * 创建文件.
	 */
	private static void createFile() {
		final File writeFile = new File(filePath, fileName);
		final File fileParent = writeFile.getParentFile();
		if(!writeFile.exists()){
			if (!fileParent.exists()) {
				fileParent.mkdirs();
			}
			try {
				writeFile.createNewFile();
				System.out.println(fileName + "创建完毕");
				writeFile();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("文件" + fileName + "已存在");
// 			writeFile.delete();
		}
	}
	
	/**
	 * 写文件
	 */
	@SuppressWarnings("resource")
	private static void writeFile() {
		try {
			final File file = new File(filePath.replaceAll("\\\\", "/") + "/" + fileName);
			if (file.exists() && file.canWrite()) {
				final FileOutputStream out = new FileOutputStream(file, true);
				final StringBuilder sb = new StringBuilder();
				String query = "";
				if (fileName.contains("Query")) {
					query = "Query";
				}
				if ("IContext".equals(now)) {
					buildContextStr(sb, query, false);
				} else if("IRole".equals(now)) {
					buildRoleStr(sb, query, false);
				} else if("IDao".equals(now)) {
					buildDaoStr(sb, query, false);
				} else if("Context".equals(now)) {
					buildContextStr(sb, query, true);
				} else if("Role".equals(now)) {
					buildRoleStr(sb, query, true);
				} else if("Dao".equals(now)) {
					buildDaoStr(sb, query, true);
				} else if("DaoConst".equals(now)) {
					buildDaoConstStr(sb, query);
				} else if("Transfer".equals(now)) {
					buildTransferStr(sb);
				} else if("POVO".contains(now)) {
					buildPOVOStr(sb, now);
				} else if ("1.1".equals(now)) {
					buildSpecialOne(sb);
				}
				out.write(sb.toString().getBytes("utf-8"));
				out .flush();
			} else {
				System.out.println(fileName + " can not be wrote");
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}  
	}
	
	/**
	 * 创建POVO.
	 */
	private static void createOhterFile(final int path) {
		final String[] detConfig = config.get(path).toString().split(",");
		now = detConfig[0] + detConfig[3];
		filePath = rootpath + prename + DOT + detConfig[1] + projectPath + main + BIAS + auxiliary.replace(".", BIAS) + BIAS + detConfig[1] + BIAS + detConfig[2] + BIAS + VOPOPATH;
		fileName = detConfig[0] + gnenglishname + detConfig[3] + ".java";
		createFile();
	}
	
	/**
	 * 构建Context
	 * @param sb 内容
	 * @param query 是否查询
	 * @param isImpl 是否实现类
	 */
	private static void buildContextStr(final StringBuilder sb, final String query,
			final Boolean isImpl) {
		buildCompanyRight(sb);
		if (!isImpl) {
			sb.append("package " + prename + ".service.context;" + N);
			sb.append(N);
			sb.append("import com.ygsoft.ecp.core.framework.domain.IGeneral" + query + "Context;" + N);
		} else {
			sb.append("package " + prename + ".impl.context;" + N);
			sb.append(N);
			sb.append("import org.springframework.stereotype.Service;" + N);
			sb.append("import org.springframework.beans.factory.annotation.Autowired;" + N);
			sb.append("import com.ygsoft.ecp.core.framework.annotations.OSGiService;" + N);
			sb.append("import com.ygsoft.ecp.core.framework.domain.General" + query + "Context;" + N);
			sb.append("import " + prename + ".service.context.I" + gnenglishname + query + "Context;" + N);
			sb.append("import " + prename + ".service.role.I" + gnenglishname + query + "Role;" + N);
		}
		sb.append(N);
		
		/**
		 *  import com.ygsoft.ecp.core.framework.annotations.ContextProvider;
		 *	import com.ygsoft.ecp.core.framework.annotations.ContextProviderDesc;
		 *	import com.ygsoft.ecp.core.framework.domain.IGeneralContext;
		 *	import com.ygsoft.gris.construction.bidding.service.model.QcsjlrVO;
		 */
		sb.append("import com.ygsoft.ecp.core.framework.annotations.ContextProvider;" + N);
		sb.append("import com.ygsoft.ecp.core.framework.annotations.ContextProviderDesc;" + N);
		sb.append("import " + prename + ".service.model" + DOTPOVO + DOT + gnenglishname + "VO;" + N);
		buildCommand(sb);
		if (isImpl) {
			sb.append("@Service" + N);
			sb.append("@OSGiService" + N);
		}
		sb.append("@ContextProvider" + N);
		sb.append("@ContextProviderDesc(caption = \"" + gnenglishname + query + CONTEXT +  "\")" + N);
		if (isImpl) {
			sb.append("public class " + gnenglishname + query + CONTEXT + " extends General" + query + "Context<" + gnenglishname + "VO, I" + gnenglishname + query + "Role> implements I" + gnenglishname + query + "Context{");
		} else {
			sb.append("public interface I" + gnenglishname + query + CONTEXT + " extends IGeneral" + query + "Context<" + gnenglishname + "VO> {");
		}
		if (isImpl) {
			// 引入Role
			buildInjectStr(sb, query, ROLE);
			if ("Query".equals(query)) {
//				buildImplQueryConRolBaseMethod(sb, query, CONTEXT);
			} else {
				
			}
		} else {
			if ("Query".equals(query)) {
//				buildInterQueryConRolBaseMethod(sb, query, CONTEXT);
			} else {
				
			}
		}
		sb.append(N);
		sb.append("}" + N);
	}
	
	/**
	 * 构建Role
	 * @param sb 内容
	 * @param query 是否查询
	 * @param isImpl 是否实现类
	 */
	private static void buildRoleStr(final StringBuilder sb, final String query,
			final Boolean isImpl) {
		buildCompanyRight(sb);
		if (!isImpl) {
			sb.append("package " + prename + ".service.role;" + N);
			sb.append(N);
			sb.append("import com.ygsoft.ecp.core.framework.domain.IGeneral" + query + "Role;" + N);
		} else {
			sb.append("package " + prename + ".impl.role;" + N);
			sb.append(N);
			sb.append("import org.springframework.osgi.extensions.annotation.ServiceReference;" + N);
			sb.append("import org.springframework.stereotype.Service;" + N);
			sb.append("import " + prename + ".data.model" + DOTPOVO + DOT + gnenglishname + "PO;" + N);
			sb.append("import " + prename + ".data.service.I" + gnenglishname + query + "Dao;" + N);
			sb.append("import " + prename + ".impl.model" + DOTPOVO + DOT + gnenglishname + "Transfer;" + N);
			sb.append("import " + prename + ".service.role.I" + gnenglishname + query + "Role;" + N);
			sb.append("import com.ygsoft.ecp.core.framework.domain.General" + query + "Role;" + N);
		}
		sb.append(N);
		
		if (NEEDPAGER) {
			sb.append("import com.ygsoft.ecp.ef.service.model.bo.Pager;" + N);
		}
		sb.append("import com.ygsoft.ecp.core.framework.context.IEcpDataContext;" + N);	
		
		/**
		 *  import com.ygsoft.ecp.core.framework.annotations.Role;
		 *	import com.ygsoft.ecp.core.framework.annotations.RoleDesc;
		 *	import com.ygsoft.gris.construction.bidding.service.model.QcsjlrVO;
		 */
		sb.append("import com.ygsoft.ecp.core.framework.annotations.Role;" + N);
		sb.append("import com.ygsoft.ecp.core.framework.annotations.RoleDesc;" + N);
		sb.append("import " + prename + ".service.model" + DOTPOVO + DOT + gnenglishname + "VO;" + N);
		buildCommand(sb);
		if (isImpl) {
			sb.append("@Service" + N);
		}
		sb.append("@Role" + N);
		sb.append("@RoleDesc(caption = \"" + gnenglishname + query + ROLE +  "\")" + N);
		if (!isImpl) {
			sb.append("public interface I" + gnenglishname + query + ROLE + " extends IGeneral" + query + "Role<" + gnenglishname + "VO> {");
		} else {
			final String expand = gnenglishname + "VO, " + gnenglishname + "PO, I" + gnenglishname + query + "Dao, " + VOPOPATH + gnenglishname + "Transfer";
			sb.append("public class " + gnenglishname + query + "Role extends General" + query + "Role<" + expand + "> implements I" + gnenglishname + query + "Role {");
		}
		if (isImpl) {
			// 引入Dao
			buildInjectStr(sb, query, DAO);
			if ("Query".equals(query)) {
//				buildImplQueryConRolBaseMethod(sb, query, ROLE);
			} else {
				// TODO
			}
		} else {
			if ("Query".equals(query)) {
//				buildInterQueryConRolBaseMethod(sb, query, ROLE);
			} else {
				// TODO
			}
		}
		
		sb.append(N);
		sb.append("}" + N);
	}

	/**
	 * 依赖注入.
	 * @param sb 字符串
	 * @param query 是否查询
	 * @param contextOrDao context还是dao
	 */
	private static void buildInjectStr(final StringBuilder sb, final String query, final String contextOrDao) {
		sb.append(N);
		/**
		 * /**
		 *  *IQcsjlrtDao.
		 *  *\/
		 * private IQcsjlrtDao qcsjlrtDao;
		 * 
		 * /**
		 *  * 设置qcsjlrtDao.
		 *  * @param newQcsjlrtDao the qcsjlrtDao to set
		 *  *\/
		 *  public void setQcsjlrtDao(final IQcsjlrtDao newQcsjlrtDao) {
		 *	   qcsjlrtDao = newQcsjlrtDao;
		 *  }
		 */
		// 注入
		buildCommand(sb);
		sb.append("    private I" + gnenglishname + query + contextOrDao + " " + firstToLowerCase(gnenglishname) + query + contextOrDao + ";" + N);
		sb.append(N);
		sb.append("    /**" + N);
		sb.append("     * 设置I" + gnenglishname + contextOrDao + "." + N);
		sb.append("     * @param new" + gnenglishname + contextOrDao + " the " + firstToLowerCase(gnenglishname) + query + contextOrDao + " to set" + N);
		sb.append("     */" + N);
		if (ROLE.equals(contextOrDao)) {
			sb.append("    @Autowired" + N);
		} else {
			sb.append("    @ServiceReference" + N);
		}
		sb.append("    public void set" + gnenglishname + query + contextOrDao + "(final I" + gnenglishname + query + contextOrDao + " new" + gnenglishname + query+ contextOrDao + ") {" + N);
		sb.append("        " + firstToLowerCase(gnenglishname) + query + contextOrDao + " = new" + gnenglishname + query + contextOrDao + ";" + N);
		sb.append("    }" + N);
	}
	
	/**
	 * 构建Dao.
	 * @param sb 内容
	 * @param query 是否查询
	 * @param isImpl 是否实现类
	 */
	private static void buildDaoStr(final StringBuilder sb, final String query,
			final Boolean isImpl) {
		buildCompanyRight(sb);
		if (!isImpl) {
			sb.append("package " + prename + ".data.service;" + N);
			sb.append(N);
			if ("Query".equals(query)) {
				sb.append("import com.ygsoft.ecp.core.framework.domain.IGeneralQueryDao;" + N);
			} else {
				sb.append("import com.ygsoft.ecp.service.dataaccess.IEntityDAO;" + N);
			}
		} else {
			sb.append("package " + prename + ".data.internal.impl;" + N);
			sb.append(N);
			if ("Query".equals(query)) {
				sb.append("import com.ygsoft.ecp.core.framework.domain.GeneralQueryDao;" + N);
			} else {
				sb.append("import com.ygsoft.ecp.service.dataaccess.EntityDAO;" + N);
			}
			sb.append("import com.ygsoft.ecp.service.log.EcpLogFactory;" + N);
			sb.append("import com.ygsoft.ecp.service.log.IEcpLog;" + N);
			sb.append("import com.ygsoft.ecp.core.framework.annotations.OSGiService;" + N);
			sb.append("import org.springframework.stereotype.Repository;" + N);
			sb.append("import " + prename + ".data.service.I" + gnenglishname + query + "Dao;" + N);
		}
		sb.append(N);
		if (NEEDPAGER) {
			sb.append("import com.ygsoft.ecp.ef.service.model.bo.Pager;" + N);
		}
		sb.append("import com.ygsoft.ecp.core.framework.context.IEcpDataContext;" + N);	
		/**
		 *  import com.ygsoft.ecp.core.framework.annotations.Dao;
		 *	import com.ygsoft.ecp.core.framework.annotations.DaoDesc;
		 *	import com.ygsoft.gris.construction.bidding.service.model.QcsjlrVO;
		 */
		sb.append("import com.ygsoft.ecp.core.framework.annotations.Dao;" + N);
		sb.append("import com.ygsoft.ecp.core.framework.annotations.DaoDesc;" + N);
		sb.append("import " + prename + ".data.model" + DOTPOVO + DOT + gnenglishname + "PO;" + N);
		buildCommand(sb);
		if (isImpl) {
			sb.append("@Repository" + N);
			sb.append("@OSGiService" + N);
		}
		sb.append("@Dao" + N);
		sb.append("@DaoDesc(caption = \"" + gnenglishname + query + DAO +  "\")" + N);
		if (!isImpl) {
			if ("Query".equals(query)) {
				sb.append("public interface I" + gnenglishname + query + DAO + " extends IGeneralQueryDao<" + gnenglishname + "PO> {" + N);
			} else {
				sb.append("public interface I" + gnenglishname + query + DAO + " extends IEntityDAO<" + gnenglishname + "PO> {" + N);
			}
		} else {
			final String expand = gnenglishname + "PO";
			if ("Query".equals(query)) {
				sb.append("public class " + gnenglishname + query + "Dao extends GeneralQueryDao<" + expand + "> implements I" + gnenglishname + query + "Dao {" + N);
			} else {
				sb.append("public class " + gnenglishname + query + "Dao extends EntityDAO<" + expand + "> implements I" + gnenglishname + query + "Dao {" + N);
			}
		}
		// 添加dao必要的方法
		if (isImpl) {
			/**
			 * /**
			 *  *Log object.
			 *  *\/
			 * private static final IEcpLog LOG = EcpLogFactory.getLog(DepartAndHskmDao.class);
			 */
			buildComment(sb, "Log object");
			sb.append("    private static final IEcpLog LOG = EcpLogFactory.getLog(" + gnenglishname + query + "Dao.class);");
			sb.append(N);
			if ("Query".equals(query)) {
//				buildImplQueryConRolBaseMethod(sb, query, CONTEXT);
			} else {
				
			}
			/**
			 * /**
			 *  *
			 *  * {@inheritDoc}
			 *  *\/
			 * protected String getEntityName() {
			 *     // TODO Auto-generated method stub
			 *	   return null;
		     * }
			 */
			buildComment(sb, "{@inheritDoc}");
			sb.append("    protected String getEntityName() {" + N);
			sb.append("        // TODO Auto-generated method stub" + N);
			sb.append("        return null;" + N);
			sb.append("    }" + N);
		} else {
			
		}
		sb.append(N);
		sb.append("}" + N);
	}
	
	/**
	 * 构建RoleConst
	 * @param sb 内容
	 * @param query 查询
	 */
	private static void buildDaoConstStr(final StringBuilder sb, final String query) {
		buildCompanyRight(sb);
		sb.append("package " + prename + ".data.internal.impl;" + N);
		sb.append(N);
		
		buildCommand(sb);
		final String className = gnenglishname + "DaoConst";
		sb.append("public final class " + className + " {" + N);
		sb.append(N);
		buildComment(sb, "私有构造器");
		sb.append("    private " + className + "() {" + N);
		sb.append(N);
		sb.append("    }");
		sb.append(N);
		sb.append("}");
	}
	
	/**
	 * 构建RoleConst
	 * @param sb 内容
	 */
	private static void buildTransferStr(final StringBuilder sb) {
		buildCompanyRight(sb);
		sb.append("package " + prename + ".impl.model" + DOTPOVO + ";" + N);
		sb.append(N);
		sb.append("import java.util.ArrayList;" + N);
		sb.append("import java.util.List;" + N);
		sb.append(N);
		sb.append("import com.ygsoft.ecp.core.framework.annotations.Transfer;" + N);
		sb.append("import com.ygsoft.ecp.core.framework.model.AbstractValueObjectTransfer;" + N);
		sb.append("import " + prename + ".service.model" + DOTPOVO + DOT + gnenglishname + "VO;" + N);
		sb.append("import " + prename + ".data.model" + DOTPOVO + DOT + gnenglishname + "PO;" + N);
		sb.append(N);
		
		buildCommand(sb);
		final String voName = gnenglishname + "VO";
		final String poName = gnenglishname + "PO";
		// @Transfer(poClass = SendPersonPO.class, voClass = SendPersonVO.class)
		sb.append("@Transfer(poClass = " + poName + ".class, voClass = " + voName + ".class)" + N);
		final String expand = voName + ", " + poName;
		// public class SendPersonTransfer extends AbstractValueObjectTransfer<SendPersonVO, SendPersonPO> {
		sb.append("public class " + gnenglishname + now + " extends AbstractValueObjectTransfer<" + expand + "> {");
		sb.append(N);
		buildTransferMethod(sb, poName, voName);
		buildTransferMethod(sb, voName, poName);
		sb.append(N);
		sb.append("}" + N);
	}
	
	/**
	 * 构建RoleConst
	 * @param sb 内容
	 */
	private static void buildPOVOStr(final StringBuilder sb, final String povo) {
		buildCompanyRight(sb);
		if ("PO".equals(povo)) {
			sb.append("package " + prename + ".data.model" + DOTPOVO + ";" + N);
		} else {
			sb.append("package " + prename + ".service.model" + DOTPOVO + ";" + N);
		}
		sb.append(N);
		sb.append("import com.ygsoft.ecp.core.framework.annotations.Topic;" + N);
		sb.append("import com.ygsoft.ecp.service.entity.AbstractEntity;" + N);
		sb.append(N);
		
		buildCommand(sb);
		// @Topic(classId = "ecp.tableentity", typeId = "DkllwhPO")
		sb.append("@Topic(classId = \"ecp.tableentity\", typeId = \"" + gnenglishname + povo + "\")" + N);
		// public class SendPersonTransfer extends AbstractValueObjectTransfer<SendPersonVO, SendPersonPO> {
		sb.append("public class " + gnenglishname + povo + now + " extends AbstractEntity {");
		sb.append(N);
		sb.append("}" + N);
	}
	
	/**
	 * 构建RoleConst
	 * @param sb 内容
	 */
	private static void buildSpecialOne(final StringBuilder sb) {
		if (filePath.contains("impl")) {
			buildCompanyRight(sb);
			sb.append("package com.ygsoft.gris.construction.framework.impl.context;" + N);
			sb.append(N);
			sb.append("import com.ygsoft.gris.business.framework.impl.utils.CastUtil;" + N);
			sb.append("import java.util.List;" + N);
			sb.append("import com.ygsoft.gris.construction.framework.service.context.I" + name + ";" + N);
			sb.append("import com.ygsoft.ecp.service.tool.JSONUtil;" + N);
			sb.append("import com.ygsoft.ecp.service.tool.StringUtil;" + N);
			sb.append("import com.ygsoft.gris.construction.framework.impl.utils.CstrMappUtils;" + N);
			sb.append("import com.ygsoft.gris.construction.framework.impl.utils.CstrMappUtilsConst;" + N);
			sb.append(N);
			/**
			 * 
			 * 兼容应用分离接口:物资.
			 * @author tuzewen <br>
			 * @version 1.0.0 2019-12-9<br>
			 * @see 
			 * @since JDK 1.5.0
			 */
			buildSpecialCommand(sb);
			sb.append("public class " + name + " implements I" + name + " {" + N);
			sb.append("    " + N);
			sb.append("}" + N);
		} else {
			buildCompanyRight(sb);
			sb.append("package com.ygsoft.gris.construction.framework.service.context;" + N);
			sb.append(N);
			sb.append("import java.util.List;" + N);
			/**
			 * 兼容应用分离接口:物资IMaterialOutService.
			 * @author tuzewen <br>
			 * @version 1.0.0 2019-12-9<br>
			 * @see 
			 * @since JDK 1.5.0
			 */
			buildSpecialCommand(sb);
			sb.append("public interface I" + name + " {" + N);
			sb.append("    " + N);
			sb.append("}" + N);
		}
	}
	
	/**
	 * 拼接类注释.
	 * @param sb 字符串
	 */
	private static void buildSpecialCommand(final StringBuilder sb) {
		/**
		 * 期初数据录入Context.
		 * @author tuzewen
		 * @version 1.0.0 2019-7-25
		 * @since JDK 1.5.0
		 */
		final Date date = new Date();
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sb.append(N);
		sb.append("/**" + N);
		sb.append(" * 兼容应用分离接口:" + name + "." + N);
		sb.append(" * @author " + anthorname + N);
		sb.append(" * @version 1.0.0 " + sdf.format(date) + N);
		sb.append(" * @since JDK 1.5.0" + N);
		sb.append(" */" + N);
	}
	
	/**
	 * 构建tovo,topo方法.
	 */
	private static void buildTransferMethod(final StringBuilder sb, final String paramName,
			final String returnName) {
		String source = "PO";
		String target = "VO";
		if (paramName.toUpperCase().contains("VO")) {
			target = "PO";
			source = "VO";
		}
		/**
		 * PO实体转VO.<br>
		 * @param po <br>
		 * @return vo <br>
		 */
		sb.append("    /**" + N);
		sb.append("     * " + target + "实体转" + source + "." + N);
		sb.append("     * @param " + source.toLowerCase() + " ." + N);
		sb.append("     * @return " + target.toLowerCase() + " ." + N);
		sb.append("     */" + N);
		/**
		 * public SendPersonVO toVO(final SendPersonPO po) {
				if (po == null) {
					return null;
				}
				final SendPersonVO vo = new SendPersonVO();
				return vo;
			}
		 */
		sb.append("    public " + returnName + " to" + target + "(final " + paramName + " " + source + ") {" + N);
		sb.append("        if (" + source + " == null) {" + N);
		sb.append("            return null;" + N);
		sb.append("        }" + N);
		sb.append("        final " + returnName + " " + target + " = new " + returnName + "();" + N);
		sb.append("        return " + target + ";" + N);
		sb.append("    }" + N);
		
		/**
		 * PO集合转VO集合.
		 * @param poList .
		 * @return voList .
		 */
		sb.append("    /**" + N);
		sb.append("     * " + target + "集合转" + source + "集合." + N);
		sb.append("     * @param " + source.toLowerCase() + "List ." + N);
		sb.append("     * @return " + target.toLowerCase() + "List ." + N);
		sb.append("     */" + N);
		/**
		 * 
			public List<SendPersonVO> toVO(final List<SendPersonPO> poList) {
				if (poList == null) {
					return null;
				}
				final List<SendPersonVO> result = new ArrayList<SendPersonVO>();
				for (final SendPersonPO item : poList) {
					result.add(toVO(item));
				}
				return result;
			}
		 */
		sb.append("    public List<" + returnName + "> to" + target + "(final List<" + paramName + "> " + source.toLowerCase() + "List) {" + N);
		sb.append("        if (" + source.toLowerCase() + "List == null) {" + N);
		sb.append("            return null;" + N);
		sb.append("        }" + N);
		sb.append("        final List<" + returnName + "> result = new ArrayList<" + returnName + ">();" + N);
		sb.append("        for (final " + paramName + " item : " + source.toLowerCase() + "List) {" + N);
		sb.append("            result.add(to" + target + "(item));" + N);
		sb.append("        }" + N);
		sb.append("        return result;" + N);
		sb.append("    }" + N);
		
	}
	
	/**
	 * 通用导入方法.
	 */
	private static void everyoneImport(final StringBuilder sb, final String query, final String contextOrDao) {
		
	}
	
	/**
	 * 构建queryContext,queryRole,queryDao接口的基础方法.
	 * @param sb 字符串
	 * @param query 是否查询
	 * @param contextOrDao context还是dao
	 */
	private static void buildInterQueryConRolBaseMethod(final StringBuilder sb, final String query, final String contextOrDao) {
		sb.append(N);
		if (contextOrDao.contains("Dao")) {
			buildInterQueryDaoMethodCode(sb);
		} else {
			buildInterQueryConRolMethodCode(sb);
		}
		sb.append(N);
	}

	/**
	 * 构建queryContext,queryRole接口的基础方法的代码拼接.
	 * @param sb str
	 */
	private static void buildInterQueryConRolMethodCode(final StringBuilder sb) {
		/**
		 * /**
		 * 查询数据.
		 * @param context 上下文
		 * @param queryCondition 参数
		 * @return 查询结果
		 * \/
		 * Map<String, Object> queryDatas(final IEcpDataContext context,
		 *		final Map<String, Object> queryCondition);
		 */
		sb.append("    /**" + N);
		sb.append("     * 查询数据" + N);
		sb.append("     * @param context 上下文" + N);
		sb.append("     * @param queryCondition 参数" + N);
		if (NEEDPAGER) {
			sb.append("     * @param pager 分页信息" + N);
		}
		sb.append("     * @return 查询结果 " + N);
		sb.append("     */" + N);
		sb.append("    Map<String, Object> queryDatas(final IEcpDataContext context," + N);
		sb.append("        final Map<String, Object> queryCondition");
		if (NEEDPAGER) {
			sb.append(",final Pager pager");
		}
		sb.append(");" + N);
	}
	
	/**
	 * 构建queryDao接口的基础方法的代码拼接.
	 * @param sb str
	 */
	private static void buildInterQueryDaoMethodCode(final StringBuilder sb) {
		/**
		 * /**
		 * 查询数据.
		 * @param querySql sql
		 * @param queryParams 参数
		 * @return 查询结果
		 * List<SendPersonPO> queryDatas(String querySql, Map<String, Object> queryParams);
		 * \/
		 */
		sb.append("    /**" + N);
		sb.append("     * 查询数据." + N);
		sb.append("     * @param querySql sql" + N);
		sb.append("     * @param queryParams 查询参数" + N);
		if (NEEDPAGER) {
			sb.append("     * @param pager 分页信息" + N);
		}
		sb.append("     * @return 查询结果 " + N);
		sb.append("     */" + N);
		sb.append("    List<" + gnenglishname + "PO> queryDatas(String querySql, Map<String, Object> queryParams");
		if (NEEDPAGER) {
			sb.append(",final Pager pager");
		}
		sb.append(");" + N);
	}
	
	/**
	 * 构建queryContext,queryRole,queryDao接口的基础方法.
	 * @param sb 字符串
	 * @param query 是否查询
	 * @param contextOrDao context还是dao
	 */
	private static void buildImplQueryConRolBaseMethod(final StringBuilder sb, final String query, final String contextOrDao) {
		sb.append(N);
		if ("Dao".equals(contextOrDao)) {
			buildImplQueryDaoMethodCode(sb);
		} else {
			buildImplQueryConRolMethodCode(sb);
		}
		sb.append(N);
	}
	
	/**
	 * 构建queryContext,queryRole接口的基础方法的代码拼接.
	 * @param sb str
	 */
	private static void buildImplQueryConRolMethodCode(final StringBuilder sb) {
		/**
		 * /**
	     *  * {@inheritDoc}
	     *  *\/
		 *	public Map<String, Object> queryDatas(final IEcpDataContext context
		 *		, final Map<String, Object> queryCondition) {
		 *		// TODO Auto-generated method stub
		 *		return null;
		 *	}
		 */
		buildComment(sb, "{@inheritDoc}");
		sb.append("    public Map<String, Object> queryDatas(final IEcpDataContext context" + N);
		sb.append("        , final Map<String, Object> queryCondition");
		if (NEEDPAGER) {
			sb.append(",final Pager pager");
		}
		sb.append(") {" + N);
		sb.append("		   // TODO Auto-generated method stub" + N);
		sb.append("		   return null;" + N);
		sb.append("	   }" + N);
	}
	
	/**
	 * 构建queryDao接口的基础方法的代码拼接.
	 * @param sb str
	 */
	private static void buildImplQueryDaoMethodCode(final StringBuilder sb) {
		/**
		 * /**
	     *  *{@inheritDoc}
	     * 	*\/
		 *	public List<SendPersonPO> queryDatas(final String querySql, final Map<String, Object> queryParams) {
		 *		// TODO Auto-generated method stub
		 *		return null;
		 *	}
		 */
		buildComment(sb, "{@inheritDoc}");
		sb.append("    public List<" + gnenglishname + "PO> queryDatas(final String querySql, final Map<String, Object> queryParams");
		if (NEEDPAGER) {
			sb.append(",final Pager pager");
		}
		sb.append(") {" + N);
		sb.append("		   // TODO Auto-generated method stub" + N);
		sb.append("		   return null;" + N);
		sb.append("	   }" + N);
	}
	
	/**
	 * 拼接类注释.
	 * @param sb 字符串
	 */
	private static void buildCommand(final StringBuilder sb) {
		/**
		 * 期初数据录入Context.
		 * @author tuzewen
		 * @version 1.0.0 2019-7-25
		 * @since JDK 1.5.0
		 */
		final Date date = new Date();
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sb.append(N);
		sb.append("/**" + N);
		sb.append(" * " + gnchinesename + now + "." + N);
		sb.append(" * @author " + anthorname + N);
		sb.append(" * @version 1.0.0 " + sdf.format(date) + N);
		sb.append(" * @since JDK 1.5.0" + N);
		sb.append(" */" + N);
	}
	
	/**
	 * 拼接公司版权注释.
	 * @param sb 字符串
	 */
	private static void buildCompanyRight(final StringBuilder sb) {
		/*
		 * Copyright 2000-2020 YGSoft.Inc All Rights Reserved.
		 */
		sb.append("/*" + N);
		sb.append(" * Copyright 2000-2020 YGSoft.Inc All Rights Reserved." + N);
		sb.append(" */" + N);
	}
	
	/**
	 * 拼接注释.
	 * @param sb 字符串
	 */
	private static void buildComment(final StringBuilder sb, final String text) {
		/**
		 * this.
		 */
		sb.append("    /**" + N);
		sb.append("     * " + text + "." + N);
		sb.append("     */" + N);
	}

	/**
	 * 首字母小写.
	 * @param sb 字符串
	 */
	private static String firstToLowerCase(final String text) {
		final String first = text.substring(0, 1);
		final String after = text.substring(1, text.length());
		return first.toLowerCase() + after;
	}

}
