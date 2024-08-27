[TOC]

# 进程

进程的基本元素：程序代码、和代码相关的一组数据

进程：当处理器开始处理一段代码时，我们将执行的实体叫做进程

程序运行：程序、代码、PCB

进程的状态：
- 就绪
- 运行
- 阻塞
- 终止
- 创建
  - 原因
    - 新的作业
    - 交互登录
    - 
- 挂起



PCB
- 标识符
  - PID：该进程的标识符
  - 父进程的标识符
  - UID：用户标识符
- 处理器状态信息
  - 用户可见寄存器
  - 控制和状态寄存器
  - 栈指针
  - PWS：程序状态字寄存器。包括条件码，如EFLAG
- 进程控制信息
  - 调度和状态信息
  - 数据结构
  - 进程间通信
  - 进程特权
  - 存储管理
  - 资源所有权和使用情况



PCB存储进程全部信息，




内核：操作系统中包含重要系统功能的部分，常驻内存，提高运行效率

内核功能
- 资源管理
  - 进程管理
  - 存储管理
  - IO设备管理
- 支撑功能
  - 中断处理
  - 时钟管理：时间片控制
  - 记账：统计、监控

执行模式
- 用户模式：具有较少的优先级，用户程序在用户模式下运行
- 内核模式：更多优先级、运行系统内核、特权模式下运行和访问

模式原因：保护操作系统和重要系统表不受程序干扰

当用户调用操纵系统服务或中断时相关位置均为内核模式
当从系统服务返回用户进程时，执行模式均为用户模式

创建进程：
分配PID->分配空间->初始化PCB->建立连接，插入后就绪或就绪挂起->建立或扩充其他数据结构



系统中断
- 普通中断
  - 时钟中断
  - IO中断
  - 内存失败
  - 超时
- 陷阱
  - 产生错误或异常
  - 产生致命错误或异常，进入退出态，切换进程
  - 不致命错误，尝试恢复


进程切换
- 普通中断
- 陷阱
- 系统调用

模式切换
- 系统调用或中断
- 进程状态切换
  - 步骤：保存处理器上下文->更新当前运行状态的PCB->将进程的PCB移至相应的队列（就绪、阻塞、挂起）->选择另一进程执行->更新其PCB->更新内存管理数据结构-> 恢复进程上下文



创建进程

- 在进程表中为新进程分配一个空项
- 为子进程分配一个唯一的进程标识符
- 复制父进程进程映像，但共享内存除外
- 增加父进程拥有的文件计数器，
- 将子进程设为就绪
- 将子进程ID返回给父进程，返回0给子进程



进程的基本属性

- 拥有独立资源
- 调度和执行的基本单位



线程是调度与分派的单位



线程中的结构

- 执行状态
- 线程上下文
- 执行栈
- 局部变量的静态存储空间
- 与其他线程共享的内存与资源

线程优点

- 创建时间少：有许多内存是共享的	
- 终止时间少：同上
- 切换时间少：保存资源少，用户态线程切换不需要内核
- 提高通信效率：



创建线程

分配TID ->分配少量内存空间->分配少量TCB->



ULT

- 线程由用户进程管理
- 线程感受不到内核



ULT优点

- 不需要切换内核模式
- 调度策略不同
- 在任何操作系统上都可以



缺点

- 阻塞进程与进程
  - 解决：将阻塞调用转为非阻塞，写成多进程而不是多线程



KLT优先

- 把同一个进程内的多个线程调度到多处理器上
- 当一个线程阻塞时，内核可以调度同一进程内的其他线程
- 内核例程本身也可以是多线程的



KLT缺点

- 切换线程需要内核模式



混合方式

- 在用户空间创建线程
- 由用户调度同步
- 映射到内核线程



长程调度

- 决定哪个程序进入系统中断，控制系统并发度
- 将进入的作业变为进程
- 添加供短程调度的队列



概念

- 响应时间：从提交请求到获得响应是时间 输入时间 处理时间 显示时间
- 截止时间：
- 系统吞吐量：
- 周转时间：进程从提交到完成的时间间隔。 等待时间（）+时间执行的时间
- 平均周转时间
- 带权周转时间（归一化周转时间）： 周转时间/服务时间（占用CPU时间）
- 平均带权周转时间：多个带权周转时间的平均值



决策

- 



响应时间：用户从一个请求开始到接收响应之间的时间间隔

响应时间的构成：输入传送时间，处理时间、响应传送时间



截止时间：某个任务开始执行的最迟时间



系统吞吐：在单位时间系统完成的系统进程数



处理器利用效率：处理器处于忙的时间百分比



周转时间：一个进程从提交到完成的时间间隔

周转时间构成：等待资源时间、执行时间



平均周转时间：多个周转时间的平均值$T=\frac{1}{n}(\sum_{i=1}^nT_i)$



带权周转时间：进程周转时间与系统给它的服务时间之比 $W_i=\frac{T_i}{T_{si}}$



平均带权周转时间：多个带权周转时间的平均值



决策模式

- 非抢占：
- 抢占



调度算法：



FCFS（先来先服务）：选择就绪队列中存在最长的进程运行

- 非抢占式
- 有利于CPU繁忙型进程，不利于IO繁忙型
- 不适合直接用于单处理器系统
- 平均周转时间长
- 对长进程有利



RR：每个进程分配时间片，周期性产生时钟中断，中断进程进入队列末尾，若进程在阻塞或接受则立即切换CPU

- 抢占式
- 用于分时系统
- 时间片是设置与系统性能有关
- 对CPU繁忙有利，IO繁忙不利

 

VRR：在RR中增加一个辅助队列，优先于就绪队列，但占用的处理时间小于就绪队列



SPF/SJF（短进程优先）：前提为执行时间预知

- 非抢占
- 长进程饥饿
- 利于短进程，减小平均周转时间
- 缺少剥夺机制，不利于分时系统于事务处理
- 不准确



SRT（剩余时间最短优先）：在SJF上增加剥夺机制

- 不偏爱长进程，也不产生过多中断，减少时间开销
- 时间周转少，短进程只要就绪就可以立即执行
- 需要预估服务时间
- 长进程饥饿
- 记录已经服务时间



HRRN（响应比高者优先）：当进程执行完毕或需要阻塞时，选择就绪队列中响应比最高的进程投入执行
$$
R_p=\frac{等待时间+要求服务时间}{要求服务时间}=\frac{w+s}{s}
$$

- 动态调度算法
- 算法说明了进程年龄
- 照顾了长短进程，也考虑了先后次序
- 调度前需要计算响应比，增加开销



FB（反馈算法）：设立多个优先级的队列，优先调度优先级高的队列，执行结束后降级





互斥

dekker算法



peterson算法





信号量

- P操作：信号量减一，若为负数则阻塞进程
- V操作：信号量加一，若小于等于一则阻塞





互斥 软件实现



```c++
int turn=0;
void P0()
{
    while(turn!=0);
    
    Critical Section;
    
    turn=1;
    
    Remian Section;
}

void P1()
{
    while(turn!=1);
    
    Critical Section;
    
    turn=0;
    
    Remain Section;
}
```





|                           CPU                            |
| :------------------------------------------------------: |
| turn=0 ; P0 : while(turn!=0);  -> P0 : Critical Section; |
|                         P0 crash                         |



实现了互斥

在程序失败时会影响另一个程序执行，违反空闲让进

忙等



flag

```c++
bool flag[2]={false,false};
void P0()
{
    while(flag[1]);
    flag[0]=true;
    
    Critical section;
    
    flag[0]=flase;
    
    Remain Section; 
}
void P1()
{
    while(flag[0]);
    flag[1]=true;
    
    Critical Section;
    
    flag[1]=false;
    
    Remain Section;
}
```



|                             CPU                              |
| :----------------------------------------------------------: |
| flag={false,false} ; P0 : while(flag[1]); -> flag={flase,false}; |
| flag={flase,false} ; P0 : flag[0]=true ; -> flag={true,false}; |
|           flag={true,false} ; P1 : while(flag[0]);           |
| flag={true,false} ; P1 : flag[1]=true;  -> flag={true,true}; |
|           flag={true,true}; P0 : Critical Section;           |
|           flag={true,true}; P1 : Critical Section;           |

没有实现互斥

忙等



```c++
bool flag[2]={false,false};
void P0()
{
    flag[0]=true;
    while(flag[1]);
    
    Critical section;
    
    flag[0]=flase;
    
    Remain Section; 
}
void P1()
{
    flag[1]=true;
    while(flag[0]);
    
    Critical Section;
    
    flag[1]=false;
    
    Remain Section;
}
```



|                             CPU                             |
| :---------------------------------------------------------: |
| flag={false,false}; P0 : flag[0]=true; -> flag={true,false} |
|  flag={true,false}; P1 : flag[1]=true; -> flag={true,true}  |
|                    P0 : while(flag[1]);                     |
|                    P1 : while(flag[0]);                     |

实现互斥

死锁



flag 谦让

```c++
bool flag[2]={false,false};
void P0()
{
    flag[0]=true;
    while(flag[1])
    {
        flag[0]=false;
    }
    
    Critical section;
    
    flag[0]=flase;
    
    Remain Section; 
}
void P1()
{
    flag[1]=true;
    while(flag[0])
    {
        flag[1]=false;
    }
    
    Critical Section;
    
    flag[1]=false;
    
    Remain Section;
}
```



|                             CPU                              |
| :----------------------------------------------------------: |
| flag={false,false} ; P0 : flag[0]=true; -> flag={true,false}; |
|  flag={true,false};  P1 : flag[1]=ture; -> flag={true,true}  |
|            flag={true,true}; P0 : while(flag[1])             |
|            flag={true,true}; P1 : while(flag[0])             |
| flag={true,true}; P0 : flag[0]=false; -> flag={false,true};  |
| flag={false,true}; P1 : flag[1]=false; -> flag={false,false}; |
|           flag={false,false}; P0 : while(flag[1])            |
|           flag={false,false}; P1 : while(flag[0])            |
|                    P0 : Critical Section;                    |
|                    P1 : Critical Section;                    |



没有互斥



flag 谦让 等待

```c++
bool flag[2]={false,false};
void P0()
{
    flag[0]=true;
    while(flag[1])
    {
        flag[0]=false;
        Sleep(Random);
        flag[0]=true;
    }
    
    Critical section;
    
    flag[0]=flase;
    
    Remain Section; 
}
void P1()
{
    flag[1]=true;
    while(flag[0])
    {
        flag[1]=false;
        Sleep(Random);
        flag[1]=true;
    }
    
    Critical Section;
    
    flag[1]=false;
    
    Remain Section;
}
```

活锁



Dekker

```c++
int turn=0;
bool flag={false,false}

void P0()
{
    while(true)
    {
        flag[0]=true;
        while(flag[1])
        {
            if(turn==1)
            {
                flag[0]=false;

                while(turn==1);
                flag[0]=true;
            }
        }

        Critical Section;

        turn=1;
        flag[0]=false;

        Remain Section;
    }
}

void P1()
{
    while(true)
    {
        flag[1]=true;
        while(flag[0])
        {
            if(turn==0)
            {
                flag[1]=false;

                while(turn==0);
                flag[1]=true;
            }
        }

        Critical Section;

        turn=0;
        flag[1]=false;

        Remain Section; 
    }
}
```



Peterson

```c++
int turn=0;
flag={false,false};

void P0()
{
    while(true)
    {
        flag[0]=ture;
        turn=1;
        while(flag[1] && turn==1);
        
        Critical Section;
        
        flag[0]=false;
        
        Remain Section;
    }
}

void P1()
{
    while(true)
    {
        flag[1]=ture;
        turn=0;
        while(flag[0] && turn==0);
        
        Critical Section;
        
        flag[1]=false;
        
        Remain Section;
    }
}
```



软件方法无法解决忙等

难以控制多个程序互斥





硬件互斥



屏蔽中断

- 只用于单处理器系统
- 通过禁止中断避免进程中断
- 执行效率下降
  - 无法响应外部请求
  - 无法切换进程
- 无法在多处理器工作



专用机器指令



compare&swap

```c++
int compare&swap(int* word,int testval,int newval)
{
    int oldval;
    oldval=*word;
    if(oldval==testval)
    {
        *word=newval;
    }
    return oldval;
}
```



exchange

```c++
void exchange(int* register,int* memory)
{
    int tmp;
    tmp=*memory;
    *memory=*register;
    *register=tmp;
}
```



```c++
int const n;
int bolt;
void P(int i)
{
    while(true)
    {
        int key=1;
        do
        {
            exchange(&key,&bolt);
        }
        while(key!=0);
     	
        Critical Setion;
        
        bolt=0;
        
        Remain Section;
        
    }
}

void main()
{
    bolt=0;
    begin(P0(0)...Pn(n));
}
```



硬件方法

- 支持多处理器
- 简单
- 支持多临界区
- 忙等
- 可能饥饿
- 可能死锁



信号量



二元信号量

```c++
struct binary_semaphore
{
    emun {0,1} value;
    queueType queue;
};

void semWaitB(binary_semaphore s)
{
    if(s.value==1)
    {
        s.value=0;
    }
    else
    {
        block;
    }
}

void semSignalB(binary_semaphore s)
{
    if(s.queue is empty)
    {
        s.value=1;
    }
    else
    {
    	place to ready;
    }
}
```





生产者与消费者



- 一个或多个生产者向缓存中放入数据
- 一个或多个消费者从缓存中读出数据
- 任何时候都只有一个生产者或者消费者在缓存中
- 缓存写满时不允许生产者向缓存中写入
- 缓存为空时不允许消费者向缓存中读取数据



```c++
semaphore empty=n,mutex=1,full=0;
void Write()
{
    while(true)
    {
        P(empty);
        P(mutex);
        Write;
        V(mutex);
        V(full);
    }
}

void Reader()
{
    while(true)
    {
        P(full);
        P(mutex);
        Read;
        V(mutex);
        V(empty);
    }
}
```



```c++
semaphore empty=n,mutex=1,full=0;
void Write()
{
    while(true)
    {
        P(mutex);
        P(empty);
        Write;
        V(mutex);
        V(full);
    }
}

void Reader()
{
    while(true)
    {
        P(mutex);
        P(full);
        Read;
        V(mutex);
        V(empty);
    }
}
```

死锁



水果问题



1. 

- 爸爸向盘子中放入苹果或者橘子，儿子只吃苹果，女儿只吃橘子。

- 盘子最多可以放入n个水果

- 每次只有一个人可以操作盘子



```c++
semaphore mutex=1,empty=n,orange=0,apple=0;
void Dad()
{
    while(true)
    {
        fruit=Random();
        P(empty);
        P(mutex);
        Put Fruit;
        if(fruit==apple)
        {
            V(apple);
        }.
        else
        {
            V(orange);
        }
        V(mutex);
    }
}

void Daughter()
    
{
    while(true)
    {
        P(orange);
        P(mutex);
        Get Orange;
        V(mutex);
        V(empty);
    }
}

void Son()
{
    while(true)
    {
        P(apple);
        P(mutex);
        Get Apple;
        V(mutex);
        V(empty);
    }
}
```



2. 

- 妈妈可以向盘子放入橘子，爸爸可以向盘子里放入苹果
- 儿子只吃苹果，女儿只吃橘子
- 当盘子为空时才可以放入一个水果
- 每次盘子又能由一个人操作



```c++
semaphore mutex=1,apple=0,orange=0,empty=1,

void Mon()
{
    while(true)
    {
        P(empty);
        P(mutex);
        Put Orange;
        v(mutex);
        V(orange);
    }
}

void Dad()
{
    P(empty);
    P(mutex);
    Put Apple;
    V(mutex);
    V(apple);
}

void Son()
{
    P(apple);
    P(mutex);
    Get Apple;
    V(mutex);
    V(empty);
}

void Daughter()
{
    P(orange);
    P(mutex);
    Get Orange;
    V(mutex);
    V(empty);
}
```



```c++
semaphore plate=1,orange=0,apple=1;

void Dad()
{
    while(true)
    {
        P(plate);
        Put Apple;
        V(apple);
    }
}

void Mon()
{
    while(true)
    {
        P(plate);
        Put Orange;
        V(orange);
    }
}

void Daughter()
{
    while(true)
    {
        P(orange);
        Get Orange;
        V(plate);
    }
}

void Son()
{
    while(true)
    {
        P(apple);
        Get Apple;
        V(plate);
    }
}
```





3. 

- 盘子最多可以放入两个水果
- 爸爸放入苹果，妈妈放入橘子
- 当盘子同时存在苹果和橘子时女儿才从中取走所有水果
- 盘子每次只能由一个人操作



```c++
semaphore empty_orange=1,empty_apple,orange=0,apple=0;

void Dad()
{
    while(true)
    {
    	P(empty_apple);
        Put Apple;
        V(apple);
    }
}

void Mon()
{
    while(true)
    {
        P(empty_orange);
        Put Orange;
        V(Orange);
    }
}

void Daughter()
{
    while(true)
    {
        P(apple);
        P(orange);
        Get Fruits;
        V(empty_orange);
        V(empty_apple);
    }
}
```







4. 

- 女儿负责画画
- 女儿画完后必须给爸爸妈妈都看过后才可以继续画画



```c++
semaphore dad_saw=1,mon_saw=1,dad_can_see=0,mon_can_see=0;

void Daughter()
{
    while(true)
    {
        P(dad_see);
        P(mon_see);
        Draw;
        V(mon_can_see);
        V(dad_can_see);
    }
}

void Dad()
{
    while(true)
    {
        P(dad_can_see);
        See;
        V(dad_saw);
    }
}

void Mon()
{
    while(true)
    {
        P(mon_can_see);
        See;
        V(mon_saw);
    }
}
```





5. 

- 有四个并发进程计算Y=X^2+X^3
- P1不断产生随机数并写入A
- P2从A中读出数据并计算其平方最后放入缓冲区B
- P3从A中读取数据并计算其立方最后放入缓冲区C
- P4从B和C取出数据计算和
- A、B、C的大小均为1



```c++
semaphore A12empty=1,A13empty=1,Bempty=1,Cempty=1,A12full=0,A13empty=0,Bfull=0,Cfull=0;

void P1()
{
    while(true)
    {
        P(A12empty);
        P(A13empty);
        Write A;
        V(A12full);
        V(A13full);
    }
}

void P2()
{
    while(true)
    {
        P(A12full);
        Read A;
        V(A12empty);
        
        P(Bempty);
        Write B;
        V(Bfull);
    }
}

void P3()
{
    while(true)
    {
        P(A13full);
        Read A;
        V(A13empty);
        
        P(Cempty);
        Write C;
        V(Cfull);
    }
}

void P4()
{
    while(true)
    {
        P(Bfull);
        Read B;
        V(Bempty);
        
        P(Cfull);
    	Read C;
        V(Cempty);
    }
}
```







读者写者



1. 读者优先

```c++

semaphore write=1,mutex=1;
int rn=0;

void Wrtier()
{
    while(true)
    {
        P(write);
        Write;
        V(write);
    }
}

void Reader()
{
    while(true)
    {
        P(mutex);
        rn++;
        if(rn==1)
        {
            P(write);
        }
        V(mutex);
        Read;
        P(mutex)
        rn--;
        if(rn==0)
        {
            V(write);
        }
        P(mutex);
    }
}
```



3. 公平



```c++
semaphore mutex=1,write=1,readwrite=1;
int rn=0;

void Writer()
{
    while(true)
    {
        P(readwrite);
        P(write);
        Write;
        V(write);
        V(readwrite);
    }
}

void Reader()
{
    while(true)
    {
        P(readwrite);
        P(mutex);
        rn++;
        if(rn==1)
        {
            P(write);
        }
        V(mutex);
        V(readwrite);
        Read;
        P(mutex);
        rn--;
        if(rn==0)
        {
            V(write);
        }
        V(mutex);
    }
}
```





2. 写者优先

```c++
semaphore wmutex=1,rmutex=1,read=1,write=1;
int wn=0,rn=0;

void Writer()
{
    while(true)
    {
        P(wmutex);
		wn++;
        if(wn==1)
        {
            P(read);
        }
        V(wmutex);
        
        P(write);
        Write;
        V(write);
        
        P(wmutex);
        wn--;
        if(wn==0)
        {
            V(read);
        }
        V(wmutex);
    }
}

void Reader()
{
    while(true)
    {
        P(read);
        P(rmutex);
        rn++;
        if(rn==1)
        {
            P(write);
        }
        V(rmutex);
        V(read);
        
        Read;
        
        P(rmutex);
       	rn--;
        if(rn==0)
        {
            V(write);
        }
        V(rmutex);
    }
}
```



|            CPU             |
| :------------------------: |
|  R1 : P(read); -> read=0   |
| W1 : P(wmutex); ->wmutex=0 |
|   R1 : V(read) -> read=1   |
|   R2 : P(read) -> read=0   |
|         R1 : Read;         |
|         R2 : Read;         |



```c++
semaphore wmutex=1,rmutex=1,read=1,write=1,z=1;
int wn=0,rn=0;

void Writer()
{
    while(true)
    {
        P(wmutex);
		wn++;
        if(wn==1)
        {
            P(read);
        }
        V(wmutex);
        
        P(write);
        Write;
        V(write);
        
        P(wmutex);
        wn--;
        if(wn==0)
        {
            V(read);
        }
        V(wmutex);
    }
}

void Reader()
{
    while(true)
    {
        P(z);
        P(read);
        P(rmutex);
        rn++;
        if(rn==1)
        {
            P(write);
        }
        V(rmutex);
        V(read);
        V(z);
        
        Read;
        
        P(rmutex);
       	rn--;
        if(rn==0)
        {
            V(write);
        }
        V(rmutex);
    }
}
```



- 独木桥

1. 有一座东西方向的独木桥，每次只能有一人通过，且不允许行人在桥上停留。东、西两端各有若干行人在等待过桥。

```c++
semaphore bridge=1;

void EastWest()
{
    P(bridge);
    East to West
    V(bridge);
}

void WestEast()
{
    P(bridge);
    West to East;
    V(bridge);
}
```



2. 有一座东西方向的独木桥，同一方向的行人可连续过桥。当某一方向有行人过桥时，另一方向行人必须等待。桥上没有行人过桥时，任何一端的行人均可上桥。

```c++
semaphore ewmutex=1,wemutex=1,brigde=1;
int ewn=0,wen=0;

void EastWest()
{
    while(true)
    {
        P(ewmutex);
        ewn++;
        if(ewn==1)
        {
			P(brige);
        }
        V(ewmutex);
        East to West;
        P(ewmutex);
        ewn--;
        if(ewn==0)
        {
            V(brigde);
        }
        V(ewmutex);
    }    
}

void WestEast()
{
    while(true)
    {
        P(wemutex);
        wen++;
        if(wen==1)
        {
            P(bridge);
        }
        V(wemutex);
        West to East;
        P(wemutex);
        wen--;
        if(wen==0)
        {
            V(brigde);
        }
        V(wemutex);
    }
}
```



3. 有一座东西方向的独木桥，同一方向的行人可连续过桥。当某一方向有行人过桥时，另一方向行人必须等待。桥上没有行人过桥时，任何一端的行人均可上桥。出于安全考虑，独木桥的最大承重为4人，即同时位于桥上的行人数目不能超过4。

```c++
semaphore ewmutex=1,wemutex=1,brigde=1,n=4;
int ewn=0,wen=0;

void EastWest()
{
    while(true)
    {
        P(ewmutex);
        ewn++;
        if(ewn==1)
        {
			P(brige);
        }
        V(ewmutex);
        P(n);
        East to West;
        V(n);
        P(ewmutex);
        ewn--;
        if(ewn==0)
        {
            V(brigde);
        }
        V(ewmutex);
    }    
}

void WestEast()
{
    while(true)
    {
        P(wemutex);
        wen++;
        if(wen==1)
        {
            P(bridge);
        }
        V(wemutex);
        P(n);
        West to East;
        V(n);
        P(wemutex);
        wen--;
        if(wen==0)
        {
            V(brigde);
        }
        V(wemutex);
    }
}
```





理发师

理发店有1位理发师、1把理发椅和5把供等候理发的顾客坐的椅子。如果没有顾客，则理发师睡觉。当一个顾客到来时，他必须叫醒理发师。如果理发师正在理发时又有顾客到来，则如果有空椅子可坐，他就坐下来等。如果没有空椅子，他就离开。

```c++
semaphore ;
int n ;

void Barber()
{
    while(true)
    {
        
    }
}

void Customer()
{
    while(true)
    {
        n++;
        if(n==1)
        {
            Wake barber up;
        }
        if(n==6)
        {
            n--;
        }
        else
        {
            P(chair);
            P(haircut);
            V(chair);
            Hair Cut;
            V(haircut);
            n--;
        }
    }
}
```

