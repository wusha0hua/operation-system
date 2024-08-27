#include "thread_hdr.h"
#include <windows.h>
#include <iostream>

thread idle_thread = { 0 };

thread_queue ready_queue;

thread* current_thread = &idle_thread;

void add_ready_thread(thread* ready_thread)
{
	ready_queue.push_back(ready_thread);
}

void schedule()
{

	if(current_thread==NULL)
	{
		if(ready_queue.size()==0)
		{
			current_thread=&idle_thread;
		}
		else
		{
			current_thread=ready_queue.front();
			ready_queue.pop_front();
		}
	}
	else
	{
		if(ready_queue.size()!=0)
		{
			if(current_thread!=&idle_thread)
			{
				add_ready_thread(current_thread);
			}
			current_thread=ready_queue.front();
			ready_queue.pop_front();
		}
	}


}

void current_thread_finished()
{
	if(current_thread!=NULL)
	{
		current_thread=NULL;
		schedule();
	}
}

void Show(int n)
{
	int i=0;
	// std::cout<<ready_queue.size()<<std::endl;
	std::cout <<n<< " ready_queue:" << " ";
	while(i<ready_queue.size())
	{
		std::cout << ready_queue[i] << " ";
		i++;
	}
	std::cout <<"current_thread:"<< current_thread << std::endl;
}

bool test_three_thread_with_finish()
{
	current_thread = &idle_thread;
	ready_queue.clear();
	// ��ready�����м��������߳�
	// thread thread1 = { 1 };
	// thread thread2 = { 2 };
	Show(1);

	// add_ready_thread(&thread1);

	Show(2);

	// add_ready_thread(&thread2);

	Show(3);

	schedule();	
					// thread1 is running
	Show(4);

	current_thread_finished();		// thread1 finished, thread2 is running
	
	Show(5);

	current_thread_finished();		// thread2 finished
	
	Show(6);
	return true;
}

void test1()
{
	current_thread = &idle_thread;
	ready_queue.clear();
	Show(1);
	schedule();
	Show(2);
	schedule();
	Show(3);
	current_thread_finished();
	Show(4);
	current_thread_finished();
	schedule();
	Show(5);
}

void test2()
{
	current_thread = &idle_thread;
	ready_queue.clear();	
	Show(1);
	thread thread1 = { 1 };
	add_ready_thread(&thread1);
	Show(2);
	schedule();
	Show(3);
	schedule();
	Show(4);
	current_thread_finished();
	Show(5);
	current_thread_finished();
	Show(6);
}

int main()
{
	std::cout << "test1" << "\n";
	test1();
	std::cout << "test2" << "\n";
	test2();
}