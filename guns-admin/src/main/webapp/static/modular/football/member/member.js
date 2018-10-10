/**
 * 广告管理初始化
 */
var Member = {
    id: "MemberTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Member.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', align: 'center', valign: 'middle', width: '50px'},
        {title: '账号', field: 'account', align: 'center', valign: 'middle', sortable: true},
        {title: '姓名', field: 'name', align: 'center', valign: 'middle', sortable: true}];
};

/**
 * 检查是否选中
 */
Member.check = function () {
    var selected = $('#' + this.id).bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Member.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加广告
 */
Member.openAddMember = function () {
    var index = layer.open({
        type: 2,
        title: '添加广告',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/member/member_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看广告详情
 */
Member.openMemberDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '广告详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/member/member_update/' + Member.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除广告
 */
Member.delete = function () {
    if (this.check()) {

        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/member/delete", function () {
                Feng.success("删除成功!");
                Member.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("memberId", Member.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否刪除该广告?", operation);
    }
};

/**
 * 查询广告列表
 */
Member.search = function () {
    var queryData = {};
    queryData['adMainHead'] = $("#condition").val();
    Member.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Member.initColumn();
    var table = new BSTreeTable(Member.id, "/member/list", defaultColunms);
    table.setExpandColumn(2);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("pid");
    table.setExpandAll(true);
    table.init();
    Member.table = table;
});
