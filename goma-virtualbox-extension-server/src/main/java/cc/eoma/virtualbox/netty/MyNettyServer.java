package cc.eoma.virtualbox.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class MyNettyServer {

  public static void main(String[]args) {
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class)
          .childHandler(new MyChannelInitializer())
          .option(ChannelOption.SO_BACKLOG, 128)
          .childOption(ChannelOption.SO_KEEPALIVE, true);

      // 绑定端口，开始接收进来的连接
      ChannelFuture f = b.bind(35888).sync();

      // 等待服务器  socket 关闭 。
      // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
      f.channel().closeFuture().sync();
    } catch (Exception ex) {
      System.out.println("my netty server start error: " + ex.getLocalizedMessage());
    } finally {
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();

      System.out.println("my netty server shutdown");
    }
  }
}