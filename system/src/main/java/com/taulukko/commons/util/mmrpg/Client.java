package com.taulukko.commons.util.mmrpg;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Date;
import java.util.Iterator;

import com.taulukko.commons.util.mmrpg.packets.PacketBase;

public class Client
{
	Session _session;

	// The host:port combination to connect to
	private InetAddress _hostAddress;

	private int _port;

	// The selector we'll be monitoring
	private Selector _selector;

	private RequestHandler _requestHandler;

	private String _username;

	private String _password;

	private BufferManager _bufferManager = new BufferManager();

	private Log _log = Log.create();

	private SocketChannel _socket;

	boolean _forceStop = true;

	private boolean _connected = false;

	private boolean _logged = false;

	public Client(RequestHandler requestHandler) throws IOException
	{
		this._requestHandler = requestHandler;
		this._selector = SelectorProvider.provider().openSelector();
		_log.setClassName(this.getClass().getName());
		// configura o tipo de aplicacao
		PacketBase.setType(PacketBase.TP_CLIENT_SERVER);
		_log.debug("Cliente inicializado.");
	}

	public void connect(String hostAddress, int port) throws Exception
	{
		_connected = true;
		this._hostAddress = InetAddress.getByName(hostAddress);
		this._port = port;
		_socket = initiateConnection();
		_log.debug("Cliente conectado.");
	}

	public void login(String username, String password) throws Exception
	{
		if (!_connected)
		{
			throw new Exception("Client not connected!");
		}
		_logged = true;
		this._username = username;
		this._password = password;
		_log.debug("Login inicializado.");
	}

	public void start() throws Exception
	{

		if (!_logged)
		{
			throw new Exception("Client not logged!");
		}

		ClientPingThread clientPingThread = null;

		while (_forceStop)
		{
			long timeFirst = new Date().getTime();
			try
			{
				// Wait for an event one of the registered channels
				this._selector.select();

				// Iterate over the set of keys for which events are available
				Iterator selectedKeys = this._selector.selectedKeys()
						.iterator();
				while (selectedKeys.hasNext())
				{

					SelectionKey key = (SelectionKey) selectedKeys.next();
					selectedKeys.remove();

					if (!key.isValid())
					{
						continue;
					}

					// Check what event is available and deal with it
					if (key.isConnectable())
					{
						this.finishConnection(key);
					}
					else if (key.isReadable())
					{
						// _log.debug("readable");
						this.read(key);
					}
					else if (key.isWritable())
					{
						// _log.debug("writable");
						this.write(key);
					}

				}

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			if (clientPingThread == null && _session != null)
			{
				// controle de ping pro servidor
				clientPingThread = new ClientPingThread(_session);
				clientPingThread.start();
			}
			// processa e envia
			this.clock();
			// controle de exesso de processamento
			long timeSecond = new Date().getTime();
			long timeDelay = timeSecond - timeFirst;
			if (timeDelay < IMMORPGConstants.TP_TIME_PER_PROC_CLI)
			{
				// System.out.println("em repouso..."
				// + (IConstants.TP_TIME_PER_PROC - timeDelay));
				Thread.sleep(IMMORPGConstants.TP_TIME_PER_PROC_CLI - timeDelay);
			}
			else
			{
				// System.out.println("trabalhando...");
			}

		}
	}

	private void clock()
	{
		PacketManager packetManager = PacketManager.getInstance();
		// System.out.println("Sistema de processamento iniciando...");
		packetManager.proccess(_session);
		// System.out.println("Processamentos restantes:" +
		// packetManager.getRemainingProccess());
		// System.out.println("Sistema de envio iniciando...");
		// System.out.println("Envios restantes:"+
		// packetManager.getRemainingSend());
		packetManager.send();
	}

	private void read(SelectionKey key) throws IOException
	{
		SocketChannel socketChannel = (SocketChannel) key.channel();

		// Attempt to read off the channel
		try
		{
			ByteBuffer buffer = ByteBuffer.allocate(_bufferManager
					.getRemainingBytes());
			int numRead = socketChannel.read(buffer);

			if (numRead == -1)
			{
				// Remote entity shut the socket down cleanly. Do the
				// same from our end and cancel the channel.
				key.channel().close();
				key.cancel();
				_bufferManager.clear();
				return;
			}

			_bufferManager.addBytes(buffer, numRead);
			if (_bufferManager.getState() == BufferManager.TP_CONTENT_COMPLETED)
			{
				_bufferManager.clear();
				PacketBase packet = _bufferManager.getPacket();

				if (packet.getHeader().getRequestID() == PacketBase.START)
				{
					_session = new Session(socketChannel, packet.getHeader()
							.getSessionID());
					_session.getParameters().put("username", _username);
					_session.getParameters().put("password", _password);
				}
				else
				{
					if (_session == null)
					{
						System.out.println("Startup fail!");
						return;
					}
				}

				packet.setSession(_session);
				PacketManager.getInstance().putToProccess(packet);
				// Register an interest in writing on this channel
				// key.interestOps(SelectionKey.OP_READ +
				// SelectionKey.OP_WRITE);
			}

		}
		catch (Exception e)
		{
			// The remote forcibly closed the connection, cancel
			// the selection key and close the channel.
			key.cancel();
			socketChannel.close();
			return;
		}

	}

	private void write(SelectionKey key) throws IOException
	{
		if (PacketManager.getInstance().getRemainingProccess() == 0)
		{
			// Register an interest in writing on this channel
			// key.interestOps(SelectionKey.OP_READ);
		}
	}

	private void finishConnection(SelectionKey key) throws IOException
	{
		SocketChannel socketChannel = (SocketChannel) key.channel();

		// Finish the connection. If the connection operation failed
		// this will raise an IOException.
		try
		{
			socketChannel.finishConnect();
		}
		catch (IOException e)
		{
			// Cancel the channel's registration with our selector
			System.out.println(e);
			key.cancel();
			return;
		}
	}

	private SocketChannel initiateConnection() throws IOException
	{
		// Create a non-blocking socket channel
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);

		// Kick off connection establishment
		socketChannel.connect(new InetSocketAddress(this._hostAddress,
				this._port));

		socketChannel.register(_selector, SelectionKey.OP_CONNECT
				+ SelectionKey.OP_READ + SelectionKey.OP_WRITE);
		return socketChannel;
	}

	public static void main(String[] args)
	{
		try
		{
			Client client = new Client(RequestHandler.getInstance());
			client.connect("192.168.0.1", 8010);
			client.login("teste", "teste");
			Thread.sleep(1000);
			client.start();
			client.stop();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void stop()
	{
		_forceStop = true;
	}
}
