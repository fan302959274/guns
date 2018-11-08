@/*
表单中label框标签中各个参数的说明:
name : label框名称
style : 附加的css属性
@*/
<div class="form-group">
    <label class="col-sm-4 control-label"
       @if(isNotEmpty(style)){
       style="${style}"
       @}
    >${name}</label>
    <p class="col-sm-8 form-control-static">${value}</p>


</div>
@if(isNotEmpty(underline) && underline == 'true'){
<div class="hr-line-dashed"></div>
@}


