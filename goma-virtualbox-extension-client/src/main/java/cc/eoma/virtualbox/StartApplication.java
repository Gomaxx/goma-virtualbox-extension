package cc.eoma.virtualbox;

import cc.eoma.virtualbox.netty.MyChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class StartApplication {
  public static void main(String[] args) throws Exception {

    EventLoopGroup group = new NioEventLoopGroup();
    Bootstrap bootstrap = new Bootstrap()
        .group(group)
        .channel(NioSocketChannel.class)
        .handler(new MyChannelInitializer());
    Channel channel = bootstrap.connect("192.168.1.95", 35888).sync().channel();
    System.out.print("请输入要启动监听数量: ");
    Integer count = Integer.valueOf(((char) System.in.read()) + "");
    for (int i = 0; i < count; i++) {
      new Thread(new TrayIconMonitor(channel)).start();
      Thread.sleep(10000L);
    }
  }
}
