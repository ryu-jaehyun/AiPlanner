![image](https://github.com/ryu-jaehyun/AiPlanner/blob/master/images/%ED%94%8C%EB%9E%98%EB%84%88logo.png?raw=true)



# 프로젝트 소개

> 머신러닝 기반 사용자 맞춤 스케줄러 웹사이트입니다.
>
> 사용자에게는 일정 설계에 어려움이 있으면 머신러닝과 알고리즘을 통해 사용자와 비슷한 그룹의 보편적인 스케줄을 제시함으로써 효율성을 제공합니다.
>
> 월별 일정, 일일 일정을 구분하여 일정관리 측면에서 접근성이 좋아 편리함을 제공합니다.
> 
> Spring Boot & Spring Data JPA & Spring Security를 사용해 REST API를 구현하고, Amazon EC2, Amazon RDS를 이용해 서버를 배포했습니다.


> ###  개발 기간 및 인원
>
> 23.03.06 ~ 23.06.05 (3개월)
>
> Frontend  : 전재오, 김민우
> 
> Backend  : 하정민(AI), 류제현(Server)-PL


# ERD 구조

![ERD](https://github.com/ryu-jaehyun/AiPlanner/blob/master/images/ERD%20%EA%B5%AC%EC%A1%B0.png?raw=true)


# 시스템 아키텍쳐

![시스템 아키텍쳐](https://github.com/ryu-jaehyun/AiPlanner/blob/master/images/%EC%8B%9C%EC%8A%A4%ED%85%9C%EC%95%84%ED%82%A4%ED%85%8D%EC%B3%90.png?raw=true)

# 기술 스택


[![stackticon](https://firebasestorage.googleapis.com/v0/b/stackticon-81399.appspot.com/o/images%2F1708924375402?alt=media&token=441b4e89-2ded-40a6-9a39-725030396c03)](https://github.com/msdio/stackticon)

# 주요기능

###  회원가입,로그인, 아이디&비밀번호 찾기

+ 사용자는 아이디, 비밀번호, 이름 , 생년월일, 성별, 핸드폰 번호를 기입하고 직업군(중고등학생/대학생)을 선택하여 회원가입할 수 있다.
+ 사용자가 24시간동안 웹 페이지를 사용하지 않는 경우 자동으로 로그아웃되어 보안을 유지한다.
+ 사용자는 핸드폰 번호를 이용해 아이디를 찾을수 있고, 아이디와 핸드폰 번호를 이용해 비밀번호를 찾을수 있다.


### 일정 관리

+  월별일정, 일일일정 별 일정목록을 조회할 수 있다. [월별일정 보기](https://github.com/ryu-jaehyun/AiPlanner/blob/master/images/%EC%9B%94%EB%B3%84%EC%9D%BC%EC%A0%95.png?raw=true)  [일일일정 보기](https://github.com/ryu-jaehyun/AiPlanner/blob/master/images/%EC%9D%BC%EC%9D%BC%EC%9D%BC%EC%A0%95.png?raw=true)
+  월별일정, 일일일정을 구분해 등록할 수 있다.
    + 일일일정은 다시 고정일정, 유동일정으로 구분된다.
    + 고정일정은 월,수,금 이런 형식으로 요일을 선택해 수업, 아르바이트 등등 유동 가능성이 거의 없는 일정이다.
+  일정타입(식사,공부,휴식,여가,운동)을 확인하여 타입에 맞춰서 등록할 수 있다.
+  일정을 하나 등록, 수정, 삭제할 때 마다 자동적으로 해당 사용자의 전체일정이 웹에 갱신된다.
+  시간대가 겹쳐도 최대 2~3(월별 3개/ 일일 2개)까지 등록할수 있다.
+  
+  
