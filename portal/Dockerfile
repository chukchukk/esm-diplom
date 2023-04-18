FROM nginx:latest
ADD /nginx_portal/conf/nginx.conf  /etc/nginx/nginx.conf
ADD /client/build  /srv/app-name
RUN chmod 777 -R /srv/app-name/*
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

