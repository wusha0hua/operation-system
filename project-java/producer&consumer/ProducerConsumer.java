import java.io.*;

public class ProducerConsumer
{
    public static int i=0,j=0;
    public static int[] data={1,2,3,4,5,6,7,8,9,10};
    public static int F1=0,F2=0,F3=0;

    public static boolean fA=false,fB=false,fC=false,fD=false,eA=true,eB=true,eC=true,eD=true;

    public void P()
    {
        
    }

    public ProducerConsumer()
    {
        int k=0;
        while(k<10)
        {
        Thread a=new A("A");
        Thread b=new B("B");
        Thread c=new C("C");
        Thread d=new D("D");

        a.start();
        b.start();
        c.start();
        d.start();
        k++;
    }
 
        
    }

    class A extends Thread
    {
        public A(String name)
        {
            super(name);
        }
        public void run() 
        {
            fA=true;
            F1=data[i];
            fA=false;
            i++;
        }
    }

    class B extends Thread
    {
        public B(String name)
        {
            super(name);
        }
        public void run() 
        {
            while(!fA | !fB);
            fA=true;
            fB=true;
            F2=F1*F1;
            fA=false;
            fB=false;
        }        
    }

    class C extends Thread
    {
        public C(String name)
        {
            super(name);
        }
        public void run() 
        {
            while(!fA | !fC);
            fA=true;
            fC=true;
            F3=F1*F1*F1;
            fA=false;
            fC=false;
        }        
    }

    class D extends Thread
    {
        public D(String name)
        {
            super(name);
        }
        public void run() 
        {
            while(!fB | !fC);
            fB=true;
            fC=true;
            System.out.println(data[j]*data[j]*(1+data[j])+":"+F2+F3);
            fB=false;
            fC=false;
            j++;
        }        
    }

    public static void main(String[] agrs)
    {
        ProducerConsumer t=new ProducerConsumer();
    }
}

