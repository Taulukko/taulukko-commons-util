package com.taulukko.commons.util.mmrpg.packets.serverclient;

import java.util.Date;

import com.taulukko.commons.util.mmrpg.Session;
import com.taulukko.commons.util.mmrpg.packets.PacketBase;

public class Ping extends PacketBase   {
	public Ping(Session session)
	{
		super(session);
		System.out.println(new Date().toString()
				+ "-Pacote de ping enviado para o cliente " + session.getID());		
		this.getHeader().setPriority(HIG);
		this.getHeader().setRequestID(PING_SERVER_CLIENT);	
		
	}

}
