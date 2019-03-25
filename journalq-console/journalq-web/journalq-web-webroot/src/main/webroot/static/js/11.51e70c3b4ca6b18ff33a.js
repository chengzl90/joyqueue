webpackJsonp([11,34],{"0DWO":function(t,e){},Ftsv:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=a("lcoF"),r=a("M3bc"),i={name:"audit-flow-form",mixins:[o.a],props:{type:0,data:{type:Object,default:function(){return{id:0,type:"",manualExecute:!0,applicantConfirm:!0,executeClass:"",executeMethod:"",description:"",status:1,auditNodes:[{nodeNo:0,auditRoles:"ADMIN",containAdmin:!0}]}}}},data:function(){var t=this;return{formData:this.data||{},urls:{},rules:{type:[{required:!0,message:"请输入类型",trigger:"change"},{pattern:/^[A-Z]+[A-Z_]{1,60}[A-Z]+$/,message:"格式不匹配",trigger:"change"}],executeClass:[{validator:function(e,a,o){return t.formData.manualExecute?t.formData.executeClass&&t.formData.executeClass.trim()?(t.formData.executeClass=t.formData.executeClass.trim(),o()):o(new Error("请输入手动执行类")):o()},trigger:"blur"}],description:[{max:512,message:"长度在 512 个字符以内",trigger:"blur"}],auditNodes:[{validator:function(t,e,a){var o=e.auditRoles;if(!o||!o.trim())return a(new Error("请输入审批角色Code，多个以英文逗号隔开"));if(null==o.match(/^[a-zA-Z]{3,50}(,[a-zA-Z]{3,50})*$/))return a(new Error("审批角色格式不匹配"));for(var r=o.trim().split(","),i=0;i<r.length;i++)"ADMIN"===r[i].toUpperCase()&&r.splice(i,1);return e.containAdmin&&r.push("ADMIN"),e.auditRoles=r.join(","),a()},trigger:"blur"}]},error:{auditNodes:[]}}},methods:{handlerManualExecuteChange:function(t){t||(this.formData.executeClass="",this.formData.executeMethod="")},addAuditNode:function(){this.formData.auditNodes.push({nodeNo:this.formData.auditNodes.length,auditRoles:"ADMIN",containAdmin:!0})},removeAuditNode:function(){var t=this.formData.auditNodes.nodeNo;-1!==t&&this.formData.auditNodes.splice(t,1)},handleAuditRolesChange:function(t){},getFormData:function(){for(var t=0;t<this.formData.auditNodes.length;t++){this.formData.auditNodes[t].nodeNo=t}return Object(r.e)(this.formData||{})}},mounted:function(){}},n={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("d-form",{ref:"form",staticStyle:{height:"370px","overflow-y":"auto",width:"100%","padding-right":"50px"},attrs:{model:t.formData,rules:t.rules,"label-width":"100px"}},[a("d-form-item",{attrs:{label:"类型:",prop:"type"}},[a("d-input",{attrs:{placeholder:"请输入用户类型，仅支持英文大写和-"},model:{value:t.formData.type,callback:function(e){t.$set(t.formData,"type",e)},expression:"formData.type"}})],1),t._v(" "),a("d-form-item",{attrs:{label:"用户确认:",prop:"applicantConfirm"}},[a("d-switch",{model:{value:t.formData.applicantConfirm,callback:function(e){t.$set(t.formData,"applicantConfirm",e)},expression:"formData.applicantConfirm"}})],1),t._v(" "),a("d-form-item",{attrs:{label:"手动执行:",prop:"manualExecute"},on:{change:t.handlerManualExecuteChange}},[a("d-switch",{model:{value:t.formData.manualExecute,callback:function(e){t.$set(t.formData,"manualExecute",e)},expression:"formData.manualExecute"}})],1),t._v(" "),t.formData.manualExecute?a("div",[a("d-form-item",{attrs:{label:"手动执行类:",prop:"executeClass"}},[a("d-input",{attrs:{placeholder:"请输入手动执行调用的service，英文大小写格式"},model:{value:t.formData.executeClass,callback:function(e){t.$set(t.formData,"executeClass",e)},expression:"formData.executeClass"}})],1),t._v(" "),a("d-form-item",{attrs:{label:"手动执行方法:",prop:"executeMethod"}},[a("d-input",{attrs:{placeholder:"请输入，默认为execute"},model:{value:t.formData.executeMethod,callback:function(e){t.$set(t.formData,"executeMethod",e)},expression:"formData.executeMethod"}})],1)],1):t._e(),t._v(" "),a("d-form-item",{attrs:{label:"备注:",prop:"description"}},[a("d-input",{attrs:{type:"textarea",rows:"2",placeholder:"请输入备注"},model:{value:t.formData.description,callback:function(e){t.$set(t.formData,"description",e)},expression:"formData.description"}})],1),t._v(" "),t._l(t.formData.auditNodes,function(e,o){return a("d-form-item",{key:e.nodeNo,attrs:{label:"节点"+o,error:t.error.auditNodes[o],required:!0,prop:"auditNodes."+o,rules:t.rules.auditNodes}},[a("d-input",{attrs:{type:"textarea",rows:"2",placeholder:"请输入审批角色code,仅支持大小写字母，多个之间以英文逗号隔开"},on:{change:t.handleAuditRolesChange},model:{value:e.auditRoles,callback:function(a){t.$set(e,"auditRoles",a)},expression:"auditNode.auditRoles"}}),t._v(" "),a("span",[t._v("授权管理员：")]),a("d-switch",{model:{value:e.containAdmin,callback:function(a){t.$set(e,"containAdmin",a)},expression:"auditNode.containAdmin"}}),t._v(" "),a("d-button",{on:{click:function(a){return a.preventDefault(),t.addAuditNode(e)}}},[t._v("添加")]),t._v(" "),a("d-button",{on:{click:function(a){return a.preventDefault(),t.removeAuditNode(e)}}},[t._v("删除")])],1)})],2)],1)},staticRenderFns:[]};var d=a("VU/8")(i,n,!1,function(t){a("0DWO")},"data-v-62b6fbef",null);e.default=d.exports},jHcK:function(t,e){},jZn3:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var o=a("T0gc"),r=a("fo4W"),i=a("Ftsv"),n=a("95hR"),d={name:"audit-flow",components:{myTable:o.a,myDialog:r.a,auditFlowForm:i.default},mixins:[n.a],data:function(){return{searchData:{keyword:""},tableData:{rowData:[],colData:[{title:"ID",key:"id"},{title:"申请类型",key:"type"},{title:"是否用户确认",key:"applicantConfirm",formatter:function(t){return t?"是":"否"}},{title:"是否手动执行",key:"manualExecute",formatter:function(t){return t?"是":"否"}},{title:"手动执行类",key:"executeClass"},{title:"手动执行方法",key:"executeMethod"},{title:"描述",key:"description"}],btns:[{txt:"编辑",method:"on-edit"},{txt:"删除",method:"on-del"}]},addDialog:{visible:!1,title:"新建审批角色",showFooter:!0,scrollable:!0,width:560},addData:{},editDialog:{visible:!1,title:"编辑审批角色",showFooter:!0,width:560},editData:{}}},methods:{openDialog:function(t){this[t].visible=!0},beforeEditData:function(t){return this.editData=t,void 0!==this.editData.auditNodes&&this.editData.auditNodes.sort(function(t,e){return t.nodeNo-e.nodeNo}),this.editData}},mounted:function(){this.getList()}},l={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("div",{staticClass:"ml20 mt30"},[a("d-input",{staticClass:"left mr10",staticStyle:{width:"20%"},attrs:{placeholder:"请输入代码/名称"},model:{value:t.searchData.keyword,callback:function(e){t.$set(t.searchData,"keyword",e)},expression:"searchData.keyword"}},[a("icon",{attrs:{slot:"suffix",name:"search",size:"14",color:"#CACACA"},on:{click:t.getList},slot:"suffix"})],1),t._v(" "),a("d-button",{attrs:{type:"primary"},on:{click:function(e){return t.openDialog("addDialog","addForm")}}},[t._v("新建流程角色"),a("icon",{staticStyle:{"margin-left":"5px"},attrs:{name:"plus-circle"}})],1)],1),t._v(" "),a("my-table",{attrs:{data:t.tableData,showPin:t.showTablePin,page:t.page},on:{"on-size-change":t.handleSizeChange,"on-current-change":t.handleCurrentChange,"on-selection-change":t.handleSelectionChange,"on-edit":t.edit,"on-del":t.del}}),t._v(" "),a("my-dialog",{attrs:{dialog:t.addDialog},on:{"on-dialog-confirm":function(e){return t.addConfirm()},"on-dialog-cancel":function(e){return t.dialogCancel("addDialog")}}},[a("audit-flow-form",{ref:"addForm",attrs:{type:t.$store.getters.addFormType}})],1),t._v(" "),a("my-dialog",{attrs:{dialog:t.editDialog},on:{"on-dialog-confirm":function(e){return t.editConfirm()},"on-dialog-cancel":function(e){return t.dialogCancel("editDialog")}}},[a("audit-flow-form",{ref:"editForm",attrs:{data:t.editData,type:t.$store.getters.editFormType}})],1)],1)},staticRenderFns:[]};var s=a("VU/8")(d,l,!1,function(t){a("jHcK")},"data-v-7676eeff",null);e.default=s.exports}});
//# sourceMappingURL=11.51e70c3b4ca6b18ff33a.js.map