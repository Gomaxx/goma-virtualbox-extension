package cc.eoma.virtualbox;

import cc.eoma.virtualbox.netty.MyChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class StartApplication {
    public static void main(String[] args) throws Exception {

        System.out.print("请输入服务器IP: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String address = br.readLine();

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new MyChannelInitializer());  // 192.168.43.204   192.168.1.95    10.190.41.179
        Channel channel = bootstrap.connect(address, 35888).sync().channel();
        System.out.print("请输入要启动监听数量: ");
        Integer count = Integer.valueOf(((char) System.in.read()) + "");
        for (int i = 0; i < count; i++) {
            new Thread(new TrayIconMonitor(channel)).start();
            Thread.sleep(10000L);
        }
    }
}
