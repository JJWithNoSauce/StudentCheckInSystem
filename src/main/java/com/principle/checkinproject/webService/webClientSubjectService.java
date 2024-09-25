package com.principle.checkinproject.webService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class webClientSubjectService {
    @Autowired
    private final WebClient webClient;

    public webClientSubjectService(WebClient webClient){
        this.webClient = webClient;
    }
}
