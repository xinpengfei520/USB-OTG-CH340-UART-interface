# USB-OTG-CH340-UART-interface

[![Version](https://img.shields.io/badge/version-1.0-blue.svg)](https://github.com/vancexin/USB-OTG-CH340-UART-interface)
[![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![GitHub stars](https://img.shields.io/github/stars/vancexin/USB-OTG-CH340-UART-interface.svg)](https://github.com/vancexin/USB-OTG-CH340-UART-interface/stargazers)

一个用于Android设备通过USB OTG与CH340 UART转换器进行通信的库，无需root权限。

## ⭐ Star History

[![Star History Chart](https://api.star-history.com/svg?repos=xinpengfei520/USB-OTG-CH340-UART-interface&type=Date)](https://star-history.com/#xinpengfei520/USB-OTG-CH340-UART-interface&Date)

## 📖 项目简介

USB-OTG-CH340-UART-interface 是一个专为Android平台设计的USB转串口通信库。该库基于CH340芯片，支持Android设备通过USB OTG功能与外部串口设备进行数据通信，无需获取设备root权限。

### 🎯 主要特性

- ✅ **无需Root权限** - 基于Android USB Host API实现
- ✅ **CH340芯片支持** - 专门针对CH340系列芯片优化
- ✅ **USB OTG检测** - 自动检测和管理USB设备连接
- ✅ **多种数据格式** - 支持ASCII和HEX格式数据传输
- ✅ **实时通信** - 支持实时数据读写操作
- ✅ **权限管理** - 自动处理USB设备访问权限
- ✅ **线程安全** - 内置线程池管理数据读取
- ✅ **易于集成** - 简洁的API设计，易于集成到现有项目

### 🔧 技术规格

- **最低Android版本**: Android 4.4 (API Level 19)
- **目标Android版本**: Android 8.1 (API Level 27)
- **支持架构**: ARM, ARM64, x86, x86_64
- **CH340芯片**: 支持CH340G、CH340C、CH340N等型号
- **串口参数**: 波特率9600，数据位8，停止位1，无校验

## 🚀 快速开始

### 环境要求

- Android Studio 3.0+
- Android SDK 27+
- 支持USB OTG的Android设备
- CH340 USB转串口模块

### 安装集成

1. **克隆项目**
```bash
git clone https://github.com/vancexin/USB-OTG-CH340-UART-interface.git
```

2. **导入模块**
将 `ch340-library` 模块导入到你的Android项目中

3. **添加依赖**
在你的 `app/build.gradle` 文件中添加：
```gradle
implementation project(':ch340-library')
```

4. **添加权限**
在 `AndroidManifest.xml` 中添加必要权限：
```xml
<uses-feature
    android:name="android.hardware.usb.host"
    android:required="true" />

<uses-permission android:name="android.hardware.usb.host" />
```

### 基本使用

#### 1. 初始化库
```java
// 在Application或Activity中初始化
CH340Master.initialize(getApplicationContext());
```

#### 2. 设置USB权限监听
```java
public class MainActivity extends AppCompatActivity implements InitCH340.IUsbPermissionListener {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 设置权限监听器
        InitCH340.setListener(this);
        
        // 初始化CH340
        CH340Master.initialize(getApplicationContext());
    }
    
    @Override
    public void result(boolean isGranted) {
        if (!isGranted) {
            // 请求USB权限
            PendingIntent permissionIntent = PendingIntent.getBroadcast(
                this, 0, new Intent(ACTION_USB_PERMISSION), 0);
            InitCH340.getmUsbManager().requestPermission(
                InitCH340.getUsbDevice(), permissionIntent);
        }
    }
}
```

#### 3. 发送数据
```java
// 发送ASCII格式数据
String data = "Hello CH340!";
CH340Util.writeData(data.getBytes(), "ascii");

// 发送HEX格式数据
CH340Util.writeData(data.getBytes(), "hex");
```

#### 4. 接收数据
数据接收通过后台线程自动处理，接收到的数据会通过回调方式通知应用层。

## 📱 应用示例

项目包含一个完整的示例应用，展示了如何使用该库进行串口通信：

- **主界面**: 提供数据输入框和发送按钮
- **格式切换**: 支持ASCII和HEX格式切换
- **实时通信**: 实时显示发送和接收的数据

### 界面功能

- `EditText`: 输入要发送的数据
- `Button (Format)`: 切换数据格式（ASCII/HEX）
- `Button (Send)`: 发送数据到串口设备

## 🏗️ 项目架构

```
USB-OTG-CH340-UART-interface/
├── app/                          # 示例应用
│   ├── src/main/java/
│   │   └── com/xpf/ch340_host/
│   │       ├── MainActivity.java # 主Activity
│   │       └── MyApplication.java# 应用程序类
│   └── src/main/res/            # 资源文件
├── ch340-library/               # CH340通信库
│   ├── libs/
│   │   └── CH34xUARTDriver.jar  # CH340驱动JAR包
│   └── src/main/java/
│       └── com/xpf/ch340_library/
│           ├── CH340Master.java      # 库初始化类
│           ├── driver/
│           │   └── InitCH340.java    # CH340驱动初始化
│           ├── utils/
│           │   └── CH340Util.java    # 数据处理工具
│           ├── runnable/
│           │   └── ReadDataRunnable.java # 数据读取线程
│           └── logger/
│               └── LogUtils.java     # 日志工具
└── README.md
```

### 核心类说明

| 类名 | 功能描述 |
|------|----------|
| `CH340Master` | 库的全局初始化和上下文管理 |
| `InitCH340` | CH340设备的初始化、配置和权限管理 |
| `CH340Util` | 数据读写和格式转换工具 |
| `ReadDataRunnable` | 后台数据读取线程 |
| `LogUtils` | 调试日志输出工具 |

## 🔌 硬件连接

### 连接示意图
```
Android设备 <--USB OTG--> CH340模块 <--UART--> 目标设备
```

### 硬件要求

1. **Android设备**: 支持USB OTG功能
2. **USB OTG线**: 连接Android设备和CH340模块
3. **CH340模块**: USB转串口转换器
4. **目标设备**: 支持UART通信的设备

### 引脚说明

| CH340引脚 | 功能 | 目标设备连接 |
|-----------|------|-------------|
| VCC | 电源正极 | 3.3V或5V |
| GND | 电源负极 | GND |
| TXD | 发送数据 | 目标设备RXD |
| RXD | 接收数据 | 目标设备TXD |

## 🛠️ API文档

### CH340Master

#### `initialize(Context context)`
初始化CH340库
- **参数**: `context` - 应用程序上下文
- **返回**: 无

### InitCH340

#### `setListener(IUsbPermissionListener listener)`
设置USB权限监听器
- **参数**: `listener` - 权限结果回调接口
- **返回**: 无

#### `getDriver()`
获取CH340驱动实例
- **返回**: `CH34xUARTDriver` - 驱动实例

#### `isIsOpenDeviceCH340()`
检查CH340设备是否已打开
- **返回**: `boolean` - 设备状态

### CH340Util

#### `writeData(byte[] byteArray, String format)`
向串口写入数据
- **参数**: 
  - `byteArray` - 要发送的字节数组
  - `format` - 数据格式（"ascii" 或 "hex"）
- **返回**: `int` - 写入结果（-1表示失败）

## ❓ 常见问题

### Q: 设备无法识别CH340模块？
A: 请检查：
1. 设备是否支持USB OTG功能
2. USB OTG线是否正常工作
3. CH340模块是否正常供电
4. 应用是否已获得USB设备访问权限

### Q: 数据发送失败？
A: 请确认：
1. CH340设备是否已成功初始化
2. 串口参数设置是否正确
3. 目标设备是否正常工作
4. 数据格式是否正确

### Q: 无法接收数据？
A: 请检查：
1. 硬件连接是否正确（TX-RX交叉连接）
2. 波特率等串口参数是否匹配
3. 目标设备是否正在发送数据

### Q: 应用崩溃或无响应？
A: 请确认：
1. 是否在主线程中进行了耗时操作
2. 是否正确处理了USB设备断开事件
3. 查看LogCat输出的错误信息

## 🤝 贡献指南

我们欢迎任何形式的贡献！请遵循以下步骤：

1. **Fork** 本仓库
2. **创建** 特性分支 (`git checkout -b feature/AmazingFeature`)
3. **提交** 更改 (`git commit -m 'Add some AmazingFeature'`)
4. **推送** 到分支 (`git push origin feature/AmazingFeature`)
5. **创建** Pull Request

### 代码规范

- 遵循Android官方代码规范
- 添加必要的注释和文档
- 确保代码通过现有测试
- 新功能需要添加相应测试

## 📄 更新日志

### v1.0 (2018-03-12)
- ✨ 初始版本发布
- ✨ 支持CH340 USB转串口通信
- ✨ 实现USB OTG设备检测
- ✨ 添加ASCII和HEX数据格式支持
- ✨ 实现无root权限通信

## 📞 联系方式

- **作者**: x-sir
- **邮箱**: [请在GitHub上联系](https://github.com/vancexin)
- **项目地址**: https://github.com/vancexin/USB-OTG-CH340-UART-interface
- **问题反馈**: [GitHub Issues](https://github.com/vancexin/USB-OTG-CH340-UART-interface/issues)

## 🙏 致谢

感谢以下开源项目和贡献者：

- [WCH官方CH340驱动](http://www.wch.cn/) - 提供CH340芯片驱动支持
- Android开源社区 - 提供USB Host API支持
- 所有为本项目贡献代码和建议的开发者

---

如果这个项目对你有帮助，请给我们一个 ⭐ Star！

## License

```
   Copyright (C) [2018] [x-sir, USB-OTG-CH340-UART-interface Open Source Project]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```