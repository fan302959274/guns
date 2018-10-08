/**
 * 广告管理初始化
 */
var Ad = {
    id: "AdTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Ad.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', align: 'center', valign: 'middle', width: '50px'},
        {title: '广告标题', field: 'adMainHead', align: 'center', valign: 'middle', sortable: true},
        {title: '广告副标题', field: 'adSubHead', align: 'center', valign: 'middle', sortable: true},
        {title: '开始时间', field: 'adStartTime', align: 'center', valign: 'middle', sortable: true},
        {title: '结束时间', field: 'adEndTime', align: 'center', valign: 'middle', sortable: true},
        {title: '广告链接', field: 'adUrl', align: 'center', valign: 'middle', sortable: true},
        {title: '广告状态', field: 'adStatus', align: 'center', valign: 'middle', sortable: true}];
};

/**
 * 检查是否选中
 */
Ad.check = function () {
    var selected = $('#' + this.id).bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Ad.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加广告
 */
Ad.openAddAd = function () {
    var index = layer.open({
        type: 2,
        title: '添加广告',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/ad/ad_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看广告详情
 */
Ad.openAdDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '广告详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/ad/ad_update/' + Ad.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除广告
 */
Ad.delete = function () {
    if (this.check()) {

        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/ad/delete", function () {
                Feng.success("删除成功!");
                Ad.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("adId", Ad.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否刪除该广告?", operation);
    }
};

/**
 * 查询广告列表
 */
Ad.search = function () {
    var queryData = {};
    queryData['adMainHead'] = $("#condition").val();
    Ad.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Ad.initColumn();
    var table = new BSTreeTable(Ad.id, "/ad/list", defaultColunms);
    table.setExpandColumn(2);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("pid");
    table.setExpandAll(true);
    table.init();
    Ad.table = table;
});
