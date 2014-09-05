package com.taulukko.commons.util.mmrpg;

/*
 * ClienteFTN.java
 * 
 * Faculdades Tancredo Neves
 * Disciplina: Redes de Computadores e Sistemas Distribu�dos
 * Prof. Luiz Gustavo Pacola Alves 
 * 
 * Data: 
 * Aluno:
 * 
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import com.taulukko.commons.util.mmrpg.packets.PacketBase;

/*
 * O desenvolvimento desse client foi abortado pois ele n�o era NIO e precisava-se de socketsChannels
 * **/
public class OutClient {
	
	public void start()
	{
		try {
			//tipo de aplicativo
			PacketBase.setType(PacketBase.TP_CLIENT_SERVER);
			// Cria variaveis para receber o endereco IP e a porta do servidor
			BufferedReader endIPServidorBuf, portaServidorBuf, mensagemUserBuf;
			endIPServidorBuf = new BufferedReader(new InputStreamReader(
					System.in));
			portaServidorBuf = new BufferedReader(new InputStreamReader(
					System.in));
			mensagemUserBuf = new BufferedReader(new InputStreamReader(
					System.in));

			// Obtem endereco IP e porta do servidor via teclado
			System.out.print("End. IP do Servidor: ");
			String endIPServidor = endIPServidorBuf.readLine();
			System.out.print("Porta do Servidor: ");
			int portaServidor = Integer.parseInt(portaServidorBuf.readLine());

			// TODO Criar uma variavel para receber as mensagens do usuario
			// (semelhante ao endereco IP e porta)
			String mensagem;

			// TODO Criar aqui o Socket que ir� conectar-se com o servidor
			Socket client = new Socket(endIPServidor, portaServidor);
			System.out.println("Se conectando em " + client.getLocalAddress().getHostAddress() + ":"
					+ client.getLocalPort());

			// Criar os canais para obter os streams de entrada / saida
			InputStream dataInput = client.getInputStream();
			OutputStream dataOutput = client.getOutputStream();

			// Variavel que armazena mensagem recebida do servidor
			String msgReceber = "";

			// Receber uma mensagem de Sucesso na conexao do stream de
			// entrada
			byte[] buff = new byte[24];
			dataInput.read(buff);			
			PacketBase pack = PacketBase.create(buff);
			if(pack.getHeader().getSize()>0)
			{
				buff = new byte[pack.getHeader().getSize()];
				dataInput.read(buff);
				pack.loadContent(buff);
			}
			Session session = new Session(null,pack.getHeader().getSessionID());
			
			// Implemente aqui o nosso protocolo - lado cliente!!!
			do {
				try {
					System.out.println("Cliente Conectado ao servidor.");
					System.out.println("Digite nome de usu�rio:");
					String username  = mensagemUserBuf.readLine()+"\n";
					System.out.println("Digite senha:");
					String password = mensagemUserBuf.readLine()+"\n";
					//Login packet = new Login()
					// Cria pacote de Login
					
					//dataOutput.write(mensagem.getBytes());

					// Receber mensagem do servidor
					//buff = new byte[2048];
					//dataInput.read(buff);
					//msgReceber = new String(buff);

					//System.out.print("SERVER>>> ");
					//System.out.println(msgReceber.trim());
					Thread.sleep(200);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} while (!msgReceber.trim().equals("TERMINATE"));

			//Fechar o socket
			dataInput.close();
			dataOutput.close();
			client.close();

			System.out.println("*** Conexao encerrada! ***");

		} catch (Exception e) {
			System.err.println("Ocorreu uma excecao: " + e.getMessage());
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public static void main(String[] args) {
		OutClient client = new OutClient();
		client.start();		
	}
}
