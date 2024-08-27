#include "thread_hdr.h"
#include <iostream>

thread idle_thread = { 0 };

thread_queue ready_queue;

thread_queue blocked_queue;

thread* current_thread = &idle_thread;

void add_ready_thread(thread* ready_thread)
{
	ready_queue.push_back(ready_thread);
}

void schedule()
{
	if(ready_queue.size()==0)
	{
		current_thread=&idle_thread;
	}
	else
	{
		if(current_thread!=&idle_thread & current_thread!=NULL)
		{
			add_ready_thread(current_thread);
		}
		current_thread=ready_queue.front();
		ready_queue.pop_front();
	}

}

void current_thread_finished()
{

	current_thread=NULL;
	schedule();

}

void current_thread_blocked()
{
	blocked_queue.push_back(current_thread);
	current_thread=NULL;
	schedule();
}

void notify()
{
	add_ready_thread(blocked_queue.front());
	blocked_queue.pop_front();
}

void notify_all()
{
	while(blocked_queue.size()>0)
	{
		notify();
	}
}

bool test_thread_thread_notify_all()
{
	current_thread = &idle_thread;
	ready_queue.clear();
	thread thread1 = { 1 };
	thread thread2 = { 2 };
	thread thread3 = { 3 };
	add_ready_thread(&thread1);
	add_ready_thread(&thread2);
	add_ready_thread(&thread3);

	schedule();					// thread1 is running
	schedule();					// thread2 is running
	schedule();					// thread3 is running

	current_thread_blocked();		// thread3 has been blocked, thread1 is running
	current_thread_blocked();		// thread1 has been blocked, thread2 is running


	notify_all();
	schedule();
	schedule();
	if (current_thread != &thread1)
	{
		return false;
	}

	schedule();
	if (current_thread != &thread2)
	{
		return false;
	}

	return true;
}

int main()
{
	bool ret = test_thread_thread_notify_all();
	std::cout << ret << std::endl;
	return 0;
}