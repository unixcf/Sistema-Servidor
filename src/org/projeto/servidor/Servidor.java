package org.projeto.servidor;

import java.net.*;
import java.text.Format;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

import org.projeto.inicio;

import java.io.*;

public class Servidor {

	private ServerSocket servidor; //Servidor
	
	private boolean ouvir_porta = false;
	HashMap<Socket, String> servidores = new HashMap<Socket, String>();
	
	
	public Servidor() {
		
		try {
		this.servidor = new ServerSocket(12345);
		this.ouvir_porta = true;
		
		new inicio.Logger("Servidor iniciado...");
		//Chamar Entrada
		Entrada();
		
		} catch(Exception ex) {
			
		}
		
	}
	
	
	
	private void Entrada() throws IOException {
		while(ouvir_porta) {
			
			  Socket conexao = servidor.accept(); //ouvir a conexao
			  new inicio.Logger("Conex�o -> " + conexao);
			  try {
		
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
							Scanner entrada = new Scanner(conexao.getInputStream());
					         
							  while (entrada.hasNextLine()) {
					        	     String texto = entrada.nextLine();
							
									 String[] comandos = texto.split("/");
									 
									 if (comandos[0].equals("CriarCliente")) {
										 servidores.put(conexao, comandos[1]); //Salvar Cliente
										 EnviarParaCliente(visualizarArquivos(), conexao);
									 } else
									 if(comandos[0].equals("Baixar")) {
										  new inicio.Logger("Solicitando download do arquivo " + comandos[1] + "....");
										  Buscar(comandos[1], conexao);
									 }
							  }
							}
							 catch (Exception e) {
								  
							  }
						}
						}).start();;
				  
				 

				 
				// new Arquivo(scan.nextLine(), conexao).Enviar();
				 
			} catch (Exception e) {
				new inicio.Logger("Conex�o desligada de forma for�ada de um cliente.");
			}
		}
	}
	
	
	private void Buscar(String arquivo, Socket cliente_que_solicitou) {
		for (Socket clientes : servidores.keySet()) {
		try {
			if (clientes != cliente_que_solicitou)
			   EnviarParaCliente("Buscar/"+arquivo, clientes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	
	private String visualizarArquivos() throws IOException {

		File diretorio = new File("diretorio");
		File arquivos[] = diretorio.listFiles();
		int i = 0;
		String  retorno = "Lista de Arquivos para Download:/n";
		for (int j = arquivos.length; i < j; i++) {
			File arquivo = arquivos[i];
			retorno += " [-] " + arquivo.getName() + "/n";
		}
		
		return retorno;
	}
	
	private void EnviarParaCliente(String msg, Socket cliente) throws Exception {
		PrintStream enviar_nome_arquivo = new PrintStream (cliente.getOutputStream()); 
		enviar_nome_arquivo.println(msg);
		enviar_nome_arquivo.flush();
	}
}
