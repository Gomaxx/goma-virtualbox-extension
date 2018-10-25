package cc.eoma.virtualbox.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyChannelHandler extends SimpleChannelInboundHandler<String> {
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s)
      throws Exception {
    System.out.println(s);
  }
}