/**
 * 球场详情对话框（可用于添加和修改对话框）
 */
var ParkInfoDlg = {
    parkInfoData: {},
    mutiString: '',
    validateFields: {
        pkname: {
            validators: {
                notEmpty: {
                    message: '球场名称不能为空'
                }
            }
        },
        area: {
            validators: {
                notEmpty: {
                    message: '球场区域不能为空'
                }
            }
        },
        cost: {
            validators: {
                notEmpty: {
                    message: '球场费用不能为空'
                }
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
    this.set('id').set('pkname').set('area').set('pkdesc').set('imgs')
        .set('pkaddr').set('cost');
    var mutiString ='';
    $("[name='dictItem']").each(function(){
        var week = $(this).find("[name='week']").val();
        var start = $(this).find("[name='start']").val();
        var end = $(this).find("[name='end']").val();
        var data = week+'&'+start+'&'+end;
        if(mutiString){
            mutiString = mutiString + "," + data ;
        }else{
            mutiString=data;
        }
    });
    this.mutiString = mutiString;
};

// 验证数据是否为空

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
    ajax.set('usetime',this.mutiString);
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
    ajax.set('usetime',this.mutiString);
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
    //初始化区选项
    $("#area").val($("#areaValue").val());
    // 初始化头像上传
    var avatarUp = new $WebUploadAd("imgs");
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
var num = 1;
DictInfoDlg.addItem = function () {
var html='';
    html+='<div class="form-group" name="dictItem" id="dictItem">'+
        '<div class="col-sm-3" style="margin-left: 65px;">'+
        '<div class="col-sm-9">'+
        '<select class="form-control" id="week" name="week">'+
        '<option value="1">周一</option>'+
        '<option value="2">周二</option>'+
        '<option value="3">周三</option>'+
        '<option value="4">周四</option>'+
        '<option value="5">周五</option>'+
        '<option value="6">周六</option>'+
        '<option value="7">周日</option>'+
        '</select>'+
        '</div>'+
        '</div>'+
        '<div class="col-sm-8">'+
        '<div class="col-sm-4">'+
        '<input class="form-control" id="start'+num+'" name="start" type="text" />'+
        '</div>'+
        ' <div class="col-sm-4">'+
        '<input class="form-control" id="end'+num+'" name="end" type="text" />'+
        '</div>'+
        '</div>'+
        '<div class="col-sm-1" style="margin-left: -194px;">'+
        '<button type="button" class="btn btn-danger " onclick="DictInfoDlg.deleteItem(event)" id="cancel">'+
        '<i class="fa fa-remove"></i>&nbsp;删除'+
        '</button>'+
        '</div>'+
        '</div>';
    $("#itemsArea").append(html);
    laydate.render({
        elem: '#start'+num //指定元素
        ,type: 'time'
    });
    laydate.render({
        elem: '#end'+num //指定元素
        ,type: 'time'
    });
    num++;
};

/**
 * 删除item
 */
DictInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};
