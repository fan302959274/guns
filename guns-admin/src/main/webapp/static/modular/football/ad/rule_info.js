/**
 * 初始化规则详情对话框
 */
var RuleInfoDlg = {
    ruleInfoData: {},
    editor: null
};

/**
 * 清除数据
 */
RuleInfoDlg.clearData = function () {
    this.ruleInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RuleInfoDlg.set = function (key, val) {
    this.ruleInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RuleInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
RuleInfoDlg.close = function () {
    parent.layer.close(window.parent.Rule.layerIndex);
}

/**
 * 收集数据
 */
RuleInfoDlg.collectData = function () {
    this.ruleInfoData['content'] = RuleInfoDlg.editor.txt.html();
    this.set('id');
}

/**
 * 验证数据是否为空
 */
RuleInfoDlg.validate = function () {
    $('#ruleInfoForm').data("bootstrapValidator").resetForm();
    $('#ruleInfoForm').bootstrapValidator('validate');
    return $("#ruleInfoForm").data('bootstrapValidator').isValid();
};



/**
 * 提交修改
 */
RuleInfoDlg.editSubmit = function () {
    this.clearData();
    this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ad/updateRule", function (data) {
        Feng.success("修改成功!");
        window.parent.Rule.table.refresh();
        RuleInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ruleInfoData);
    ajax.start();
}

$(function () {
    Feng.initValidator("ruleInfoForm", RuleInfoDlg.validateFields);

    //初始化编辑器
    var E = window.wangEditor;
    var editor = new E('#editor');
    editor.create();
    editor.txt.html($("#contentVal").val());
    RuleInfoDlg.editor = editor;
});
