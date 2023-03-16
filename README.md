# JB-Node
Java build for node application

## 该项目已废弃

不建议使用 Java 或 Go 来编译 Node.js 源代码，因为 Node.js 是用 C/C++ 语言编写的，这些语言在系统编程中的性能和稳定性方面非常出色。Java 和 Go 不适合编译 C/C++ 程序，因此使用它们编译 Node.js 可能会出现不兼容的问题，而且性能也不如直接使用 C/C++ 编译器来编译。

如果你想编译 Node.js 源代码，建议使用 Node.js 自带的编译工具来进行编译。Node.js 的源代码采用 GYP（Generate Your Projects）构建系统，可以根据不同的平台生成相应的项目文件。在 Windows 平台上，你可以使用 Visual Studio 编译器来编译 Node.js。在 Mac 平台上，你可以使用 Xcode 编译器来编译 Node.js。

以下是编译 Node.js 的一些参考资料：

• Node.js 官方文档：https://nodejs.org/en/docs/
• Node.js 源代码：https://github.com/nodejs/node
• 编译 Node.js（Windows）：https://github.com/nodejs/node-gyp#on-windows
• 编译 Node.js（Mac）：https://github.com/nodejs/node-gyp#on-macos