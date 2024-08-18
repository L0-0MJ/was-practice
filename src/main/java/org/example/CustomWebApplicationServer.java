package org.example;


import org.example.calculator.domain.Calculator;
import org.example.calculator.domain.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CustomWebApplicationServer {

    private final int port;

    private static final Logger logger = LoggerFactory.getLogger(CustomWebApplicationServer.class); //로그추가

    public CustomWebApplicationServer(int port){ //생성자 추가
        this.port = port;

    }

    public void start() throws IOException { //서버소켓 생성
        try(ServerSocket serverSocket = new ServerSocket(port)){ //이 포트로 서버소켓 생성
            logger.info("[CustomWebApplicationServer]started {} port.", port);

            Socket clientSocket; //클라이언트 기다리는 소켓
            logger.info("[CustomWebApplicationServer] Waiting for client.");

            while ((clientSocket = serverSocket.accept()) != null){ //서버소켓이 accept로 클라이언트를 기다리게함, 클라이언트 들어오면 null이 아니어서 while문 내부 실행
                logger.info("[CustomWebApplicationServer] client connected.]");


                //사용자 요청을 메인스레드가 처리하도록 한다.

                try (InputStream in = clientSocket.getInputStream(); OutputStream out =  clientSocket.getOutputStream()){
                    BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                    DataOutputStream dos = new DataOutputStream(out);

//                    String line;
//                    while ((line = br.readLine()) != ""){
//                        System.out.println(line);
//                }
                    HttpRequest httpRequest = new HttpRequest(br);

///calculate?operand1=11&operator=/&operand2=55

                    if(httpRequest.isGetRequest() && httpRequest.matchPath("/calculate")){
                        QueryStrings queryStrings = httpRequest.getQueryStrings();

                        int operand1 = Integer.parseInt(queryStrings.getValue("operand1"));
                        String operator = queryStrings.getValue("operator");
                        int operand2 = Integer.parseInt(queryStrings.getValue("operand2"));

                        int result = Calculator.calculate(new PositiveNumber(operand1), operator, new PositiveNumber(operand2));

                        //body length 구하기
                        byte[] body = String.valueOf(result).getBytes();

                        HttpResponse response = new HttpResponse(dos);
                        response.response200Header("application/json", body.length);
                        response.responseBody(body);


                    }





                }
            }
        }
    }
}
