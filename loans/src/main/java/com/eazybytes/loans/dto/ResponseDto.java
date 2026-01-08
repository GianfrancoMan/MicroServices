package com.eazybytes.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema( //permette di dare dettagli sulle classi usate negli endpoint da visualizzare nella documentazione swagger
        name = "Response", // anziche visualizzare il nome tecnico "AccountsDto" verrà visualizzato "Accounts"
        description = "Schema to hold Loans information"
)
@Data @AllArgsConstructor
public class ResponseDto {

    @Schema(description = "Status Code in the response")
    private String statusCode;

    @Schema(description = "Status message in the response")
    private String statusMsg;

}
