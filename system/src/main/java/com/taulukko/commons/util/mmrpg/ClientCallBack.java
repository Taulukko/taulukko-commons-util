package com.taulukko.commons.util.mmrpg;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class ClientCallBack{
	
	private Session _session;
	
	static Log _log = Log.create();

	private SocketChannel _channel;

	private BufferManager _bufferManager = new BufferManager();

	
	public ClientCallBack(Session session , SocketChannel channel) throws IOException {
		this._session=session;
		this._channel = channel;
		ClientCallBack.class.getName();
		_log.setClassName(ClientCallBack.class.getName());
	}

	public SocketChannel getChannel() {
		return this._channel;
	}

	public Session getSession() {
		return _session;
	}

	public BufferManager getBufferManager() {
		return _bufferManager;
	}
}
