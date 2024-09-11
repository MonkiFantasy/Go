# 局域网联机围棋

UI java awt

联机：java socket

难点：

悔棋 用栈记录每次绘画指令，栈顶出栈重绘

打劫 

禁入点

导出SGF棋谱





# 目录结构

- draw 用于界面的绘制
  - MyFrame 控制界面监听
  - Mypaint 绘制围棋相关
- socket 用于实现网络通信
- util工具类
  - Calculator 用于各种坐标的计算
  - Config 用于存储程序所用到的常量
  - Position 用于记录坐标
  - Stone用于存储棋子信息
