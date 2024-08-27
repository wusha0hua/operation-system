import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

class Disk implements Serializable
{
	public static final long serialVersionUID=1L;
	int PageNum;
	int PageSize;
	byte[] Context;
}


class Memory
{
	public static int PageNum;
	public static int PageSize;
	public static byte[] Memory;
	public static int Address;

	public static void PrintMemory()
	{
		for(int i=0;i<PageNum*PageSize;i++)
		{
			if(i%PageSize==0)
			{
				System.out.println("Page"+i/PageSize);
			}
			if(i%PageSize!=0 && i%16==0)
			{
				System.out.println();
			}
			System.out.format("%02X",Memory[i]);
		}
	}
}


public class Page
{
	static  void  CreateDisk()
	{
		Disk disk=new Disk();
		disk.PageNum=5;
		disk.PageSize=10;
		disk.Context=new byte[disk.PageNum*disk.PageSize];
		for(int i=0;i<disk.PageNum*disk.PageSize;i++)
		{
			disk.Context[i]=(byte)(i%256);
		}

		try
		{
			String FileName="Disk";

			FileOutputStream fos=new FileOutputStream(FileName);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			
			oos.writeObject(disk);

			fos.close();
			oos.close();
		}
		catch(Exception e)
		{

		}
	}

	
	static void LoadPage()
	{
		try
		{
			String FileName="Disk";
			FileInputStream fin=new FileInputStream(FileName);
			ObjectInputStream ois=new ObjectInputStream(fin);
			Disk disk=(Disk)ois.readObject();
			
			int PageSize,PageNum;
		
			PageSize=disk.PageSize;
			PageNum=disk.PageNum;

			fin.close();
			ois.close();
		}
		catch(Exception e)
		{

		}
	}

	public static void main(String[] args)
	{
		CreateDisk();
			

	}
}
