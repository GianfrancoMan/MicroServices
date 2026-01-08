package com.eazybytes.cards.mapper;

import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.entity.Cards;

public class CardsMapper {
    /**
     * This method is used to convert the Cards entity to CardsDto
     *
     * @param cards
     * @param cardsDto
     * @return the mapped CardsDto instance
     */
    public static CardsDto cardsToCardsDto(Cards cards, CardsDto cardsDto) {

        cardsDto.setMobileNumber(cards.getMobileNumber());
        cardsDto.setCardNumber(cards.getCardNumber());
        cardsDto.setCardType(cards.getCardType());
        cardsDto.setTotalLimit(cards.getTotalLimit());
        cardsDto.setAmountUsed(cards.getAmountUsed());
        cardsDto.setAvailableAmount(cards.getAvailableAmount());

        return cardsDto;
    }

    /**
     * This method is used to convert the CardsDto to Cards entity
     *
     * @param cardsDto
     * @param cards
     * @return the mapped Cards instance
     */
    public static Cards cardsDtoToCards(CardsDto cardsDto, Cards cards) {

        cards.setMobileNumber(cardsDto.getMobileNumber());
        cards.setCardNumber(cardsDto.getCardNumber());
        cards.setCardType(cardsDto.getCardType());
        cards.setTotalLimit(cardsDto.getTotalLimit());
        cards.setAmountUsed(cardsDto.getAmountUsed());
        cards.setAvailableAmount(cardsDto.getAvailableAmount());

        return cards;
    }


}
