package com.taulukko.commons.util.mmrpg;

import java.nio.channels.ClosedChannelException;
import java.util.Date;
import java.util.HashMap;

import com.taulukko.commons.util.lang.EDate;
import com.taulukko.commons.util.mmrpg.packets.PacketBase;
import com.taulukko.commons.util.mmrpg.packets.clientserver.Login;
import com.taulukko.commons.util.mmrpg.packets.clientserver.Test;
import com.taulukko.commons.util.mmrpg.test.ITestSwitch;

public class RequestThreadBase extends Thread
{

	private Session _session;

	private PacketBase _packet;

	public RequestThreadBase(Session session, PacketBase packet)
	{
		_session = session;
		_packet = packet;
	}

	public void run()
	{
		try
		{
			resolveClientServerPacket();
			resolveServerClientPacket();
		}
		catch (ClosedChannelException cce)
		{
			System.out
					.println("fine: RequestThreadBase.run() catch: Server disconnect");
			_session.logoff();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private void resolveServerClientPacket() throws Exception
	{
		PacketManager packetManager = PacketManager.getInstance();

		if (!_packet.getIsServerClient())
		{
			return;
		}

		switch (_packet.getHeader().getRequestID())
		{
			case PacketBase.PING_SERVER_CLIENT:
			{
				System.out.println("Ping recebido da session:"
						+ _session.getID());
				break;
			}
			case PacketBase.START:
			{

				String username = (String) _session.getParameters().get(
						"username");
				String password = (String) _session.getParameters().get(
						"password");
				Login loginPacket = new Login(_session, username, password);
				packetManager.putToSend(loginPacket);

				if (ITestSwitch.TP_PACKET_TEST)
				{
					for (int cont = 0; cont < ITestSwitch.TP_PACKET_TEST_TIMES; cont++)
					{
						//Thread.sleep(10000);
						Test test = new Test(_session, "novo teste No" + cont,
								"novo teste No" + cont);
						packetManager.putToSend(test);
						System.out.println(new EDate().toString());
					}
				}

				if (ITestSwitch.TP_AUTO_LOGOFF_BY_CLIENT)
				{
					System.out.println("Enviando logoff...");
					com.taulukko.commons.util.mmrpg.packets.clientserver.Logoff logoffPacket = new com.taulukko.commons.util.mmrpg.packets.clientserver.Logoff(
							_session);
					packetManager.putToSend(logoffPacket);
				}

				if (ITestSwitch.TP_AUTO_DISCONNECT)
				{
					System.out.println("Disconectando...");
					_session.logoff();
				}

				break;

			}
			case PacketBase.LOGOFF_SERVER_CLIENT:
			{
				System.out.println("Session ID=" + _session.getID()
						+ " recebeu pacote de Logoff do servidor.");
				_session.logoff();
				break;
			}
		}

	}

	private static HashMap<Integer,Long> time = new HashMap<Integer,Long>();
	private static long maxTime =0;
	public void resolveClientServerPacket()
	{
		PacketManager packetManager = PacketManager.getInstance();
		if (!_packet.getIsClientServer())
		{
			return;
		}

		switch (_packet.getHeader().getRequestID())
		{
			case PacketBase.PING_CLIENT_SERVER:
			{
				System.out.println("Ping recebido da session:"
						+ _session.getID());
				break;
			}

			case PacketBase.LOGIN:
			{

				System.out.println(new EDate().toString()
						+ ":Login recebido para sessao:"
						+ _packet.getSession().getID() + " conteudo:"
						+ new String(_packet.getContent()));
				time.put(_packet.getSession().getID(),new Date().getTime());
				try
				{
					if (ITestSwitch.TP_AUTO_LOGOFF_BY_SERVER)
					{
						System.out.println("Enviando logoff...");
						com.taulukko.commons.util.mmrpg.packets.serverclient.Logoff logoffPacket = new com.taulukko.commons.util.mmrpg.packets.serverclient.Logoff(
								_session);						
						packetManager.putToSend(logoffPacket);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				break;
			}

			case PacketBase.TEST:
			{
				System.out.println("Teste recebido:"
						+ new String(_packet.getContent()));
				break;
			}
			case PacketBase.LOGOFF_CLIENT_SERVER:
			{
				System.out.println(new EDate().toString()
						+ "Logoff recebido da sess�o:" + _session.getID());
				long t = time.get(_packet.getSession().getID());
				long diff = new Date().getTime() - t;
				maxTime=(maxTime>diff)?maxTime:diff;
				System.out.println("Tempo maximo ateh o momento:"+ maxTime);
				_session.logoff();
				break;
			}
		}

	}
}
