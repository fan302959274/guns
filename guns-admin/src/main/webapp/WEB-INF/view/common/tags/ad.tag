@/*
头像参数的说明:
name : 名称
id : 头像的id
@*/
<div class="form-group">
    <label class="col-sm-2 control-label head-scu-label" style="width: 80px;">${name}</label>
    <div class="col-sm-8">
        <div id="${id}PreId">
            @if(isEmpty(ads)){
            <div style="float:left;text-align: right;height:120px;" class="file-item thumbnail draggable-element"><img
                    width="100px" height="100px"

                    src="${ctxPath}/static/img/girl.gif"></div>
            @}else{
            @for(item in adsImg){
            <div id="${item.id}" style="position:relative;float:left;" class="file-item thumbnail draggable-element"><a
                    class="file-panel" style="position:absolute;top:10px;right:10px;" href="javascript:;"><span
                    class="fa fa-close"></span></a><img
                    width="100px" height="100px" value="${item.url}"
                    src="${ctxPath}/kaptcha/${item.url}">
            </div>
            @}
            @}
        </div>
        <div class="col-sm-1">
            <div class="head-scu-btn upload-btn" id="${id}BtnId">
                <i class="fa fa-upload"></i>&nbsp;添加
            </div>
        </div>
    </div>

    <input type="hidden" id="${id}" value="${ads!}"/>
</div>

@if(isNotEmpty(underline) && underline == 'true'){
<div class="hr-line-dashed"></div>
@}


