package com.taulukko.commons.util.mmrpg.packets.serverclient;

import com.taulukko.commons.util.mmrpg.Session;
import com.taulukko.commons.util.mmrpg.packets.PacketBase;

public class ConnectionOK extends PacketBase   {
	public ConnectionOK(Session session)
	{		
		super(session);
		System.out.println("ConecionOK criado");
		this.getHeader().setPriority(HIG);
		this.getHeader().setRequestID(START);		
	}

}
