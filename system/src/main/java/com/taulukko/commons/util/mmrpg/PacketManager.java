package com.taulukko.commons.util.mmrpg;

import java.util.Date;
import java.util.HashMap;

import com.taulukko.commons.util.game.EDiceKit;
import com.taulukko.commons.util.mmrpg.packets.PacketBase;
import com.taulukko.commons.util.mmrpg.test.ITestSwitch;
import com.taulukko.commons.util.struct.ENoSearchException;
import com.taulukko.commons.util.struct.EPoolList;

public class PacketManager
{ 
	private IConnectionListnner _connectionListnner = new ConnectorListnnerBase();

	private static PacketManager _me;

	private HashMap<PacketUniqueKey, PacketBase> _proccessedPackets = new HashMap<PacketUniqueKey, PacketBase>();

	private HashMap<Session, Long> _lastPackets = new HashMap<Session, Long>();

	// Filas de prioridade para envio

	private EPoolList<PacketBase> _sendHP = new EPoolList<PacketBase>();

	private EPoolList<PacketBase> _sendMP = new EPoolList<PacketBase>();

	private EPoolList<PacketBase> _sendLP = new EPoolList<PacketBase>();

	// Filas de prioridade para processamento
	private EPoolList<PacketBase> _procHP = new EPoolList<PacketBase>();

	private EPoolList<PacketBase> _procMP = new EPoolList<PacketBase>();

	private EPoolList<PacketBase> _procLP = new EPoolList<PacketBase>();

	private int _percentH = 60;

	private int _percentM = 30;

	private int _percentL = 10;

	private PacketManager()
	{
	}

	public static PacketManager getInstance()
	{

		if (_me == null)
		{
			_me = new PacketManager();
		}
		return _me;
	}

	public int getRemainingProccess()
	{
		return _procHP.getLength() + _procLP.getLength() + _procMP.getLength();
	}

	public int getRemainingSend()
	{
		return _sendHP.getLength() + _sendLP.getLength() + _sendMP.getLength();
	}

	// processa dando prioridade para os pacotes conforme a fila que ele se
	// encontra.
	public void proccess(Session session)
	{
		if ((_percentH + _percentM + _percentL) != 100)
		{
			System.out
					.println("Configura��o incorreta de prioridades em PacketManager class.");
			return;
		}

		RequestHandler handler = RequestHandler.getInstance();
		try
		{
			int turn = EDiceKit.rool(1, 100, 0);
			PacketBase packet = null;
			if (turn < _percentH)
			{

				if (_procHP.getLength() > 0)
				{
					packet = _procHP.remove();
				}

			}
			else if (turn < (_percentH + _percentM))
			{

				if (_procMP.getLength() > 0)
				{

					packet = _procMP.remove();
				}

			}
			else
			{

				if (_procLP.getLength() > 0)
				{

					packet = _procLP.remove();
				}

			}

			if (packet != null)
			{
				System.out.println("Processou pacote ID= "
						+ packet.getHeader().getPackID());
				System.out.println("Processos remanecentes ="
						+ this.getRemainingProccess());
				handler.doAction(session, packet);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// envia dando prioridade para os pacotes conforme a fila que ele se
	// encontra.
	public void send()
	{
		//synchronized (this)
		{
			// System.out.println("Processos remanecentes:"
			// + this.getRemainingProccess());
			if ((_percentH + _percentM + _percentL) != 100)
			{
				System.out
						.println("Configura��o incorreta de prioridades em PacketManager class.");
				return;
			}

			try
			{
				int turn = EDiceKit.rool(1, 100, 0);
				PacketBase packet = null;
				if (turn < _percentH)
				{
					packet = getLastPacketToSend(_sendHP);
				}
				else if (turn < (_percentH + _percentM))
				{
					packet = getLastPacketToSend(_sendMP);
				}
				else
				{
					packet = getLastPacketToSend(_sendLP);
				}
				if (packet != null)
				{
					long nowTime = new Date().getTime();
					long diffTime = nowTime - packet.getLastSendTimes();
					int sendAndReturn = IMMORPGConstants.TP_PACKET_TIME_OUT * 2;
					if (diffTime > sendAndReturn)
					{
						packet.send();

						System.out.println("Pacote enviado ID="
								+ packet.getHeader().getPackID());
						System.out.println("Pacotes remanecentes para enviar ="
								+ this.getRemainingSend());

						if (packet.getSendTimes() > IMMORPGConstants.TP_PACKET_LIFE_TIME)
						{
							cancel(packet);
							System.out
									.println("Pacote cancelado, vida dele acabou!");
							if (_connectionListnner != null)
							{
								// avisa que foi cancelado
								_connectionListnner.packetLifeOut(packet);
							}
						}

					}
				}
			}
			catch (Exception e)
			{
				System.out.println("error in PacketManager.send");
				e.printStackTrace();
			}
		}
	}

	private PacketBase getLastPacketToSend(EPoolList<PacketBase> pool)
			throws ENoSearchException
	{
		PacketBase packet = null;
		if (pool.getLength() > 0)
		{
			packet = pool.get();
			if ((packet.getHeader().getConfig() & IConfigHeader.TP_IGNORE_RESPONSE) == IConfigHeader.TP_IGNORE_RESPONSE)
			{
				System.out
						.println("Pacote removido da pilha pois nao necessita de retorno");
				// remove pois n�o exige retorno
				pool.remove();

			}
		}
		return packet;
	}

	// Adiciona na pilha para envio
	public void putToSend(PacketBase packet) throws Exception
	{

		packet.toBox();
		System.out.println("Pacote recebido para envio PID="
				+ packet.getHeader().getPackID());
		if (packet.getHeader().getPriority() == IPriority.HIG)
		{
			if ((packet.getHeader().getConfig() & IConfigHeader.TP_IGNORE_RESPONSE) != IConfigHeader.TP_IGNORE_RESPONSE)
			{
				System.out.println("Pacote com sistema de reenvio on");
				// se n�o vier resposta ele reenvia
				_sendHP.add(packet);

			}
			System.out.println("Pacote de alta prioridade enviado PID="
					+ packet.getHeader().getPackID());
			packet.send();
		}
		else if (packet.getHeader().getPriority() == IPriority.MID)
		{
			if ((packet.getHeader().getConfig() & IConfigHeader.TP_IGNORE_RESPONSE) != IConfigHeader.TP_IGNORE_RESPONSE)
			{

				// se nao vier resposta ele reenvia
				_sendMP.add(packet);

			}
		}
		else
		{
			if ((packet.getHeader().getConfig() & IConfigHeader.TP_IGNORE_RESPONSE) != IConfigHeader.TP_IGNORE_RESPONSE)
			{

				// se nao vier resposta ele reenvia
				_sendLP.add(packet);

			}
		}

	}

	public void ping(Session session) throws Exception
	{

		if (session == null)
		{
			return;
		}

		Date now = new Date();
		long nowTime = now.getTime();

		Long lastPacket = null;
		if (_lastPackets.containsKey(session))
		{
			lastPacket = _lastPackets.get(session);
		}
		else
		{
			lastPacket = Long.valueOf(0);
		}

		if (nowTime > (lastPacket + IMMORPGConstants.TP_SEND_PING_TIME))
		{
			// atualiza o ultimo pacote para nao reenviar o ping
			_lastPackets.remove(session);
			_lastPackets.put(session, nowTime);

			if (PacketBase.getType() == PacketBase.TP_CLIENT_SERVER)
			{
				putToSend(new com.taulukko.commons.util.mmrpg.packets.clientserver.Ping(
						session));
			}
			else
			{
				putToSend(new com.taulukko.commons.util.mmrpg.packets.serverclient.Ping(
						session));
			}

		}

	}

	// Adiciona na pilha para envio
	public void putToProccess(PacketBase packet) throws Exception
	{
		if (!packet.getIsValid())
		{

			return;
		}

		updateLastPacketSession(packet);

		System.out.println("Pacote recebido para processamento ID="
				+ packet.getHeader().getPackID());
		if ((PacketBase.getType() == PacketBase.TP_CLIENT_SERVER && packet
				.getIsClientServer())
				|| (PacketBase.getType() == PacketBase.TP_SERVER_CLIENT && packet
						.getIsServerClient()))
		{
			System.out.println("Pacote de retorno recebido ID="
					+ packet.getHeader().getPackID());

			int rid = packet.getHeader().getRequestID();
			if (rid == PacketBase.LOGOFF_CLIENT_SERVER
					|| rid == PacketBase.LOGOFF_SERVER_CLIENT)
			{
				packet.getSession().logoff();
			}

			if (ITestSwitch.TP_SERVER_NOT_ANSWER)
			{
				if (PacketBase.getType() != PacketBase.TP_CLIENT_SERVER)
				{
					cancel(packet);
				}
			}
			else if (ITestSwitch.TP_CLIENT_NOT_ANSWER)
			{
				if (PacketBase.getType() != PacketBase.TP_SERVER_CLIENT)
				{
					cancel(packet);
				}
			}
			else
			{
				System.out.println("Pacote removido, retorno recebido");
				// pacote de retorno
				cancel(packet);
			}

		}
		else
		{
			PacketUniqueKey key = new PacketUniqueKey(packet.getSession().getID(),packet.getHeader().getPackID());
			if (!_proccessedPackets.containsValue(key))
			{
				_proccessedPackets.put(key, packet);

				// pacote ainda nao processado
				if (packet.getHeader().getPriority() == IPriority.HIG)
				{
					_procHP.add(packet);
				}
				else if (packet.getHeader().getPriority() == IPriority.MID)
				{
					_procMP.add(packet);
				}
				else
				{
					_procLP.add(packet);
				}
			}
			else
			{
				System.out
						.println("Pacote recebido para processamento e ignorado ID="
								+ packet.getHeader().getPackID()
								+ " SessionID=" + packet.getSession().getID());
				PacketBase prepacket = _proccessedPackets.get(key);
				System.out
						.println("Pacote recebido para processamento e ignorado ID="
								+ prepacket.getHeader().getPackID()
								+ " SessionID="
								+ prepacket.getSession().getID());

			}

			if ((packet.getHeader().getConfig() & IConfigHeader.TP_IGNORE_RESPONSE) != IConfigHeader.TP_IGNORE_RESPONSE)
			{
				// nao pode ignorar a resposta
				putToSend(packet.createResponse());
			}
		}
	}

	private void updateLastPacketSession(PacketBase packet)
	{
		long lastPacket = new Date().getTime();
		Session session = packet.getSession();
		if (!_lastPackets.containsKey(session))
		{
			_lastPackets.put(session, lastPacket);
		}
		else
		{
			_lastPackets.remove(session);
			_lastPackets.put(session, lastPacket);
		}
	}

	// remove da pilha de envio
	private void cancel(PacketBase packet)
	{

		if (packet.getHeader().getPriority() == IPriority.HIG)
		{

			// se nao vier resposta ele reenvia
			_sendHP.remove(packet);

		}
		else if (packet.getHeader().getPriority() == IPriority.MID)
		{

			_sendMP.remove(packet);

		}
		else
		{

			_sendLP.remove(packet);

		}
	}

	public IConnectionListnner getConnectionListnner()
	{
		return _connectionListnner;
	}

	public void setConnectionListnner(IConnectionListnner connectionListnner)
	{
		_connectionListnner = connectionListnner;
	}
	
	
	private class PacketUniqueKey
	{

	    private static final int HASH_PRIME = 9369319;
	    
		private int _pid;
		private int _sid;
		public PacketUniqueKey(int sid, int pid)
		{
			_pid=pid;
			_sid=sid;
		}
		
		
		public boolean equals(Object o)
		{
			if (!(o instanceof PacketUniqueKey))
			{
				return false;
			}
			
			PacketUniqueKey receive = (PacketUniqueKey)o;
			
			return receive._pid==this._pid &&  receive._sid == this._sid;
		}


		@Override
	    /**
	     * This overrides hashCode() in java.lang.Object
	     */
	    public int hashCode() {
	        int result = 0;

	        result = HASH_PRIME * result + _pid;
	        result = HASH_PRIME * result + _sid ;	        

	        return result;
	    }
	}
}


