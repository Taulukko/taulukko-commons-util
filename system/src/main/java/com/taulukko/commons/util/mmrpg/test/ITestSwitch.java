package com.taulukko.commons.util.mmrpg.test;

public interface ITestSwitch
{
	public static final boolean TP_AUTO_DISCONNECT = false; //desconecta o cliente do servidor apos o login
	public static final boolean TP_AUTO_LOGOFF_BY_CLIENT = true; //envia pacote de logoff para o servidor (o que ocasionara com o tempo disconnect)
	public static final boolean TP_AUTO_LOGOFF_BY_SERVER = false; //envia pacote de logoff para o servidor (o que ocasionara com o tempo disconnect)
	public static final boolean TP_PACKET_TEST = true;
	public static final int TP_PACKET_TEST_TIMES = 10;
	public static final boolean TP_HEADER_VERIFY_FIELD = false;
	public static final int TP_HEADER_VERIFY_FIELD_RATE = 4; //1 =100%, 2 = 50%, 4 = 25% ...
	public static final boolean TP_SERVER_NOT_ANSWER = false; //packet time life test
	public static final boolean TP_CLIENT_NOT_ANSWER = false; //packet time life test	
}
