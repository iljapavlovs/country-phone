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
./gradlew build && java -jar build/libs/spring-boot-hw-phone-codes-0.0.1-SNAPSHOT.jar
```


### Building Docker Image
```bash
 docker build -f docker/Dockerfile -t spring-boot-hw-phone-codes .
```

### Running Docker Image
```
docker run -p 8080:8080 spring-boot-hw-phone-codes
```

specify `-d` in order to run in detached mode

### Building and Running via Docker Compose
```
docker-compose up
```


### Performance testing
```
docker run -it --rm -v  /Users/iljapavlovs/Desktop/Projects/ilja2/PoC/Spring Boot/performancetesting/configs:/bzt-configs -v /Users/iljapavlovs/Desktop/Projects/ilja2/PoC/Spring Boot/performancetesting/artifacts:/tmp/artifacts blazemeter/taurus config.yaml
```

OR just install Taurus locally - https://gettaurus.org/docs/Installation/
```
brew install bzt
```
