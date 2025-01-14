# Build 스테이지
FROM gradle:8.10.2-jdk17 AS builder

# 작업 디렉토리 설정
WORKDIR /apps

# 의존성 캐싱
COPY build.gradle settings.gradle ./
COPY gradle gradle/
RUN gradle dependencies --no-daemon

# 소스 코드 복사
COPY src src/

# 테스트 스킵하고 빌드
RUN gradle clean build -x test --no-daemon --parallel

# 실행 스테이지
FROM eclipse-temurin:17-jre-jammy

LABEL type="application"

WORKDIR /apps

# wait-for-it 스크립트 다운로드
RUN apt-get update && apt-get install -y curl
RUN curl -o /usr/local/bin/wait-for-it https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh
RUN chmod +x /usr/local/bin/wait-for-it

COPY --from=builder /apps/build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 8080

USER nobody

ENTRYPOINT ["wait-for-it", "hnh-mysql:3306", "--", "wait-for-it", "redis:6379", "--", "java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]