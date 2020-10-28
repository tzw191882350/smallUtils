package com.ygsoft.smallTool.frame;import java.awt.Color;import java.awt.Font;import java.awt.event.ActionEvent;import java.awt.event.ActionListener;import java.awt.event.ItemEvent;import java.awt.event.ItemListener;import java.awt.event.KeyEvent;import java.io.File;import java.io.IOException;import java.util.HashMap;import java.util.Map;import javax.swing.BorderFactory;import javax.swing.JButton;import javax.swing.JCheckBoxMenuItem;import javax.swing.JComboBox;import javax.swing.JFrame;import javax.swing.JLabel;import javax.swing.JMenu;import javax.swing.JMenuBar;import javax.swing.JMenuItem;import javax.swing.JPanel;import javax.swing.JScrollPane;import javax.swing.JTextArea;import javax.swing.KeyStroke;import com.ygsoft.smallTool.buildCodeUtil.BuildCodesUtil;import com.ygsoft.smallTool.dealStringUtil.SqlUtil;import com.ygsoft.smallTool.dealStringUtil.TransStringUtil;import com.ygsoft.smallTool.frameConst.ExampleConst;import com.ygsoft.smallTool.frameConst.ToolTypeConst;import com.ygsoft.smallTool.loopFileUtil.LoopFileUtil;public class ToolFrame {    // 工具处理需要的属性---------------------------------------------------	// 工具类型    private String toolTypeStr = "";    // 菜单类型    private String menuTypeStr = "STRUTIL";    // 工具路径    private String filePath;    // 画页面需要的属性-----------------------------------------------------    private final JFrame frame = new JFrame("多功能工具1.0");        // 工具类型菜单-------------------------------------------------------    private JMenu typeMenu = new JMenu();    private JMenu effectMenu = new JMenu();    // 主panel--------------------------------------------------------------    private final JPanel mainPanel = new JPanel();    // 说明panel------------------------------------------------------------    private final JPanel explainPanel = new JPanel();    // 工具类型    private final JLabel toolTypeLabel = new JLabel("工具类型: ");        public final JComboBox toolTypeComboBox = new JComboBox(ToolTypeConst.STRTOOLTYPE);    //创建JComboBox    // 说明标签    private final JLabel explainLabel = new JLabel("说明: ");    private final JLabel explainTextLabel = new JLabel();    // 转换文本panel----------------------------------------------    private final JPanel textPanel = new JPanel();    // 转换前panel    private final JTextArea beforeTextArea = new JTextArea();    private final JScrollPane beforeJScrollPane = new JScrollPane(beforeTextArea);    // 转换后panel    private final JTextArea afterTextArea = new JTextArea();    private final JScrollPane afterJScrollPane = new JScrollPane(afterTextArea);    // 按钮区----------------------------------------------    private final JPanel buttonPanel = new JPanel();    // 清除全部    private final JButton clearAllButton = new JButton("清除全部");    // 清除全部    private final JButton clearEmptyStrButton = new JButton("去除左右空格");    // 转换    private final JButton transButton = new JButton("转换");    // 退出    private final JButton existButton = new JButton("退出");    /**     * 构造函数     */    public ToolFrame() {    	try {			filePath = new File("").getCanonicalPath();		} catch (final IOException e) {			e.printStackTrace();		}        setMainPanelElement();        addElement();        setTextPanelElement();        bindEvent();        frame.setBounds(260,130,1000,630);        // 点×退出窗体        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        // 不想让窗体大小改变        frame.setResizable(false);        // 设置菜单栏        frame.setJMenuBar(setJMenuBar());        // 显示整个窗体        frame.setVisible(true);    }        /**     * 绑定事件     */    private void bindEvent() {    	toolTypeComboBoxBindEvent();        transButtonBindEvent();        clearAllButtonBindEvent();        clearEmptyStrButtonBindEvent();        existButtonBindEvent();    }    /**     * 添加组件     */    private void addElement(){        explainPanel.add(toolTypeLabel);        explainPanel.add(toolTypeComboBox);        explainPanel.add(explainLabel);        explainPanel.add(explainTextLabel);        textPanel.add(beforeJScrollPane);        textPanel.add(afterJScrollPane);        buttonPanel.add(clearAllButton);        buttonPanel.add(clearEmptyStrButton);        buttonPanel.add(transButton);        buttonPanel.add(existButton);        mainPanel.add(explainPanel);        mainPanel.add(textPanel);        mainPanel.add(buttonPanel);        frame.add(mainPanel);    }    /**     * 设置panel的样式     */    private void setMainPanelElement(){        // 自定义布局        mainPanel.setBackground(Color.LIGHT_GRAY);        mainPanel.setLayout(null);        explainPanel.setLayout(null);        textPanel.setLayout(null);        buttonPanel.setLayout(null);        setExplainPanelElement();        setTextPanelElement();        setButtonPanelElement();    }    /**     * 设置说明区属性     */    private void setExplainPanelElement(){        final int x = 16;        final int width = 45;        explainPanel.setBounds(x,10,950,40);        toolTypeLabel.setBounds(x,1,90,19);        toolTypeComboBox.setBounds(x + 90,1,200,19);        explainLabel.setBounds(x,21,width,19);        explainLabel.setFont(new Font("宋体", Font.PLAIN, 13));        explainTextLabel.setBounds(x + width,21,910,19);        explainTextLabel.setFont(new Font("宋体",Font.PLAIN, 13));        explainTextLabel.setEnabled(false);    }    /**     * 设置文本区属性     */    private void setTextPanelElement() {        textPanel.setBounds(16,41,950,460);        beforeJScrollPane.setBounds(16,10,450,450);        beforeTextArea.setFont(new Font("宋体",Font.PLAIN, 15));        afterJScrollPane.setBounds(476,10,450,450);        afterTextArea.setFont(new Font("宋体",Font.PLAIN, 15));    }    /**     * 设置按钮区属性     */    private void setButtonPanelElement(){        buttonPanel.setBounds(16,500,950,50);        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));        clearAllButton.setBounds(16,10,100,30);        clearAllButton.setFont(new Font("宋体",Font.PLAIN, 13));        clearEmptyStrButton.setBounds(120,10,130,30);        clearEmptyStrButton.setFont(new Font("宋体",Font.PLAIN, 13));        transButton.setBounds(750,10,80,30);        transButton.setFont(new Font("宋体",Font.PLAIN, 13));        existButton.setBounds(850,10,80,30);        existButton.setFont(new Font("宋体",Font.PLAIN, 13));    }    /**     * 定义“分类”菜单     */    private JMenu createTypeMenu() {        final JMenu menu = new JMenu("分类(F)");        menu.setMnemonic(KeyEvent.VK_F);    //设置快速访问符        final JMenuItem strItem = new JMenuItem("字符串处理工具(N)", KeyEvent.VK_N);        strItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));        menu.add(strItem);        final JMenuItem sqlItem = new JMenuItem("数据库工具(O)", KeyEvent.VK_O);        sqlItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));        menu.add(sqlItem);        final JMenuItem fileItem = new JMenuItem("文件遍历工具(S)", KeyEvent.VK_S);        fileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));        menu.add(fileItem);        menu.addSeparator();        final JMenuItem codeItem = new JMenuItem("代码生成工具(E)", KeyEvent.VK_E);        codeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));        menu.add(codeItem);                strItem.addActionListener(new ActionListener() {            @Override            public void actionPerformed(final ActionEvent e) {            	menuTypeStr = "STRUTIL";            	setToolTypeComboBox(ToolTypeConst.STRTOOLTYPE);            }        });        sqlItem.addActionListener(new ActionListener() {            @Override            public void actionPerformed(final ActionEvent e) {            	menuTypeStr = "SQLUTIL";            	setToolTypeComboBox(ToolTypeConst.SQLTOOLTYPE);            	afterTextArea.setText(getAllToolTypeStr(ToolTypeConst.SQLTOOLTYPE));            }        });        codeItem.addActionListener(new ActionListener() {            @Override            public void actionPerformed(final ActionEvent e) {            	menuTypeStr = "BUILDCODETOOL";            	setToolTypeComboBox(ToolTypeConst.BUILDCODETYPE);            }        });        fileItem.addActionListener(new ActionListener() {            @Override            public void actionPerformed(final ActionEvent e) {                menuTypeStr = "LOOPFILEUTIL";                setToolTypeComboBox(ToolTypeConst.LOOPFILETYPE);            }        });        return menu;    }    /**     * 设置工具类型下拉选.     * @param toolType     */    private void setToolTypeComboBox(final String[] toolType) {    	toolTypeComboBox.removeAllItems();    	for (int i = 0; i < toolType.length; i++) {    		toolTypeComboBox.addItem(toolType[i]);		}    }    /**     * 定义“功能”菜单     */    private JMenu createEffectMenu() {        final JMenu menu=new JMenu("编辑(E)");        menu.setMnemonic(KeyEvent.VK_E);        JMenuItem item=new JMenuItem("撤销(U)",KeyEvent.VK_U);        item.setEnabled(false);        menu.add(item);        menu.addSeparator();        item=new JMenuItem("剪贴(T)",KeyEvent.VK_T);        menu.add(item);        item=new JMenuItem("复制(C)",KeyEvent.VK_C);        menu.add(item);        menu.addSeparator();        final JCheckBoxMenuItem cbMenuItem=new JCheckBoxMenuItem("自动换行");        menu.add(cbMenuItem);        return menu;    }    /**     * 设置菜单栏     * @return 菜单栏     */    private JMenuBar setJMenuBar() {        final JMenuBar bar = new JMenuBar();        typeMenu = createTypeMenu();        effectMenu = createEffectMenu();        bar.add(typeMenu);        bar.add(effectMenu);        return bar;    }    // 事件--------------------------------------------------------------------    /**     * 工具类型comobox绑定选择事件     */    private void toolTypeComboBoxBindEvent() {        toolTypeComboBox.addItemListener(new ItemListener() {            @Override			public void itemStateChanged(final ItemEvent e) {                final String item = (String) e.getItem();                final int index = item.indexOf(".");                if (e.getStateChange() == ItemEvent.SELECTED && index > -1) {                    toolTypeStr = item.substring(0, index);                    explainTextLabel.setText(ToolTypeConst.TOOLTYPEMAP.get(toolTypeStr));                    if ("STRUTIL".equals(menuTypeStr)) {                    	transExample(ExampleConst.STREXAMPLEMAP);					} else if("SQLUTIL".equals(menuTypeStr)) {						final String usualSql = SqlUtil.getUsualSql(filePath);						beforeTextArea.setText(usualSql);//						transExample(ExampleConst.SQLEXAMPLEMAP);					} else if("BUILDCODETOOL".equals(menuTypeStr)) {                        transExample(ExampleConst.BUILDCODEEXAMPLEMAP);                    } else if("LOOPFILEUTIL".equals(menuTypeStr)) {                        transExample(ExampleConst.LOOPFILEEXAMPLEMAP);                    }                }            }        });    }        /**     * 转换按钮绑定事件     */    private void transButtonBindEvent() {        transButton.addActionListener(new ActionListener() {            @Override            public void actionPerformed(final ActionEvent e) {            	if (!"".equals(toolTypeStr)) {            		if ("STRUTIL".equals(menuTypeStr)) {                    	final String str = beforeTextArea.getText();                    	final String result = TransStringUtil.executeStrTrans(toolTypeStr, str);                    	afterTextArea.setText(result);					} else if("BUILDCODETOOL".equals(menuTypeStr)) {						final String str = beforeTextArea.getText();						final String[] strArr = str.split("\n");						final Map<String, String> params = new HashMap<String, String>();						for (int i = 0; i < strArr.length; i++) {				            String line = strArr[i];				            final String[] paramArr = line.split(":");				            params.put(paramArr[0], paramArr[1]);				        }						BuildCodesUtil.begin(params);                    } else if("LOOPFILEUTIL".equals(menuTypeStr)) {                        final String str = beforeTextArea.getText();                        final String result = LoopFileUtil.loopFile(toolTypeStr, str);                        afterTextArea.setText(result);                    }            	}            }        });    }    /**     * 清空全部按钮绑定事件     */    private void clearAllButtonBindEvent() {        clearAllButton.addActionListener(new ActionListener() {            @Override            public void actionPerformed(final ActionEvent e) {                if("LOOPFILEUTIL".equals(menuTypeStr)) {                    setToolTypeComboBox(ToolTypeConst.LOOPFILETYPE);                } else {                    beforeTextArea.setText("");                }            }        });    }    /**     * 去除左右空格钮绑定事件     */    private void clearEmptyStrButtonBindEvent() {    	clearEmptyStrButton.addActionListener(new ActionListener() {            @Override            public void actionPerformed(final ActionEvent e) {            	final String str = beforeTextArea.getText();            	final String result = TransStringUtil.clearEmptyLineAndTrim(str);        		beforeTextArea.setText(result);            }        });    }    /**     * 退出按钮绑定事件     */	private void existButtonBindEvent() {		existButton.addActionListener(new ActionListener() {			@Override			public void actionPerformed(final ActionEvent ae) {				System.exit(0);			}		});	}	/**     * 示例     */    private void transExample(final Map<String, String[]> exampleMap) {    	final String[] exampleArr = exampleMap.get(toolTypeStr);    	if (exampleArr != null) {    		beforeTextArea.setText(exampleArr[0]);    		afterTextArea.setText(exampleArr[1]);		}    }    /**     * 获取所有菜单类型字符串.     * @return 所有菜单类型字符串     */    private String getAllToolTypeStr(final String[] toolTypeArr) {    	final StringBuilder toolTypeStr = new StringBuilder();    	for (int i = 1; i < toolTypeArr.length; i++) {			toolTypeStr.append(toolTypeArr[i]).append("\n");		}		return this.toolTypeStr;    }}