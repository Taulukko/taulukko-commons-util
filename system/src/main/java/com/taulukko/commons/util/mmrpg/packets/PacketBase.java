package com.taulukko.commons.util.mmrpg.packets;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;

import com.taulukko.commons.util.lang.EByte;
import com.taulukko.commons.util.mmrpg.ICommonRequests;
import com.taulukko.commons.util.mmrpg.IConfigHeader;
import com.taulukko.commons.util.mmrpg.IPriority;
import com.taulukko.commons.util.mmrpg.Session;

public class PacketBase implements IPriority, ICommonRequests
{

	// TIPOS DE PACOTES
	public final static int TP_SERVER_CLIENT = 1000000000;

	public final static int TP_CLIENT_SERVER = 2000000000;

	// START PACKEGE
	public final static int TP_START_PCKT = 11184810; //101010101010101010101010

	// sizes
	public final static int TP_MAX_SIZE = 100000;

	public final static int TP_HEADER_SIZE = 32;

	private int _sendTimes = 0;

	private long _lastSendTimes = 0;

	private Header _header;

	private byte[] _content;

	private Session _session;

	private static int _type;

	private boolean _inbox = false;

	private PacketBase()
	{
	}

	public PacketBase(Session session)
	{
		_header = new Header(session.getChannel());
		_session = session;
	}

	public byte[] getContent()
	{
		return _content;
	}

	public void setContent(byte[] m_content)
	{
		_content = m_content;
	}

	public Header getHeader()
	{
		return _header;
	}

	private int calcChkSum()
	{
		int ret = _header.getConfig() + (_header.getPackID() % 1000000000)
				+ _header.getPriority() + (_header.getRequestID() % 1000000000)
				+ _header.getSessionID() + _header.getSize();
		if (_header.getSize() != 0)
		{
			ret += _content[0];
			ret += _content[_header.getSize() - 1];
		}
		return ret;
	}

	public boolean getIsValid()
	{
		boolean ret = this.calcChkSum() == this.getHeader().getChkSum();
		if (!ret)
		{
			System.out.println("Cheksun invalido!");
			System.out.println("Recebido:" + this.getHeader().getChkSum());
			System.out.println("Esperado:" + this.calcChkSum());
		}
		return ret;
	}

	public void toBox()
	{
		if (!_inbox)
		{// evita encaixotar 2x
			_header.setSessionID(this.getSession().getID());
			_header.setPackID(_type + this.getSession().getFreePacketID());
			if (_content == null)
			{
				_header.setSize(0);
			}
			else
			{
				_header.setSize(_content.length);// tamanho do conteudo
			}

			this.crypt();
			_header.setChkSum(this.calcChkSum());
			_inbox = true;
		}
	}

	public void toUnBox()
	{
		this.decrypt();
	}

	public static long getType()
	{
		return _type;
	}

	public static void setType(int type)
	{
		_type = type;
		System.out.println("Cinfigurado para criar pacotes entre:");
		if (_type == TP_SERVER_CLIENT)
		{
			System.out.println(TP_SERVER_CLIENT + "-" + TP_CLIENT_SERVER);

		}
		else
		{
			System.out.println(TP_CLIENT_SERVER + "-OO");
		}
	}

	private void crypt()
	{
		// encripta o content
	}

	private void decrypt()
	{
		// encripta o content
	}

	public void send() throws IOException
	{

		_lastSendTimes = new Date().getTime();
		_sendTimes++;
		this.toBox();
		SocketChannel channel = _session.getChannel();
		_header.printToChannel();
		if (_content != null)
		{
			ByteBuffer buffer = ByteBuffer.wrap(_content);
			channel.write(buffer);
		}

	}

	public PacketBase createResponse()
	{
		PacketBase packet = new PacketBase(this.getSession());
		packet.getHeader().setPackID(this.getHeader().getPackID());
		// nao se espera retorno de retorno
		packet.getHeader().setConfig(IConfigHeader.TP_IGNORE_RESPONSE);
		packet.getHeader().setPriority(this.getHeader().getPriority());
		packet.getHeader().setSize(0);
		packet.getHeader().setChkSum(packet.calcChkSum());
		packet._inbox = true;
		return packet;
	}

	public Session getSession()
	{
		return _session;
	}

	public void setSession(Session session)
	{
		_session = session;		
		
		if (_header != null)
		{
			_header.setChannel(session.getChannel());
		}
	}

	private void setHeader(Header header)
	{
		_header = header;
	}

	/***************************************************************************
	 * PROTOCOLO ____________ |HEAD|CONTENT| ____________
	 * 
	 * HEAD _______________________________________
	 * |SIZE-SID-PRIORITY-RID-PID-CHKSUM-CONFIG|
	 * _______________________________________
	 **************************************************************************/
	public void loadContent(byte[] buff) throws SizePacketError
	{
		if (this.getHeader().getSize() != 0)
		{
			if (this.getHeader().getSize() > TP_MAX_SIZE)
			{
				throw new SizePacketError();
			}
			byte[] content = new byte[this.getHeader().getSize()];
			System.arraycopy(buff, 24, content, 0, this.getHeader().getSize());
			this.setContent(content);

		}
	}

	/***************************************************************************
	 * PROTOCOLO ____________ |HEAD|CONTENT| ____________
	 * 
	 * HEAD _______________________________________
	 * |SIZE-SID-PRIORITY-RID-PID-CHKSUM-CONFIG|
	 * _______________________________________
	 **************************************************************************/

	public static PacketBase create(byte[] buff)
	{
		PacketBase ret = null;
		Header header = new Header(null);
		header.setPackID(EByte.bytesToInt(buff));
		header.setRequestID(EByte.bytesToInt(buff,4));
		
		header.setSize(EByte.bytesToInt(buff, 4));
		header.setSessionID(EByte.bytesToInt(buff, 8));
		header.setPriority(EByte.bytesToInt(buff, 12));
		header.setRequestID(EByte.bytesToInt(buff, 16));
		header.setPackID(EByte.bytesToInt(buff, 20));
		header.setChkSum(EByte.bytesToInt(buff, 24));
		header.setConfig(EByte.bytesToInt(buff, 28));
		ret = new PacketBase();
		ret.setHeader(header);
		ret._inbox = true;
		return ret;
	}

	public void print()
	{
		System.out.print(this.toString());
	}

	public void println()
	{
		System.out.println(this.toString());
	}

	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append(_header.toString());
		ret.append("\n\n\nContent:");
		if (_content != null)
		{
			ret.append(_content.toString());
		}
		return ret.toString();
	}

	public boolean getIsServerClient()
	{
		if (_header == null)
		{
			return false;
		}
		return !this.getIsClientServer();
	}

	public boolean getIsClientServer()
	{
		if (_header == null)
		{
			return false;
		}
		return _header.getPackID() > TP_CLIENT_SERVER;
	}
	
	public boolean equals(Object o)
	{
		if (!(o instanceof PacketBase))
		{
			return false;
		}
		PacketBase packet = (PacketBase) o;
		return packet.getHeader().getPackID() == this.getHeader().getPackID()
				&& packet.getSession().getID() == this.getSession()
						.getID();
	}

	public long getLastSendTimes()
	{
		return _lastSendTimes;
	}

	public int getSendTimes()
	{
		return _sendTimes;
	}
}
