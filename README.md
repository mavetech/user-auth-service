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
-  Or kubectl port-forward svc/<user-auth-service> <port>:<port-user-auth-service> then curl

## Install Istio
- Create minikube cluster with adequate resources for istio `minikube start --cpus 6 --memory 8192`
- For downloading and installing Istio: `https://istio.io/latest/docs/setup/getting-started/#bookinfo`
- Istio downloaded -> change Path to istio/bin -> istioctl install
- Inject roxy containers in pods: Add label to ns(kubectl get ns <namespace> --show-labels,  kubectl label namespace <namespace> istio-injection=enabled)
- Delete and recreate the deployment to have the proxy container injected in the pods.

## Install Addons
- In the location where binary folder exists, there is a samples folder with yaml files for installing addons.
- Apply all those files

## View traffic on Kiali
- Forward port: k port-forward svc/kiali -n istio-system <kiali-svc-port>

## Request per second
- `while true; do curl -X POST -H "Content-Type: application/json" -d '{"username": "user1", "password": "your_password"}' http://localhost:8082/login; sleep 1; done`

  
