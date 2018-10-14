/**
 * 球队管理的单例
 */
var Team = {
    id: "teamTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Team.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '球队名称', field: 'descteam', align: 'center', valign: 'middle', sortable: true},
        {title: '球队级别', field: 'level', align: 'center', valign: 'middle', sortable: true},
        {title: '球场区域', field: 'area', align: 'center', valign: 'middle', sortable: true},
        {title: '排名', field: 'ad_start_time', align: 'center', valign: 'middle', sortable: true},
        {title: '胜', field: 'winnum', align: 'center', valign: 'middle', sortable: true},
        {title: '负', field: 'debtnum', align: 'center', valign: 'middle', sortable: true},
        {title: '平', field: 'drawnum', align: 'center', valign: 'middle', sortable: true},
        {title: '积分', field: 'point', align: 'center', valign: 'middle', sortable: true}
    ]
    return columns;
};

/**
 * 检查是否选中
 */
Team.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Role.seItem = selected[0];
        return true;
    }
};

$(function () {
    var defaultColunms = Team.initColumn();
    var table = new BSTable(Team.id, "/team/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Team.table = table;
});
/**
 * 点击查看球员信息
 */
Team.teamMember = function () {
    var index = layer.open({
        type: 2,
        title: '添加球场',
        area: ['900px', '650px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/team/teamMember'
    });
    this.layerIndex = index;
};
