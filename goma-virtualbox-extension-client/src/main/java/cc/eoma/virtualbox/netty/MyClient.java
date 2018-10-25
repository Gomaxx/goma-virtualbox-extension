package cc.eoma.virtualbox.netty;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class MyClient {

  public void run() throws Exception {
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap bootstrap = new Bootstrap()
          .group(group)
          .channel(NioSocketChannel.class)
          .handler(new MyChannelInitializer());
      Channel channel = bootstrap.connect("127.0.0.1", 35888).sync().channel();
      //      while (true) {
      //        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      //        System.out.println(in.readLine());
      //        channel.writeAndFlush(in.readLine() + "\r\n");
      //
      //        if ("exit".equalsIgnoreCase(in.readLine())) {
      //          break;
      //        }
      //
      //      }
      //      Thread.sleep(5000L);
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      for (int i = 0; i < 100; i++) {
        char xxx = (char) System.in.read();
        channel.writeAndFlush("send message" + xxx);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      group.shutdownGracefully();
    }
  }

  public static void main(String[] args) throws Exception {
    new MyClient().run();
  }

}