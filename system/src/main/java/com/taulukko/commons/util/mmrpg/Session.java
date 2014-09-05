package com.taulukko.commons.util.mmrpg;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

public class Session
{

	private Map<String, Object> _parameters = new HashMap<String, Object>();

	private int _ID;

	private SocketChannel _channel;

	private int _freePacketID = 0;

	public Session(SocketChannel channel, int sessionID)
	{
		_channel = channel;
		_ID = sessionID;
	}

	public int getID()
	{
		return _ID;
	}

	public SocketChannel getChannel()
	{
		return _channel;
	}

	public int getFreePacketID()
	{
		return ++_freePacketID;
	}

	public Map<String, Object> getParameters()
	{
		return _parameters;
	}

	public void logoff()
	{
		try
		{
			
			if(_channel.isConnected())
			{
				_channel.close();
			}
			IConnectionListnner connectionListnner =PacketManager.getInstance().getConnectionListnner(); 
			if(connectionListnner!=null)
			{
				connectionListnner.disconnect(this);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
