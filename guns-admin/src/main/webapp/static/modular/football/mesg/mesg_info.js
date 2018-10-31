/**
 * 球场详情对话框（可用于添加和修改对话框）
 */
var MesgInfoDlg = {
    mesgInfoData: {}
};


/**
 * 清除数据
 */
MesgInfoDlg.clearData = function () {
    this.mesgInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MesgInfoDlg.set = function (key, val) {
    this.mesgInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MesgInfoDlg.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
MesgInfoDlg.close = function () {
    parent.layer.close(window.parent.Mesg.layerIndex);
};

/**
 * 收集数据
 */
MesgInfoDlg.collectData = function () {
    this.set('id').set('mesgname').set('objtype').set('content');
};

/**
 * 提交修改球队
 */
MesgInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/mesg/edit", function (data) {
        Feng.success("修改成功!");
        if (window.parent.Mesg != undefined) {
            window.parent.Mesg.table.refresh();
            MesgInfoDlg.close();
        }
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.mesgInfoData);
    ajax.start();
};

MesgInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/mesg/add", function(data){
        Feng.success("添加成功!");
        window.parent.Mesg.table.refresh();
        MesgInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.mesgInfoData);
    ajax.start();
}

$(function () {
    // Feng.initValidator("parkInfoForm", ParkInfoDlg.validateFields);
  $('#objtype').val($('#objtypeValue').val());
});





