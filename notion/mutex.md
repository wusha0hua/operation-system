
# first 

## software mutex

### mutex with turn

```c++
int turn=0;

void P0()
{
	while(true)
	{
		while(trun!=0);
		CriticalSection;
		turn=1;
	}
}

void P1()
{
	while(true)
	{
		while(trun!=1);
		CriticalSection;
		turn=0;
	}
}

```
use turn to mark which process can go into  critical section , after being out of critical section and sets the turn as the value represent for other side

bug : 
- while crashing in critical section , the value of turn will not be set again , and deathclock occurs 
- busy wait 
- while no process is in critical section and other process maybe can not go into critical section

error : at the beginning turn is 0 , and P0 pass the loop , P1 is blocking in the loop , if P0 is crashed , P0 exits , the P1 will block in the loop 

### mutex with flag


```c++
bool flag[2]={0,0};
void P0()
{
	while(true)
	{
		while(flag[1]);
		flag[0]=1;
		CriticalSection;
		flag[0]=0;
	}
}

void P1()
{
	while(true)
	{
		while(flag[0]);
		flag[1]=1;
		CriticalSection;
		flag[1]=0;
	}
}
```

feature : each process can only change its flag value . use other process'es flag value to test if it can go into critical section or blocking . while passing the test , which means other process'es flag value is false , can go into critical section . before going into , it should set its flag value as true to provent other process go into critical section . when its flag value is true , other process will block in the loop .

bug : 
- can not implement mutual exclusion
- busy wait

error : at the beginning the flag={1,0} means P0 first , and P1 is blocking in the loop . and then P0 executes flag[0]=1 , if now system switch process to P1 , and P1 can pass the loop and go into critical section together with P0  


### mutex with flag and pre-indicate

show its attitude towords critical section before go in the loop

```c++
bool flag[2]={0,0};
void P0()
{
	while(true)
	{
		flag[0]=true;
		while(flag[1]);
		CriticalSection;
		flag[0]=false;
	}
}

void P1()
{
	while(true)
	{
		flag[1]=true;
		while(flag[0]);
		CriticalSection;
		flag[1]=false;
	}
}
```

feature : a process can only change its flag value and test if it can go into critical section by other flag . before go in the loop , set its flag as true in advance which can avoid system switch process 

bug : 
- dead lock 

error : at the beginning flag={1,0} , and P0 executes flag[0]=true , P1 executes flag[1]=true , dead clock 


### mutex with flag and pre-indicate and humility

if a process'es flag value is true and other process'es flag value is true too , let it go into critical section first

```c++
bool flag[2]={0};

void P0()
{
	while(true)
	{
		flag[0]=true;
		while(flag[1])
		{
			flag[0]=false;
			Wait for Random Time;
			flag[0]=true;
		}
		CriticalSection;
		flag[0]=false;
	}
}

void P1()
{
	while(true)
	{
		flag[1]=true;
		while(flag[0])
		{
			flag[1]=false;
			Wait for Random Time;
			flag[1]=true;
		}
		CriticalSection;
		flag[1]=false;
	}
}
```

bug :
- alive clock 

error : at the beginning flag={1,0} , P0 executes flag[0]=true and P1 executes flag[]1=true . P0 and P1 will go in the loop , because each wait time is not equal to other's , they will go out the loop after few time 


### dekker

feature : mutex with flag and turn and pre-indicate and humility

```c++
int turn=0;
bool flag={0};

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
		CriticalSection;
		turn=1;
		flag[0]=false;
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
				flag[1]=flase;
				while(turn==0);
				flag[1]=ture;
			}
		}
	}
}
```


### peterson

```c++
int turn=0;
bool flag={0};

void P0()
{
	while(true)
	{
		flag[0]=true;
		turn=1;
		while(flag[1] && turn==1);
		CriticalSection;
		flag[0]=false;
	}
}

void P1()
{
	while(true)
	{
		flag[1]=true;
		turn=0;
		while(flag[0] && turn==0);
		CriticalSection;
		flag[1]=false;
	}
}
```


### evaluation

software method can not solve busy wait , hard to implement mutual exclusion for multiple processes , easy to fail and cause dead clock



## hardware mutex


### shield interrupt

feature : before into critical section , start to shield interrupt to avoid system to switch process

```c++
void P()
{
	disable interrupt;
	CriticalSection;
	enable interrupt;
}
```


## compare&swap

feature : compare test value with old value , if they are same , set old value as new value and return new value , else return new value

```c++
int compare&swap(int *word,int test,int newval)
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

feature : atomic operation . when a process is in the critical section bolt value must is 0 , other process will block in loop , when it is out of critical section , bolt value is 1 , and the first process will pass the loop 

```c++
const int n=getProcessNumber();
int bolt;

void P(int i)
{
	while(true)
	{
		while(compare&swap(&bolt,0,1)==1);
		CriticalSection;
		bolt=0;
	}
}

void main()
{
	bolt=0;
	cobegin(P(1),P(2),...,P(n));
}
```


### exchange

```c++
void exchange(int *register,int *memory)
{
	int temp;
	temp=*memory;
	*memory=*register;
	*register=temp;
}
```

feature : atomic operation . if at the beginning bolt=0 , and 0 is like a token , who get it will go into critical section , and only one process can get it

```c++
int const n=getProcessnumber();
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
		CriticalSection;
		bolt=0;
	}
}

void main()
{
	bolt=0;
	cobegin(P(1),...,P(n));
}
```

### evalution

advantage : 
- multiple processes
- easy
- multiple critical sections

disadvantage :
- busy wait
- maybe hunge
- maybe dead clock


## signal 

```c++
struct semaphore_binary
{
	enum {0,1} value;
	queueType queue;
};

void semWait(semaphore_binary s)
{
	if(s.value==1)
	{
		s.value==0;
	}
	else
	{
		s.queue.push();		//add to block queue
	}
}

void semSignal(semaphore_binary s)
{
	if(s.queue.isempty())
	{
		s.value==1;
	}
	else
	{
		s.queue.pop()
	}
}
```

```c++
struct semaphore
{
	int count;
	queueType queue;
};

void semWait(semaphore s)
{
	s.count--;
	if(s.count<0)
	{
		s.queue.push();
	}
}

void semSignal(semaphore s)
{
	s.count++;
	if(s.count<=0)
	{
		s.queue.pop();
	}
}
```

```c++
const int n=getProcessNumber();
semaphore s=1;

void P(int i)
{
	semWait(s);
	CriticalSection;
	semSignal(s);
}

void main()
{
	cobegin(P(1),..,P(n));
}
```


### Producor&Consumer


#### write and read with limited buffer 

description : there is a buffer to store data produced by writer , the buffer has n units can storage data , buffer can store new data to a buffer unit when there has any empty unit , reader can read a data unit when there has a full unit . a unit can just be read or write at the same time .

```c++
semaphore empty=n,full=0,mutex=1;

void Writer()
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

{
	P(empty);
	P(mutex);
}
{
	P(full);
	P(mutex);
}
can not exchange to 
{
	P(mutex);
	P(empty);
}
{
	P(mutex);
	P(full)
}

reason : if now empty=0 and full=n and  mutex=1 , Writer executes P(mutex) , mutex=0 , Reader is blocking at P(mutex) , Writer is blocking at P(empty)


#### fruit 1

decription : there is a plate which can put n fruits in . dad put a orange or orange on the plate , son only eats apple and daughter only eats orange . dad can put a fruit on the plate while the number of ftuit on the plate are less than n , son and daughter can get fruit from plate when there is the fruit which he/she wants to eat . only a person can operate the plate at the same time

```c++
semaphore empty=n,mutex=1,orange=0,apple=0;

void dad()
{
	while(true)
	{
		P(empty);
		P(mutex);
		fruit=PutFruit();
		V(mutex);
		if(fruit==orange)
		{
			V(orange);
		}
		else
		{
			V(apple);
		}
	}
}

void son()
{
	while(true)
	{
		P(apple);
		P(mutex);
		GetApple();
		V(mutex);
		V(empty);
	}
}

void daughter()
{
	while(true)
	{
		P(orange);
		P(mutex);
		GetOrange();
		V(mutex);
		V(empty);
	}
}
```


#### fruit 2

decription : there is a plate , dad can put a apple on the plate , mon can put a orange on the plate , son only eats apple , daughter only eats orange , dad and mon can put their fruit on the plate when the plate is empty , son and daughter can get their fruit when the plate has the fruit which he/she wants to eat . only one person can operate the plate at the same time 

four semaphore 
```c++ 
semaphore empty=1,mutex=1,orange=1,apple=1;

void dad()
{
	while(true)
	{
		P(empty);
		P(mutex);
		PutApple();
		V(mutex);
		V(apple);
	}
}

void mon()
{
	while(true)
	{
		P(empty);
		P(mutex);
		PutOrange();
		V(mutex);
		V(orange);
	}
}

void son()
{
	while(true)
	{
		P(apple);
		P(mutex);
		GetApple();
		V(mutex);
		V(empty);
	}
}

void daughter()
{
	while(true)
	{
		P(orange);
		P(mutex);
		GetOrange();
		V(mutex);
		V(empty);
	}
}
```

three semaphore : 
```c++
semaphore mutex=1,orange=0,apple=0;

void dad()
{
	while(true)
	{
		P(mutex);
		PutApple();
		V(apple);
	}
}

void mon()
{
	while(true)
	{
		P(mutex);
		PutOrange();
		V(orange);
	}
}

void son()
{
	while(true)
	{
		P(apple);
		GetApple();
		V(mutex);
	}
}

void daughter()
{
	while(true)
	{
		P(orange);
		GetOrange();
		V(mutex);
	}
}
```


#### fruit 3

desciption : there is a plate which can put two fruit on , dad can put an apple on the plate and mon can put an orange on the plate , daughter can get fruits when there are apple and orange on the plate , only one person can operate the plate at the same time 

```c++
semaphore apple_full=0,apple_empty=1,orange_full=0,orange_empty=1;

void dad()
{
	while(true)
	{
		P(apple_empty);
		PutApple();
		V(apple_full);
	}
}

void mon()
{
	while(true)
	{
		P(orange_empty);
		PutOrange();
		V(orange_full);
	}
}

void daughter()
{
	while(true)
	{
		P(apple_full);
		P(orange_full);
		GetFruits();
		V(orange_empty);
		V(apple_empty);
	}
}
```


#### draw picture 

desciption : daughter can draw a picture , after appreciated by dad and mon can she draw anthor picture 

```c++
semaphore mon_can_see=0,mon_saw=1,dad_can_see=0,dad_saw=1;

void daughter()
{
	while(true)
	{
		P(mon_saw);
		P(dad_saw);
		Draw();
		V(mon_can_see);
		V(dad_can_see);
	}
}

void dad()
{
	while(true)
	{
		P(dad_can_see);
		Appreciate();
		V(dad_saw);
	}
}

void mon()
{
	while(true)
	{
		P(mon_can_see);
		Appreciate();
		V(mon_saw);
	}
}
```


#### caculation

desciption : P1 and P2 and P3 and P4 want to finish a caculation together , P1 create random number and store to buffer A , P2 and P3 can read buffer A and get the number , P2 caculate its square value and store to buffer B , P2 can caculate its cube calue and store to buffer C , P4 can read buffer B and buffer C and get their values and add together and output

```c++
semaphore empty12=1,empty13=1,empty24=1,empty34=1,full12=0,full13=0,full24=0,full34=0;

void P1()
{
	while(true)
	{
		P(empty12);
		P(empty13);
		PuttoBufferA();
		V(full12);
		V(full13);
	}
}

void P2()
{
	while(true)
	{
		P(full12);
		GetfromBufferA();
		V(empty12);
		Caculate();
		P(empty24);
		PuttoBufferB();
		V(full24);
	}
}

void P3()
{
	while(true)
	{
		P(full13);
		GetfromBuffer13();
		V(empty13);
		Caculate();
		P(empty34);
		PuttoBufferC();
		V(full34);
	}
}

void P4()
{
	while(true)
	{
		P(full24);
		GetfromBufferB();
		V(empty24);
		P(full34);
		GetfromBufferC();
		V(empty34);
	}
}
```


### Writer&Reader

#### reader first

```c++
int readn=0;
semaphore mutex=1,write=1;

void Writer()
{
	while(true)
	{
		P(write);
		Write();
		V(write);
	}
}

void Reader()
{
	while(true)
	{
		P(mutex);
		readn++;
		if(readn==1)
		{
			P(write);
		}
		V(mutex);
		Read();
		P(mutex);
		readn--;
		if(readn==0)
		{
			V(write);
		}
		V(mutex);
	}
}
```


#### fairness

```c++
int readn=0,writen=0;
semaphore write=1,mutex=1,wr=1;

void Writer()
{
	while(true)
	{
		P(wr);
		P(mutex);
		readn++;
		if(readn==1)
		{
			P(write);
		}
		V(mutex);
		V(wr);
		Read();
		P(mutex);
		readn--;
		if(readn==0)
		{
			V(write);
		}
		V(mutex);
	}
}

void Reader()
{
	while(true)
	{
		P(wr);
		P(write);
		Write();
		V(write);
		V(wr);
	}
}
```


#### writer first

```c++
int readn=0,writen=0;
semaphore rmutex=1,wmutex=1,write=1,read=1;

void Writer()
{
	while(true)
	{
		P(read);
		P(wmutex);
		readn++;
		if(readn==1)
		{
			P(write);
		}
		V(Wmutex);
		V(read);
		Read();
		P(rmutex);
		readn--;
		if(readn==0)
		{
			V(write);
		}
		V(wmutex);
	}
}

void Reader()
{
	while(true)
	{
		P(write);
		P(wmutex);
		writen++;
		if(writen==1)
		{
			P(read);
		}
		V(wmutex);
		V(write);
		Write();
		P(wmutex);
		writen--;
		if(writen==0)
		{
			V(read);
		}
		V(wmutex);
	}
}
```

```c++
int readn=0,writen=0;
semaphore rmutex=1,wmutex=1,write=1,read=1,rq=1;

void Write()
{
	while(true)
	{
		P(write);
		P(wmutex);
		writen++;
		if(writen==1)
		{
			P(read);
		}
		V(wmutex);
		V(write);
		Write();
		P(wmutex);
		writen--;
		if(writen==0)
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
		P(rq);
		P(read);
		P(rmutex);
		readn++;
		if(readn==1)
		{
			P(write);
		}
		V(rmutex);
		V(read);
		V(rq);
		Read();
		P(rmutex);
		readn--;
		if(readn==0)
		{
			V(write);
		}
		V(rmutex);
	}
}
```


#### bridge 1

desciption : there is a bridge which people can pass it by one direction , some people are at the east and west of the bridge want to pass the bridge 

```c++
int wen=0,esn=0;
semaphore we=1,ew=1,wem=1,ewm=1;

void EasttoWest()
{
	while(true)
	{
		P(ewm);
		P(ew);
		ewn++;
		if(ewn==1)
		{
			P(we);
		}
		V(ew);
		V(ewm);
		Pass();
		P(ewm);
		ewn--;
		if(ewn==0)
		{
			V(we);
		}
		V(ewm);
	}
}

void WesttoEast()
{
	while(true)
	{
		P(wem);
		P(we);
		wen++;
		if(wen==1)
		{
			P(ew);
		}
		V(we);
		V(wem);
		Pass();
		P(wem);
		wen--;
		if(wen==0)
		{
			V(ew);
		}
		V(wem);
	}
}
```

```c++
int wen=0,ewn=0;
semaphore we=1,ew=1,bridge=1;

void EasttoWest()
{
	while(true)
	{
		P(ewm);
		ewn++;
		if(ewn==1)
		{
			P(bridge);
		}
		V(ewm);
		Pass();
		P(ewm);
		ewn--;
		if(ewn==0)
		{
			V(bridge);
		}
		V(ewm);
	}
}

void WesttoEast()
{
	while(true)
	{
		P(wem);
		wen++;
		if(wen==1)
		{
			P(bridge);
		}
		V(wem);
		Pass();
		P(wem);
		wen--;
		if(wen==0)
		{
			V(bridge);
		}
		V(wem);
	}
}
```


#### bridge 2

description : the maximum on the bridge is 4

```c++
int wen=0,ewn=0;
semaphore empty=4,we=1,ew=1,bridge=1;

void EasttoWest()
{
	while(true)
	{
		P(ew);
		ewn++;
		if(ewn==1)
		{
			P(bridge);
		}
		P(empty);
		V(ew);
		Pass();
		P(ew);
		ewn--;
		if(ewn==0)
		{
			V(bridge);
		}
		V(empty);
		V(ew);
	}
}

void WesttoEast()
{
	while(true)
	{
		P(we);
		wen++;
		if(ewn==1)
		{
			P(bridge);
		}
		P(empty);
		V(we);
		Pass();
		P(we);
		wen--;
		if(wen==0)
		{
			V(bridge);
		}
		V(empty);
		V(we);
	}
}
```


#### barber

description : barber will sleep when there is no customer , if there is no customer on the barber chair he can sit on it , or he will set on chair , there are 5 chair , if a customer can not sit down , he will go away 

```c++
int chairn=6;
semaphore barberchair_empty=1,barberchair_full=0,chair=6,mutex=1;

void Barbaer()
{
	while(true)
	{
		P(baberchair_full);
		CutHair();
		V(barberchair_empty);
	}
}

void Customer()
{
	while(true)
	{
		P(mutex);
		if(chairn>0)
		{
			chairn--;
			V(mutex);
			P(chair);
			P(barberchair_empty);
			V(barberchair_full);
			V(chair);
			P(mutex);
			chairn++;
			V(mutex);
		}
		else
		{
			V(mutex);
			Goaway();
		}
	}
}
```


#### bank 

desciption : a bank severs  a service window and 10 seats , if there is a empty seat customer will get a token when he arrived at the bank .

```c++
int waitn=0;
semaphore ready=0,finish=1,seat=10;
void Stuff()
{
	while(true)
	{
		P(ready);
		Serve();
		V(finish);
	}
}

void Customer()
{
	while(true)
	{
		P(mutex);
		if(waitn<11)
		{
			waitn++;
			V(mutex);
			P(seat);
			P(finish);
			V(ready);
			P(mutex);
			waitn--;
			V(mutex);
		}
		else
		{
			V(mutex);
			Goout();
		}
	}
}
```

```c++
semaphore mutex=1,empty=10,full=0,service=0;

void Stuff()
{
	while(true)
	{
		P(full);
		Server();
		V(service);
	}
}

void Customer()
{
	while(true)
	{
		P(empty);
		P(mutex);
		GetToken();
		V(mutex);
		V(full);
		P(service);
	}
}
```


## Monitor


### Producer&Consumer

```c++

char buffer[n]={0};
int nextin=0,nextout=0;
int count=0;
condition notfull,notempty;

void append(char x)
{
	if(count==n)
	{
		cwait(notfull);
	}
	buffer[nextin]=x;
	nextin=(nextin+1)%n;
	count++;
	csignal(notempty);
}

void take(char x)
{
	if(count==0)
	{
		cwait(notempty);
	}
	x=buffer[nextout];
	nextout=(nextout)%n;
	count--;
	csignal(notfull);
}

void Writer()
{
	char x;
	while(true)
	{
		x=GetChar();
		append(x);
	}
}

void Reader()
{
	char x;
	while(true)
	{
		take(x);
	}
}
```


## philosopher

desciption : 5 philosophers eat around a table and there are 6 forks between every philosopher , each philosopher should hold his left fork and right fork to eat .

```c++
semaphore fork[5]={1,1,1,1,1};

void Philosopher(int i)
{
	while(true)
	{
		P(fork[i]);
		P(fork[(i+1)%5]);
		Eat();
		V(fork[(i+1)%%5]);
		V(fork[i]);
	}
}

void main()
{
	cobegin(Philosopher(0),...Philosopher(4));
}
```

bug : deadlock

```c++
semaphore fork[5]={1,1,1,1,1};

void Philosopher(int i)
{
	while(true)
	{
		P(fork[i]);
		timeout(P(fork[(i+1)%5]),T);
		Eat();
		V(fork[i]);
		V(fork[(i+1)%5]);
	}
}

void main()
{
	cobegin(Philosopher(0),...,Philosopher(4));
}
```

bug : alive clock

```c++
semaphore fork[5]={1,1,1,1,1};

void Philosopher(int i)
{
	while(true)
	{
		if(i!=4)
		{
			P(fork[i]);
			P(fork[(i+1)%5])
		}
		else
		{
			P(fork[(i+1)%5]);
			P(fork[i]);
		}
		Eat();
		if(i!=4)
		{
			V(fork[(+1)%5]);
			V(fork[i]);
		}
		else
		{
			V(fork[i]);
			V(fork[(i+1)%5]);
		}
	}
}
```


```c++
semaphore fork[5]={1,1,1,1,1};

void Philosopher(int i)
{
	while(true)
	{
		if(i%2!=0)
		{
			P(fork[i]);
			P(forl[(i+1)%5]);
		}
		else
		{
			P(fork[(i+1)%5]);
			P(fork[i]);
		}
		Eat();
		V(fork[(i+1)%5]);
		V(fork[i]);
	}
}
```

```c++
semaphore fork[5]={1,1,1,1,1},seat=4;

void main()
{
	while(true)
	{
		P(seat);
		P(fork[i]);
		P(fork[(i+1)%5]);
		Eat();
		V(fork[i]);
		V(fork[(i+1)%5]);
		V(seat);
	}
}
```

```c++
condition forkready[5];
boolean fork[5]={true};

void GetFork(int pid)
{
	int left=pid;
	int right=(pid++)%5;
	if(!fork[left])
	{
		cwait(forkready[left]);
	}
	fork[left]=false;
	if(!fork[right])
	{
		cwait(forkready[right]);
	}
	fork[right]=false;
}

void ReleaseFork(int pid)
{
	int left=pid;
	int right=(++pid)%5;
	if(queue(forkready[left]).isempty)
	{
		fork[left]=true;
	}
	else
	{
		csignal(forkready[left]);
	}
	if(queue(forkready[right]).isempty)
	{
		fork[right]=true;
	}
	else
	{
		csignal(forkready[right]);
	}
}

void Philosopher(int i)
{
	while(true)
	{
		GetFork(i);
		Eat();
		ReleaseFork(i);
	}
}
```


# second

## software 

### mutex with turn

```c++
int turn=0;

void P0()
{
	while(true)
	{
		while(turn!=0);
		CriticalSection;
		turn=1;
	}
}

void P1()
{
	while(true)
	{
		while(turn!=1);
		CriticalSection;
		turn=0;
	}
}
```


### mutex with flag

```c++
bool flag={true,false};

void P0()
{
	while(true)
	{
		while(flag[1]);
		flag[0]=true;
		CirticalSection;
		flag[0]=false;
	}
}

void P1()
{
	while(true)
	{
		while(flag[0]);
		flag[1]=true;
		CriticalSection;
		flag[1]=false;
	}
}
```


### mutex with flag and pre-indicate

```c++
bool flag[2]={true,false};

void P0()
{
	while(true)
	{
		flag[0]=true;
		while(flag[1]);
		CriticalSection();
		flag[0]=false;
	}
}

void P1()
{
	while(true)
	{
		flag[1]=true;
		while(flag[0]);
		CiticalSection;
		flag[1]=false;
	}
}
```


### mutex with flag and pre-indicate and humility

```c++
bool flag[2]={true,false};

void P0()
{
	while(true)
	{
		flag[0]=true;
		while(flag[1])
		{
			flag[0]=false;
			WaitforRandomTime();
			flag[0]=true;
		}
		CriticalSection();
		flag[0]=false;
	}
}

void P1()
{
	while(true)
	{
		flag[1]=true;
		while(flag[0])
		{
			flag[1]=flase;
			WaitforRandomTime();
			flag[1]=true;
		}
		CiriticalSection();
		flag[1]=false;
	}
}
```


### dekker

```c++
int turn=0;
bool flag[2]={true,false};

void P0()
{
	while(true)
	{
		flag[0]=true;
		while(falg[1])
		{
			if(turn==1)
			{
				flag[0]=false;
				while(turn==1);
				flag[0]=true;
			}
		}
		CriticalSection();
		flag[0]=flase;
		turn=1;
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
		CriticalSection();
		turn=0;
		flag[1]=flase;
	}
}
```


### peterson

```c++
int turn=0;
bool flag[2]={true,false};

void P0()
{
	while(true)
	{
		flag[0]=true;
		turn=1;
		while(flag[1] && turn==1);
		CriticalSection();
		flag[0]=false;
	}
}

void P1()
{
	while(true)
	{
		flag[1]=true;
		turn=0;
		while(flag[0] && turn==0);
		CriticalSecion();
		flag[1]=false;
	}
}
```


## hardware

### compare&swap

```c++
int compare&swap(int *val,int test,int newval)
{
	int oldval;
	oldval=*val
	if(*oldval==test)
	{
		*val=newval;
	}
	return oldval;
}
```

```c++
int n;
int bolt=0;

void P(int i)
{
	while(true)
	{
		while(compare&swap(&bolt,0,1)==1);
		Critical;
		bolt=0;
	}
}
```


### exchange

```c++
void exchange(int *a,int *b)
{
	int tmp=*a;
	*a=*b;
	*b=tmp;
}
```

```c++
int bolt=0;

void P()
{
	key=1;
	do
	{
		exchange(&bolt,&key);
	}
	while(key==1);
	CriticalSection();
}
```


## signal

### producer&consumer

#### limited buffer

```c++
semaphore empty=n,full=0,mutex=1;

void Writer()
{
	while(true)
	{
		P(empty);
		P(mutex);
		Write();
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
		Read();
		V(mutex);
		V(empty);
	}
}
```


#### fruit 1

```c++
semaphore empty=n,orange=0,apple=0,mutex=1;

void dad()
{
	while(true)
	{

	}
}

void son()
{
	while(true)
	{

	}
}

void daughter()
{
	while(true)
	{

	}
}
```


#### fruit 2

```c++
semaphore empty=1,orange=0,apple=0;

void dad()
{
	while(true)
	{
		P(empty);
		PutApple();
		V(apple);
	}
}

void mon()
{
	while(true)
	{
		P(empty);
		PutOrange();
		V(orange);
	}
}

void daughter()
{
	while(true)
	{
		P(orange);
		GetOrange();
		V(empty);
	}
}

void son()
{
	while(true)
	{
		P(apple);
		GetApple();
		V(empty);
	}
}
```


#### fruit 3

```c++
semaphore apple_empty=1,apple_full=1,orange_empty=1,orange_full=0;

void dad()
{
	while(true)
	{
		P(apple_empty);
		Putapple();
		V(apple_full);
	}
}

void mon()
{
	while(true)
	{
		P(orange_empty);
		PutOrange();
		V(orange_full);
	}
}


void daughter()
{
	while(true)
	{
		P(apple_full);
		P(orange_full);
		GetOrangeandApple();
		V(apple_empty);
		V(orange_empty);
	}
}
```


#### paint

```c++
semaphore mon_can_see=0,dad_can_see=0,man_saw=1,dad_saw=1;

void daughter()
{
	while(true)
	{
		P(mon_saw);
		P(dad_saw);
		Draw();
		V(mon_can_see);
		V(dad_can_see);
	}
}

void mon()
{
	while(true)
	{
		P(mon_can_see);
		Appreciate();
		V(mon_saw);
	}
}

void dad()
{
	while(true)
	{
		P(dad_can_see);
		Appreciate();
		V(dad_saw);
	}
}
```


#### caculate

```c++
semaphore empty12=1,empty13=1,empty24=1,empty34=1,full12=0,full13=0,full24=0,full34=0;

void P1()
{
	while(true)
	{
		P(empty12);
		P(empty13);
		PutDatatoA();
		V(full12);
		V(full13);
	}
}

void P2()
{
	while(true)
	{
		P(full12);
		GetDatafromA();
		V(empty12);
		P(empty24);
		PutDatatoB();
		V(full24);
	}
}

void P3()
{
	while(true)
	{
		P(empty13);
		GetDatafromA();
		V(full13);
		P(empty34);
		PutDatatoC();
		V(full34);
	}
}

void P4()
{
	while(true)
	{	
		P(full24);
		GetDatafromB();
		V(empty24);
		P(full34);
		GetDatafromC();
		V(empty34);
	}	
}
```


### writer&reader

#### reader first

```c++
int readn=0;
semaphore write=1,mutex=1;

void Reader()
{
	while(true)
	{
		P(mutex);
		readn++;
		if(readnn==1)
		{
			P(write);
		}
		V(mutex);
		Read();
		P(mutex);
		readn--;
		if(readn==0)
		{
			V(write);
		}
		V(mutex);
	}
}

void Writer()
{
	while(true)
	{
		P(write);
		Write();
		V(write);
	}
}
```


#### fairness

```c++
int readn=0;
semaphore rw=1,mutex=1,write=1;

void Reader()
{
	while(true)
	{
		P(rw);
		P(mutex);
		readn++;
		if(readn=1)
		{
			P(write);
		}
		V(mutex);
		V(rw);
		Read();
		P(mutex);
		readn--;
		if(readn==0)
		{
			V(write);
		}
		V(mutex);
	}
}

void Writer()
{
	while(true)
	{
		P(rw);
		P(write);
		Write()
		V(write);
		V(rw);
	}
}
```


#### writer first

```c++
int readn=0,writen=0;
semaphore z=1,rm=1,wm=1,write=1,read=1;

void Reader()
{
	while(true)
	{
		P(z);
		P(read);
		P(rm);
		readn++;
		if(readn==1)
		{
			P(write);
		}
		V(rm);
		V(read);
		V(z);
		Read();
		P(rm);
		readn--;
		if(readn==0)
		{
			V(write);
		}
		V(rm);
	}
}

void Writer()
{
	while(true)
	{
		P(wm);
		writen++;
		if(wm==1)
		{
			P(read);
		}
		V(wm);
		P(write);
		Write();
		V(write);
		P(wm);
		writen--;
		if(writen==0)
		{
			V(read);
		}
		V(wm);
	}
}
```


#### bridge 1

```c++
int wen=0,ewn=0;
semaphore bridge=1,wem=1,ewm=1;

void WE()
{
	while(true)
	{
		P(wem);
		wen++;
		if(wen==1)
		{
			P(bridge);
		}
		V(wem);
		Pass();
		P(wem);
		wen--;
		if(wen==0)
		{
			V(bridge);
		}
		V(wem);
	}
}

void EW()
{
	while(true)
	{
		P(ewm);
		ewn++;
		if(ewn==1)
		{
			P(bridge);
		}
		V(wem);
		Pass();
		P(ewm);
		ewn--;
		if(ewn==0)
		{
			V(bridge);
		}
		V(ewm);
	}
}
```


#### bridge 2

```c++
int wen=0,ewn=0;
semaphore n=4,bridge=1,ewm=1,wem=1;

void EW()
{
	while(true)
	{
		P(ewm);
		ewn++;
		if(ewn==1)
		{
			P(bridge);
		}
		P(n);
		V(ewm);
		Pass();
		V(n);
		P(ewm);
		ewn++;
		if(ewn==0)
		{
			V(bridge);
		}
		V(ewm);
	}
}

void We()
{
	while(true)
	{
		P(wem);
		wen++;
		if(wen==1)
		{
			P(bridge);
		}
		P(n);
		V(wem);
		Pass();
		P(wem);
		wen--;
		if(wen==0)
		{
			V(bridge);
		}
		V(wem);
	}
}
```


#### barber
