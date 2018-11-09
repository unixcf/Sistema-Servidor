package org.projeto.servidor;

import java.io.File;

import org.projeto.Sistema;
import org.projeto.cliente.util.ClienteDados;
import org.projeto.importante.Tarefas;
import org.projeto.importante.logger.Logger;

public class Operador {
	
	
	public Operador(String mensagem, ClienteDados dados) {
		
		if (dados.nome == null) {
			 dados.nome = mensagem;
			 new Logger("Usuario cadastrado <" + dados.nome + ">.", dados.tipo);
		}
		
		if (mensagem.contains(":")) {
			String[] variavel = mensagem.split(":");
			if (variavel[0].equals("arquivo")) {
				 dados.arquivo = variavel[1];
				 new Logger("Arquivo solicitado <" + dados.arquivo + "> pelo cliente <" + dados.nome + ">.", dados.tipo);
				 CheckArquivo(dados.arquivo, dados);
			}
		}
		
		new Logger("Mensagem recebida do cliente <" +dados.nome+ ">: "+ mensagem, Sistema.Tipo.CLIENTE);
		Tarefas.Executar();
	}

	
	private void CheckArquivo(String nome, ClienteDados dados) {
		Tarefas.Adicionar(new Tarefas.Criar() {
			@Override
			public void Executar() {
				new Thread(dados.Enviar("Testando conex�o..")).start();;
                if (!(new File("servidor/arquivos/" + nome)).exists()) new Thread(dados.Enviar("Nenhum arquivo localizado no diretorio servidor/arquivos/"+nome)).start();
			}
		});
	}
}