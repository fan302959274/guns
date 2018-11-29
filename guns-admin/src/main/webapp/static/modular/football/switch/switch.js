$(function () {

    $('#sms-switch').bootstrapSwitch({
        "onColor": "success",
        "offColor": "danger",
        "onText": "开",
        "offText": "关",
        "onSwitchChange": function (event, state) {
            var ProductId = event.target.defaultValue;
            if (state == true) {
                alert("ON");
            } else {
                alert("OFF");
            }
        }

    });

    $('#my-checkbox2').bootstrapSwitch({
        "onColor": "success",
        "offColor": "danger",
        "onText": "开",
        "offText": "关"
    });
});
