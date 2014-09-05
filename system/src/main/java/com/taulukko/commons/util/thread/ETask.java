package com.taulukko.commons.util.thread;

public abstract class ETask extends Thread
{
	
	private long _delay=0;
	private boolean _continuos=false;
	
	public ETask(String name, long delay, boolean continuos)
	{
		this.setName(name);
		_continuos = continuos;
		_delay=delay;		
	}
	
	public abstract void loop();
	
	public void run()
	{
		boolean continueWhile =isAlive() ; 
		while(continueWhile )
		{
			try
			{
				loop();
				Thread.sleep(_delay);
			}
			catch(Exception e)
			{
				System.out.println("Task " + this.getName() + " Exception");
				e.printStackTrace();
			}
			continueWhile =  isAlive() && _continuos;
		}
	}
}
