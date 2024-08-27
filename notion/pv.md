
[TOC]

1. there is a reading room which has 100 seat , reader should create a record while coming into , destroy record while leaving . synchronizw

```c++
seamaphore seat=100,mutex=1;
void Reader()
{
	P(seat);
	P(mutex);
	Login();
	V(mutex);
	Read()
	P(mutex);
	Logout();
	V(mutex);
	V(seat);
}
```

2. there is a cage which can put one animal in it , hunter can put a tiger in it , farmer can put a pig in it , zoo stuff can get a tiger from it , cook can get a pig from it 

```c++
semaphore tiger=1,pig=1,empty=1;

void Hunter()
{
	while(true)
	{
		P(empty);
		PutTiger();
		V(tiger);
	}
}

void Farmer()
{
	while(true)
	{
		P(empty);
		PutPig();
		V(pig);
	}
}

void Stuff()
{
	while(true)
	{
		P(tiger);
		GetTiger();
		V(empty);
	}
}

void Cook()
{
	while(true)
	{
		P(pig);
		GetPig();
		V(empty);
	}	
}
```

3. on the bus , driver is charge of driving and stoping , stuff is charge of opening door and closing the door . after stoping can stuff open the door , after door closed can driver drive , there is a drive and 2 stuff and 2 doors , each stuff is charge of a door' state 

```c++
semaphore doo1_close=1,door2_close=1,door1_can_open=0,door2_can_open=0;

void Driver()
{
	while(true)
	{
		P(door1_close);
		P(door2_close);
		Drive();
		V(door1_can_open);
		V(door2_can_open);
	}
}

void Stuff1()
{
	while(true)
	{
		P(door1_can_open);
		OpenDoor1();
		V(door1_close);
	}
}

void Stuff2()
{
	while(true)
	{
		P(door2_can_open);
		OpenDoor2();
		V(door2_close);
	}
}
```

4. there are 3 people , the first has a walkman , the second has a tap , the third has a charge , who wants to listen music should have walkman,tap and charge , shop ower sell 2 items among the 3 items each time 

```c++


void Shop()
{
	while(true)
	{
		P(mutex)
	}
}

void P1()
{

}

void P2()
{

}

void P3()
{

}
```

5. `
