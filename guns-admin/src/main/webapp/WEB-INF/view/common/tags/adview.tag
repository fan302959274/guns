@/*
头像参数的说明:
name : 名称
id : 头像的id
@*/
<div class="form-group">
    <label class="col-sm-3 control-label" >${name}</label>
    <div class="col-sm-9 control-label">
        <div id="${id}PreId">
            @if(isEmpty(ads)){
            <div style="float:left;text-align: right;height:120px;" class="file-item thumbnail draggable-element"><img
                    width="100px" height="100px"

                    src="${ctxPath}/static/img/girl.gif"></div>
            @}else{
            @for(item in adsImg){
            <div id="${item.id}" style="float:left;text-align: right;height:120px;"
                 class="file-item  draggable-element"><img
                    width="100px" height="100px" value="${item.url}"
                    src="${ctxPath}/kaptcha/${item.url}">
            </div>
            @}
            @}
        </div>

    </div>

    <input type="hidden" id="${id}" value="${ads!}"/>
</div>

@if(isNotEmpty(underline) && underline == 'true'){
<div class="hr-line-dashed"></div>
@}


