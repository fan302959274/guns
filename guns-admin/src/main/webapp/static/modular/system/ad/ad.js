/**
 * 部门管理初始化
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
        {title: 'id', field: 'id', align: 'center', valign: 'middle',width:'50px'},
        {title: '部门简称', field: 'simplename', align: 'center', valign: 'middle', sortable: true},
        {title: '部门全称', field: 'fullname', align: 'center', valign: 'middle', sortable: true},
        {title: '排序', field: 'num', align: 'center', valign: 'middle', sortable: true},
        {title: '备注', field: 'tips', align: 'center', valign: 'middle', sortable: true}];
};

/**
 * 检查是否选中
 */
Ad.check = function () {
    var selected = $('#' + this.id).bootstrapTreeTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Ad.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加部门
 */
Ad.openAddAd = function () {
    var index = layer.open({
        type: 2,
        title: '添加部门',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/ad/ad_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看部门详情
 */
Ad.openAdDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '部门详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/ad/ad_update/' + Ad.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除部门
 */
Ad.delete = function () {
    if (this.check()) {

        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/ad/delete", function () {
                Feng.success("删除成功!");
                Ad.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("adId",Ad.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否刪除该部门?", operation);
    }
};

/**
 * 查询部门列表
 */
Ad.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
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
