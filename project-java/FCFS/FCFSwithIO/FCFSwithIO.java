import java.util.concurrent.TimeUnit;
import java.util.Random;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;

class Process
{
    public String ProcessName;
    public int ProcessID;
    public int ProcessState;
    public int Remaintime;
    public int WaitTime;
    public int ServedTime;
    public int TurnRoundTime;
    public double WightedTurnRoundTime;
    public int CreateTime;
    public int IOStartTime;
    public int IOServedTime;
    public int IORemainTime;
}
public class FCFSwithIO 
{
    public static void main(String[] args)
    {
        Process[] P=new Process[5];
        Queue<Process> ReadyQueue=new LinkedList<>();
        Queue<Process> BlockedQueue=new LinkedList<>();

        Random R=new Random();
        int r;

        Scanner sc=new Scanner(System.in);

        int i=1;
        for(i=0;i<5;i++)
        {
            P[i]=new Process();
            P[i].ProcessID=i+1;
            r=R.nextInt(10)+1;
            P[i].CreateTime=r;
            r=R.nextInt(10)+1;
            P[i].Remaintime=r;
            P[i].ServedTime=0;
            P[i].TurnRoundTime=0;
            P[i].WaitTime=0;
            P[i].WightedTurnRoundTime=0;
            r=P[i].CreateTime+R.nextInt(3);
            P[i].IOStartTime=r;
            r=R.nextInt(100)+1;
            P[i].IORemainTime=r;
            P[i].IOServedTime=0;
            System.out.println("id:"+P[i].ProcessID+"  CreateTime:"+P[i].CreateTime+"  ReaminTime:"+P[i].Remaintime+"  IOStartTime:"+P[i].IOStartTime+"  IORemainTime"+P[i].IORemainTime);
        }

        int Time=0;
        Process CurrentProcess=new Process();
        CurrentProcess=null;

        while(true)
        {
            System.out.print("Now Time:"+Time+"  ");
            for(i=0;i<5;i++)
            {
                if(P[i].CreateTime==Time)
                {
                    ReadyQueue.offer(P[i]);
                }
            }

            if(CurrentProcess==null)
            {
                if(ReadyQueue.size()!=0)
                {
                    CurrentProcess=ReadyQueue.poll();
                }
            }
            else if(CurrentProcess.Remaintime==0)
            {
                if(ReadyQueue.size()!=0)
                {
                    CurrentProcess=ReadyQueue.poll();
                }
                else
                {
                    CurrentProcess=null;
                }
            }

            if(CurrentProcess!=null)
            {
                System.out.println("CurrentProcess: id:"+CurrentProcess.ProcessID+"  RemainTime: "+CurrentProcess.Remaintime+"  ServedTime:"+CurrentProcess.ServedTime+" WaitTime:"+CurrentProcess.WaitTime+"  IORemainTime"+CurrentProcess.IORemainTime+"  IOServedTime"+CurrentProcess.IOServedTime);
            
                if(CurrentProcess.IOStartTime<=Time && CurrentProcess.IORemainTime!=0)
                {
                    System.out.println("IO used");
                    CurrentProcess.IORemainTime--;
                    CurrentProcess.IOServedTime++;
                    CurrentProcess.TurnRoundTime++;
                }
                else
                {
                    System.out.println("CPU used");
                    CurrentProcess.Remaintime--;
                    CurrentProcess.ServedTime++;
                    CurrentProcess.TurnRoundTime++;
                }
            }
            
            for(Process p:ReadyQueue)
            {
                p.WaitTime++;
                p.TurnRoundTime++;
            }

            Time++;

        }


    }
}
