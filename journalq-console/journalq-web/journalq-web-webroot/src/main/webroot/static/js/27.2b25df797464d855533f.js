webpackJsonp([27],{"4CFe":function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=a("T0gc"),r=a("95hR"),i=a("1a0f"),s=a("toiR"),o={name:"task-executor-detail",components:{MyTable:n.a},mixins:[r.a],props:{doSearch:{type:Boolean,default:!1},executorId:{type:Number,default:-1},colData:{type:Array,default:function(){return[{title:"启动时间",key:"bootstrapTime",formatter:function(t){return Object(s.b)(t.bootstrapTime)}},{title:"主",key:"leader",render:function(t,e){if(void 0===e.item.leader||e.item.leader==={})return t("span","");var a=!1===e.item.leader?"否":"是";return t("DButton",{props:{size:"small",borderless:!0,color:!1===e.item.leader?"warning":"success"}},a)}},{title:"上次任务派发时间",key:"lastDispatchSuccessTime",formatter:function(t){return Object(s.b)(t.lastDispatchSuccessTime)}},{title:"上次派发任务数",key:"lastDispatchTaskAmount"},{title:"执行中任务数",key:"taskIds",formatter:function(t){return t.taskIds?t.taskIds.length:0}},{title:"最新任务开始时间",key:"lastTaskStartTime",formatter:function(t){return Object(s.b)(t.lastTaskStartTime)}},{title:"平均执行时间(ns)",key:"avgTaskExecuteTime"}]}}},data:function(){return{urls:{getMonitor:"/executor/monitor/"},tableData:{rowData:[],colData:this.colData}}},methods:{getList:function(){var t=this;this.showTablePin=!0;var e=this.executorId;i.a.getBase(this.urls.getMonitor+e,{},!1).then(function(e){e.data=e.data||[],t.tableData.rowData=[e.data.body],t.showTablePin=!1})}},mounted:function(){this.getList()}},c={render:function(){var t=this.$createElement,e=this._self._c||t;return e("div",[e("my-table",{attrs:{data:this.tableData,showPin:this.showTablePin,showPagination:!1,page:this.page},on:{"on-size-change":this.handleSizeChange,"on-current-change":this.handleCurrentChange}})],1)},staticRenderFns:[]};var l=a("VU/8")(o,c,!1,function(t){a("c+pO")},"data-v-8af10de8",null);e.default=l.exports},"c+pO":function(t,e){}});
//# sourceMappingURL=27.2b25df797464d855533f.js.map