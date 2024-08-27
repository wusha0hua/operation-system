
class CriticalSection extends Thread
{
    public static int N=0;
    public void Fun()
    {
        CriticalSection.N++;
        System.out.println(CriticalSection.N);
        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException e)
        {
            ;
        }
        CriticalSection.N--;
    }
}

class P0 extends Thread
{
    public void run()
    {
        CriticalSection cs=new CriticalSection();
        while(true)
        {
            cs.Fun();
        }
    }
}

class P1 extends Thread
{
    public void run()
    {
        CriticalSection cs=new CriticalSection();
        
        while(true)
        {
            cs.Fun();
        }

    }
}

public class MutualExclusionSoftWare 
{
    public static void main(String[] args)
    {
        P0 t0=new P0();
        P1 t1=new P1();


        t0.start();
        t1.start();
    }
}
