#include "thread_hdr.h"
int b, c, d=1;
void add_ready_thread(thread* ready_thread)
{
	//ready_queue
	ready_queue.push_back(ready_thread);
	// ��Ӧ�Ĵ���ʵ��
}

void schedule()
{
	if (current_thread == &idle_thread && ready_queue.size() != 0) {
		current_thread = ready_queue.front();
		ready_queue.pop_front();
	}
	else if (current_thread != &idle_thread || ready_queue.size() == 0) {
		ready_queue.push_back(current_thread);
		current_thread = ready_queue.front();
		ready_queue.pop_front();
	}
	// ��Ӧ�Ĵ���ʵ��
}

void current_thread_finished()
{
	// ʵ�ֵĴ���
	if (current_thread != &idle_thread && ready_queue.size() != 0) {
		current_thread = ready_queue.front();
		ready_queue.pop_front();
	}
	else {
		current_thread = &idle_thread;
	}
}

void current_thread_blocked()
{
	// ��Ӧ�Ĵ���ʵ��
	blocked_queue.push_back(current_thread);
	if (ready_queue.size() != 0) {
		current_thread = ready_queue.front();
		ready_queue.pop_front();
	}
	else
		current_thread = &idle_thread;
}

void notify()
{
	// ��Ӧ�Ĵ���ʵ��
	thread* a = blocked_queue.front();
	blocked_queue.pop_front();
	ready_queue.push_back(a);

}

void notify_all()
{
	// ��Ӧ�Ĵ���ʵ��
	while (blocked_queue.size() > 0) {
		thread* a = blocked_queue.front();
		blocked_queue.pop_front();
		ready_queue.push_back(a);
	}
}

void on_clock()
{
	// ��Ӧ�Ĵ���ʵ��
	if (current_thread == &idle_thread)
		schedule();
	else {
		current_thread->clock_times = current_thread->clock_times + c;
		if (current_thread->clock_times >= b) {
			current_thread->clock_times = 0;
			schedule();
		}
	}
}

void set_time_ticks(unsigned int ticks)
{
	// ��Ӧ�Ĵ���ʵ��
	if(ticks!=0)
	b = ticks;
}

void set_time_interval(unsigned int interval)
{
	// ��Ӧ�Ĵ���ʵ��
	if (interval != 0)
	c = interval;
}