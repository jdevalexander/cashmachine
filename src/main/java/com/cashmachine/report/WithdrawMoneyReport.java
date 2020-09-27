package com.cashmachine.report;

public class WithdrawMoneyReport extends BalanceReport{

    private Long moneyAmount;

    public Long getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Long moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}
