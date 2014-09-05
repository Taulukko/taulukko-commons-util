package com.taulukko.commons.util.mmrpg;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.taulukko.commons.util.mmrpg.packets.PacketBase;

public class RequestHandler
{
	private static ExecutorService _poolThread = null;

	private static PacketManager _packetManager;

	private static RequestHandler _me;

	private RequestHandler()
	{
		RequestHandler._packetManager = PacketManager.getInstance();
		if (_poolThread == null)
		{
			// cria um thread pool para gerenciar as requests
			if (IMMORPGConstants.TP_THREADS_CACHE)
			{
				System.out.println("Threads Pool Cache ON");
				_poolThread = Executors.newCachedThreadPool();
			}
			else
			{
				System.out.println("Threads Pool Fixed Size "
						+ IMMORPGConstants.TP_THREADS_SIZE + " ON");
				_poolThread = Executors
						.newFixedThreadPool(IMMORPGConstants.TP_THREADS_SIZE);				
			}
		}
	}

	public static PacketManager getPacketManager()
	{
		return _packetManager;
	}

	public static void setPacketManager(PacketManager sendManager)
	{
		_packetManager = sendManager;
	}

	public static RequestHandler getInstance()
	{
		if (_me == null)
		{
			_me = new RequestHandler();
		}
		return _me;
	}

	public void doAction(Session session, PacketBase packet) throws IOException
	{
		// adiciona a request para ser executada pela pool
		_poolThread.submit(new RequestThreadBase(session, packet));
		System.out.println("Threads Ativas:"+Thread.activeCount());		
	}

	public IConnectionListnner getConnectionListnner()
	{
		if (_packetManager == null)
		{
			return null;
		}
		else
		{
			return _packetManager.getConnectionListnner();
		}
	}

	public void setConnectionListnner(IConnectionListnner connectionListnner)
	{
		if (_packetManager != null)
		{
			_packetManager.setConnectionListnner(connectionListnner);
		}
	}

}
