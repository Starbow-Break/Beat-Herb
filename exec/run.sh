#!/bin/bash

echo "소유하고있는 root 도메인을 입력하세요 : "
read DOMAIN

echo "SMTP 용 EMAIL 을 입력해주세요 : "
read EMAIL

echo "SMTP 용 비밀번호를 입력해주세요 : "
read MAIL_PASSWORD

echo "SMTP 용 주소를 입력해주세요 : "
read MAIL_HOST



IP=$(curl -s ifconfig.me)

echo "============="
echo "openvidu.$DOMAIN"
echo "socket.$DOMAIN"
echo "$DOMAIN" 
echo "상위 3개 도메인의 A레코드를 $IP 로 설정후 엔터를 눌러주세요"
echo "============="
read t



curl -s https://s3-eu-west-1.amazonaws.com/aws.openvidu.io/install_openvidu_latest.sh | bash

sed -i "s/DOMAIN_OR_PUBLIC_IP=.*/DOMAIN_OR_PUBLIC_IP=openvidu.$DOMAIN/" ./openvidu/.env

mkdir mysql
characters="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"

generate_random() {
    length=$1
    result=""
    for i in $(seq 1 $length); do
        random_index=$(( $RANDOM % ${#characters} ))
        result="${result}${characters:$random_index:1}"
    done
    echo "$result"
}

# 20자리의 영어와 숫자가 섞인 난수 생성
OPENVIDU_SECRET=$(generate_random 50)
JWT_SALT=$(generate_random 50)
DB_PASSSWORD=$(generate_random 50)
MYSQL_ROOT_PASSWORD=$(generate_random 50)


sed -i "s/MYSQL_ROOT_PASSWORD=.*/MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASSWORD/" ./env/.env_database
sed -i "s/MYSQL_PASSWORD=.*/MYSQL_PASSWORD=$DB_PASSSWORD/" ./env/.env_database

sed -i "s/DB_PASSSWORD=.*/DB_PASSSWORD=$DB_PASSSWORD/" ./env/.env_backend
sed -i "s/JWT_SALT=.*/JWT_SALT=$JWT_SALT/" ./env/.env_backend
sed -i "s/OPENVIDU_SECRET=.*/OPENVIDU_SECRET=$OPENVIDU_SECRET/" ./env/.env_backend
sed -i "s/OPENVIDU_BASE_URL=.*/OPENVIDU_BASE_URL=https:\/\/openvidu.$DOMAIN/" ./env/.env_backend
sed -i "s/MAIL_HOST=.*/MAIL_HOST=$MAIL_HOST/" ./env/.env_backend
sed -i "s/MAIL_USERNAME=.*/MAIL_USERNAME=$EMAIL/" ./env/.env_backend
sed -i "s/MAIL_PASSWORD=.*/MAIL_PASSWORD=$MAIL_PASSWORD/" ./env/.env_backend


sed -i "s/REACT_APP_TEST_SERVER_BASE_URL=.*/REACT_APP_TEST_SERVER_BASE_URL=https:\/\/$DOMAIN\/api/" ./env/.env_frontend


sed -i "s/JWT_SALT=.*/JWT_SALT=$JWT_SALT/" ./env/.env_socket
sed -i "s/OPENVIDU_SECRET=.*/OPENVIDU_SECRET=$OPENVIDU_SECRET/" ./openvidu/.env
sed -i "s/LETSENCRYPT_EMAIL=.*/LETSENCRYPT_EMAIL=$EMAIL/" ./openvidu/.env
sed -i "s/CERTIFICATE_TYPE=.*/CERTIFICATE_TYPE=letsencrypt/" ./openvidu/.env

mkdir -p resource/{music/{convert,cropped,image,reference,lyrics,decribe},profile/image}

sudo apt install jq -y
docker build -t front_build -f frontend/dockerfile ../

FRONT_BUILD_DIR=$(docker  image inspect front_build | jq '.[0].GraphDriver.Data.UpperDir')

FRONT_BUILD_DIR=${FRONT_BUILD_DIR:1:-1}/file/build

sudo cp -r "$FRONT_BUILD_DIR" ./openvidu/custom-nginx-vhosts/html

docker rmi front_build

echo "Backend APP 을 시작합니다"
docker-compose up -d

#docker cp exec_beatherb-frontend-build_1:/file/build ./openvidu/custom-nginx-vhosts/html

#docker stop exec_beatherb-frontend-build_1


git clone https://github.com/wmnnd/nginx-certbot.git ./ssl

echo "server {
    listen 80;
    server_name $DOMAIN;
    server_tokens off;

    location /.well-known/acme-challenge/ {
        root /var/www/certbot;
    }

    location / {
        return 301 https://\$host\$request_uri;
    }
}
server {
    listen 80;
    server_name socket.$DOMAIN;
    server_tokens off;

    location /.well-known/acme-challenge/ {
        root /var/www/certbot;
    }

    location / {
        return 301 https://\$host\$request_uri;
    }
}
server {
    listen 80;
    server_name openvidu.$DOMAIN;
    server_tokens off;

    location /.well-known/acme-challenge/ {
        root /var/www/certbot;
    }

    location / {
        return 301 https://\$host\$request_uri;
    }
}
" > ./ssl/data/nginx/app.conf

sed -i "s/domains=.*/domains=($DOMAIN socket.$DOMAIN openvidu.$DOMAIN)/" ./ssl/init-letsencrypt.sh
sed -i "s/email=.*/email=\"$LETSENCRYPT_EMAIL\"/" ./ssl/init-letsencrypt.sh

cd ./ssl
./init-letsencrypt.sh
docker-compose down
sleep 3
cd ..
sudo cp  ./ssl/data/certbot/conf/archive/$DOMAIN/privkey1.pem ./openvidu/owncert/certificate.key
sudo cp  ./ssl/data/certbot/conf/archive/$DOMAIN/cert1.pem ./openvidu/owncert/certificate.cert
sudo rm -r ssl

echo "
server {
    listen 443 ssl;
    server_name $DOMAIN;

    ssl_certificate /owncert/certificate.cert;  # SSL 인증서 파일
    ssl_certificate_key /owncert/certificate.key;  # SSL 키 파일

    root /etc/nginx/vhost.d/html;
    index index.html;
    location / {
        try_files \$uri \$uri /index.html;
    }

    location /api {
                proxy_pass http://localhost:28080;
                # 역방향 프록시 설정이 잘못된 것으로 파악되었습니다. 해결
                proxy_set_header Host \$host:\$server_port;
                proxy_set_header X-Real-IP \$remote_addr;
                proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto \$scheme;

                # Required for new HTTP-based CLI
                proxy_http_version 1.1;
                proxy_request_buffering off;
                proxy_buffering off; # Required for HTTP-based CLI to work over SSL
                add_header 'X-SSH-Endpoint' '$DOMAIN' always;
    }

}
server {
    listen 443 ssl;
    server_name socket.$DOMAIN;

    ssl_certificate /owncert/certificate.cert;  # SSL 인증서 파일
    ssl_certificate_key /owncert/certificate.key;  # SSL 키 파일

   location / {
        proxy_pass http://localhost:18080;
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto \$scheme;

        proxy_set_header Upgrade \$http_upgrade;
        proxy_set_header Connection "upgrade";
    }

}

server {
    listen 80;
    server_name $DOMAIN;
    return 301 https://\$host\$request_uri;
}
" > ./openvidu/custom-nginx-vhosts/custom-nginx.conf
cd openvidu
./openvidu start
