# 가동중인 travelmate 도커 중단 및 삭제
sudo docker ps -a -q --filter "name=travelmate" | grep -q . && docker stop travelmate && docker rm travelmate | true

# 기존 이미지 삭제
sudo docker rmi woong7361/travelmate:1.0

# 도커허브 이미지 pull  dockerhub/name:tag
sudo docker pull woong7361/travelmate:1.0

# 도커 run
sudo docker run -d -p 8080:8080 -v /home/ubuntu/config:/config --name travelmate woong7361/travelmate:1.0

# 사용하지 않는 불필요한 이미지 삭제 -> 현재 컨테이너가 물고 있는 이미지는 삭제되지 않습니다.
sudo docker rmi -f $(docker images -f "dangling=true" -q) || true