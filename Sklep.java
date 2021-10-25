
public class Sklep
{
	static int liczba_kas = 6;
	static int liczba_klientow = 20;
	static int liczba_zajetych;
	
	static int PRZY_KASIE = 1;
	static int ROZPOCZNIJ = 2;
	static int ZAKUPY = 3;
	static int DO_KASY = 4;
	static int FAILED = 5;
	
	Sklep(int kasy,int klienci)
	{
		kasy= liczba_kas;
		klienci = liczba_klientow;
		liczba_zajetych = 0;
	}
	
	synchronized int start(int numer)
	{
		System.out.println("Klient nr "+numer+" mo¿e przejœæ do kasy.");
		liczba_zajetych--;
		return ROZPOCZNIJ;
	}
	
	synchronized int zakupy()
	{
		try
		{
			Thread.currentThread().sleep(1000);
		}
		catch(Exception e)
		{
			e.printStackTrace();	
		}
		
		if(liczba_zajetych < liczba_kas)
		{
			liczba_zajetych++;
			System.out.println("Zapraszamy do kasy nr "+liczba_zajetych);			
			return PRZY_KASIE;
		}
		else 
		{
			return DO_KASY;
		}
	}
	
	synchronized void klientOut()
	{
		liczba_kas--;
		System.out.println("Zdenerwowany klient opuœci³ sklep");
	}
	
	public static void main(String[] args) 
	{
		Sklep s = new Sklep(liczba_kas, liczba_klientow);
		for(int i=0; i<liczba_klientow; i++)
		{
			new Klient(s,i,600).start();
		}
	}
}
