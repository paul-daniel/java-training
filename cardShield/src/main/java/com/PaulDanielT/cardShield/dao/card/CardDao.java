package com.PaulDanielT.cardShield.dao.card;

import com.PaulDanielT.cardShield.model.Card;

import java.util.List;
import java.util.Optional;

public class CardDao implements ICardDao{
    @Override
    public List<Card> selectAllCards() {
        return null;
    }

    @Override
    public Optional<Card> selectCardById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Card> selectCardByCategory(String categoryName) {
        return null;
    }

    @Override
    public void createCard(Card card) {

    }

    @Override
    public void deleteCard(Integer cardId) {

    }

    @Override
    public void updateCard(Integer cardId, String categoryName) {

    }

    @Override
    public void existCardWithNumber(String cardNumber) {

    }
}
