package cc.eoma.virtualbox;

import io.netty.channel.Channel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TrayIconMonitor implements Runnable {
  private Channel channel;
  public TrayIconMonitor(Channel channel) {
    this.channel = channel;
  }

  public void run() {
    System.out.println("请将鼠标移动到要抓取的图标位置，8秒后开始获取图标");
    this.sleep(8000L);
    PointerInfo pointerInfo = MouseInfo.getPointerInfo();
    Point point = pointerInfo.getLocation();
    int x = point.x;
    int y = point.y;
    System.out.println("开始监听（" + x + "," + y + ")");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Integer baseRgb = this.getRgb(x, y);
    while (true) {
      int currentRgb = this.getRgb(x, y);
      if (baseRgb.compareTo(currentRgb) != 0) {
        this.channel.writeAndFlush( sdf.format(new Date())+ "new message\r\n");
      }
      if (currentRgb == -1111) {
        break;
      }
      this.sleep(5000L);
    }
  }

  private void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }

  private Integer getRgb(int x, int y) {
    try {
      Robot robot = new Robot(); //在此用来抓取屏幕，即截屏。详细可见API
      Toolkit toolkit = Toolkit.getDefaultToolkit(); // 获取缺省工具包
      Dimension dimension = toolkit.getScreenSize(); //屏幕尺寸规格
      Rectangle rectangle = new Rectangle(0, 0, dimension.width, dimension.height);
      BufferedImage bufferedImage = robot.createScreenCapture(rectangle);
      return bufferedImage.getRGB(x, y);//获得自定坐标的像素值
    } catch (Exception ex) {
      return 0;
    }
  }
}
