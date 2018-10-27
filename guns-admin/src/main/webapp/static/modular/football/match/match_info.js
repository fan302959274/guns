/**
 * 初始化部门详情对话框
 */
var MatchInfoDlg = {
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
MatchInfoDlg.clearData = function () {
    this.adInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MatchInfoDlg.set = function (key, val) {
    this.adInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MatchInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MatchInfoDlg.close = function () {
    parent.layer.close(window.parent.Match.layerIndex);
}

/**
 * 收集数据
 */
MatchInfoDlg.collectData = function () {
    this.set('id').set('challengepaystatus').set('hostpaystatus');
}

/**
 * 验证数据是否为空
 */
MatchInfoDlg.validate = function () {
    $('#matchInfoForm').data("bootstrapValidator").resetForm();
    $('#matchInfoForm').bootstrapValidator('validate');
    return $("#matchInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 提交添加部门
 */
MatchInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/match/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Match.table.refresh();
        MatchInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.adInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MatchInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/match/update", function (data) {
        Feng.success("修改成功!");
        window.parent.Match.table.refresh();
        MatchInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.adInfoData);
    ajax.start();
}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "parentMatchMenu" || $(
            event.target).parents("#parentMatchMenu").length > 0)) {
        MatchInfoDlg.hideMatchSelectTree();
    }
}

$(function () {
    Feng.initValidator("matchInfoForm", MatchInfoDlg.validateFields);
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
