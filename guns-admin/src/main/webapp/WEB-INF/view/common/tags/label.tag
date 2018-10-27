@/*
表单中label框标签中各个参数的说明:
name : label框名称
style : 附加的css属性
@*/
<div class="form-group">
    <label class="col-sm-3 control-label">${name}</label>
    <label class="col-sm-9 control-label" style="text-align:left;"
    >
        ${value}
    </label>

</div>
@if(isNotEmpty(underline) && underline == 'true'){
<div class="hr-line-dashed"></div>
@}


