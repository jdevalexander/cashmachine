package com.cashmachine.report;

/**
 * Class report showing card balance information with current date
 */
public class BalanceReport {

    private Long cardNumber;
    private Long date;
    private Long cardBalance;

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(Long cardBalance) {
        this.cardBalance = cardBalance;
    }
}
