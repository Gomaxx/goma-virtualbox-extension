# goma-virtualbox-extension
## 虚拟机有消息时宿主机弹窗提示
本人工作电脑 Ubuntu + VirtualBox（Win10），Win10中跑部分特殊软件（QQ、WeChat、iHaier等等），但是在虚拟机中收到新消息，在主机（Ubuntu）中并没有提醒，偶尔有重要通知不能及时查看，为解决该问题编写次应用。

本项目主要分为两部分：goma-virtualbox-extension-server、goma-virtualbox-extension-client

## goma-virtualbox-extension-server
server 主要是接受client发来的消息，当接受到消息后，执行shell （notify-send）发送通知。

## goma-virtualbox-extension-client
client 主要是监听指定区域图标是否闪烁，判断是否有新消息，如果收到新消息发送通知给服务器。



# 效果图如下
![服务端](https://raw.githubusercontent.com/Gomaxx/goma-virtualbox-extension/7aaf56245fbc9d4118a7c92c922c1f3cd6ee846f/server.png)

![客户端](https://raw.githubusercontent.com/Gomaxx/goma-virtualbox-extension/7aaf56245fbc9d4118a7c92c922c1f3cd6ee846f/client.png)
