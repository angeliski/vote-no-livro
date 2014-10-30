package br.com.angeliski.configuration;

import javax.enterprise.event.Observes;
import javax.servlet.ServletContext;

public class Configuracao {

	public void onCreate(@Observes ServletContext context) {
		System.out.println("iniciando sistema...");
	}

}
