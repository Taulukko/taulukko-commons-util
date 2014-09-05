/*
 * Created on 14/06/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.taulukko.commons.util.mmrpg;

/*
 * ServidorFTN.java
 * 
 * Faculdades Tancredo Neves Disciplina: Redes de Computadores e Sistemas
 * Distribu�dos Prof. Luiz Gustavo Pacola Alves
 * 
 * Data: Aluno:
 * 
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import com.taulukko.commons.util.mmrpg.packets.PacketBase;
import com.taulukko.commons.util.mmrpg.packets.serverclient.ConnectionOK;

public class Server
{

	private int _port = 8010;

	private ArrayList<Session> _sessions = new ArrayList<Session>();

	private Selector selector = null;

	private ServerSocketChannel selectableChannel = null;

	int keysAdded = 0;

	static Log _log = Log.create();

	private RequestHandler _requestHandler;

	public Server(RequestHandler requestHandler)
	{
		_requestHandler = requestHandler;
		_log.setClassName(Server.class.getName());
	}

	public Server(RequestHandler requestHandler, int port)
	{
		this(requestHandler);
		this._port = port;
	}

	public void initialize()

	throws IOException
	{
		this.selector = SelectorProvider.provider().openSelector();
		this.selectableChannel = ServerSocketChannel.open();
		this.selectableChannel.configureBlocking(false);
		InetAddress lh = InetAddress.getLocalHost();
		InetSocketAddress isa = new InetSocketAddress(lh, this._port);
		this.selectableChannel.socket().bind(isa);
		// configura o tipo de aplica��o
		PacketBase.setType(PacketBase.TP_SERVER_CLIENT);
	}

	protected void finalize()

	throws IOException
	{
		this.selectableChannel.close();
		this.selector.close();
	}

	public void acceptConnections() throws Exception
	{

		SelectionKey acceptKey = this.selectableChannel.register(this.selector,
				SelectionKey.OP_ACCEPT);

		System.out.println("Escutando no host: " + InetAddress.getLocalHost());
		System.out.println("Escutando na porta: " + _port);
		_log.debug("Acceptor loop...");

		while ((this.keysAdded = acceptKey.selector().select()) > 0)
		{

			Set readyKeys = this.selector.selectedKeys();
			Iterator i = readyKeys.iterator();

			while (i.hasNext())
			{
				long timeFirst = new Date().getTime();
				SelectionKey key = (SelectionKey) i.next();
				i.remove();
				ClientCallBack callback = (ClientCallBack) key.attachment();
				try
				{
					if (key.isAcceptable())
					{

						ServerSocketChannel nextReady = (ServerSocketChannel) key
								.channel();

						acceptClient(key, nextReady);
					}

					else if (key.isReadable())
					{
						SelectableChannel nextReady = (SelectableChannel) key
								.channel();
						this.readMessage(callback);
					}
					else if (key.isWritable())
					{
						try
						{
							SelectableChannel nextReady = (SelectableChannel) key
									.channel();
							this.writeMessage(callback);
						}
						catch (Exception e)
						{
							System.out.println("Erro bizarro");
							e.printStackTrace();
						}
					}
					else if (key.isConnectable())
					{
						Socket socket = callback.getChannel().socket();
						System.out.println("Conex�o com Cliente "
								+ socket.getInetAddress().getHostAddress()
								+ ":" + socket.getPort() + " encerrada.");
					}

					long timeSecond = new Date().getTime();
					long timeDelay = timeSecond - timeFirst;
					if (timeDelay < IMMORPGConstants.TP_TIME_PER_PROC_SRV)
					{
						// System.out.println("em repouso...");
						Thread.sleep(IMMORPGConstants.TP_TIME_PER_PROC_SRV
								- timeDelay);
					}
					else
					{
						// System.out.println("trabalhando...");
					}
				}
				catch (IOException e)
				{
					_log.info("IOException");
					if (e.getMessage() != null
							&& (e.getMessage().hashCode() == IErrors.ERR_CONNECTION_ABORT || e
									.getMessage().hashCode() == IErrors.ERR_CONNECTION_ABORT2))
					{
						_log.info("Session:" + _sessions.size());
						_log.info("Connections:"
								+ this.selector.selectedKeys().size());
						Session session = callback.getSession();
						_sessions.remove(session);
						session.logoff();
						_log.error("Connection aborted by client "
								+ session.getID());
						_log.info("Session:" + _sessions.size());
						_log.info("Connections:"
								+ this.selector.selectedKeys().size());

					}
					else
					{
						_log.error(e);
						if (e.getMessage() != null)
						{
							_log.info("Code:" + e.getMessage().hashCode());
						}
						e.printStackTrace();
					}
				}

			}
		}

		_log.debug("End acceptor loop...");

	}

	/**
	 * @param key
	 * @param nextReady
	 * @throws IOException
	 * @throws ClosedChannelException
	 */
	private void acceptClient(SelectionKey key, ServerSocketChannel nextReady)
			throws Exception
	{
		_log.debug("Processing selection key read=" + key.isReadable()
				+ " write=" + key.isWritable() + " accept="
				+ key.isAcceptable());
		SocketChannel channel = nextReady.accept();
		channel.configureBlocking(false);
		// registra escuta em leitura e escrita
		SelectionKey readwriteKey = channel.register(this.selector,
				SelectionKey.OP_READ + SelectionKey.OP_WRITE);
		Session session = SessionManager.newInstance(channel);
		ClientCallBack clicall = new ClientCallBack(session, channel);
		readwriteKey.attach(clicall);

		_log.info("Conectado com Cliente "
				+ channel.socket().getInetAddress().getHostAddress() + ":"
				+ channel.socket().getPort());
		ConnectionOK packet = new ConnectionOK(session);
		_sessions.add(session);
		_requestHandler.getPacketManager().putToSend(packet);

		// Mensagem de sucesso na conex�o
		// String msgSucesso = "*** Conexao com sucesso! ***";

		// Enviar mensagem de sucesso ao cliente
		// clicall.write(msgSucesso.getBytes());
	}

	public void readMessage(ClientCallBack callback) throws Exception
	{

		BufferManager buffManager = callback.getBufferManager();
		ByteBuffer byteBuffer = ByteBuffer.allocate(buffManager
				.getRemainingBytes());
		SocketChannel channel = callback.getChannel();
		byteBuffer.clear();
		int nbytes = channel.read(byteBuffer);
		if (nbytes == -1)
		{
			System.out.print("erro!!!");
			return;
		}
		buffManager.addBytes(byteBuffer, nbytes);

		if (buffManager.getState() == BufferManager.TP_CONTENT_COMPLETED)
		{
			buffManager.clear();
			PacketBase packet = buffManager.getPacket();
			packet.setSession(callback.getSession());
			PacketManager.getInstance().putToProccess(packet);
		}
	}

	private void writeMessage(ClientCallBack callback) throws Exception
	{
		// processa (geralmente eh no processamento que � enviado respostas)
		if (callback != null)
		{
			PacketManager.getInstance().proccess(callback.getSession());
			PacketManager.getInstance().send();

			PacketManager.getInstance().ping(callback.getSession());
		}
	}

	public static void main(String[] args)
	{

		Server nbServer = new Server(RequestHandler.getInstance(), 8010);

		try
		{
			nbServer.initialize();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}

		String msg = "Servidor foi abortado";

		try
		{
			nbServer.acceptConnections();
			msg = "Server disconnect!";
		}
		catch (IOException e)
		{
			_log.error(e);
			e.printStackTrace();
		}
		catch (InterruptedException e)
		{
			_log.error("Exiting normally...");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			_log.error("Unknow exception...");
			e.printStackTrace();
		}
		System.err.println(msg);

	}

}