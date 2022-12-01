package cc.eoma.virtualbox;

import cc.eoma.virtualbox.netty.MyChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.internal.StringUtil;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StartApplication {
  public static void main(String[] args) throws Exception {
    Properties properties = loadProperties();
    String address = properties.getProperty("ip");
    if (StringUtil.isNullOrEmpty(address)) {
      System.out.print("请输入服务器IP: ");
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      address = br.readLine();
      properties.setProperty("ip", address);
    }

    EventLoopGroup group = new NioEventLoopGroup();
    Bootstrap bootstrap = new Bootstrap()
            .group(group)
            .channel(NioSocketChannel.class)
            .handler(new MyChannelInitializer());
    Channel channel = bootstrap.connect(address, 35888).sync().channel();

    String point = properties.getProperty("points");
    if (StringUtil.isNullOrEmpty(point)) {
      point = genPoint();
      properties.setProperty("points", point);
    }
    List<Point> points = genPoint(point);
    for (Point pointx : points) {
      new Thread(new TrayIconMonitor(channel, pointx)).start();
      Thread.sleep(10000L);
    }
    saveProperties(properties);
  }


  private static String genPoint() throws Exception {
    StringBuilder points = new StringBuilder("");
    System.out.print("请输入要启动监听数量: ");
    int count = Integer.parseInt(((char) System.in.read()) + "");
    for (int i = 0; i < count; i++) {
      System.out.println("请将鼠标移动到要抓取的图标位置，8秒后开始获取图标");
      Thread.sleep(8000L);
      PointerInfo pointerInfo = MouseInfo.getPointerInfo();
      Point point = pointerInfo.getLocation();
      points.append(Double.valueOf(point.getX()).intValue()).append(",").append(Double.valueOf(point.getY()).intValue()).append("#");
    }
    return points.substring(0, points.length() - 1);
  }


  private static List<Point> genPoint(String point) {
    List<Point> points = new ArrayList<Point>();
    String[] xxx = point.split("#");
    for (String item : xxx) {
      String[] xx = item.split(",");
      points.add(new Point(Integer.parseInt(xx[0]), Integer.parseInt(xx[1])));
    }
    return points;
  }

  private static Properties loadProperties() {
    Properties properties = new Properties();
    String path = StartApplication.class.getResource("").toString();
    if (!path.contains(".jar")) {
      return properties;
    }
    path = path.replace("jar:file:", "").split(".jar")[0];
    path = path.substring(1, path.lastIndexOf("/"));
    try {
      properties.load(new FileInputStream(path + "application.properties"));
    } catch (Exception ex) {
      System.out.println("can't find " + path + "application.properties");
      return properties;
    }
    return properties;
  }


  private static void saveProperties(Properties properties) {
    String path = StartApplication.class.getResource("").toString();
    if (!path.contains(".jar")) {
      return;
    }
    path = path.replace("jar:file:", "").split(".jar")[0];
    path = path.substring(1, path.lastIndexOf("/"));
    try {
      FileOutputStream fos = new FileOutputStream(path + "application.properties");
      properties.store(fos, "xxx");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
