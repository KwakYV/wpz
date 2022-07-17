package ru.wpz.nio_server;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class NettyConfig {

    @Bean
    public NettyNetwork nettyNetwork(){
        return new NettyNetwork();
    }
}
