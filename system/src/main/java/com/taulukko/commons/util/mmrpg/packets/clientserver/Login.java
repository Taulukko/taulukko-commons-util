package com.taulukko.commons.util.mmrpg.packets.clientserver;

import java.nio.ByteBuffer;

import com.taulukko.commons.util.lang.EString;
import com.taulukko.commons.util.mmrpg.Session;
import com.taulukko.commons.util.mmrpg.packets.PacketBase;

public class Login extends PacketBase   {
	public Login(Session session,String username, String password)
	{
		super(session);
		this.getHeader().setPriority(HIG);
		this.getHeader().setRequestID(LOGIN);		
		EString message = new EString(" ").repeat(50);
		username = new EString(username+message.toString()).left(50).toString();
		password = new EString(password+message.toString()).left(50).toString();		
		ByteBuffer buffer = ByteBuffer.allocate(100);
		buffer.put(username.getBytes());
		buffer.put(password.getBytes());
		buffer.flip();
		this.setContent(buffer.array());
	}

}
