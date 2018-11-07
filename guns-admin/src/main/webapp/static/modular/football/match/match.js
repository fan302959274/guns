/**
 * 广告管理初始化
 */
var Match = {
    id: "matchTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Match.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id',visible: false},
        {title: '订单号', field: 'no', align: 'center', valign: 'middle', width: '50px'},
        {title: '发起方', field: 'hostteam', align: 'center', valign: 'middle', width: '200px',sortable: true},
        {title: '球场区域', field: 'area', align: 'center', valign: 'middle',width: '200px', sortable: true},
        {title: '比赛状态', field: 'status', align: 'center', valign: 'middle',width: '200px', sortable: true},
        {title: '比赛时间', field: 'createdate', align: 'center', valign: 'middle',width: '200px', sortable: true}];
};

/**
 * 检查是否选中
 */
Match.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Match.seItem = selected[0];
        return true;
    }
};



/**
 * 打开查看广告详情
 */
Match.openMatchView = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '比赛详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/match/match_view/' + Match.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除广告
 */
Match.delete = function () {
    if (this.check()) {

        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/match/delete", function () {
                Feng.success("删除成功!");
                Match.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("matchId", Match.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否刪除该比赛?", operation);
    }
};

/**
 * 查询比赛列表
 */
Match.search = function () {
    var queryData = {};
    queryData['areas'] = $("#areas").val();
    queryData['pkstatus'] = $("#pkstatus").val();
    queryData['hostname'] = $("#hostname").val();
    queryData['no'] = $("#no").val();
    Match.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Match.initColumn();
    var table = new BSTable(Match.id, "/match/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Match.table = table;
});
