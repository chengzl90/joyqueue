webpackJsonp([18,39],{"+3vO":function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var r=a("T0gc"),o=a("fo4W"),i=a("95hR"),n=a("ucPn"),l=a("M3bc"),d={name:"metric",components:{myTable:r.a,myDialog:o.a,metricForm:n.default},mixins:[i.a],data:function(){return{searchData:{keyword:"",status:1},searchRules:{},tableData:{rowData:[],colData:[{title:"ID",key:"id"},{title:"代码",key:"code"},{title:"别名",key:"aliasCode"},{title:"名称",key:"name"},{title:"类型",key:"type",formatter:function(t){return"ATOMIC"===t.type?"原子":"聚集"}},{title:"指标来源",key:"source"}],btns:[{txt:"编辑",method:"on-edit"},{txt:"删除",method:"on-del"}]},addDialog:{visible:!1,title:"新建指标",showFooter:!0},addData:{},editData:{},editDialog:{visible:!1,title:"编辑指标",showFooter:!0}}},methods:{openDialog:function(t){this[t].visible=!0},beforeEditData:function(t){var e=Object(l.e)(t);return void 0===e.groupField||""===e.groupField?e.groupFieldArray=[]:e.groupFieldArray=e.groupField.split(","),this.editData=e,this.editData}},mounted:function(){this.getList()}},s={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("div",{staticClass:"ml20 mt30"},[a("d-input",{staticClass:"left mr10",staticStyle:{width:"20%"},attrs:{placeholder:"请输入代码/名称/来源"},model:{value:t.searchData.keyword,callback:function(e){t.$set(t.searchData,"keyword",e)},expression:"searchData.keyword"}}),t._v(" "),a("d-button",{attrs:{type:"primary"},on:{click:t.getList}},[t._v("查询"),a("icon",{staticStyle:{"margin-left":"5px"},attrs:{name:"search"}})],1),t._v(" "),a("d-button",{attrs:{type:"primary"},on:{click:function(e){return t.openDialog("addDialog")}}},[t._v("新建指标"),a("icon",{staticStyle:{"margin-left":"5px"},attrs:{name:"plus-circle"}})],1)],1),t._v(" "),a("my-table",{attrs:{data:t.tableData,showPin:t.showTablePin,page:t.page},on:{"on-size-change":t.handleSizeChange,"on-current-change":t.handleCurrentChange,"on-selection-change":t.handleSelectionChange,"on-edit":t.edit,"on-del":t.del}}),t._v(" "),a("my-dialog",{attrs:{dialog:t.addDialog},on:{"on-dialog-confirm":function(e){return t.addConfirm()},"on-dialog-cancel":function(e){return t.dialogCancel("addDialog")}}},[a("metric-form",{ref:"addForm",attrs:{type:t.$store.getters.addFormType}})],1),t._v(" "),a("my-dialog",{attrs:{dialog:t.editDialog},on:{"on-dialog-confirm":function(e){return t.editConfirm()},"on-dialog-cancel":function(e){return t.dialogCancel("editDialog")}}},[a("metric-form",{ref:"editForm",attrs:{data:t.editData,type:t.$store.getters.editFormType}})],1)],1)},staticRenderFns:[]};var c=a("VU/8")(d,s,!1,function(t){a("2FAN")},"data-v-1e3913c0",null);e.default=c.exports},"2FAN":function(t,e){},jGCZ:function(t,e){},ucPn:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var r={name:"metric-form",mixins:[a("lcoF").a],props:{type:0,data:{type:Object,default:function(){return{code:"",aliasCode:"",name:"",type:0,source:"",aggregator:"",groupField:"",groupFieldArray:[],summaryLevel:""}}}},methods:{getFormData:function(){return void 0===this.formData.groupFieldArray||this.formData.groupFieldArray===[]?this.formData.groupField="":this.formData.groupField=this.formData.groupFieldArray.join(","),this.formData}},data:function(){return{formData:this.data,rules:{code:[{required:!0,message:"请填写代码",trigger:"change"}],aliasCode:[{required:!0,message:"请填写别名",trigger:"change"}],name:[{required:!0,message:"请填写名称",trigger:"change"}],type:[{required:!0,message:"请选类型",trigger:"change"}],source:[{required:!0,message:"请填写来源代码",trigger:"change"}]}}}},o={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("d-form",{ref:"form",staticStyle:{height:"350px","overflow-y":"auto",width:"100%","padding-right":"20px"},attrs:{model:t.formData,rules:t.rules,"label-width":"100px"}},[a("d-form-item",{attrs:{label:"代码:",prop:"code"}},[a("d-input",{staticStyle:{width:"60%"},attrs:{placeholder:"如app.connections"},model:{value:t.formData.code,callback:function(e){t.$set(t.formData,"code",e)},expression:"formData.code"}})],1),t._v(" "),a("d-form-item",{attrs:{label:"别名:",prop:"aliasCode"}},[a("d-input",{staticStyle:{width:"60%"},attrs:{placeholder:"connection"},model:{value:t.formData.aliasCode,callback:function(e){t.$set(t.formData,"aliasCode",e)},expression:"formData.aliasCode"}})],1),t._v(" "),a("d-form-item",{attrs:{label:"名称:",prop:"name"}},[a("d-input",{staticStyle:{width:"60%"},attrs:{placeholder:"如app连接数"},model:{value:t.formData.name,callback:function(e){t.$set(t.formData,"name",e)},expression:"formData.name"}})],1),t._v(" "),a("d-form-item",{attrs:{label:"类型:",prop:"type"}},[a("d-select",{staticStyle:{width:"60%"},attrs:{placeholder:"类型",value:"0"},model:{value:t.formData.type,callback:function(e){t.$set(t.formData,"type",e)},expression:"formData.type"}},[a("d-option",{attrs:{value:"1"}},[t._v("原子")]),t._v(" "),a("d-option",{attrs:{value:"2"}},[t._v("聚集")])],1)],1),t._v(" "),a("d-form-item",{attrs:{label:"来源:",prop:"source"}},[a("d-input",{staticStyle:{width:"60%"},attrs:{placeholder:"如agent.collector"},model:{value:t.formData.source,callback:function(e){t.$set(t.formData,"source",e)},expression:"formData.source"}})],1),t._v(" "),a("d-form-item",{attrs:{label:"描述:"}},[a("d-input",{staticStyle:{width:"60%"},attrs:{placeholder:"描述"},model:{value:t.formData.description,callback:function(e){t.$set(t.formData,"description",e)},expression:"formData.description"}})],1)],1)},staticRenderFns:[]};var i=a("VU/8")(r,o,!1,function(t){a("jGCZ")},"data-v-4e44402c",null);e.default=i.exports}});
//# sourceMappingURL=18.36455ba0749292eb5cfe.js.map