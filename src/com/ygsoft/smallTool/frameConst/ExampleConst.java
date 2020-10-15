package com.ygsoft.smallTool.frameConst;

import java.util.HashMap;
import java.util.Map;

/**
 * 范例常量类.
 * @author tuzewen
 *
 */
public final class ExampleConst {

    /**
     * 字符串转换示例Map.
     */
    public static final Map<String, String[]> STREXAMPLEMAP = new HashMap<String, String[]>();
    
    /**
     * 生成代码转换示例Map.
     */
    public static final Map<String, String[]> BUILDCODEEXAMPLEMAP = new HashMap<String, String[]>();

    /**
     * "1.将java中sql与plsql中sql进行转换"
     * "2.根据VO或者PO生成POVO转换类"
     * "3.获取每行某一位置字符串"
     * "4.灵活多行合并成一行"
     *
     */
    static {
        STREXAMPLEMAP.put("1", new String[]{
                "SELECT T.DXID, T.DXMC\n  FROM XTGLDX2021 T\n WHERE T.DXID = '123456'\n GROUP BY T.DXID, T.DXMC\n ORDER BY T.DXID;",
                "\"SELECT T.DXID, T.DXMC\"\n+ \"   FROM XTGLDX2021 T\"\n+ \"  WHERE T.DXID = '123456'\"\n+ \"  GROUP BY T.DXID, T.DXMC\"\n+ \"  ORDER BY T.DXID\""
        });
        STREXAMPLEMAP.put("2", new String[]{
                "	/**\n	 * serialVersionUID.\n	 */\n	private static final long serialVersionUID = -3554091546870869550L;\n	\n	/**\n	 * 项目分类.\n	 */\n	private String xmfl;\n	/**\n	/**\n	 * 到款认领金额.\n	 */\n	private BigDecimal kydkrlje = BigDecimal.ZERO;\n	\n	/**\n	 * 获取xmfl.\n	 * @return the xmfl\n	 */\n	public String getXmfl() {\n		return xmfl;\n	}\n	/**\n	 * 设置xmfl.\n	 * @param newXmfl the xmfl to set\n	 */\n	public void setXmfl(final String newXmfl) {\n		xmfl = newXmfl;\n	}\n	/**\n	 * 获取kydkrlje.\n	 * @return the kydkrlje\n	 */\n	public BigDecimal getKydkrlje() {\n		return kydkrlje;\n	}\n	/**\n	 * 设置kydkrlje.\n	 * @param newKydkrlje the kydkrlje to set\n	 */\n	public void setKydkrlje(final BigDecimal newKydkrlje) {\n		kydkrlje = newKydkrlje;\n	}\n",
                "po.setXmfl(vo.getXmfl());\npo.setKydkrlje(vo.getKydkrlje());"
        });
        STREXAMPLEMAP.put("3", new String[]{
                "name\n{ \"name\": \"ksrq\", \"showHint\": true, \"dataType\": \"date\" },\n{ \"name\": \"jsrq\", \"showHint\": true, \"dataType\": \"date\" },\n{ \"name\": \"ssxm\", \"showHint\": true, \"dataType\": \"gldx\", \"typeId\": \"2021\" },\n{ \"name\": \"sskt\", \"showHint\": true, \"dataType\": \"gldx\", \"typeId\": \"2021\" },\n{ \"name\": \"sfkt\", \"showHint\": true, \"dataType\": \"enum\", \"typeId\": \"50000001\" },\n",
                "ksrq,jsrq,ssxm,sskt,sfkt"
        });
        STREXAMPLEMAP.put("4", new String[]{
        		"案例一:\n"
                + "\"合同编号\",\n"
                + "\"合同总金额\",\n"
                + "\"合同签订项目类型\",\n"
                + "\"合同收付款类型\",\n"
                + "---\n"
                + "案例二:\n"
                + "final String {0} = \"{0}\";\n"
                + "ksrq,jsrq,ssxm,sskt,sfkt",
                
                "案例一:\n"
                + "\"合同编号\",\"合同总金额\",\"合同签订项目类型\",\"合同收付款类型\",\n"
                + "---\n"
                + "案例二:\n"
                + "final String ksrq = ksrq;\n"
                + "final String jsrq = jsrq;\n"
                + "final String ssxm = ssxm;\n"
                + "final String sskt = sskt;\n"
                + "final String sfkt = sfkt;"
        });
        BUILDCODEEXAMPLEMAP.put("1", new String[]{
                "workspacePath: E:\\eclipse6.0\n"
        		+ "projectPath: \\src\\com\\ygsoft\\gris\\\n"
                + "groupId: construction\n"
                + "detailId: bidding\n"
                + "author: tuzewen\n"
                + "projectEnglishName: Gccbgj\n"
                + "projectName: 工程成本归集\n",
                
                ""
        });
    }


}
