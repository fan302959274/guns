/**
 * 广告管理初始化
 */
var Match = {
    id: "MatchTable",	//表格id
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
        {title: 'id', field: 'id', align: 'center', valign: 'middle', width: '50px'},
        {title: '挑战方', field: 'challengeteam', align: 'center', valign: 'middle', width: '200px',sortable: true},
        {title: '球场区域', field: 'area', align: 'center', valign: 'middle',width: '200px', sortable: true},
        {title: '球场', field: 'parkname', align: 'center', valign: 'middle',width: '200px', sortable: true},
        {title: '比赛状态', field: 'status', align: 'center', valign: 'middle',width: '200px', sortable: true},
        {title: '比赛时间', field: 'time', align: 'center', valign: 'middle',width: '200px', sortable: true},
        {title: '支付状态', field: 'challengepaystatus', align: 'center', valign: 'middle',width: '200px', sortable: true}];
};

/**
 * 检查是否选中
 */
Match.check = function () {
    var selected = $('#' + this.id).bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Match.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加广告
 */
Match.openAddMatch = function () {
    var index = layer.open({
        type: 2,
        title: '添加广告',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/match/match_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看广告详情（编辑）
 */
Match.openMatchDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '广告详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/match/match_update/' + Match.seItem.id
        });
        this.layerIndex = index;
    }
};


/**
 * 打开查看广告详情
 */
Match.openMatchView = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '广告详情',
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

        Feng.confirm("是否刪除该广告?", operation);
    }
};

/**
 * 查询广告列表
 */
Match.search = function () {
    var queryData = {};
    queryData['status'] = $("#status").val();
    queryData['paystatus'] = $("#paystatus").val();
    Match.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Match.initColumn();
    var table = new BSTable(Match.id, "/match/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Match.table = table;
});
