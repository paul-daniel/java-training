package com.PaulDanielT.cardShield.dao.card;

import com.PaulDanielT.cardShield.model.Card;

import java.util.List;
import java.util.Optional;

public interface ICardDao {
    List<Card> selectAllCards();
    Optional<Card> selectCardById(Integer id);
    List<Card> selectCardByCategory(String categoryName);
    void createCard(Card card);
    void deleteCard(Integer cardId);
    void updateCard(Integer cardId, String categoryName);
    void existCardWithNumber(String cardNumber);
}
