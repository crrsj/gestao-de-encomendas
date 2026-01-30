FROM nginx:alpine

# Remove os arquivos padrão do Nginx e copia os seus
RUN rm -rf /usr/share/nginx/html/*
COPY . /usr/share/nginx/html

# O Nginx roda na porta 80 por padrão
EXPOSE 80