/**
 * 球场管理的单例
 */
var Role = {
    id: "roleTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Role.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '球场名称', field: 'ad_main_head', align: 'center', valign: 'middle', sortable: true},
        {title: '球场区域', field: 'ad_sub_head', align: 'center', valign: 'middle', sortable: true},
        {title: '球场地址', field: 'ad_start_time', align: 'center', valign: 'middle', sortable: true},
        {title: '球场描述', field: 'ad_start_time', align: 'center', valign: 'middle', sortable: true}
    ]
    return columns;
};


/**
 * 检查是否选中
 */
Role.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Role.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加管理员
 */
Role.openAddRole = function () {
    var index = layer.open({
        type: 2,
        title: '添加角色',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/role/role_add'
    });
    this.layerIndex = index;
};

/**
 * 点击修改按钮时
 */
Role.openChangeRole = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改角色',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/role/role_edit/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除角色
 */
Role.delRole = function () {
    if (this.check()) {

        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/role/remove", function () {
                Feng.success("删除成功!");
                Role.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("roleId", Role.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否删除角色 " + Role.seItem.name + "?",operation);
    }
};

/**
 * 权限配置
 */
Role.assign = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '权限配置',
            area: ['300px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/role/role_assign/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 搜索角色
 */
Role.search = function () {
    var queryData = {};
    queryData['roleName'] = $("#roleName").val();
    Role.table.refresh({query: queryData});
}

$(function () {
    var defaultColunms = Role.initColumn();
    var table = new BSTable(Role.id, "/park/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Role.table = table;
});
