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
	public int n,wait,run,create,runned;

	Process(int n)
	{
		Random R=new Random();
		this.n=n;
		this.wait=0;
		this.run=R.nextInt(10,20);
		this.create=R.nextInt(0,20);
		this.runned=0;
	}
}

class CPUState
{
	public static Process RunningProcess;
	public static int runtime;
	public static int trick;
	public static Queue<Process> ReadyQueue,BlockQueue,FinishQueue;
}

class CPU extends Thread
{
	
	CPU(int trick)
	{
		CPUState.ReadyQueue=new LinkedList<Process>();
		CPUState.BlockQueue=new LinkedList<Process>();
		CPUState.FinishQueue=new LinkedList<Process>();
		CPUState.RunningProcess=null;
		CPUState.runtime=0;
		CPUState.trick=trick;
	}

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

			System.out.print(clk+":");

			if(CPUState.RunningProcess==null)
			{
				if(CPUState.ReadyQueue.isEmpty())
				{
					System.out.println("null");
				}
				else if(CPUState.ReadyQueue.element().create<=clk)
				{
					CPUState.RunningProcess=CPUState.ReadyQueue.poll();
					CPUState.runtime=0;
					CPUState.RunningProcess.wait=clk-CPUState.RunningProcess.create;
					System.out.println("Process"+CPUState.RunningProcess.n+" create");
				}
				else
				{
					System.out.println("null");
				}
			}
			else
			{
				if(CPUState.RunningProcess.runned==CPUState.RunningProcess.run)
				{
					System.out.println("Process"+CPUState.RunningProcess.n+" finish");
					CPUState.RunningProcess=null;
				}
				else if(CPUState.runtime==CPUState.trick)
				{
					System.out.println("Process"+CPUState.RunningProcess.n+" time run out");
					CPUState.ReadyQueue.offer(CPUState.RunningProcess);
					CPUState.RunningProcess=null;
				}
				else
				{
					System.out.println("Process"+CPUState.RunningProcess.n+" running");
					CPUState.RunningProcess.runned++;
					CPUState.runtime++;
				}
			}

		}
	}
}

public class RoundRobin
{
	public static void main(String[] args)
	{
		Clock clock=new Clock();
		clock.start();
	
		CPU cpu=new CPU(3); 	
		for(int i=0;i<3;i++)
		{
			Process p=new Process(i);
			CPUState.ReadyQueue.offer(p);
		}
		cpu.start();
	}
}
