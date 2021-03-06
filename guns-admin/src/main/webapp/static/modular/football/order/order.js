/**
 * 广告管理初始化
 */
var Order = {
    id: "orderTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Order.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id',visible: false},
        {title: '比赛订单号', field: 'no', align: 'center', valign: 'middle', width: '50px'},
        {title: '发起方', field: 'hostteam', align: 'center', valign: 'middle', width: '200px',sortable: true},
        {title: '匹配方', field: 'challengeteam', align: 'center', valign: 'middle', width: '200px',sortable: true},
        {title: '球场区域', field: 'area', align: 'center', valign: 'middle',width: '200px', sortable: true},
        {title: '球场名', field: 'parkname', align: 'center', valign: 'middle',width: '200px', sortable: true},
        {title: '比赛状态', field: 'status', align: 'center', valign: 'middle',width: '200px', sortable: true},
        {title: '比赛日期', field: 'date', align: 'center', valign: 'middle',width: '200px', sortable: true},
        {title: '比赛时间', field: 'time', align: 'center', valign: 'middle',width: '200px', sortable: true},
        {title: '支付状态', field: 'paystatus', align: 'center', valign: 'middle',width: '200px', sortable: true}];
};

/**
 * 检查是否选中
 */
Order.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Order.seItem = selected[0];
        return true;
    }
};




/**
 * 打开查看广告详情
 */
Order.openOrderView = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '比赛详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/order/order_view/' + Order.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除广告
 */
Order.delete = function () {
    if (this.check()) {

        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/order/delete", function () {
                Feng.success("删除成功!");
                Order.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("orderId", Order.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否刪除该比赛?", operation);
    }
};

/**
 * 查询比赛列表
 */
Order.search = function () {
    var queryData = {};
    queryData['areas'] = $("#areas").val();
    queryData['parks'] = $("#parks").val();
    queryData['pkstatus'] = $("#pkstatus").val();
    queryData['hostname'] = $("#hostname").val();
    queryData['no'] = $("#no").val();
    Order.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Order.initColumn();
    var table = new BSTable(Order.id, "/order/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Order.table = table;
});
