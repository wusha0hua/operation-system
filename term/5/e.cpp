#include "thread_hdr.h"
unsigned int interval,g_first_ticks,g_second_ticks;

void add_ready_thread(thread* ready_thread)
{
	ready_thread->clock_times = 0;
	ready_thread->max_clock_times = g_first_ticks;
	first_ready_queue.push_back(ready_thread);


}
void current_thread_finished()
{
	if (first_ready_queue.size()!=0) 
	{
		current_thread = first_ready_queue.front();
		first_ready_queue.pop_front();
	}
	else if(first_ready_queue.size()==0&&second_ready_queue.size()!=0)
	{
		current_thread = second_ready_queue.front();
		second_ready_queue.pop_front();
	}
	else if (first_ready_queue.size()==0&&second_ready_queue.size()==0)
	{
		current_thread = &idle_thread;
	}
}
void current_thread_blocked()
{
	if (current_thread != &idle_thread) 
	{
		current_thread->clock_times = 0;
		blocked_queue.push_back(current_thread);
		if (first_ready_queue.size() != 0) 
		{
			current_thread = first_ready_queue.front();
			first_ready_queue.pop_front();
		}
		else if (first_ready_queue.size() == 0 && second_ready_queue.size() != 0) 
		{
			current_thread = second_ready_queue.front();
			second_ready_queue.pop_front();
		}
		else if (first_ready_queue.size() == 0 && second_ready_queue.size() == 0)
		{
			current_thread = &idle_thread;
		}
	}
}
void notify()
{
	if (blocked_queue.size() != 0) 
	{
		blocked_queue.front()->max_clock_times = g_first_ticks;
		first_ready_queue.push_back(blocked_queue.front());
		blocked_queue.pop_front();
	}
}

void notify_all()
{
	while (blocked_queue.size()!=0)
	{
		notify();
	}
}
void on_clock() 
{
	if (current_thread == &idle_thread && first_ready_queue.size() != 0) 
	{
		current_thread =first_ready_queue.front();
		first_ready_queue.pop_front();
	}

	else if (current_thread == &idle_thread && first_ready_queue.size() == 0&&second_ready_queue.size()==0)
	{
		return ;
	}

	current_thread->clock_times += interval;
					
	if (first_ready_queue.size() != 0&&current_thread->max_clock_times==g_second_ticks) 
	{
		current_thread->clock_times = 0;
		second_ready_queue.push_back(current_thread);
		current_thread = first_ready_queue.front();
		first_ready_queue.pop_front();
	}
						
	else if (current_thread->clock_times >= current_thread->max_clock_times) 
	{
		current_thread->clock_times = 0;
		second_ready_queue.push_back(current_thread);
		current_thread->max_clock_times = g_second_ticks;
		if (first_ready_queue.size()!= 0) 
		{
			current_thread = first_ready_queue.front();
			first_ready_queue.pop_front();
		}
		else if (second_ready_queue.size() != 0) 
		{
			current_thread = second_ready_queue.front();
			second_ready_queue.pop_front();
		}
	}
};
void set_first_time_ticks(unsigned int ticks)
{
		g_first_ticks= ticks;
}

void set_second_time_ticks(unsigned int ticks)
{
		g_second_ticks= ticks;
}
void set_time_interval(unsigned int interval) {
		interval = interval;
}
