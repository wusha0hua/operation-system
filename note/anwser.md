[TOC]

# 第一章

1. 有效性、方便性、可扩展性、开放性

2. 作为用户与计算机硬件之间的接口、计算机资源的管理者、对计算机资源的抽象

3. os首先在裸机上覆盖一层io设备管理软件，实现了对计算机硬件的第一层抽象。在第一层软件上覆盖文件管理软件，实现了对硬件资源操作的第二层抽象。os通过在计算机硬件上安装多层系统软件，增强了系统功能，隐藏了对硬件操作的细节，由它们共同实现了对计算机资源的抽象。

4. 不断提高的计算机资源利用率；方便用户；器件不断更新迭代；计算机体系结构不断发展

5. 由外围机或主机输入输出的区别

6. 缩短作业平均周转时间；人机交互能力使用户直接控制自己的作业；主机的共享使用多用户能同时使用一台计算机

7. 关键问题是当用户在自己的终端上输入命令后，系统应及时接收并处理该命令，在用户能接收的延时内返回结果；针对接收问题，在系统中设置多路卡；为每个终端配置缓冲区，暂存输入命令；针对及时处理，应使所有用户作业都进入内存，并分配时间片

8. 实时操作系统是能够及时响应外部请求并在规定时间完成，更好地满足实时控制领域和实时信息处理领域的需要。

9. 一定在规定时间内完成

10. 实时信息处理系统具有交互性，但人与系统的交互仅限于访问系统中某些特定的专用服务程序

11. 并发性、共享性、虚拟性和异步性四个基本特征；最基本的特征是并发性和共享性。

12. 一般情况下的共享与操作系统环境下的共享其含义并不完全相同。前者只是说明某种资源能被大家使用,如图书馆中的图书能提供给大家借阅,但并未限定借阅者必须在同一时间(间隔)和同一地点阅读。又如,学校中的计算机机房供全校学生上机,或者说,全校学生共享该机房中的计算机设备,虽然所有班级的上机地点是相同的,但各班的上机时间并不相同。对于这样的资源共享方式,只要通过适当的安排,用户之间并不会产生对资源的竞争,因此资源管理是比较简单的。

     而在0S环境下的资源共享或称为资源复用，是指系统中的资源可供内存中多个并发执行的进程共同使用。这里在宏观上既限定了时间（进程在内存期间），也限定了地点（内存）。对于这种资源共享方式，其管理就要复杂得多，因为系统中的资源少于多道程序需求的总和，会形成它们对共享资源的争夺。所以，系统必须对资源共享进行妥善管理。

    对独占资源采用互斥共享方式。

13. 