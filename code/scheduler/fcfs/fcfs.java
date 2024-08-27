import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;

class Clock extends Thread
{
	public static int clk=0;


	public void run()
	{
		try
		{
			while(true)
			{
				sleep(100);
				clk++;
			}
		}
		catch(InterruptedException e)
		{

		}
	}
}

class Process
{
	public int n,wait,run,create;

	Process(int n)
	{
		Random R=new Random();
		this.n=n;
		this.wait=0;
		this.run=R.nextInt(2,5);
		this.create=R.nextInt(0,20);
	}
}


class CPU extends Thread
{
	Queue<Process> ReadyQueue,BlockQueue,FinishQueue;
	Process RunningProcess;

	CPU()
	{
		ReadyQueue=new LinkedList<Process>();
		BlockQueue=new LinkedList<Process>();
		FinishQueue=new LinkedList<Process>();
		RunningProcess=null;
	}

	
	/*public void run()
	{
		int clk=-1;
		while(true)
		{
			while(Clock.clk==clk){try{sleep(1);}catch(InterruptedException e){}};
			clk=Clock.clk;
			
			System.out.print(clk+":");

			if(RunningProcess==null)
			{
				if(ReadyQueue.isEmpty())
				{
					System.out.println("null");
				}
				else if(ReadyQueue.element().create<=clk)
				{
					RunningProcess=ReadyQueue.poll();
					RunningProcess.wait=clk-RunningProcess.create;
					System.out.println("Process"+RunningProcess.n+"created");
				}
				else
				{
					System.out.println("null");
				}
			}
			else
			{
				if(RunningProcess.create<clk && clk<RunningProcess.run+RunningProcess.create)
				{
					System.out.println("Process"+RunningProcess.n+"running");
				}
				else if(clk==RunningProcess.create+RunningProcess.run)
				{
					System.out.println("Process"+RunningProcess.n+"finished");
					RunningProcess=null;
				}
				else
				{
					System.out.println(RunningProcess+" create:"+RunningProcess.create+" run:"+RunningProcess.run+" wait:"+RunningProcess.wait+" "+ReadyQueue);
				}
			}
		
			if(ReadyQueue.size()==0 && RunningProcess==null)
			{
				break;
			}
		}
	}*/

	public void run()
	{
		int clk=-1;
		while(true)
		{
			while(clk==Clock.clk)
			{
				try
				{
					sleep(1);
				}
				catch(InterruptedException e)
				{}
			}
			clk=Clock.clk;
			

			if(RunningProcess==null)
			{
				if(ReadyQueue.isEmpty())
				{
					System.out.println("null");
				}
				else if(ReadyQueue.element().create<=clk)
				{
					RunningProcess=ReadyQueue.poll();
					RunningProcess.wait=clk-RunningProcess.create;
					System.out.println("Process"+RunningProcess.n+" create");
				}
				else
				{
					System.out.println("null");
				}
			}
			else
			{
				if(RunningProcess.run==0)
				{
					System.out.println("Process"+RunningProcess.n+" finished");
					RunningProcess=null;
				}
				else
				{
					RunningProcess.run--;
					System.out.println("Process"+RunningProcess.n+" running");
				}
			}
		}
	}

}

public class fcfs
{
	public static void main(String[] args)
	{
		Clock clock=new Clock();
		clock.start();
	
		CPU cpu=new CPU();
		for(int i=0;i<3;i++)
		{
			Process p=new Process(i);
			cpu.ReadyQueue.offer(p);
		}
		cpu.start();
	}
}
