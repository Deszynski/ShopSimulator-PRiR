import java.util.Random;

public class Klient extends Thread
{
	static int PATIENCY = 1000;
	static int ANGRY = 300;

	static int PRZY_KASIE = 1;
	static int ROZPOCZNIJ = 2;
	static int ZAKUPY = 3;
	static int DO_KASY = 4;
	static int FAILED = 5;
	
	int index;
	int patiency;
	int state;
	
	Sklep s;
	Random random;
	
	public Klient(Sklep s, int index, int patiency_level)
	{
		this.s = s;
		random = new Random();
		this.index = index;
		this.patiency = patiency_level;	
		this.state = ZAKUPY;
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			if(state==PRZY_KASIE)
			{
				if(random.nextInt(5)==1)
				{
					state=ROZPOCZNIJ;
					patiency=PATIENCY;
					System.out.println("Chcialbym zakonczyc zakupy ~ klient nr "+index);
					state=s.start(index);		
				}
				else
				{
					System.out.println("Klient nr "+index+" poczeka w kolejce...");
				}
			}
			else if(state==ROZPOCZNIJ)
			{
				System.out.println("Klient nr "+index+" rozpoczal zakupy.");
				state=ZAKUPY;
			}
			else if(state==ZAKUPY)
			{
				patiency-=random.nextInt(500);
				
				if(patiency<=ANGRY){
					state=DO_KASY;
				}
				else try
				{
					sleep(random.nextInt(1000));
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			else if(state==DO_KASY)
			{
				System.out.println("Chce zaplacic za zakupy ~ klient nr "+index+" poziom cierpliwosci: "+patiency);
				state=s.zakupy();
				if(state==DO_KASY)
				{
					patiency-=random.nextInt(500);
					System.out.println("Klient "+index+" sie niecierpliwi");
					if(patiency<=0) state=FAILED;
				}
			}
			else if(state==FAILED)
			{
				System.out.println("Klient nr "+index+" opuscil sklep");
				s.klientOut();
			}
		}
	}
}
