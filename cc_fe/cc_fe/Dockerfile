FROM node:alpine

WORKDIR /usr/src/app

COPY . /usr/src/app

RUN npm install -g @angular/cli

RUN npm install

CMD ["ng", "serve", "--host", "0.0.0.0"]

#FROM nginx:latest
#COPY --from=build /usr/local/app/dist/cc_fe /usr/share/nginx/html
#COPY nginx.conf /etc/nginx/conf.d/default.conf
#
#EXPOSE 80



