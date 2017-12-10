#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* 

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* 

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 멤버 변수로써 question과 answers를 가지고 있었기 때문에 question을 받은 후에 다른 사용자가 question을 받을때 questionId의 값이 변경될 수 있기 때문에 멤버 변수가 아닌 새로운 객체를 생성해서 전달 하는 것이 더 안전하다고 판단된다.
