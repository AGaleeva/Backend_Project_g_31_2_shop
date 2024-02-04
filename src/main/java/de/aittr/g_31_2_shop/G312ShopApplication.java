package de.aittr.g_31_2_shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("de.aittr.g_31_2_shop")
@SpringBootApplication
public class G312ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(G312ShopApplication.class, args);
	}

}
