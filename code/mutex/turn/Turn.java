import java.util.Random;
import java.util.ArrayList;

class CriticalSection
{
	public static int turn,max=10;
	public static ArrayList<Process> arr=new ArrayList<Process>();

	public static void InCriticalSection(Process p)
	{
		arr.add(p);
		System.out.print("Process ");
		for(int i=0;i<arr.size();i++)
		{
			System.out.print(arr.get(i).n+" ");
		}
		System.out.print("in CriticalSection\t");
	}

	public static void OutCriticalSection(Process p)
	{
		Random R=new Random();
		if(R.nextInt(50)==25)
		{
			p.stop();
			System.out.println("\nProcess "+p.n+" stop");
		}
		arr.remove(p);
		System.out.println("Process "+p.n+" out of CriticalSection");
		turn=(turn+1)%max;
	}
}

class Process extends Thread
{
	int n,r;
	Random R;
	Process(int n)
	{
		this.n=n;
		this.R=new Random();
	}

	public void run()
	{
		r=R.nextInt(100);
		while(true)
		{
			while(CriticalSection.turn!=n)
			{
				try
				{
					sleep(1);
				}
				catch(InterruptedException e)
				{}
			
			}
			CriticalSection.InCriticalSection(this);

			try
			{
				sleep(r);
			}
			catch(InterruptedException e)
			{}

			CriticalSection.OutCriticalSection(this);
		}
	}
}

public class Turn extends Thread
{
	public static void main(String[] args)
	{
		CriticalSection.turn=0;
	
		for(int i=0;i<CriticalSection.max;i++)
		{
			Process p=new Process(i);
			p.start();
		}

		try
		{
			sleep(3000);
		}
		catch(InterruptedException e)
		{}

	}

}
