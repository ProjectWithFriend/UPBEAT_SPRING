package com.example.springtest.websocket_route;

import com.example.springtest.RequestBody.updateConfig;
import com.example.springtest.Responses.PlayerSlot;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

@RestController
@MessageMapping("/ready")
public class ReadyPage {

    private final PlayerSlot[] playerSlot = new PlayerSlot[2];
    private int numberOfConnectedPlayers = 0;
    private int nowEditingPlayer = 0;
    private updateConfig config;
    private String nameP1 = "";
    private String nameP2 = "";

    @Autowired
    private SimpMessagingTemplate simpleMessagingTemplate;

    @MessageMapping("/typeName")
    @SendTo("/topic/name")
    public NameResponse typeName(@RequestBody NameRequest nameRequest) {
        NameResponse nameResponse = new NameResponse();
        nameResponse.setNameP1(nameRequest.getNameP1());
        nameResponse.setNameP2(nameRequest.getNameP2());
        this.nameP1 = nameRequest.getNameP1();
        this.nameP2 = nameRequest.getNameP2();
        return nameResponse;
    }

    @MessageMapping("/updateConfig")
    @SendTo("/topic/updateConfig")
    public updateConfig configUpdate(@RequestBody updateConfig updateConfig) {
        this.config = updateConfig;
        return updateConfig;
    }

    @SubscribeMapping("/entry")
    public EntryResponse entry() {
        EntryResponse entryResponse = new EntryResponse();
        entryResponse.setNameP1(nameP1);
        entryResponse.setNameP2(nameP2);
        if(config != null){
            entryResponse.setConfig(config);
        }
        return entryResponse;
    }


    /*
     * To send message to client to know what player slot is
     */
    @MessageMapping("/lockPlayerSlot")
    public void playerSlot(final Principal principal) {
        simpleMessagingTemplate.convertAndSendToUser(principal.getName(), "/topic/playerSlot", playerSlot[nowEditingPlayer]);
    }

    @MessageMapping("/start")
    @SendTo("/topic/gameStart")
    public boolean gameStart() {
        nameP1 = "";
        nameP2 = "";
        config = null;
        return true;
    }

    private void autoSelectSlot(Principal principal) {
        if (playerSlot[0] == null) {
            playerSlot[0] = new PlayerSlot();
            playerSlot[0].setPlayerSlot(1);
            playerSlot[0].setUuid(principal.getName());
            nowEditingPlayer = 0;
        } else if (playerSlot[1] == null) {
            playerSlot[1] = new PlayerSlot();
            playerSlot[1].setPlayerSlot(2);
            playerSlot[1].setUuid(principal.getName());
            nowEditingPlayer = 1;
        }
    }

    private void autoRemoveSlot(Principal principal) {
        if (playerSlot[0] != null && playerSlot[0].getUuid().equals(principal.getName())) {
            playerSlot[0] = null;
        } else if (playerSlot[1] != null && playerSlot[1].getUuid().equals(principal.getName())) {
            playerSlot[1] = null;
        }
    }

    @EventListener(SessionConnectedEvent.class)
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        numberOfConnectedPlayers++;
        autoSelectSlot(event.getUser());
        System.out.println("Number of connected players: " + numberOfConnectedPlayers);
    }

    @EventListener(SessionDisconnectEvent.class)
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        numberOfConnectedPlayers--;
        autoRemoveSlot(event.getUser());
        System.out.println("Number of connected players: " + numberOfConnectedPlayers);
    }
}


@Data
class NameRequest {
    private String nameP1;
    private String nameP2;
}

@Data
class NameResponse {
    private String nameP1;
    private String nameP2;
}

@Data
class EntryResponse {
    private String nameP1;
    private String nameP2;
    private updateConfig config;
}