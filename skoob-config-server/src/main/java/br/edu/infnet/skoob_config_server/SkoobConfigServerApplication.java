package br.edu.infnet.skoob_config_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SkoobConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkoobConfigServerApplication.class, args);
	}
}