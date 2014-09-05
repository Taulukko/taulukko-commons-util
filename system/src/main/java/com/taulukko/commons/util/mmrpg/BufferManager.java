package com.taulukko.commons.util.mmrpg;

import java.nio.ByteBuffer;

import com.taulukko.commons.util.lang.EByte;
import com.taulukko.commons.util.mmrpg.packets.PacketBase;

public class BufferManager
{
	public static final int TP_HEADER_START = 1;

	public static final int TP_HEADER_COMPLETED = 2;

	public static final int TP_CONTENT_START = 3;

	public static final int TP_CONTENT_COMPLETED = 4;

	private int _state = 0;

	private ByteBuffer _buffer;

	private PacketBase _packet;

	private Log _log = Log.create();

	public BufferManager()
	{
		this.clear();
	}

	public void clear()
	{
		_state = TP_HEADER_START;
		_buffer = ByteBuffer.allocate(PacketBase.TP_HEADER_SIZE);
	
	}

	public int getState()
	{
		return _state;
	}

	public int getRemainingBytes()
	{
		return _buffer.remaining();
	}

	public void addBytes(ByteBuffer byteBuffer, int nbytes)
	{
		synchronized (_buffer)
		{
			if (_state < TP_HEADER_COMPLETED)
			{
				addHeaderBytes(byteBuffer, nbytes);
			}
			else
			{
				addContentBytes(byteBuffer, nbytes);
			}
		}
	}
	
	private byte[] verifyStartHeader(byte[] readBuff,int nbytes)
	{
		if(_buffer.position()==0)
		{
			if(nbytes>3)
			{
				int pad = 0;
				int verify = EByte.bytesToInt(readBuff);
				if (verify != PacketBase.TP_START_PCKT)
				{
					// correcao de ponteiro
					System.out.println("Inicio errado de pacote, numero recebido" + verify);
					for (int cont = 1; cont < (nbytes - 3) && pad == 0; cont++)
					{
						verify = EByte.bytesToInt(readBuff, cont);
						if (verify == PacketBase.TP_START_PCKT)
						{
							pad = cont;
						}
					}
					if(pad!=0)
					{
						System.out.println("inicio de correcao");
						byte fixBuff[]=new byte[nbytes-pad]; 
						//System.arraycopy(readBuff,pad,fixBuff,0,nbytes-pad);
						readBuff=fixBuff;
						System.out.println("final de correcao");
					}
					else
					{
						return null;
					}
				}
			}
		}
		return readBuff;
	}

	private void addHeaderBytes(ByteBuffer byteBuffer, int nbytes)
	{

		byte readBuff[] = byteBuffer.array();
		//corrige o comeco do cabecalho caso esteja errado
		readBuff = verifyStartHeader(readBuff,nbytes);
		if(readBuff==null)
		{
			System.out
			.println("N�o foi poss�vel corrigir o cabecalho, dados ignorados!");
			this.clear();
			return;
		}
		
		int size = (readBuff.length<nbytes)?readBuff.length:nbytes;
		_buffer.put(readBuff, 0, size);

		if (_buffer.remaining() == 0)
		{
			byte[] bytes = _buffer.array();			
			
			_packet = PacketBase.create(bytes);
			if (_packet == null)
			{
				System.out
						.println("N�o foi poss�vel determinar o in�cio do pacote");
				this.clear();
				return;
			}

			if (_packet.getHeader().getSize() > PacketBase.TP_MAX_SIZE || _packet.getHeader().getSize() <0)
			{
				//DEBUG
				if(_packet.getHeader().getSize() <0)
				{
					//erro reinicia a maq estados
					_log
							.warning("Tamanho de pacote menor que zero ");
				}
				//DEBUG
				// erro reinicia a maq estados
				_log
						.warning("Tamanho de pacote incorreto recebido no servidor");
				this.clear();
				return;
			}
			// _packet.println();
			_buffer = ByteBuffer.allocate( _packet.getHeader().getSize());			
			_state = (_packet.getHeader().getSize() == 0) ? TP_CONTENT_COMPLETED
					: TP_HEADER_COMPLETED;
		}
		else
		{
			_state = TP_HEADER_START;
		}
	}


	private void addContentBytes(ByteBuffer byteBuffer, int nbytes)
	{
		byte readBuff[] = byteBuffer.array();
		_buffer.put(readBuff, 0, nbytes);
		if (_buffer.remaining() == 0)
		{
			byte[] bytes = _buffer.array();
			_packet.setContent(bytes);
			_state = TP_CONTENT_COMPLETED;

		}
		else
		{
			_state = TP_CONTENT_START;
		}
		_log
				.debug("Adicionou " + _buffer.position() + "/"
						+ _buffer.capacity());
		_log.debug(new String(byteBuffer.array()));
	}

	public PacketBase getPacket()
	{
		return _packet;
	}
}
