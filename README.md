# reward-core
#### 리워드 관련 서비스 API (GITHUB-1030)

###
JDK 및 Gradle 설치 확인  
```
> java -version
> gradle --version
```
###

JDK  & gradle 설치(sdkman)
```
//JDK 11.0.15 zulu 
> curl -s "https://get.sdkman.io" | bash
> source "$HOME/.sdkman/bin/sdkman-init.sh"
> sdk install java 11.0.15-zulu
> sdk use java 11.0.15-zulu
> sdk default java 11.0.15-zulu

//gradle 최신버전
> sdk install gradle 
```  

###
지마켓 DB 접근을 위한 hosts 파일에 아래 내용 추가
```
> sudo vi /private/etc/hosts
```
```
//추가할 내용
172.30.217.221 gdevdb01
172.30.217.222 gdevdb02
```
###
Gradle Build
```
> gradle clean api:build
> gradle clean core:build
```
###
Gradle Run (http://localhost:8080/swagger-ui/index.html#/)

```
> gradle api:bootRun
```
###
Redis Command (MAC-OS)
```
> brew services start redis
> brew services stop redis
> brew services restart redis
> redis-cli
```

###