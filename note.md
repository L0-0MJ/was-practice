step 1. 사용자 요청을 메인 스레드가 처리하도록 함

step 2. 사용자 요청이 들어올 때마다 스레드 생성해서 사용자 요청을 처리하도록 함
- 스레드는 할당될 때마다 독립적인 메모리 할당받는데 이 작업은 비싼 작업임
- 요청 들어올 때마다 생성되면 성능저하 유발, 동시접속자 수 많아지면 cpu, 메모리 사용량 증가됨 서버가 다운될 수도 있음

step 3. 스레드 pool을 적용해서 안정적인 서비스가 되도록 함