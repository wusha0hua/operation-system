
class CriticalSection extends Thread
{
    public static int N=0;
    public static int turn;
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
            while(CriticalSection.turn!=0) ;

            cs.Fun();
            

            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                ;
            }

            CriticalSection.turn=1;
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
            while(CriticalSection.turn!=1) ;

            cs.Fun();
            

            try
            {
                System.out.println(CriticalSection.turn);
                Thread.sleep(1000);
            }
            catch(InterruptedException e)
            {
                ;
            }

            CriticalSection.turn=0;
        }

    }
}

public class Turn 
{
    public static void main(String[] args)
    {
        P0 t0=new P0();
        P1 t1=new P1();


        t0.start();
        t1.start();
    }
}
