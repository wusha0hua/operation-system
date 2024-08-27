#include "thread_hdr.h"
unsigned int inter;
unsigned int lev1_tick;
unsigned int lev2_tick;

void add_ready_thread(thread* ready_thread)
{
	ready_thread->clock_times = 0;
	ready_thread->max_clock_times = lev1_tick;
	first_ready_queue.push_back(ready_thread);


}
void current_thread_finished()
{
	if (first_ready_queue.size()!=0) 
	{
		current_thread = first_ready_queue.front();
		first_ready_queue.pop_front();
		return;
	}

	if(first_ready_queue.size()==0&&second_ready_queue.size()!=0)
	{
		current_thread = second_ready_queue.front();
		second_ready_queue.pop_front();
		return;
	}

	if (first_ready_queue.size()==0&&second_ready_queue.size()==0)
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
			return;
		}
		if (first_ready_queue.size() == 0 && second_ready_queue.size() != 0) 
		{
			current_thread = second_ready_queue.front();
			second_ready_queue.pop_front();
			return;
		}
		if (first_ready_queue.size() == 0 && second_ready_queue.size() == 0)
		{
			current_thread = &idle_thread;
		}
	}
}
void notify()
{
	if (blocked_queue.size() != 0) 
	{
		blocked_queue.front()->max_clock_times = lev1_tick;
		first_ready_queue.push_back(blocked_queue.front());
		blocked_queue.pop_front();
	}
}

void notify_all()
{
	while (blocked_queue.size()!=0)
	{
		blocked_queue.front()->max_clock_times = lev1_tick;
		first_ready_queue.push_back(blocked_queue.front());
		blocked_queue.pop_front();
	}
}
void on_clock() 
{
	if (current_thread == &idle_thread && first_ready_queue.size() != 0) 
	{
		current_thread =first_ready_queue.front();
		first_ready_queue.pop_front();
		return;
	}

	if (current_thread == &idle_thread && first_ready_queue.size() == 0&&second_ready_queue.size()==0)
		return;

	current_thread->clock_times += inter;
					
	if (first_ready_queue.size() != 0&&current_thread->max_clock_times==lev2_tick) 
	{
		current_thread->clock_times = 0;
		second_ready_queue.push_back(current_thread);
		current_thread = first_ready_queue.front();
		first_ready_queue.pop_front();
		return;
	}
						
	if (current_thread->clock_times >= current_thread->max_clock_times) 
	{
		current_thread->clock_times = 0;
		second_ready_queue.push_back(current_thread);
		current_thread->max_clock_times = lev2_tick;
		if (first_ready_queue.size()!= 0) 
		{
			current_thread = first_ready_queue.front();
			first_ready_queue.pop_front();
			return;
		}
		if (second_ready_queue.size() != 0) 
		{
			current_thread = second_ready_queue.front();
			second_ready_queue.pop_front();
			return;
		}
	}
};
void set_first_time_ticks(unsigned int ticks)
{
		lev1_tick = ticks;
}

void set_second_time_ticks(unsigned int ticks)
{
		lev2_tick = ticks;
}
void set_time_interval(unsigned int interval) {
		inter = interval;
}
