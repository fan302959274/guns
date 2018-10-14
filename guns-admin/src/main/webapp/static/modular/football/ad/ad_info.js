/**
 * 初始化部门详情对话框
 */
var AdInfoDlg = {
    adInfoData : {},
    validateFields: {
        mainhead: {
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
AdInfoDlg.clearData = function() {
    this.adInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AdInfoDlg.set = function(key, val) {
    this.adInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AdInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AdInfoDlg.close = function() {
    parent.layer.close(window.parent.Ad.layerIndex);
}

/**
 * 收集数据
 */
AdInfoDlg.collectData = function() {
    this.set('id').set('mainhead').set('subhead').set('starttime').set('endtime').set('url').set('status').set('ads');
}

/**
 * 验证数据是否为空
 */
AdInfoDlg.validate = function () {
    $('#adInfoForm').data("bootstrapValidator").resetForm();
    $('#adInfoForm').bootstrapValidator('validate');
    return $("#adInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 提交添加部门
 */
AdInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ad/add", function(data){
        Feng.success("添加成功!");
        window.parent.Ad.table.refresh();
        AdInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.adInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AdInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ad/update", function(data){
        Feng.success("修改成功!");
        window.parent.Ad.table.refresh();
        AdInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.adInfoData);
    ajax.start();
}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "parentAdMenu" || $(
            event.target).parents("#parentAdMenu").length > 0)) {
        AdInfoDlg.hideAdSelectTree();
    }
}

$(function() {
    Feng.initValidator("adInfoForm", AdInfoDlg.validateFields);
    // 初始化头像上传
    var avatarUp = new $WebUploadAd("ads");
    avatarUp.init();
});
