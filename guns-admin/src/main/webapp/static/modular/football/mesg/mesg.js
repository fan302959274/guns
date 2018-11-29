/**
 * 球队管理的单例
 */
var Mesg = {
    id: "mesgTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Mesg.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '消息名称', field: 'mesgname', align: 'center', valign: 'middle', sortable: true},
        {title: '消息渠道', field: 'channel', align: 'center', valign: 'middle', sortable: true},
        {title: '创建时间', field: 'createdate', align: 'center', valign: 'middle', sortable: true}
    ]
    return columns;
};

/**
 * 检查是否选中
 */
Mesg.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Mesg.seItem = selected[0];
        return true;
    }
};


/**
 * 点击添加消息信息
 */
Mesg.openAddMesg = function () {
    var index = layer.open({
        type: 2,
        title: '新增消息',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/mesg/mesg_add'
    });
    this.layerIndex = index;
};

/**
 * 点击修改按钮时
 */
Mesg.openEditMesg = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改消息',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/mesg/mesg_edit/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};


/**
 * 打开查看队员详情
 */
Mesg.openMesgView = function () {

    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: "消息详情",
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/mesg/detail/' + Mesg.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 删除消息
 */
Mesg.delMesg = function () {
    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/mesg/remove", function () {
                Feng.success("删除成功!");
                Mesg.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("mesgId", Mesg.seItem.id);
            ajax.start();
        };
        Feng.confirm("是否删除消息 " + Mesg.seItem.mesgname + "?",operation);
    }
};

/**
 * 搜索消息
 */
Mesg.search = function () {
    var queryData = {};
    queryData['mesgname'] = $("#mesgname").val();
    Mesg.table.refresh({query: queryData});
}
/**
 * 初始化信息
 */
$(function () {
    var defaultColunms = Mesg.initColumn();
    var table = new BSTable(Mesg.id, "/mesg/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Mesg.table = table;
});







