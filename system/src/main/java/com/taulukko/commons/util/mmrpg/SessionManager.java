package com.taulukko.commons.util.mmrpg;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class SessionManager {
	private static ArrayList<Session> _threads = new ArrayList<Session>();
	private static int _sessionID=1;
	
	public static Session newInstance(SocketChannel channel)
	{		
		Session session = new Session(channel,_sessionID++);
		_threads.add(session);		
		return session;
	}
}
