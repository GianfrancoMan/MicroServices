package com.eazybytes.accounts;

import com.eazybytes.accounts.dto.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl") //tramite auditorAwareRef indico il/i bean/s eseguono l'auditing di Jpa
@EnableConfigurationProperties(value = {AccountsContactInfoDto.class})
@OpenAPIDefinition( //permette  di definire dettagli della documentazione Swagger OpenApi
        info= @Info( //diamo  alcune informazioni sull'applicazione titolo sotto titolo...
            title = "Accounts microservice REST API Documentation",
            description = "Documentation for Accounts microservice which is part of EazyBank Application " ,
             version = "v1",
             contact = @Contact( //dettagli su chi contattare
                     name = "Gianfranco Manca",
                     email = "discent@gdot.com",
                     url = "https://www.gianfrancomanca.com"
             ),
             license = @License( //dettagli su eventuali licenze
                     name = "Apache 2.0",
                     url = "https://gianfrancomanca.org"
             )
        ),
        externalDocs = @ExternalDocumentation( //eventuale documentazione esterna creata per chi vuole approfondire la conoscenza sull'applicazione
                description = "EazyBank Accounts microservice REST API Documentation in deep",
                url = "https://eazybank.org/swagger-docs.html"
        )
)
public class AccountsApplication {

	static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
