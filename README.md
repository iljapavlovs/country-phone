# country-phone 
[![Build Status](https://travis-ci.org/iljapavlovs/country-phone.svg?branch=master)](https://travis-ci.org/iljapavlovs/country-phone)

###
* https://git.neotech.lv/neotech/homeworks/wikis/home
* http://localhost:8080/countries?phoneNumber=0061891641233




* https://en.wikipedia.org/w/api.php?action=parse&page=List_of_country_calling_codes&format=json&formatversion=2&prop=sections
* 


* http://localhost:8080/countries?phoneNumber=%2B371
* https://en.wikipedia.org/wiki/List_of_country_calling_codes

### Running tests locally

Unit Tests
```
./gradlew clean test
```

Integration Tests
```
./gradlew clean intTest
```


### Building and running application
```
./gradlew build && java -jar build/libs/country-phone-0.0.1-SNAPSHOT.jar
```

## Docker Image
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
```bash
docker tag country-phone ilja07/country-phone:0.0.1-SNAPSHOT
```
3. Push the image to docker hub
```bash
docker push ilja07/country-phone:0.0.1-SNAPSHOT
```   

### Deployed version
https://country-phone.herokuapp.com/

## Performance testing
```
docker run -it --rm -v  /Users/iljapavlovs/Desktop/Projects/ilja2/PoC/Spring Boot/performancetesting/configs:/bzt-configs -v /Users/iljapavlovs/Desktop/Projects/ilja2/PoC/Spring Boot/performancetesting/artifacts:/tmp/artifacts blazemeter/taurus config.yaml
```

OR just install Taurus locally - https://gettaurus.org/docs/Installation/
```
brew install bzt
```
