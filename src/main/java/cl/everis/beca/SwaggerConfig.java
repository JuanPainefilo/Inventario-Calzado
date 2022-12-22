package cl.everis.beca;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Transaccion Calzado")
				.description("Sistema de inventario mas transacciones de Zapateria")
                .version("0.1")
				.build();
	}

	@Bean
	public Docket calzadosApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Inventario")
				.apiInfo(apiInfo())
				.select()
				.paths(regex("/Inventario.*"))
				.build();
	}
	
	@Bean
	public Docket ventasApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Ventas")
				.apiInfo(apiInfo())
				.select()
				.paths(regex("/ventas.*"))
				.build();
	}

}