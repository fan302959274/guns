/**
 * 球场管理的单例
 */
var Park = {
    id: "parkTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Park.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '球场名称', field: 'pkname', align: 'center', valign: 'middle', sortable: true},
        {title: '球场区域', field: 'area', align: 'center', valign: 'middle', sortable: true},
        {title: '球场地址', field: 'pkaddr', align: 'center', valign: 'middle', sortable: true},
        {title: '球场描述', field: 'pkdesc', align: 'center', valign: 'middle', sortable: true},
        {title: '球场状态', field: 'status', align: 'center', valign: 'middle', sortable: true}
    ]
    return columns;
};


/**
 * 检查是否选中
 */
Park.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Park.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加球场信息
 */
Park.openAddPark = function () {
    var index = layer.open({
        type: 2,
        title: '添加球场',
        area: ['900px', '650px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/park/park_add'
    });
    this.layerIndex = index;
};

/**
 * 点击修改按钮时
 */
Park.openEditPark = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改球场',
            area: ['900px', '650px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/park/park_edit/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除球场
 */
Park.delPark = function () {
    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/park/remove", function () {
                Feng.success("删除成功!");
                Park.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("parkId", Park.seItem.id);
            ajax.start();
        };
        Feng.confirm("是否删除球场 " + Park.seItem.pkname + "?",operation);
    }
};

/**
 * 搜索角色
 */
Park.search = function () {
    var queryData = {};
    queryData['parkname'] = $("#parkname").val();
    Park.table.refresh({query: queryData});
}

$(function () {
    var defaultColunms = Park.initColumn();
    var table = new BSTable(Park.id, "/park/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Park.table = table;
});


/**
 * 禁用球场
 * @param parkId
 */
Park.freezePark = function () {
    if (this.check()) {
        var parkId = this.seItem.id;
        var ajax = new $ax(Feng.ctxPath + "/park/freeze", function (data) {
            Feng.success("禁用成功!");
            Park.table.refresh();
        }, function (data) {
            Feng.error("禁用失败!" + data.responseJSON.message + "!");
        });
        ajax.set("parkId", parkId);
        ajax.start();
    }
};

/**
 * 解除球场
 * @param parkId
 */
Park.unfreezePark = function () {
    if (this.check()) {
        var parkId = this.seItem.id;
        var ajax = new $ax(Feng.ctxPath + "/park/unfreeze", function (data) {
            Feng.success("解除禁用成功!");
            Park.table.refresh();
        }, function (data) {
            Feng.error("解除禁用失败!");
        });
        ajax.set("parkId", parkId);
        ajax.start();
    }
}