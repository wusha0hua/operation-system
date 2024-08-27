#include "thread_hdr.h"


unsigned int g_ticks;
unsigned int g_interval;
void add_ready_thread(thread* ready_thread)
{
	ready_thread->clock_times=0;
	ready_queue.push_back(ready_thread);
}

void schedule()
{
	if(current_thread==NULL)
	{
		if(ready_queue.size()==0)
		{
			current_thread=&idle_thread;
			current_thread->clock_times=0;
		}
		else
		{
			current_thread=ready_queue.front();
			ready_queue.pop_front();
			current_thread->clock_times=0;
		}
	}
	else
	{
		if(ready_queue.size()!=0)
		{
			if(current_thread!=&idle_thread)
			{
				current_thread->clock_times=0;
				add_ready_thread(current_thread);
			}
			current_thread=ready_queue.front();
			current_thread->clock_times=0;
			ready_queue.pop_front();
		}
		else if(current_thread==&idle_thread)
		{
			current_thread->clock_times=0;
		}
	}

}

void current_thread_finished()
{
	current_thread=NULL;
	schedule();

}

void current_thread_blocked()
{
	if(current_thread!=&idle_thread)
	{
		current_thread->clock_times=0;
		blocked_queue.push_back(current_thread);
		current_thread=NULL;
		schedule();
	}
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

void on_clock()
{
	if(current_thread==&idle_thread)
	{
		schedule();
		return;
	}

	unsigned int over;
	current_thread->clock_times+=g_interval;
	if(current_thread->clock_times>=g_ticks)
	{
		over=current_thread->clock_times-g_ticks;
		current_thread->clock_times=0;
		schedule();
		current_thread->clock_times=over;
	}
}


void set_time_ticks(unsigned int ticks)
{
	g_ticks=ticks;
}

void set_time_interval(unsigned int interval)
{
	g_interval=interval;
}
