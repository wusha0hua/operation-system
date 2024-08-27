#include "thread_hdr.h"

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
	current_thread=NULL;
	schedule();
}