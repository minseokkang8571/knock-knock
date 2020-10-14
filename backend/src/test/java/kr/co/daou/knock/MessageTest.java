package kr.co.daou.knock;

import kr.co.daou.knock.common.db.mybatis.dto.Chat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.junit.*;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = "server.port=4001")
public class MessageTest {
    static final String WEBSOCKET_URL = "ws://localhost:4000/chat";
    static final String WEBSOCKET_SEND_URL = "/receive/12";
    static final String WEBSOCKET_TOPIC_URL = "/send/12";
    int numberOfConnections = 50;
    int numberOfChat = 10000;

    CountDownLatch lock = new CountDownLatch(numberOfConnections*numberOfChat);

    BlockingQueue<Chat> blockingQueue;
    List<StompSession> socketSession;
    WebSocketStompClient stompClient;

    Chat data = new Chat();

    private List<Transport> createTransportClient(){
        List<Transport> transports = new ArrayList<>(numberOfConnections);
        for(int i = 0;i<numberOfConnections;i++) {
            transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        }
        return transports;
    }

    @Before
    public  void setup() throws InterruptedException{
        blockingQueue = new LinkedBlockingDeque<>();
        stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        socketSession = new ArrayList<StompSession>(numberOfConnections);
    }
    @Test
    public void contextLoads() throws InterruptedException{
        StompSession stompSession = null;

        for(int i = 0; i < numberOfConnections ; i++){
            TimeUnit.SECONDS.sleep(1);
            try {
                WebSocketHttpHeaders handshakeHeaders = new WebSocketHttpHeaders();
                handshakeHeaders.add("roomIdx","12");

                StompHeaders stompHeaders = new StompHeaders();
                stompHeaders.add("roomIdx","12");

                stompSession = stompClient.connect(WEBSOCKET_URL,new StompSessionHandlerAdapter(){}).get(1,TimeUnit.SECONDS);

                socketSession.add(stompSession);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        long beforeTime = System.currentTimeMillis();
        for(int i =0;i<socketSession.size();i++){
            Thread t = new Thread(new ThreadTest(i));
            t.start();
//            TimeUnit.SECONDS.sleep(1);
        }
        boolean ok = lock.await(1000,TimeUnit.SECONDS);
        if (ok == true) {
            long afterTime = System.currentTimeMillis();
            System.out.println("성공 : 스레드 종료 실행시간 : "+(afterTime - beforeTime)/1000);
        } else {
            long afterTime = System.currentTimeMillis();
            System.out.println("실패 : 스레드 종료"+(afterTime - beforeTime)/1000);
        }

    }

    class DefaultStompFrameHandler implements StompFrameHandler{
        @Override
        public Type getPayloadType(StompHeaders stompHeaders){
            return Chat.class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            blockingQueue.offer((Chat) o);
        }
    }


    public class ThreadTest implements Runnable{
        int index;
        int count = 0;
        ThreadTest(int i){
            this.index = i;
        }
        @Override
        public void run() {

            System.out.println("run 시작 : "+index);

            String jsonStr;


            while (count!=numberOfChat){
                jsonStr = "{"
                        + "\"roomIdx\":\"12\","
                        + "\"userIdx\":"+(index+1)+","
                        + "\"contents\":\""+(index+1)+" - "+count+"\","
                        + "\"name\":\"강정구\""
                        + "}"
                        ;
                System.out.println("while 시작 : "+ index + " : count : "+count);
                socketSession.get(index).send(WEBSOCKET_SEND_URL,jsonStr);
                count++;
//            System.out.println("while 종료 : "+ index + " : count : "+count);
            lock.countDown();

           /*     try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }*/
            }
        }
    }
}


