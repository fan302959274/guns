/**
 * 球场详情对话框（可用于添加和修改对话框）
 */
var TeamInfoDlg = {
    teamInfoData: {},
    mutiString: '',
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '球队名称不能为空'
                },callback: {
                    message: '球队名称已存在',
                    callback: function (value) {
                        var id=$('#id').val();
                        var flag=true;
                        //提交信息
                        var ajax = new $ax(Feng.ctxPath + "/team/isExistTeamName", function(data){
                            flag=data;
                        },function(data){
                            Feng.error("添加失败!" + data.responseJSON.message + "!");
                        });
                        ajax.set({"name":value,"id":id});
                        ajax.start();
                        return flag;
                    }
                }
            }
        }
    }
};


/**
 * 验证数据是否为空
 */
TeamInfoDlg.validate = function () {
    $('#memberInfoForm').data("bootstrapValidator").resetForm();
    $('#memberInfoForm').bootstrapValidator('validate');
    return $("#memberInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 清除数据
 */
TeamInfoDlg.clearData = function () {
    this.teamInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TeamInfoDlg.set = function (key, val) {
    this.teamInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TeamInfoDlg.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
TeamInfoDlg.close = function () {
    parent.layer.close(window.parent.Team.layerIndex);
};

/**
 * 收集数据
 */
TeamInfoDlg.collectData = function () {
    this.set('id').set('name').set('startpoint').set('point').set('winnum') .set('drawnum') .set('debtnum')
        .set('teamdesc').set('logo');
};

/**
 * 提交修改球队
 */
TeamInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
     return;
     }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/team/update", function (data) {
        Feng.success("修改成功!");
        if (window.parent.Team != undefined) {
            window.parent.Team.table.refresh();
            TeamInfoDlg.close();
        }
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.teamInfoData);
    ajax.start();
};



$(function () {
     Feng.initValidator("teamInfoForm", TeamInfoDlg.validateFields);
    //初始化区选项
    $("#area").val($("#areaValue").val());
    // 初始化头像上传
    var avatarUp = new $WebUpload("logo");
    avatarUp.setUploadBarId("progressBar");
    avatarUp.init();
    $('#winnum').spinner({value:$('#winnumVal').val()});
    $('#debtnum').spinner({value:$('#debtnumVal').val()});
    $('#drawnum').spinner({value:$('#drawnumVal').val()});
});





