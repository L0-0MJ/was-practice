package org.example;


import ch.qos.logback.core.net.server.Client;
import org.example.calculator.domain.Calculator;
import org.example.calculator.domain.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomWebApplicationServer {

    private final int port;

    private final ExecutorService excutorService = Executors.newFixedThreadPool(18);

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


                //3. 스레드 pool 이용해서 안정적인 서비스 가능하도록 함.

                excutorService.execute(new ClientRequestHandler(clientSocket));


            }
        }
    }
}
