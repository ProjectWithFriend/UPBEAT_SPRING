package com.example.springtest.api;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@MessageMapping("/ready")
public class ReadyPage {

    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ReadyPage(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    @MessageMapping("/typeName")
    @SendTo("/topic/name")
    public NameResponse typeName(@RequestBody NameRequest nameRequest) {
        NameResponse nameResponse = new NameResponse();
        nameResponse.setNameP1(nameRequest.getNameP1());
        nameResponse.setNameP2(nameRequest.getNameP2());
//        messagingTemplate.convertAndSend("/topic/name", nameResponse);
        return nameResponse;
    }

    @MessageMapping("/changeReady")
    @SendTo("/topic/ready")
    public ReadyResponse changeReady(@RequestBody ReadyRequest readyRequest) {
        ReadyResponse readyResponse = new ReadyResponse();
        readyResponse.setReadyP1(readyRequest.isReadyP1());
        readyResponse.setReadyP2(readyRequest.isReadyP2());
        return readyResponse;
    }
}

@Data
class ReadyRequest {
    private boolean readyP1;
    private boolean readyP2;

    public boolean isReadyP1() {
        return readyP1;
    }

    public void setReadyP1(boolean readyP1) {
        this.readyP1 = readyP1;
    }

    public boolean isReadyP2() {
        return readyP2;
    }

    public void setReadyP2(boolean readyP2) {
        this.readyP2 = readyP2;
    }
}

@Data
class ReadyResponse {
    private boolean readyP1;
    private boolean readyP2;

    public boolean isReadyP1() {
        return readyP1;
    }

    public void setReadyP1(boolean readyP1) {
        this.readyP1 = readyP1;
    }

    public boolean isReadyP2() {
        return readyP2;
    }

    public void setReadyP2(boolean readyP2) {
        this.readyP2 = readyP2;
    }
}

@Data
class NameRequest {
    private String nameP1;
    private String nameP2;

    public String getNameP1() {
        return nameP1;
    }

    public void setNameP1(String nameP1) {
        this.nameP1 = nameP1;
    }

    public String getNameP2() {
        return nameP2;
    }

    public void setNameP2(String nameP2) {
        this.nameP2 = nameP2;
    }
}

@Data
class NameResponse {
    private String nameP1;
    private String nameP2;

    public String getNameP1() {
        return nameP1;
    }

    public void setNameP1(String nameP1) {
        this.nameP1 = nameP1;
    }

    public String getNameP2() {
        return nameP2;
    }

    public void setNameP2(String nameP2) {
        this.nameP2 = nameP2;
    }
}