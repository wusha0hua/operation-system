import java.util.concurrent.TimeUnit;
import java.util.Random;
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
}

public class FCFS 
{

    public static void main(String[] args)
    {
        Queue<Process> ReadyQueue=new LinkedList<Process>();
        Random R=new Random();
        int r,i;
        int Time=0;
        for(i=0;i<5;i++)
        {
            r=R.nextInt(3)+1;
            Process P=new Process();
            P.Remaintime=r;
            r=R.nextInt(10)+1;
            P.CreateTime=r;
            P.ProcessName=Integer.toString(i);
            P.ProcessID=i;
            ReadyQueue.offer(P);
        }
        
        Process CurrentProcess;
        double AvergeTurnRoundTime=0;
        double AvergeWightedTurnRoundTime=0;
        int n=ReadyQueue.size();
        while(ReadyQueue.size()!=0)
        {
            CurrentProcess=ReadyQueue.poll();
            CurrentProcess.WaitTime=Time;
            Time+=CurrentProcess.Remaintime;
            CurrentProcess.DeadLine=Time;
            CurrentProcess.Remaintime=0;
            CurrentProcess.ServedTime=Time-CurrentProcess.WaitTime;
            CurrentProcess.TurnRoundTime=CurrentProcess.WaitTime+CurrentProcess.ServedTime;
            CurrentProcess.WightedTurnRoundTime=CurrentProcess.TurnRoundTime/CurrentProcess.ServedTime;

            AvergeTurnRoundTime+=CurrentProcess.TurnRoundTime;
            AvergeWightedTurnRoundTime+=CurrentProcess.WightedTurnRoundTime;

            System.out.println("id:"+CurrentProcess.ProcessID+"  WaitTime:"+CurrentProcess.WaitTime+"  ServedTime:"+CurrentProcess.ServedTime+"  TurnRoundTime:"+CurrentProcess.TurnRoundTime+"  WightedTurnRoundTime:"+CurrentProcess.WightedTurnRoundTime);

        }

        AvergeTurnRoundTime/=n;
        AvergeWightedTurnRoundTime/=n;
        System.out.println("AverageTurnRoundTime:"+AvergeTurnRoundTime+"\nAvergeWightedTurnRoundTime:"+AvergeWightedTurnRoundTime);

    }

}
