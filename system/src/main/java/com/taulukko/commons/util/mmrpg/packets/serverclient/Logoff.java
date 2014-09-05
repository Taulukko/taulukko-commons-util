package com.taulukko.commons.util.mmrpg.packets.serverclient;

import com.taulukko.commons.util.mmrpg.Session;
import com.taulukko.commons.util.mmrpg.packets.PacketBase;

public class Logoff extends PacketBase   {
	public Logoff(Session session)
	{
		super(session);
		this.getHeader().setPriority(LOW);
		this.getHeader().setRequestID(LOGOFF_SERVER_CLIENT);
	}

}
