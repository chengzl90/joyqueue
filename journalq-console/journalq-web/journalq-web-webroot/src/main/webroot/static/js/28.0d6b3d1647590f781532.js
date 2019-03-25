webpackJsonp([28],{"7YrC":function(a,e){},Fv1G:function(a,e,t){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var r=t("lcoF"),l=t("M3bc"),o={name:"alarm-rule-default-form",mixins:[r.a],props:{type:0,data:{type:Object,default:function(){return{alarmLevel:"ALL",userType:"ALL",alarmMode:"",app:"",topic:"",hosts:"",broker:"",data:0,alarmInterval:0,detectDuration:0,detectTimes:0,effectiveTime:"",expirationTime:"",noticeProducer:!1,status:1}}}},data:function(){return{formData:{},typeList:[{value:"backlog",label:"backlog"}],intervalList:[{value:0,label:"10分钟"},{value:30,label:"30分钟"},{value:60,label:"1个小时"},{value:120,label:"2个小时"},{value:180,label:"3个小时"},{value:240,label:"4个小时"},{value:300,label:"5个小时"},{value:360,label:"6个小时"},{value:720,label:"12个小时"}],rules:{alarmLevel:[{required:!0,message:"请选择报警级别",trigger:"change"}],userType:[{required:!0,message:"请选择报警对象",trigger:"change"}],alarmModeArray:[{type:"array",required:!0,message:"请至少选择一个报警方式",trigger:"change"}],app:[{required:!0,min:1,message:"请输入应用",trigger:"change"}],topic:[{required:!0,min:1,message:"请输入主题",trigger:"change"}],hosts:[{required:!0,message:"请输入主机",trigger:"blur"},{min:5,max:20,message:"长度在 5 到 20 个字符",trigger:"blur"}],broker:[{required:!0,message:"请输入broker",trigger:"blur"},{min:1,max:200,message:"长度200 个字符以内",trigger:"blur"}],threshold:[{type:"number",required:!0,message:"请输入阈值",trigger:"blur"}],alarmInterval:[{type:"number",required:!0,message:"请选择报警间隔",trigger:"blur"}],thresholdCount:[{type:"number",required:!0,message:"请选择检测次数",trigger:"blur"}]}}},methods:{getFormData:function(){return void 0===this.formData.alarmModeArray||this.formData.alarmModeArray===[]?this.formData.alarmWays="":this.formData.alarmWays=this.formData.alarmModeArray.join(","),this.formData}},mounted:function(){var a=Object(l.e)(this.data);void 0===a.alarmWays||""===a.alarmWays?a.alarmModeArray=[]:a.alarmModeArray=a.alarmWays.split(","),this.formData=a}},s={render:function(){var a=this,e=a.$createElement,t=a._self._c||e;return t("d-form",{ref:"form",staticStyle:{height:"350px","overflow-y":"auto",width:"100%","padding-right":"20px"},attrs:{model:a.formData,rules:a.rules,"label-width":"100px"}},[t("d-form-item",{attrs:{label:"指标名称",prop:"metricCode"}},[t("d-input",{staticStyle:{width:"50%"},model:{value:a.formData.metricCode,callback:function(e){a.$set(a.formData,"metricCode",e)},expression:"formData.metricCode"}})],1),a._v(" "),t("d-form-item",{attrs:{label:"报警级别:",prop:"alarmLevel"}},[t("d-select",{staticStyle:{width:"50%"},model:{value:a.formData.alarmLevel,callback:function(e){a.$set(a.formData,"alarmLevel",e)},expression:"formData.alarmLevel"}},[t("d-option",{attrs:{value:"2"}},[a._v("严重")]),a._v(" "),t("d-option",{attrs:{value:"1"}},[a._v("次要")]),a._v(" "),t("d-option",{attrs:{value:"0"}},[a._v("警告")])],1)],1),a._v(" "),t("d-form-item",{attrs:{label:"报警对象:",prop:"alarmUsers"}},[t("d-select",{staticStyle:{width:"50%"},model:{value:a.formData.alarmUsers,callback:function(e){a.$set(a.formData,"alarmUsers",e)},expression:"formData.alarmUsers"}},[t("d-option",{attrs:{value:"ALL"}},[a._v("全部")]),a._v(" "),t("d-option",{attrs:{value:"ADMIN"}},[a._v("管理员")]),a._v(" "),t("d-option",{attrs:{value:"USER"}},[a._v("普通用户")])],1)],1),a._v(" "),t("d-form-item",{attrs:{label:"报警方式:",prop:"alarmModeArray"}},[t("d-checkbox-group",{attrs:{name:"Checkboxgroup"},model:{value:a.formData.alarmModeArray,callback:function(e){a.$set(a.formData,"alarmModeArray",e)},expression:"formData.alarmModeArray"}},[t("d-checkbox",{attrs:{label:"email"}},[a._v("EMAIL")]),a._v(" "),t("d-checkbox",{attrs:{label:"message"}},[a._v("MESSAGE")]),a._v(" "),t("d-checkbox",{attrs:{label:"phone"}},[a._v("PHONE")]),a._v(" "),t("d-checkbox",{attrs:{label:"timline"}},[a._v("TIMLINE")])],1)],1),a._v(" "),t("d-form-item",{attrs:{label:"主题"}},[t("d-input",{staticStyle:{width:"50%"},model:{value:a.formData.topic,callback:function(e){a.$set(a.formData,"topic",e)},expression:"formData.topic"}})],1),a._v(" "),t("d-form-item",{attrs:{label:"应用"}},[t("d-input",{staticStyle:{width:"50%"},model:{value:a.formData.app,callback:function(e){a.$set(a.formData,"app",e)},expression:"formData.app"}})],1),a._v(" "),t("d-form-item",{attrs:{label:"主机"}},[t("d-input",{staticStyle:{width:"50%"},model:{value:a.formData.hosts,callback:function(e){a.$set(a.formData,"hosts",e)},expression:"formData.hosts"}})],1),a._v(" "),t("d-form-item",{attrs:{label:"阈值"}},[t("d-input",{staticStyle:{width:"50%"},model:{value:a.formData.threshold,callback:function(e){a.$set(a.formData,"threshold",e)},expression:"formData.threshold"}})],1),a._v(" "),t("d-form-item",{attrs:{label:"检测时间"}},[t("d-input",{staticStyle:{width:"50%"},model:{value:a.formData.detectDuration,callback:function(e){a.$set(a.formData,"detectDuration",e)},expression:"formData.detectDuration"}})],1),a._v(" "),t("d-form-item",{attrs:{label:"检测次数"}},[t("d-input",{staticStyle:{width:"50%"},model:{value:a.formData.thresholdCount,callback:function(e){a.$set(a.formData,"thresholdCount",e)},expression:"formData.thresholdCount"}})],1),a._v(" "),t("d-form-item",{attrs:{label:"报警间隔"}},[t("d-select",{staticStyle:{width:"50%"},attrs:{value:"0"},model:{value:a.formData.alarmInterval,callback:function(e){a.$set(a.formData,"alarmInterval",e)},expression:"formData.alarmInterval"}},a._l(a.intervalList,function(e){return t("d-option",{key:e.value,attrs:{value:e.value}},[a._v(a._s(e.label))])}),1)],1)],1)},staticRenderFns:[]};var i=t("VU/8")(o,s,!1,function(a){t("7YrC")},"data-v-82824ca8",null);e.default=i.exports}});
//# sourceMappingURL=28.0d6b3d1647590f781532.js.map