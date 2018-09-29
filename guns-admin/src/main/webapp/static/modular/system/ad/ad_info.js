/**
 * 初始化部门详情对话框
 */
var AdInfoDlg = {
    adInfoData : {},
    zTreeInstance : null,
    validateFields: {
        simplename: {
            validators: {
                notEmpty: {
                    message: '部门名称不能为空'
                }
            }
        },
        fullname: {
            validators: {
                notEmpty: {
                    message: '部门全称不能为空'
                }
            }
        },
        pName: {
            validators: {
                notEmpty: {
                    message: '上级名称不能为空'
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
 * 点击部门ztree列表的选项时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
AdInfoDlg.onClickAd = function(e, treeId, treeNode) {
    $("#pName").attr("value", AdInfoDlg.zTreeInstance.getSelectedVal());
    $("#pid").attr("value", treeNode.id);
}

/**
 * 显示部门选择的树
 *
 * @returns
 */
AdInfoDlg.showAdSelectTree = function() {
    var pName = $("#pName");
    var pNameOffset = $("#pName").offset();
    $("#parentAdMenu").css({
        left : pNameOffset.left + "px",
        top : pNameOffset.top + pName.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
}

/**
 * 隐藏部门选择的树
 */
AdInfoDlg.hideAdSelectTree = function() {
    $("#parentAdMenu").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
}

/**
 * 收集数据
 */
AdInfoDlg.collectData = function() {
    this.set('id').set('simplename').set('fullname').set('tips').set('num').set('pid');
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

    var ztree = new $ZTree("parentAdMenuTree", "/ad/tree");
    ztree.bindOnClick(AdInfoDlg.onClickAd);
    ztree.init();
    AdInfoDlg.zTreeInstance = ztree;
});
