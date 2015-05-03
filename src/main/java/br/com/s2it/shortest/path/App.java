package br.com.s2it.shortest.path;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Classe responsável por iniciar o contexto do Spring. Define quais pacotes
 * devem ser scaneados em busca de beans e classes mapeadas.
 * */
@SpringBootApplication
@ComponentScan(basePackages = { "br.com.s2it.shortest.path.algorithm",
		"br.com.s2it.shortest.path.controller",
		"br.com.s2it.shortest.path.model" })
public class App {
	public static void main(String[] args) {
		run(App.class, args);
	}
}
