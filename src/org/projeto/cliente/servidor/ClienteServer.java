package org.projeto.cliente.servidor;

import java.net.ServerSocket;

import org.projeto.arquivo.Arquivo;

public class ClienteServer {

	private ServerSocket servidor;
	public String ip;
	int arquivo = 0;
	
	public ClienteServer(int porta) {
		try {
			this.servidor = new ServerSocket(porta);
			this.ip = servidor.getInetAddress().getHostAddress() + ":" + servidor.getLocalPort();
			System.out.println("Servidor de recebimento, ligado..");	
			while(true) {
			new Arquivo((arquivo++) + ".zip", servidor.accept()).Receber();
			}
		    } catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}