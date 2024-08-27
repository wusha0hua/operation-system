#include "thread_hdr.h"
#include <iostream>
#include<string.h>


thread idle_thread = { 0 };

thread_queue ready_queue;

thread_queue blocked_queue;

thread* current_thread = &idle_thread;






bool test_multi_thread()
{
	ready_queue.clear();
	set_time_ticks(40);
	set_time_interval(20);
	current_thread = &idle_thread;

	thread thread1 = { 1 };
	thread thread2 = { 2 };
	thread thread3 = { 3 };
	add_ready_thread(&thread1);
	add_ready_thread(&thread2);
	add_ready_thread(&thread3);

	on_clock();
	on_clock();
	on_clock();
	on_clock();
	if (current_thread != &thread2)
	{
		return false;
	}

	on_clock();
	on_clock();
	if (current_thread != &thread3)
	{
		return false;
	}

	return true;
}

void State()
{
	std::cout<<"current thread: "<<current_thread->id<<" "<<current_thread->clock_times<<std::endl;
	int i=0;
	std::cout<<"\nready_queue:"<<std::endl;
	for(i=0;i<ready_queue.size();i++)
	{
		std::cout<<ready_queue[i]->id<<" "<<ready_queue[i]->clock_times<<"\n";
	}
	std::cout<<"\nblocked_queue:\n";
	for(i=0;i<blocked_queue.size();i++)
	{
		std::cout<<blocked_queue[i]->id<<" "<<blocked_queue[i]->clock_times<<"\n";
	}	
}

void Test()
{
	int i;
	int c=0;
	int n=0;

	current_thread = &idle_thread;

	thread t[10];
	t[0]={1};
	t[1]={2};
	t[2]={3};
	t[3]={4};
	t[4]={5};
	t[5]={6};
	t[6]={7};
	t[7]={8};
	t[8]={9};
	t[9]={10};

	unsigned int ticks,interval;
	std::cout<<"0:state 1:add_thread 2:schedule 3:finish 4:block 5:notify 6:on_clock 7:set_time_ticks 8:set_time_interval"<<std::endl;
	while(1)
	{
		std::cin>>c;
		switch (c)
		{
			case 0:
				State();
				break;
			case 1:
				add_ready_thread(&t[n%10]);
				n++;
				State();
				break;
			case 2:
				schedule();
				State();
				break;
			case 3:
				current_thread_finished();
				State();
				break;
			case 4:
				current_thread_blocked();
				State();
				break;
			case 5:
				notify();
				State();
				break;
			case 6:
				on_clock();
				State();
				break;
			case 7:
				std::cin>>ticks;
				set_time_ticks(ticks);
				State();
				break;
			case 8:
				std::cin>>interval;
				set_time_interval(interval);
				State();
				break;
		}

	}
}

int main()
{
	// bool ret = test_multi_thread();
	// std::cout << ret << std::endl;
	// return 0;

	Test();
}
