## `application.properties`
### 실행된 쿼리를 로그로 확인하기
``` java
spring.jpa.show_sql=true
```
### H2의 쿼리 로그를 MySQL로 수정하기
``` java
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect
```
### H2 웹 콘솔 사용하기
``` java
spring.h2.console.enabled=true
// 후에 http://localhost:포트번호/h2-console 로 접속하여
// JDBC URL을 jdbc:h2:mem:testdb로 변경한다.
```
