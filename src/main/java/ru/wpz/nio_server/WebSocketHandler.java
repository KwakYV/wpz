package ru.wpz.nio_server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.wpz.dto.MessageDto;

@Slf4j
@Service
public class WebSocketHandler extends SimpleChannelInboundHandler<MessageDto> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, MessageDto messageDto)  {
        ctx.writeAndFlush(messageDto)ж
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

    }
}
