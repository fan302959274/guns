/**
 * 球场详情对话框（可用于添加和修改对话框）
 */
var ParkInfoDlg = {
    parkInfoData: {},
    validateFields: {
        account: {
            validators: {
                notEmpty: {
                    message: '账户不能为空'
                }
            }
        },
        name: {
            validators: {
                notEmpty: {
                    message: '姓名不能为空'
                }
            }
        },
        citySel: {
            validators: {
                notEmpty: {
                    message: '部门不能为空'
                }
            }
        },
        password: {
            validators: {
                notEmpty: {
                    message: '密码不能为空'
                },
                identical: {
                    field: 'rePassword',
                    message: '两次密码不一致'
                },
            }
        },
        rePassword: {
            validators: {
                notEmpty: {
                    message: '密码不能为空'
                },
                identical: {
                    field: 'password',
                    message: '两次密码不一致'
                },
            }
        }
    }
};

/**
 * 清除数据
 */
ParkInfoDlg.clearData = function () {
    this.parkInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ParkInfoDlg.set = function (key, val) {
    this.parkInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ParkInfoDlg.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
ParkInfoDlg.close = function () {
    parent.layer.close(window.parent.Park.layerIndex);
};

/**
 * 收集数据
 */
ParkInfoDlg.collectData = function () {
    this.set('id').set('pkname').set('area').set('pkdesc').set('avatar')
        .set('pkaddr');
};

/**
 * 验证数据是否为空
 */
ParkInfoDlg.validate = function () {
    $('#parkInfoForm').data("bootstrapValidator").resetForm();
    $('#parkInfoForm').bootstrapValidator('validate');
    return $("#parkInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加球场
 */
ParkInfoDlg.addSubmit = function () {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/park/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Park.table.refresh();
        ParkInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.parkInfoData);
    ajax.start();
};

/**
 * 提交修改球场
 */
ParkInfoDlg.editSubmit = function () {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/park/edit", function (data) {
        Feng.success("修改成功!");
        if (window.parent.Park != undefined) {
            window.parent.Park.table.refresh();
            ParkInfoDlg.close();
        }
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.parkInfoData);
    ajax.start();
};

/**
 * 修改密码
 */
ParkInfoDlg.chPwd = function () {
    var ajax = new $ax(Feng.ctxPath + "/mgr/changePwd", function (data) {
        Feng.success("修改成功!");
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set("oldPwd");
    ajax.set("newPwd");
    ajax.set("rePwd");
    ajax.start();

};

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
            event.target).parents("#menuContent").length > 0)) {
        UserInfoDlg.hideDeptSelectTree();
    }
}

$(function () {
    Feng.initValidator("parkInfoForm", ParkInfoDlg.validateFields);
    //初始化性别选项
    $("#sex").val($("#sexValue").val());
    // 初始化头像上传
    var avatarUp = new $WebUpload("ads");
    avatarUp.setUploadBarId("progressBar");
    avatarUp.init();
});





/**
 * 初始化字典详情对话框
 */
var DictInfoDlg = {
    count: 1,
    dictName: '',	    //字典的名称
    mutiString: '',		//拼接字符串内容(拼接字典条目)
    itemTemplate: $("#itemTemplate").html()
};

/**
 * 添加条目
 */
DictInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
};

/**
 * 删除item
 */
DictInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};
