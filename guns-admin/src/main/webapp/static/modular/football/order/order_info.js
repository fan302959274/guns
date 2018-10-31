/**
 * 初始化部门详情对话框
 */
var OrderInfoDlg = {
    adInfoData: {},
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '广告标题不能为空'
                }
            }
        },
        subhead: {
            validators: {
                notEmpty: {
                    message: '广告副标题不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
OrderInfoDlg.clearData = function () {
    this.adInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
OrderInfoDlg.set = function (key, val) {
    this.adInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
OrderInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
OrderInfoDlg.close = function () {
    parent.layer.close(window.parent.Order.layerIndex);
}

/**
 * 收集数据
 */
OrderInfoDlg.collectData = function () {
    this.set('id').set('areas').set('teamid').set('teamid').set('no');
}

/**
 * 验证数据是否为空
 */
OrderInfoDlg.validate = function () {
    $('#orderInfoForm').data("bootstrapValidator").resetForm();
    $('#orderInfoForm').bootstrapValidator('validate');
    return $("#orderInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 提交添加部门
 */
OrderInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/order/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Order.table.refresh();
        OrderInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.adInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
OrderInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/order/update", function (data) {
        Feng.success("修改成功!");
        window.parent.Order.table.refresh();
        OrderInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.adInfoData);
    ajax.start();
}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "parentOrderMenu" || $(
            event.target).parents("#parentOrderMenu").length > 0)) {
        OrderInfoDlg.hideOrderSelectTree();
    }
}

$(function () {
    Feng.initValidator("orderInfoForm", OrderInfoDlg.validateFields);
    //初始化是否支付菜单
    if ($("#challengepaystatusVal").val() == undefined) {
        $("#challengepaystatus").val(0);
    } else {
        $("#challengepaystatus").val($("#challengepaystatusVal").val());
    }

    if ($("#hostpaystatusVal").val() == undefined) {
        $("#hostpaystatus").val(0);
    } else {
        $("#hostpaystatus").val($("#hostpaystatusVal").val());
    }
});
