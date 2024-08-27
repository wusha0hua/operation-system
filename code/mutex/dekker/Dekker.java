import java.util.Random;
import java.util.ArrayList;

class CriticalSection
{
	public static int max=2;
	public static int turn=0;
	public static boolean[] flag={false,false};
	public static ArrayList<Process> arr=new ArrayList<>();

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

	public static void OutCriticalSecion(Process p)
	{
		arr.remove(p);
		System.out.println("Process "+p.n+" out of CriticalSection");
	}
	
}


class Process extends Thread
{
	int n;
	Process(int n)
	{
		this.n=n;
	}

	public void run()
	{
		try
		{
			Random R=new Random();
			while(true)
			{
				int r=R.nextInt(200);
			
				sleep(10);
				CriticalSection.flag[n]=true;
				while(CriticalSection.flag[(n+1)%CriticalSection.max])
				{
					if(CriticalSection.turn==(n+1)%CriticalSection.max)
					{
						CriticalSection.flag[n]=false;
						while(CriticalSection.turn==(n+1)%CriticalSection.max)
						{
							sleep(1);
						}
						CriticalSection.flag[n]=true;
					}
				}

				CriticalSection.InCriticalSection(this);
				sleep(r);
				CriticalSection.OutCriticalSecion(this);
				CriticalSection.flag[n]=false;
				CriticalSection.turn=(n+1)%CriticalSection.max;
			}
		}
		catch(InterruptedException e)
		{

		}
	}
}

public class Dekker
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
