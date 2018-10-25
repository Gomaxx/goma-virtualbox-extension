package cc.eoma.virtualbox;

import java.awt.*;

import javax.swing.*;

public class VirtualboxExtensionFrame {
  public static void main(String[] args) throws Exception {
    Integer width = 400;
    Integer height = 40;

    Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();

    JFrame frame = new JFrame("goma-virtualbox-extension");
    frame.setSize(width, height);
    //    frame.setLocation((screensize.width - width) / 2, (screensize.height - height) / 2);
    frame.setLocation(screensize.width - width - 20, 60);
    frame.setUndecorated(true);

    //    JTextArea jTextArea = new JTextArea();
    //    jTextArea.setEditable(false);
    //
    //    JScrollPane jScrollPane = new JScrollPane(jTextArea);
    //    jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    //    jScrollPane.setSize(width, height);

    JLabel jLabel = new JLabel("goma-virtualbox-extension has new message.");
    JPanel jPanel = new JPanel();
    jPanel.setBackground(Color.white);
    jPanel.setSize(width, height);
    jPanel.add(jLabel);

    String imageName =
        VirtualboxExtensionFrame.class.getClassLoader().getResource("logo.png").getPath();
    frame.setIconImage(frame.getToolkit().getImage(imageName));

    //    frame.setContentPane(jScrollPane);
    frame.add(jPanel);
    frame.show();


    //    for (int i = 0; i < 10; i++) {
    //      jTextArea.append("=============>" + i + "\n");
    //      Thread.sleep(500L);
    //    }
  }
}
