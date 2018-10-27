/**
 * 队员管理初始化
 */
var Member = {
    id: "MemberTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    type : $('#type').val()
};

/**
 * 初始化表格的列
 */
Member.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', align: 'center', valign: 'middle'},
        {title: '账号', field: 'account', align: 'center', valign: 'middle', sortable: true},
        {title: '姓名', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '球队名称', field: 'teamName', align: 'center', valign: 'middle', sortable: true},
        {title: '创建时间', field: 'createdate', align: 'center', valign: 'middle', sortable: true}
        ];
};

/**
 * 检查是否选中
 */
Member.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Member.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加队员
 */
Member.openAddMember = function () {
    var index = layer.open({
        type: 2,
        title: '添加队员',
        area: ['900px', '600px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/member/member_add?type='+Member.type
    });
    this.layerIndex = index;
};

/**
 * 打开查看队员详情（编辑）
 */
Member.openMemberDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '队员详情',
            area: ['900px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/member/member_update/' + Member.seItem.id+'?type='+Member.type
        });
        this.layerIndex = index;
    }
};


/**
 * 打开查看队员详情
 */
Member.openMemberView = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '广告详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/member/member_view/' + Member.seItem.id+'?type='+Member.type
        });
        this.layerIndex = index;
    }
};

/**
 * 删除队员
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
 * 查询队员列表
 */
Member.search = function () {
    var queryData = {};
    queryData['account'] = $("#condition").val();
    Member.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Member.initColumn();
    var table = new BSTable(Member.id, "/member/list?type="+Member.type, defaultColunms);
    table.setPaginationType("client");
    table.init();
    Member.table = table;
});
