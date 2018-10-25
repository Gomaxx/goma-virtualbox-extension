package cc.eoma.virtualbox.netty;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyChannelHandler extends SimpleChannelInboundHandler<String> {
  public ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) {
    Channel incoming = ctx.channel();
    System.out.println(incoming.remoteAddress() + " connected");

    for (Channel channel : channels) {
      channel.writeAndFlush("[server] - " + incoming.remoteAddress() + " connected\n");
    }
    channels.add(ctx.channel());
  }

  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) {
    Channel incoming = ctx.channel();
    System.out.println(incoming.remoteAddress() + " logout");

    for (Channel channel : channels) {
      channel.writeAndFlush("[server] - " + incoming.remoteAddress() + " logout\n");
    }
    channels.remove(ctx.channel());
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
    Channel incoming = ctx.channel();
    System.out.println(incoming.remoteAddress() + " send message: " + s);
    this.systemNotifySend();

    for (Channel channel : channels) {
      if (channel != incoming) {
        channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + s + "\n");
      } else {
        channel.writeAndFlush("[you]" + s + "\n");
      }
    }
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) {
    Channel incoming = ctx.channel();

    System.out.println(incoming.remoteAddress() + " online");
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) {
    Channel incoming = ctx.channel();

    System.out.println(incoming.remoteAddress() + " outline");
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    Channel incoming = ctx.channel();

    System.out.println(incoming.remoteAddress() + " connection error, closed.");

    // 当出现异常就关闭连接
    cause.printStackTrace();
    ctx.close();
  }


  private void systemNotifySend() throws Exception {
    List<String> cmds = new ArrayList<String>();
    cmds.add("sh");
    cmds.add("-c");
    cmds.add("notify-send \"goma-virtualbox-extension\" \"has new message,please to virtalobx view.\"");
    ProcessBuilder pb = new ProcessBuilder(cmds);
    //    Process p = pb.start();
    pb.start();
  }
}