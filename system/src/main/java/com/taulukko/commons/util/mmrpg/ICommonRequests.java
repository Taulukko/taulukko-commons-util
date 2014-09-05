package com.taulukko.commons.util.mmrpg;

public interface ICommonRequests {
	
	final int TYPE_SERVER_CLIENT = 1000000000;
	final int TYPE_CLIENT_SERVER = 2000000000;
	
	// PACOTES SERVIDOR--> CLIENT	
	public final static int START = TYPE_SERVER_CLIENT + 1;
	public final static int  PING_SERVER_CLIENT = TYPE_SERVER_CLIENT + 2;	
	public final static int  DISCONNECT = TYPE_SERVER_CLIENT + 3;
	public final static int  LOGOFF_SERVER_CLIENT = TYPE_SERVER_CLIENT + 4;
	//PACOTES CLIENT-->SERVIDOR
	public final static int LOGIN = TYPE_CLIENT_SERVER + 1;	
	public final static int LOGOFF_CLIENT_SERVER = TYPE_CLIENT_SERVER + 2;	
	public final static int PING_CLIENT_SERVER = TYPE_CLIENT_SERVER + 3;
	public final static int TEST = TYPE_CLIENT_SERVER + 4;
}
