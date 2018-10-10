/**
 * 初始化部门详情对话框
 */
var MemberInfoDlg = {
    adInfoData : {},
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
MemberInfoDlg.clearData = function() {
    this.adInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberInfoDlg.set = function(key, val) {
    this.adInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MemberInfoDlg.close = function() {
    parent.layer.close(window.parent.Member.layerIndex);
}

/**
 * 收集数据
 */
MemberInfoDlg.collectData = function() {
    this.set('id').set('account').set('name').set('birth').set('height').set('weight').set('position').set('habitfeet').set('type').set('avatar').set('idcard');
}

/**
 * 验证数据是否为空
 */
MemberInfoDlg.validate = function () {
    $('#memberInfoForm').data("bootstrapValidator").resetForm();
    $('#memberInfoForm').bootstrapValidator('validate');
    return $("#memberInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 提交添加部门
 */
MemberInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/member/add", function(data){
        Feng.success("添加成功!");
        window.parent.Member.table.refresh();
        MemberInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.adInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MemberInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/member/update", function(data){
        Feng.success("修改成功!");
        window.parent.Member.table.refresh();
        MemberInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.adInfoData);
    ajax.start();
}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "parentMemberMenu" || $(
            event.target).parents("#parentMemberMenu").length > 0)) {
        MemberInfoDlg.hideMemberSelectTree();
    }
}

$(function() {
    Feng.initValidator("memberInfoForm", MemberInfoDlg.validateFields);
    // 初始化头像上传
    var avatarUp = new $WebUpload("avatar");
    avatarUp.init();

    // 初始化身份证照上传
    var idcardUp = new $WebUpload("idcard");
    idcardUp.init();

    // 初始化球队logo上传
    var teamlogoUp = new $WebUpload("teamlogo");
    teamlogoUp.init();


});
