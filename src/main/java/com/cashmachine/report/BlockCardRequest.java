package com.cashmachine.report;

/**
 * Class request for blocking card? using in post/put methods
 */
public class BlockCardRequest {

    private Long cardNumber;

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }
}
