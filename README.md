# country-phone 
[![Build Status](https://travis-ci.org/iljapavlovs/country-phone.svg?branch=master)](https://travis-ci.org/iljapavlovs/country-phone)
[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=iljapavlovs_country-phone&metric=alert_status)](https://sonarcloud.io/dashboard?id=iljapavlovs_country-phone)

https://country-phone.herokuapp.com/

## Tests
### Running tests locally

Unit Tests
```
./gradlew clean test
```

Integration Tests
```
./gradlew clean intTest
```

Unit, Int tests and Code Coverage report
```
./gradlew clean build jacocoTestReport
```

## Building and running application
```
./gradlew build && java -jar build/libs/country-phone-0.0.1-SNAPSHOT.jar
```

## Docker
### Building Docker Image
```bash
./gradlew clean build && docker build -t country-phone .
```
Build jar first and create an image out of it
### Running Docker Image
```
docker run -e "SPRING_PROFILES=local" -p 8080:8080 country-phone
```

specify `-d` in order to run in detached mode

### Building and Running via Docker Compose
```
docker-compose up
```

### Pushing images to DockerHub

1. Login with your Docker Id `docker login`, create a Docker Hub repo with the same title
2. Tag the image (To push a local image to docker registry, you need to associate the local image with a repository on the docker registry. The notation for the repository on docker registry is `username/repository:tag`)

Need to push 2 times - with the tag and tag = latest
```bash
docker tag country-phone ilja07/country-phone:0.0.1-SNAPSHOT
```

```bash
docker tag country-phone ilja07/country-phone:latest
```
3. Push the image to docker hub
```bash
docker push ilja07/country-phone:0.0.1-SNAPSHOT
```   

```bash
docker push ilja07/country-phone:latest
```  
## Kubernetes
```
minikube start
kubectl create deployment country-phone --image=ilja07/country-phone
```
OR 
```
kubectl create -f k8s/manifest.yml 
```
OR
```
kubectl apply -f k8s/manifest.yml 
```
create vs apply -> https://stackoverflow.com/questions/47369351/kubectl-apply-vs-kubectl-create


Verify status of pods
```
kubectl get pods
```

Get all Kubernetes resources info - shows all pods, replicasets, deployments, services, etc
```
kubectl get all
```

If POD is running, then open app locally
1. find out cluster's ip
```
minikube ip
```
OR
```
minikube service country-phone-service --url
```
2. open app
```
http://<minikube_ip>:30001/
```

### [Update Deployment](https://dev.to/itnext/stateless-apps-in-kubernetes-beyond-pods-4p52)

In case when you need to update some thing
1. change the specification file
2. apply the changes to Kubernetes master
```
kubectl apply -f k8s/manifest.yml
```

### Connect to Pod and Container
In this case, our Pod has a single container, so we don't need to specify one
```
kubectl exec pod1 -it -- env | grep ENVVAR
```
### Troubleshooting
```
kubectl logs <POD_NAME>
```
```
kubectl describe pods <POD_NAME>
```

## Performance testing
```
docker run -it --rm -v  /<fullpath>/performancetesting/configs:/bzt-configs -v/ /<fullpath>/performancetesting/artifacts:/tmp/artifacts blazemeter/taurus config.yaml
```

OR just install Taurus locally - https://gettaurus.org/docs/Installation/
```
brew install bzt
```
