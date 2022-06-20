# Quick start

部署文件的渲染模板，我们下文将定义一些变量，helm执行时会将变量渲染进模板文件中。

## _helpers.tpl

这个文件我们用来进行标签模板的定义，以便在上文提到的位置进行标签渲染。

此项目标签总共分为两个部分: 平台、日志。

### 平台标签

#### deployment 级:

```
{{- define "service.labels.standard" -}}
choerodon.io/release: {{ .Release.Name | quote }}
{{- end -}}
```
平台管理实例需要的实例ID。


### 日志标签

#### deployment 级:

```
{{- define "service.logging.deployment.label" -}}
choerodon.io/logs-parser: {{ .Values.logs.parser | quote }}
{{- end -}}
```
日志管理所需要的应用标签。该标签指定应用程序的日志格式，内置格式有`nginx`,`spring-boot`,`docker`对于前端服务请使用`nginx`，如果不需要收集日志请移除此段代码，并删除模板文件关于`service.logging.deployment.label`的引用。

## values.yaml

这个文件中的键值对，即为我们上文中所引用的变量。

将所以有变量集中在一个文件中，方便部署的时候进行归档以及灵活替换。

同时，helm命令支持使用 `--set FOO_BAR=FOOBAR` 参数对values 文件中的变量进行赋值，可以进一步简化部署流程。


## 参数对照表

参数名 | 含义 
--- | --- 
preJob.preConfig.mysql{}|初始化配置所需manager_service数据库信息
env.open.PRO_API_HOST|api地址
env.open.PRO_DEVOPS_HOST|devops service地址
env.open.PRO_CLIENT_ID|client id
env.open.PRO_TITLE_NAME|页面显示标题
env.open.PRO_HEADER_TITLE_NAME|页面header标题
env.open.PRO_HTTP|使用协议
service.enable|创建service对象
ingress.enable|创建ingress对象
ingress.host|域名地址，此处不能带http://
