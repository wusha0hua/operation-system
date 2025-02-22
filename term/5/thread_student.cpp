#include "thread_hdr.h"

unsigned int g_first_ticks,g_second_ticks,g_interval;

void add_ready_thread(thread* ready_thread)
{
	ready_thread->clock_times=0;
	first_ready_queue.push_back(ready_thread);
}

void schedule()
{
	if(current_thread==NULL)
	{
		if(first_ready_queue.size()>0)
		{
			current_thread=first_ready_queue.front();
			first_ready_queue.pop_front();
			current_thread->clock_times=0;
			current_thread->max_clock_times=1;
		}
		else if(second_ready_queue.size()>0)
		{
			current_thread=second_ready_queue.front();
			second_ready_queue.pop_front();
			current_thread->clock_times=0;
			current_thread->max_clock_times=2;
		}
		else
		{
			current_thread=&idle_thread;
		}
	}
	else if( current_thread==&idle_thread)
	{
		if(first_ready_queue.size()>0)
		{
			current_thread=first_ready_queue.front();
			first_ready_queue.pop_front();
			current_thread->clock_times=0;
			current_thread->max_clock_times=1;
		}
		else if(second_ready_queue.size()>0)
		{
			current_thread=second_ready_queue.front();
			second_ready_queue.pop_front();
			current_thread->clock_times=0;
			current_thread->max_clock_times=2;
		}
	}
	else
	{
		if(first_ready_queue.size()>0)
		{
			current_thread->clock_times=0;
			current_thread->max_clock_times=2;
			second_ready_queue.push_back(current_thread);
			current_thread=first_ready_queue.front();
			first_ready_queue.pop_front();
			current_thread->clock_times=0;
			current_thread->max_clock_times=1;
		}
		else if(second_ready_queue.size()>0)
		{
			current_thread->clock_times=0;
			current_thread->max_clock_times=2;
			second_ready_queue.push_back(current_thread);
			current_thread=second_ready_queue.front();
			second_ready_queue.pop_front();
			current_thread->clock_times=0;
			current_thread->max_clock_times=2;
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
	unsigned int over;
	if(current_thread==&idle_thread)
	{
		schedule();
		return;
	}
	else if(current_thread->max_clock_times==2 && first_ready_queue.size()>0)
	{
		schedule();
		return ;
	}
	current_thread->clock_times+=g_interval;
	if(current_thread->max_clock_times==1 && current_thread->clock_times>=g_first_ticks)
	{
		over=current_thread->clock_times-g_first_ticks;
		current_thread->clock_times=0;
		schedule();
		current_thread->clock_times=over;
	}
	else if(current_thread->max_clock_times==2 && current_thread->clock_times>=g_second_ticks)
	{
		over=current_thread->clock_times-g_second_ticks;
		current_thread->clock_times=0;
		schedule();
		current_thread->clock_times=over;
	}
}

void set_first_time_ticks(unsigned int ticks)
{
	g_first_ticks=ticks;
}

void set_second_time_ticks(unsigned int ticks)
{
	g_second_ticks=ticks;
}

void set_time_interval(unsigned int interval)
{
	g_interval=interval;
}
