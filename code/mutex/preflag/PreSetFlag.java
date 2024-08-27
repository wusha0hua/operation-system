import java.util.Random;
import java.util.ArrayList;

class CriticalSection
{
	public static int max=2;
	public static boolean[] flag={false,false};
	public static ArrayList<Process> arr=new ArrayList<>();

	public static void InCriticalSection(Process p)
	{
		arr.add(p);
		System.out.print("Process: ");
		for(int i=0;i<arr.size();i++)
		{
			System.out.print(arr.get(i).n+" ");
		}
		System.out.println("in CriticalSection");
	}

	public static void OutCriticalSection(Process p)
	{
		arr.remove(p);
		System.out.println("Process "+p.n+" out of CriticalSection");
	}
}

class Process extends Thread
{
	int n;
	Random R=new Random();
	Process(int n)
	{
		this.n=n;
	}
	
	public void run() 
	{
		try
		{
			while(true)
			{
				CriticalSection.flag[n]=true;
				sleep(50);
				while(CriticalSection.flag[(n+1)%CriticalSection.max])
				{
					sleep(1);
				}
				int r=R.nextInt(200);
				CriticalSection.InCriticalSection(this);

				sleep(r);

				CriticalSection.OutCriticalSection(this);
				CriticalSection.flag[n]=false;
				sleep(r);
			}
		}
		catch(InterruptedException e)
		{}
	}
}

public class PreSetFlag
{
	public static void main(String[] args)
	{
		for(int i=0;i<CriticalSection.max;i++)
		{
			Process p=new Process(i);
			p.start();
		}
	}
}
