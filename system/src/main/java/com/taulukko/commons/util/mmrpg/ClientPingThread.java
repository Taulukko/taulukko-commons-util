package com.taulukko.commons.util.mmrpg;

import java.nio.channels.ClosedChannelException;



public class ClientPingThread extends Thread
{
	private Session _session;
	
	public ClientPingThread(Session session)
	{
		_session = session;
	}

	public void run()
	{
		try
		{
			boolean exitForced = false;
			while(!exitForced)
			{
				//System.out.println("Schedule client ping...");
				PacketManager manager = PacketManager.getInstance();
				manager.ping(_session);
				Thread.sleep(IMMORPGConstants.TP_CHECK_PING);
			}
		}
		catch (ClosedChannelException cce)
		{
			System.out.println("fine: Conexao terminou de forma inesperada a partir do servidor!" );
			_session.logoff();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
