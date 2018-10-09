@/*
头像参数的说明:
name : 名称
id : 头像的id
@*/
<div class="form-group">
    <label class="col-sm-3 control-label head-scu-label">${name}</label>
    <div class="col-sm-8">
        <div id="${id}PreId">
            <div style="float:left;"><img width="100px" height="100px"
                                          @if(isEmpty(adsImg)){
                                          src="${ctxPath}/static/img/girl.gif"></div>
            @}else{
            src="${ctxPath}/kaptcha/${adsImg}">
        </div>
        @}
    </div>
    <div class="col-sm-1">
        <div class="head-scu-btn upload-btn" id="${id}BtnId">
            <i class="fa fa-upload"></i>&nbsp;添加
        </div>
    </div>
</div>

<input type="hidden" id="${id}" value="${avatarImg!}"/>
</div>

@if(isNotEmpty(underline) && underline == 'true'){
<div class="hr-line-dashed"></div>
@}


