package com.taulukko.commons.util.mmrpg;

import com.taulukko.commons.util.mmrpg.packets.PacketBase;

public class ConnectorListnnerBase implements IConnectionListnner
{	
	public void packetLifeOut(PacketBase packet)
	{
		if(PacketBase.PING_CLIENT_SERVER == packet.getHeader().getRequestID() ||
				PacketBase.PING_SERVER_CLIENT == packet.getHeader().getRequestID())
		{
			System.out.println("Ping nao respondido, disconnect!");
			packet.getSession().logoff();
		}

	}

	public void disconnect(Session session)
	{
		System.out.println("A conexao " + session.getID() + " terminou.");
		if(PacketBase.getType()==PacketBase.TP_CLIENT_SERVER)
		{
			try
			{
				System.exit(0);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}

}
