package com.example.springtest.api;

import com.example.springtest.Business.RandomCT;
import com.example.springtest.model.RespCT;
import com.example.springtest.model.TestResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestAPI {

    private final RandomCT business;
    public TestAPI(RandomCT business) {
        this.business = business;
    }

    @GetMapping("/test")
    public TestResponse test() {
        TestResponse response = new TestResponse();
        response.setName("Mingz");
        response.setMessage("Hello World");
        return response;
    }

    @GetMapping("/random")
    public RespCT randomCityCT(){
        RespCT response = new RespCT();
        final int[] randomCityCT = business.getCities();
        response.setCtOfP1(randomCityCT[0]);
        response.setCtOfP2(randomCityCT[1]);
        return response;
    }
}
