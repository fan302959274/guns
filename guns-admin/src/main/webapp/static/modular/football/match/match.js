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
        {title: '账号', field: 'account', align: 'center', valign: 'middle', sortable: true},
        {title: '姓名', field: 'name', align: 'center', valign: 'middle', sortable: true}];
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
    queryData['account'] = $("#condition").val();
    Match.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Match.initColumn();
    var table = new BSTreeTable(Match.id, "/match/list", defaultColunms);
    table.setExpandColumn(2);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("pid");
    table.setExpandAll(true);
    table.init();
    Match.table = table;
});
