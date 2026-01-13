package com.eazybytes.cards.controller;

import com.eazybytes.cards.constants.CardsConstants;
import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.dto.ErrorResponseDto;
import com.eazybytes.cards.dto.ResponseDto;
import com.eazybytes.cards.service.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/*@Tag, @Operation, @ApiResponses and their nidified annotations are relative to Swagger OPEN API*/
@Tag(name = "CRUD REST APIs for Cards  in EazyBank",
        description = "CRUD REST APIs for Cards microservice in EazyBank to CREATE, UPDATE, FETCH AND DELETE card details")
@RestController
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class CardsController {

    private final ICardsService cardsService;

    /**
     *  Creates a new card based on the given mobile number if the mobile number is not already used
     *
     * @param mobileNumber the mobile number of the card owner
     * @return an instance of ResponseEntity<ResponseDto> if the operation success
     */
    @Operation(summary = "Create Card REST API", description = "REST API to create new card inside EazyBank")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "HTTP Status CREATED"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            ))
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createNewCard(
                                                                    @RequestParam
                                                                    @NotEmpty(message = "Mobile number cannot be empty or null")
                                                                    @Pattern(regexp = "(^[0-9]{10}$)", message = "Mobile number must be 10 digits")
                                                                    String mobileNumber) {

        this.cardsService.createCard(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }

    /**
     * Fetches the specific card from the DB using the mobile number of as a param request
     *
     * @param mobileNumber the mobile number
     * @return an instance of ResponseEntity<CardsDto> with card details.
     */
    @Operation(summary = "Fetch Card Details REST API", description = "REST API to fetch card details based on a mobile number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            ))
    })
    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCardDetails(
                                                            @NotEmpty(message = "Mobile number cannot be empty or null")
                                                            @Pattern(regexp = "(^[0-9]{10}$)", message = "Mobile number must be 10 digits")
                                                            @RequestParam String mobileNumber) {
        CardsDto cardsDto = this.cardsService.fetchCard(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsDto);
    }

    /**
     * Updates the specific card from the DB using its card number
     * so that the user cannot update it.
     *
     * @param cardsDto card details mapped in a CardsDto object
     * @return an instance of ResponseEntity<CardsDto> with card details if operation success.
     */
    @Operation(summary = "Update Card Details REST API", description = "REST API to update card details based on a card number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "417", description = "HTTP Status Expectation Failed"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            ))
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCardDetails(
                                                                    @Valid
                                                                    @RequestBody CardsDto cardsDto) {

        boolean updated = this.cardsService.updateCard(cardsDto);

       if(updated) {
           return ResponseEntity
                   .status(HttpStatus.OK)
                   .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
       } else {
           return ResponseEntity
                   .status(HttpStatus.EXPECTATION_FAILED)
                   .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));
       }
    }

    /**
     * Deletes the specific card from the DB using its mobile number
     *
     * @param mobileNumber the mobile number
     * @return an instance of ResponseEntity<ResponseDto> with card details if operation success.
     */
    @Operation(summary = "Delete Card REST API", description = "REST API to delete card  based on a mobile number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "417", description = "HTTP Status Expectation Failed"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(
                    schema = @Schema(implementation = ErrorResponseDto.class)
            ))
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCard(
            @NotEmpty(message = "Mobile number cannot be empty or null")
            @Pattern(regexp = "(^[0-9]{10}$)", message = "Mobile number must be 10 digits")
            @RequestParam String mobileNumber) {

        boolean deleted = this.cardsService.deleteCard(mobileNumber);

        if(deleted) {
            return ResponseEntity.ok(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE));
        }
    }

}
