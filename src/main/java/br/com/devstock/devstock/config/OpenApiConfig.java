package br.com.devstock.devstock.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "DevStock API",
                version = "1.0.0",
                description = """
                        API REST para gerenciamento de ativos de TI.

                        Permite o gerenciamento de notebooks, desenvolvedores,
                        kits de equipamentos, onboardings, entregas,
                        devoluções, movimentações e ocorrências.
                        """
        )
)
public class OpenApiConfig {
}
