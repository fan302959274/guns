/**
 * 广告管理初始化
 */
var Rule = {
    id: "ruleTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
};

/**
 * 初始化表格的列
 */
Rule.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '规则', field: 'type', align: 'center', valign: 'middle', sortable: true, width: '200px'},
        {title: '内容', field: 'content', align: 'center', valign: 'center',  align: 'center',sortable: true, width: '200px'},
        {title: '修改时间', field: 'createtime', align: 'center', valign: 'center', align: 'center',sortable: true, width: '200px'},
      ];
};

/**
 * 检查是否选中
 */
Rule.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Rule.seItem = selected[0];
        return true;
    }
};
/**
 * 打开查看规则编辑
 */
Rule.openRuleDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '规则详情',
            area: ['780px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/ad/rule_detail/' + Rule.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 打开查看规则详情
 */
Rule.openRuleView = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '规则详情',
            area: ['780px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/ad/rule_view/' + Rule.seItem.id
        });
        this.layerIndex = index;
    }
};
$(function () {
    var defaultColunms = Rule.initColumn();
    var table = new BSTable(Rule.id, "/ad/ruleList", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Rule.table = table;
});
