/*
 * 由项目财务支出明细表穿透到查看财务凭证控制器类
 * 
 * 作者：LiuHui6
 * 日期：2017-06-26
 */
require([
    "ecp.osgi.service", //ecp服务
    "jjgk.base", //基类
    "jjgk.query.init",
    "ecp.component.loading",
    "ecp.utils", //ecp.utils
    'ecp.utils.window',
    "jjgk.common.utils",
    'ecp.component.dialog',
    "jjgk.component.nw.hskmSelector",
    "qzz.grid",
    "bs-dataTable",
    "bootstrap-select",
    "datetimepicker"
],
function ($ecp, $base, queryInit, PageLoading, $ecpUtils, $winUtil, cstrUtil, $dialog, hskmSelector) {

    'use strict'; //使用js语法严格模式(提高代码运行效率)

    var controller = $base.Class(queryInit, {

        init: function () {
            //加载等待控件， 默认装载到body	 
            this.loading = new PageLoading();
            //上下工具类和远程服务访问工具类	
            this.dcUtil = $ecp.DataContextUtil;
            this.remote = $ecp.RemoteService;
            this.context = this.dcUtil.getEcpDataContext();
            // 父窗口的传参 by miaoshaojun 适配通过url传参
            if (!cstrUtil.isEmpty(window._options)) {
                this.params = window._options.data;
            } else {
                //获取url参数
                this.params = $ecpUtils.getAllArgument();
                $("#bottomBar").show();
            }
        },

        /**
         * 初始化页面
         */
        initPage: function () {
            this.initUI();
            this.initCriteria();
            this.bindEvent();
        },

        //构建表头
        buildYwdjColNames: function () {
            var fields = [
                "billid",
                "billid1",
                "billid2",
                "凭证号",
                "制证日期",
                "财务核算科目",
                "借/贷",
                "发生额"
            ];
            return fields;
        },

        //构建表的列模型
        buildYwdjColModels: function () {
            var fields = [
                { "name": "billid", "editable": false, "filter": false, "hidden": true },
                { "name": "billid1", "editable": false, "filter": false, "hidden": true },
                { "name": "billid2", "editable": false, "filter": false, "hidden": true },
                { "name": "pzh", "editable": false, "filter": false, "textOverflow": "ellipsis" },
                { "name": "zzrq", "editable": false, "filter": false, "dataType": "date" },
                { "name": "caption", "editable": false, "filter": false, "textOverflow": "ellipsis" },
                { "name": "moneytype", "editable": false, "filter": false, "textOverflow": "ellipsis" },
                { "name": "tmoneyf", "editable": false, "filter": false, "dataType": "number", "scale": 2 }
            ];
            return fields;
        },

        /**
         * 初始化表格
         */
        initUI: function () {
            var that = this;
            // 设置qzztable外面div高度，不调用该方法无法显示grid
            function windowAlign() {
                var winH = $(window).height();
                $(".qzz-wrapper").css("height", winH - 300);
            }
            // 初始化grid高度
            windowAlign();
            // 当调整窗口的时候动态修正位置
            $(window).resize(function () {
                windowAlign();
            });
            //日期控件初始化				 
            this.ywtime = $('#year,#endyear').datetimepicker({
                format: 'yyyy',
                autoclose: true,
                todayBtn: true,
                startView: 'decade',
                minView: 'decade',
                maxView: 'decade',
                language: 'zh-CN'
            });
            //初始化表格
            this.cwpzGrid = $("#mainTable").qzzquerygrid({
                "pageSize": 100,
                "pager": true,
                "Align": "alClient", // 对齐方式
                "shrinkToFit": true, // 表格宽度填满
                "hidePopMenu": true, // 是否隐藏右键菜单
                "colNames": that.buildYwdjColNames(),
                "colModels": that.buildYwdjColModels()
            });


        },

        initCriteria: function () {
            // 父窗口的传参
            var criteria = this.params;
            // 设置本窗口的查询条件值
            var year = null;
            if (!cstrUtil.isEmpty(criteria.year)) {
                year = criteria.year;
            } else {
                year = criteria.endyear;
            }
            // 开始年份	
            $("#year").datetimepicker("setDate", new Date(year));
            //结束年份	
            $("#endyear").datetimepicker("setDate", new Date(criteria.endyear));
            // 所属工程项目	
            $("#gcxmmc").val(criteria.ssgcxm);
            // 查询编码
            $("#cxbm").val(criteria.cxbm);
            //查询维度	
            $("#cxwd").val(criteria.cxwd);
        },

        /**
         * 事件绑定
         */
        bindEvent: function () {
            var that = this;
            $("#fullcaption").click(function () {
                var option = {
                    isSingle: true, //true 为单选 false 为多选
                    isShowAlreadySelectList: true, //显示列表
                    justLeaf: true, //仅能选择叶子节点
                    isPopClear: true, //允许清空
                    //科目查询参数
                    queryParam: {
                        filterQx: true, //filterQx 是否过滤权限(true-通过权限过滤,默认值; 非true(如:false)-不通过权限过滤)
                        filterXm: true, //是否过滤(是否包含)数据项目(true-过滤,即不包含数据项目; 非true(如:false)-不过滤,即包含数据项目,默认值)
                        filterTy: true, //是否过滤停用科目(如果上级停用,下级科目及数据项目均不被列出),默认展示停用科目
                        filterTz: false, //是否过滤调整期科目，默认不过滤
                        justLeaf: false //是否仅保留非底层科目,默认保留全部科目
                    },
                    callback: that.ItemcodeCallback
                };
                hskmSelector.openWindow(option);
            });
            $("#queryBtn").click(function () {
                //起止年份
                var year = $("#year").val();
                var endyear = $("#endyear").val();
                if (!cstrUtil.checkDateRange(year, endyear)) {
                    cstrUtil.dialog("结束年份不能小于开始年份。");
                    return;
                }
                var pageSize = that.cwpzGrid.pageObj.getPageSize();
                that.cwpzGrid.trigger("onPageChanged", [0, pageSize, 1]);
            });
            //重置	        
            $("#resetBtn").on("click", function () {
                that.doReset();
            });
            this.cwpzGrid.bind("onPageChanged", function (pageCount, pageSize, pageIndex) {
                that.doQuery(pageIndex, pageSize);
            }, true);
            //穿透业务单据
            $("#cxpzBtn").bind("click", $.proxy(that.onCWPZClick, this));
            // 导出
            $("#doExportDataBtn").on("click", $.proxy(this.doExportDataEvent, this));
        },
        /**
         * 核算科目选中后回调
         */
        ItemcodeCallback: function (resultObj) {
            $('#fullcaption').val(resultObj.fullcaption);
            $('#itemcode').val(resultObj.gitemcode);
        },

        /**
         *重置方法
         */
        doReset: function () {
            var _this = this;
            var tipMsg = "是否重置查询条件到默认值？";
            $dialog.dialog({
                title: "请选择", //模态窗标题
                content: tipMsg, //模态窗内容
                isTip: true, //标准的提示窗口
                showCloseButton: false, //不显示关闭按钮
                otherButtons: ["取消", "确定"], //增加两个按钮
                otherButtonStyles: ['btn-link', 'btn-primary'], //按钮样式,
                clickButton: function (sender, modal, index) {
                    modal.modal("hide");
                    if (index == 1) { //确定按钮
                        $("#itemcode").val("");
                        $("#fullcaption").val("");
                        var now = new Date().getFullYear();
                        $("#year").val(now);
                        $("#endyear").val(now);
                    }
                }
            });
        },

        //查询操作
        doQuery: function (pageIndex, pageSize) {
            var that = this;
            var beanId = "com.ygsoft.gris.construction.framework.service.context.IFinancialProofQueryContext";
            var method = "findList";
            //获取参数
            /***页面查询条件选择后不起作用  by miaoshoajun 2017-08-02***/
            var criteria = this.params;
            criteria.year = $("#year").val();
            criteria.endyear = $("#endyear").val();
            criteria.ssgcxm = $("#gcxmmc").val();
            criteria.cxbm = $("#cxbm").val();
            criteria.cxwd = $("#cxwd").val();
            /***页面查询条件选择后不起作用  by miaoshoajun 2017-08-02***/

            var itemcode = $('#itemcode').val();
            if (!cstrUtil.isEmpty(itemcode)) {
                criteria.itemcode = itemcode;
            }
            criteria.jcls = "com.ygsoft.gris.construction.framework.service.model.cwpz.FinancialProofCriteriaVO";
            var pager = {
                "currentPageNum": pageIndex,
                "pageSize": pageSize,
                'jcls': 'com.ygsoft.ecp.ef.service.model.bo.Pager'
            };
            // 组装pageCondition
            var pageCondition = { "pager": pager };
            var params = [this.context, criteria, pageCondition];
            this.loading.show();
            this.remote.doPostAsync(beanId, method, params, function (resp, textStatus) {
                //ajax调用异常
                if (resp.isError()) {
                    cstrUtil.dialog(resp.data);
                } else {
                    var respData = resp.data;
                    var result = respData.data;
                    if (result && result.length > 0) {
                        that.cwpzGrid.value(result);
                        that.cwpzGrid.setTotalRecord(result.length, false);
                    } else {
                        that.cwpzGrid.value([]);
                        that.cwpzGrid.setTotalRecord(0, false);
                    }
                }
                that.loading.hide();
            });
        },

        /**
         * 导出当前数据
         */
        doExportDataEvent: function () {
            var that = this;
            var opt = {
                'fileName': "财务凭证表"
            };
            cstrUtil.exportCurPage(that["cwpzGrid"], opt);
        },

        /** 穿透财务凭证 */
        onCWPZClick: function () {
            var that = this;
            // 获取行数据			   
            var rowdata = that.cwpzGrid.getSelectedRowData();
            if (rowdata == null) {
                cstrUtil.dialog("请选择一条记录。");
                return;
            }
            var billid = rowdata.billid;
            //rowdata.billid字段值等于99999999为虚拟期初凭证特殊给定值
            if (billid == 99999999) {
                cstrUtil.dialog('虚拟期初凭证不能穿透。');
                return;
            }
            var pzyear = $ecpUtils.formatEcpDate(rowdata.zzrq).substr(0, 4);
            //穿透凭证:pzlx=1
            var url = cstrUtil.getWebAppRoot() + "/assets/components/constructframeworkweb/comm/showPZ.html?gid="+billid+"&year="+pzyear;
		    $winUtil.openWindow(url,"穿透凭证");
        }

    });
    // 实例化controller
    var financialProofController = new controller();
    financialProofController.initPage();

});