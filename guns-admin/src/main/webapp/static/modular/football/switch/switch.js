$(function () {

    $('#sms-switch').bootstrapSwitch({
        "onColor": "success",
        "offColor": "danger",
        "onText": "开",
        "offText": "关",
        "onSwitchChange": function (event, state) {
            var ProductId = event.target.defaultValue;

            var ajax = new $ax(Feng.ctxPath + "/switch/switchs", function () {
                Feng.success("操作成功!");
                Park.table.refresh();
            }, function (data) {
                Feng.error("操作失败!" + data.responseJSON.message + "!");
            });
            ajax.set("category", "SMS");
            ajax.set("optype", state);
            ajax.start();
        }

    });

    // $('#my-checkbox2').bootstrapSwitch({
    //     "onColor": "success",
    //     "offColor": "danger",
    //     "onText": "开",
    //     "offText": "关"
    // });
});
