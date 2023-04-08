package com.ninep.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author NineP
 */
@RestController
public class IndexController {

    @GetMapping(value = "/")
    public Mono<String> index() {
        String html = "<center>Gateway Run Success</center><br/>";
        html = html + "<center>网关地址：http://192.168.171.199:88</center>";
        //TODO 响应式编程
        return Mono.just(html);
    }
}
