import java.util.*;
class Process
{
    String ProcessName;
    int PID;
    int State;  //0:ready;1:running;2:blocked
    int RunTime;
    public Process(String ProcessName)
    {
        this.ProcessName=ProcessName;
    }
}

public class ThreeState
{
    int Time=0;
    int ProcessNum=0;
    int Interval=40;
    Queue<Process> ReadyQueue=new LinkedList<>();
    Queue<Process> RunningQueue=new LinkedList<>();
    Queue<Process> BlockedQueue=new LinkedList<>();
    Process RunningProcess=new Process(null);
    public static void main(String[] args)
    {

    }

    public void State()
    {
        
    }

    public void SelectProcess()
    {

    }

    public void Update()
    {
        this.RunningProcess.RunTime+=20;
        if(this.RunningProcess.RunTime==40)
        {
            this.RunningProcess.State=0;
            this.RunningProcess.RunTime=0;
            this.ReadyQueue.offer(this.RunningProcess);
        }
        if(this.ReadyQueue.size()!=0)
        {
            this.RunningProcess=this.ReadyQueue.poll();
            this.RunningProcess.State=1;
        }
    }

    public void Append(Process NewProcess)
    {
        NewProcess.PID=this.ProcessNum;
        NewProcess.State=0;
        NewProcess.RunTime=0;
        ReadyQueue.offer(NewProcess);
    }

    public void Dispatch()
    {
        RunningProcess=ReadyQueue.poll();
        RunningProcess.State=1;
    } 

    public void Time()
    {
        System.out.println("now time:"+this.Time);
        Scanner sc=new Scanner(System.in);
        sc.nextLine();
        sc.close();
        this.Time+=20;
        System.out.println("now time:"+this.Time);
    }
}