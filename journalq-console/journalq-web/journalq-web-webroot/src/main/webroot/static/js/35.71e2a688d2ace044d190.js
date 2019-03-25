webpackJsonp([35],{Il3R:function(e,r){},"zHY+":function(e,r,t){"use strict";Object.defineProperty(r,"__esModule",{value:!0});var a=t("lcoF"),o=t("X2Oc"),s={name:"audit-role-form",mixins:[a.a],props:{type:0,data:{type:Object,default:function(){return{id:0,code:"",name:"",sql:"",userCodes:"",description:"",status:1}}}},data:function(){var e=this;return{valueSetting:"SQL",formData:this.data||{},urls:{},rules:{code:[{required:!0,message:"请输入英文名",trigger:"change"},{pattern:/^[a-zA-Z]{3,50}$/,message:"英文名格式不匹配",trigger:"change"}],name:Object(o.h)(),sql:[{validator:function(r,t,a){return"USER"===e.valueSetting?(e.formData.sql="",a()):void 0===e.formData.sql||""===e.formData.sql.trim()?a(new Error("请输入查询SQL条件")):(e.formData.sql=e.formData.sql.trim(),e.formData.userCodes="",a())},trigger:"blur"}],userCodes:[{validator:function(r,t,a){return"SQL"===e.valueSetting?(e.formData.userCode="",a()):"ADMIN"===e.formData.code?(e.formData.sql="",e.formData.userCode="0",a()):(e.formData.sql="",e.formData.userCodes&&e.formData.userCodes.trim()?(e.formData.userCodes=e.formData.userCodes.trim().replace("，",",").toLowerCase(),a()):a(new Error("请输入指定审批人")))},trigger:"blur"}],description:[{max:512,message:"长度在 512 个字符以内",trigger:"blur"}]},error:{code:"",sql:"",userCodes:""}}},methods:{handleValueSettingChange:function(e){"SQL"===e?this.formData.userCodes="":"USER"===e&&(this.formData.sql="")}},mounted:function(){void 0!==this.formData.userCodes&&""!==this.formData.userCodes?this.valueSetting="USER":this.valueSetting="SQL"}},l={render:function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("div",[t("d-form",{ref:"form",staticStyle:{height:"370px","overflow-y":"auto",width:"100%","padding-right":"50px"},attrs:{model:e.formData,rules:e.rules,"label-width":"100px"}},[t("d-form-item",{attrs:{label:"英文名:",error:e.error.code,prop:"code"}},[t("d-input",{attrs:{placeholder:e.$store.getters.placeholder.code},model:{value:e.formData.code,callback:function(r){e.$set(e.formData,"code",r)},expression:"formData.code"}})],1),e._v(" "),t("d-form-item",{attrs:{label:"中文名:",prop:"name"}},[t("d-input",{attrs:{placeholder:e.$store.getters.placeholder.name},model:{value:e.formData.name,callback:function(r){e.$set(e.formData,"name",r)},expression:"formData.name"}})],1),e._v(" "),t("d-form-item",{attrs:{label:"定值方式:",prop:"userType"}},[t("d-radio-group",{attrs:{name:"radiogroup"},on:{"on-change":e.handleValueSettingChange},model:{value:e.valueSetting,callback:function(r){e.valueSetting=r},expression:"valueSetting"}},[t("d-radio",{attrs:{label:"SQL"}},[e._v("设置查询SQL")]),e._v(" "),t("d-radio",{attrs:{label:"USER"}},[e._v("指定审批人")])],1)],1),e._v(" "),"SQL"==e.valueSetting?t("div",[t("d-form-item",{attrs:{label:"查询SQL:",error:e.error.sql,prop:"sql"}},[t("d-input",{attrs:{type:"textarea",rows:"3",placeholder:"请输入审批人查询条件SQL，完整SQL为：SELECT u.* FROM user u WHERE + 查询条件SQL。查询条件SQL支持变量，格式:#{v},变量范围仅限apply表中存储的字段，包括payload中可解析的字段。"},model:{value:e.formData.sql,callback:function(r){e.$set(e.formData,"sql",r)},expression:"formData.sql"}})],1)],1):"USER"==e.valueSetting?t("div",[t("d-form-item",{attrs:{label:"指定审批人:",error:e.error.userCodes,prop:"userCodes"}},[t("d-input",{attrs:{type:"textarea",rows:"3",placeholder:"请输入指定审批人Erp(格式：英文小写)，支持多个审批人，以英文逗号格式"},model:{value:e.formData.userCodes,callback:function(r){e.$set(e.formData,"userCodes",r)},expression:"formData.userCodes"}})],1)],1):e._e(),e._v(" "),t("d-form-item",{attrs:{label:"备注:",prop:"description"}},[t("d-input",{attrs:{type:"textarea",rows:"2",placeholder:"请输入备注"},model:{value:e.formData.description,callback:function(r){e.$set(e.formData,"description",r)},expression:"formData.description"}})],1)],1)],1)},staticRenderFns:[]};var i=t("VU/8")(s,l,!1,function(e){t("Il3R")},"data-v-60823cdd",null);r.default=i.exports}});
//# sourceMappingURL=35.71e2a688d2ace044d190.js.map