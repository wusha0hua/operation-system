#include "thread_hdr.h"
#include <iostream>

using namespace std;
thread idle_thread = { 0 };

thread_queue first_ready_queue;
thread_queue second_ready_queue;

thread_queue blocked_queue;

thread* current_thread = &idle_thread;

void Help()
{
	cout<<"0:state 1:add_thread 2:schedule 3:finish 4: block 5:notify 6:om_clock 7:set_first_ticks 8: set_second_ticks 9:set_interval 10:help"<<endl;
}

void State()
{
	int i;
	cout<<"current_thread: id:"<<current_thread->id<<" clock:"<<current_thread->clock_times<<" max:"<<current_thread->max_clock_times<<endl;
	cout<<"first ready queue:"<<endl;
	for(int i=0;i<first_ready_queue.size();i++)
	{
		cout<<"id:"<<first_ready_queue[i]->id<<" clock:"<<first_ready_queue[i]->clock_times<<" max:"<<first_ready_queue[i]->max_clock_times<<endl;
	}
	cout<<"second ready queue:"<<endl;
	for(i=0;i<second_ready_queue.size();i++)
	{
		cout<<"id:"<<second_ready_queue[i]->id<<" clock:"<<second_ready_queue[i]->clock_times<<" max:"<<second_ready_queue[i]->max_clock_times<<endl;
	}
	cout<<"blocked:"<<endl;
	for(i=0;i<blocked_queue.size();i++)
	{
		cout<<"id:"<<blocked_queue[i]->id<<" clock:"<<blocked_queue[i]->clock_times<<" max:"<<blocked_queue[i]->max_clock_times<<endl;

	}

	cout<<"first:"<<first_ready_queue.size()<<endl;
	cout<<"second:"<<second_ready_queue.size()<<endl;
	cout<<"block:"<<blocked_queue.size()<<endl;
	
}

void Test()
{
	Help();

	int i,input,n=1;
	unsigned int ticks,interval;
	thread t[10];
	t[0]={1};
	t[1]={1};
	t[2]={2};
	t[3]={3};
	t[4]={4};
	t[5]={5};
	t[6]={6};
	t[7]={7};
	t[8]={8};
	t[9]={9};

	while(1)
	{
		cout<<endl;
		cout<<"input:";
		cin>>input;

		switch(input)
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
				cout<<"input first trick:";
				cin>>ticks;
				set_first_time_ticks(ticks);
				State();
				break;
			case 8:
				cout<<"input second trick:";
				cin>>ticks;
				set_second_time_ticks(ticks);
				State();
				break;
			case 9:
				cout<<"input interval:";
				cin>>interval;
				set_time_interval(interval);
				State();
				break;
			case 10:
				Help();
				break;
		}
	}

}

int main()
{
	Test();
	return 0;
}
