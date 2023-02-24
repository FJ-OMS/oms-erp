{{/* vim: set filetype=mustache: */}}
{{- /*
service.labels.standard prints the standard service Helm labels.
The standard labels are frequently used in metadata.
*/ -}}

{{- define "service.image" -}}
{{- printf "%s:%s" .Values.image.repository (default (.Chart.Version) .Values.image.tag) -}}
{{- end -}}

{{/*
Return the appropriate apiVersion for deployment.
*/}}
{{- define "app.deployment.apiVersion" -}}
{{- if semverCompare "<1.9-0" .Capabilities.KubeVersion.GitVersion -}}
{{- print "apps/v1beta2" -}}
{{- else -}}
{{- print "apps/v1" -}}
{{- end -}}
{{- end -}}


{{- define "service.labels.kubesphere" -}}
app: {{ .Release.Name | quote }}
app.kubernetes.io/name: {{ .Values.kubesphere.app.name | quote }}
app.kubernetes.io/version: {{ .Values.kubesphere.app.version | quote }}
version: {{ .Values.kubesphere.app.version | quote }}
{{- end -}}
