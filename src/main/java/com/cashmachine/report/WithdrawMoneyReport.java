package com.cashmachine.report;

/**
 * Class report showing card balance information with current date
 * and amount of money for withdraw
 */
public class WithdrawMoneyReport extends BalanceReport{

    private Long moneyAmount;

    public Long getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Long moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}
