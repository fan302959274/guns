/**
 * 球队成员管理的单例
 */
var TeamMember = {
    id: "teamMemberTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    teamId:$('#teamId').val()
};

/**
 * 初始化表格的列
 */
TeamMember.initColumn = function () {
    var columns = [
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '头像', field: 'position', align: 'center' , valign: 'middle', sortable: true,
            formatter: function (value, row) {
                return  '<img  height="30" width="50"  src="'+'/kaptcha/'+row.position+'"/>';
            }
        },
        {title: '姓名', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '位置', field: 'position', align: 'center', valign: 'middle', sortable: true},
        {title: '状态', field: 'status', align: 'center', valign: 'middle', sortable: true},
        {title: '审核状态', field: 'cstauts', align: 'center', valign: 'middle', sortable: true},
        {title: '操作', field: 'cstauts', align: 'center', valign: 'middle', sortable: true,
            formatter: function (value, row) {
                return  '<a class="btn" href="javascript:deleteMember('+row.id+')" >删除</a>';
            }
        }
    ]
    return columns;
};


function  deleteMember(id) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/team/removeMember", function () {
                Feng.success("删除队员成功!");
                TeamMember.table.refresh();
            }, function (data) {
                Feng.error("删除队员失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", id);
            ajax.start();
        };
        Feng.confirm("是否删除该队员?",operation);
};
/**
 * 检查是否选中
 */
TeamMember.check = function () {

    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Role.seItem = selected[0];
        return true;
    }
};

$(function () {
    var defaultColunms = TeamMember.initColumn();
    table
    var table = new BSTable(TeamMember.id, "/team/memberList/"+TeamMember.teamId, defaultColunms);
    table.setPaginationType("client");
    table.init();
    TeamMember.table = table;
});

