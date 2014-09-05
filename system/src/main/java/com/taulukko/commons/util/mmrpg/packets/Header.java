package com.taulukko.commons.util.mmrpg.packets;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SocketChannel;

import com.taulukko.commons.util.game.EDiceKit;
import com.taulukko.commons.util.lang.EByte;
import com.taulukko.commons.util.mmrpg.test.ITestSwitch;

public class Header {
	private SocketChannel _channel;
	private int _size;
	private int _sessionID;
	private int _priority;
	private int _requestID;
	private int _packID;
	private int _chkSum;
	private int _config;
	
	public Header( SocketChannel channel)
	{
		_channel = channel;
	}
	
	public int getPriority() {
		return _priority;
	}
	public void setPriority(int m_priority) {
		_priority = m_priority;
	}
	public int getRequestID() {
		return _requestID;
	}
	public void setRequestID(int m_requestID) {
		_requestID = m_requestID;
	}
	public int getSessionID() {
		return _sessionID;
	}
	public void setSessionID(int m_sessionID) {
		_sessionID = m_sessionID;
	}
	public int getSize() {
		return _size;
	}
	public void setSize(int m_size) {
		_size = m_size;
	}

	public int getChkSum() {
		return _chkSum;
	}

	public void setChkSum(int chkSum) {
		_chkSum = chkSum;
	}

	public int getPackID() {
		return _packID;
	}

	public void setPackID(int packID) {
		_packID = packID;
	}
	public Channel getChannel() {
		return _channel;
	}

	public void printToChannel() throws IOException
	{
		//TODO@ Melhorar para buffar tudo na variavel buffer e enviar uma unica vez
		ByteBuffer buffer = null;
				
		if(ITestSwitch.TP_HEADER_VERIFY_FIELD)
		{
			if(EDiceKit.rool(1,ITestSwitch.TP_HEADER_VERIFY_FIELD_RATE,0)==1)
			{
				buffer = ByteBuffer.wrap(new byte[] {1,2,3,4,5,6}); //lixo teste, causa cabecalho incorreto
				_channel.write(buffer);
			}
		}
		
		buffer = ByteBuffer.wrap(EByte.intToBytes(PacketBase.TP_START_PCKT)); //marca inicio do pacote
		_channel.write(buffer); 
		buffer = ByteBuffer.wrap(EByte.intToBytes(this.getSize()));
		_channel.write(buffer);
		buffer = ByteBuffer.wrap(EByte.intToBytes(this.getSessionID()));
		_channel.write(buffer);
		buffer = ByteBuffer.wrap(EByte.intToBytes(this.getPriority()));
		_channel.write(buffer);
		buffer = ByteBuffer.wrap(EByte.intToBytes(this.getRequestID()));
		_channel.write(buffer);	
		buffer = ByteBuffer.wrap(EByte.intToBytes(this.getPackID()));
		_channel.write(buffer);		
		buffer = ByteBuffer.wrap(EByte.intToBytes(this.getChkSum()));
		_channel.write(buffer);
		buffer = ByteBuffer.wrap(EByte.intToBytes(this.getConfig()));
		_channel.write(buffer);		
	}
	
	public String toString()
	{
		StringBuffer ret = new StringBuffer(PacketBase.TP_HEADER_SIZE);
		ret.append("size=");
		ret.append(_size);
		ret.append("\nsid=");
		ret.append(_sessionID);
		ret.append("\npriority=");
		ret.append(_priority);
		ret.append("\nrid=");
		ret.append(_requestID);
		ret.append("\npid=");
		ret.append(_packID);
		ret.append("\nchksun=");
		ret.append(_chkSum);
		return ret.toString();
	}

	public int getConfig() {
		return _config;
	}

	public void setConfig(int config) {
		_config = config;
	}

	public void setChannel(SocketChannel channel) {
		_channel = channel;
	}
	
}