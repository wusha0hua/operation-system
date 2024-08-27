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
    public int Runningtime;
    public int TurnRoundTime;
    public double WightedTurnRoundTime;
    public int CreateTime;
    public int IOStartTime;
    public int IOServedTime;
    public int IORemainTime;
}

public class RR 
{
    public static void main(String[] args)
    {
        Process[] P=new Process[5];
        Queue<Integer> ReadyQueue=new LinkedList<>();
        Queue<Integer> BlockedQueue=new LinkedList<>();
        Queue<Integer> FinishedQueue=new LinkedList<>();

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
            P[i].Runningtime=0;
            System.out.println("id:"+P[i].ProcessID+"  CreateTime:"+P[i].CreateTime+"  ReaminTime:"+P[i].Remaintime+"  IOStartTime:"+P[i].IOStartTime+"  IORemainTime"+P[i].IORemainTime);
        }

        int Time=0;
        int CurrentProcessIndex;
        CurrentProcessIndex=-1;

        int trick;
        System.out.print("trick:");
        trick=sc.nextInt();

        while(true)
        {
            System.out.println("Now Time:"+Time);
            
            for(i=0;i<5;i++)
            {
                if(P[i].CreateTime==Time)
                {
                    ReadyQueue.offer(i);
                }
            }

            if(CurrentProcessIndex==-1)
            {
                if(ReadyQueue.size()!=0)
                {
                    CurrentProcessIndex=ReadyQueue.poll();
                }
            }
            else
            {

                if(P[CurrentProcessIndex].Remaintime==0)
                {
                    FinishedQueue.offer(CurrentProcessIndex);
                    if(ReadyQueue.size()!=0)
                    {
                        CurrentProcessIndex=ReadyQueue.poll();
                    }
                    else
                    {
                        CurrentProcessIndex=-1;
                    }
                }
                else
                {
                    if(P[CurrentProcessIndex].Runningtime==trick)
                    {
                        P[CurrentProcess].Runningtime=0;
                        ReadyQueue.offer(CurrentProcessIndex);
                        CurrentProcessIndex=ReadyQueue.poll();
                    }
                }
            }

        }

    }    
}
