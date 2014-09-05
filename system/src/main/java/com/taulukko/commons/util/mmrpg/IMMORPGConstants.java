
package com.taulukko.commons.util.mmrpg;

public interface IMMORPGConstants
{
	public final static int TP_TIME_PER_PROC_CLI = 50;
	public final static int TP_TIME_PER_PROC_SRV = 100;
	public final static int TP_PACKET_TIME_OUT = 2000;
	public final static int TP_PACKET_LIFE_TIME = 10;
	//de qto em qto tempo (sem resposta) envia pacote de ping
	public final static int TP_SEND_PING_TIME = 10000;// 300000;
	//de quanto em qto tempo verifica se houve resposta
	public final static int TP_CHECK_PING = 1000;
	public final static boolean TP_THREADS_CACHE = false;
	public final static int TP_THREADS_SIZE = 20;
	

	
	
}
