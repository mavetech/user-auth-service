# user-auth-service

## Build project
- mvn: clean package

## Dockerize and push to dockerhub
- `docker build -t user-auth-service:latest .`
- `docker tag user-auth-service:latest <dockerhub-username>/user-auth-service:latest`
- `docker push <dockerhub-username>/user-auth-service:latest`

## Install Minikube to run on K8
- Generate the command from the link to install minikube: https://minikube.sigs.k8s.io/docs/start/
- minikube start

## Apply K8 Manifest files
- `kubectl apply -f kubernetes/user-auth-service.yaml`

## Test Service on K8
-  `minikube ip`
-  `curl -X POST -H "Content-Type: application/json" -d '{"username": "user1", "password": "your_password"}' http://<minikube-ip>:<NodePort-port>/login`
