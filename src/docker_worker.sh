#!/bin/bash

# Обновление списка пакетов
sudo apt-get update

# Установка пакетов, необходимых для добавления репозитория по HTTPS
sudo apt-get install -y apt-transport-https ca-certificates curl software-properties-common

# Добавление ключа GPG официального репозитория Docker
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

# Добавление репозитория Docker в список доступных и обновление списка пакетов
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
sudo apt-get update

# Установка Docker
sudo apt-get install -y docker-ce

# Добавление текущего пользователя в группу docker, чтобы не использовать sudo для выполнения команд Docker
sudo usermod -aG docker $USER

# Получение токена из файла
join_token=$(cat /vagrant/swarm_token.txt)

# Подключение воркера к кластеру Swarm
sudo docker swarm join --token $join_token 192.168.50.10:2377
