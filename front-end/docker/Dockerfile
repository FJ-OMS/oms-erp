FROM registry.cn-shanghai.aliyuncs.com/c7n/frontbase:0.9.0
RUN echo "Asia/shanghai" > /etc/timezone;
RUN chown -R nginx:nginx /usr/share/nginx/html
COPY --chown=nginx:nginx ./dist /usr/share/nginx/html
USER 101
CMD ["nginx", "-g", "daemon off;"]
