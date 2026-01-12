package com.eazybytes.cards.service.impl;

import com.eazybytes.cards.constants.CardsConstants;
import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.entity.Cards;
import com.eazybytes.cards.exception.CardAlreadyExistsException;
import com.eazybytes.cards.exception.ResourceNotFoundException;
import com.eazybytes.cards.mapper.CardsMapper;
import com.eazybytes.cards.repository.CardsRepository;
import com.eazybytes.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private final CardsRepository cardsRepository;

    /**
     * Create a save in the database for a new card
     *
     * @param mobileNumber the mobile number of the card owner
     * @throws CardAlreadyExistsException if the mobile number already exists
     */
    @Override
    public void createCard(String mobileNumber) {

        Optional<Cards> optionalCard = cardsRepository.findByMobileNumber(mobileNumber);

        if (optionalCard.isPresent()) {
            throw new CardAlreadyExistsException("Card", "mobileNumber", mobileNumber);
        }

        cardsRepository.save(createNewCard(mobileNumber));
    }

    /**
     * Create a new card
     * @param mobileNumber
     * @return the new card
     */
    private Cards createNewCard(String mobileNumber) {

        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);

        return newCard;
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards card = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );

        CardsDto cardsDto = new CardsDto();
        CardsMapper.cardsToCardsDto(card, cardsDto);

        return cardsDto;
    }

    /**
     * Update the card details based on the card number present in the cardsDto.cardNumber field
     *
     * @param cardsDto  the card details to be updated
     * @return true if the update was successful
     * @throws ResourceNotFoundException if the card number is not found
     */
    @Override
    public boolean updateCard(CardsDto cardsDto) {

        Cards card = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "cardNumber", cardsDto.getCardNumber())
        );

        CardsMapper.cardsDtoToCards(cardsDto, card);
        cardsRepository.save(card);

        return true;
    }

    /**
     * Delete the card based on the mobile number
     *
     * @param mobileNumber  the owner's mobile number
     * @return boolean true if the delete operation was successful
     * @throws ResourceNotFoundException if the mobile number is not found
     */
    @Override
    public boolean deleteCard(String mobileNumber) {

       Cards card = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
               () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
       );

       cardsRepository.deleteById(card.getCardId());

       return true;
    }


}





