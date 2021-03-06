/**
 * 广告管理初始化
 */
var Ad = {
    id: "AdTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    type: $("#adType").val()
};

/**
 * 初始化表格的列
 */
Ad.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '广告标题', field: 'mainhead', align: 'center', valign: 'middle', sortable: true, width: '200px'},
        {title: '广告副标题', field: 'subhead', align: 'center', valign: 'center',align: 'center', sortable: true,width: '200px'},
        {title: '开始时间', field: 'starttime', align: 'center', valign: 'center', align: 'center',sortable: true, width: '200px'},
        {title: '结束时间', field: 'endtime', align: 'center', valign: 'center',  align: 'center',sortable: true, width: '200px'},
        {title: '广告链接', field: 'url', align: 'center', valign: 'middle', sortable: true},
        {title: '广告状态', field: 'status', align: 'center', valign: 'middle', sortable: true,width: '150px'}];
};

/**
 * 检查是否选中
 */
Ad.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
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
        area: ['790px', '570px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/ad/ad_add?type='+Ad.type
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
            area: ['790px', '570px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/ad/ad_update/' + Ad.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 打开查看广告详情
 */
Ad.openAdView = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '广告详情',
            area: ['790px', '570px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/ad/ad_view/' + Ad.seItem.id
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
    queryData['adMainHead'] = $("#adMainHead").val();
    queryData['type'] = $("#adType").val();
    Ad.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Ad.initColumn();
    var type =$("#adType").val();
    var table = new BSTable(Ad.id, "/ad/list?type="+type, defaultColunms);
    table.setPaginationType("client");
    table.init();
    Ad.table = table;
});
