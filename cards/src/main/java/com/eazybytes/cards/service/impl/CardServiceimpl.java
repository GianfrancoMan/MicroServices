package com.eazybytes.cards.service.impl;

import com.eazybytes.cards.constants.CardsConstants;
import com.eazybytes.cards.entity.Cards;
import com.eazybytes.cards.exception.CardAlreadyExistsException;
import com.eazybytes.cards.repository.CardsRepository;
import com.eazybytes.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardServiceimpl implements ICardsService {

    private CardsRepository cardsRepository;

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
}





