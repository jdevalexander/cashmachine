package com.cashmachine.service;

import com.cashmachine.model.CardInfo;
import com.cashmachine.model.OperationLog;
import com.cashmachine.report.BalanceReport;
import com.cashmachine.report.BlockCardRequest;
import com.cashmachine.report.WithdrawMoneyReport;
import com.cashmachine.report.WithdrawMoneyRequest;
import com.cashmachine.repository.CardInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class CashMachineService {

    private CardInfoRepository cardInfoRepository;

    @Autowired
    public CashMachineService(CardInfoRepository cardInfoRepository) {
        this.cardInfoRepository = cardInfoRepository;
    }


    /**
     * @param cardNumber Card number
     * @return Status of the card. Blocked (false) or not blocked (true) card
     */
    public boolean checkCardStatus(Long cardNumber) {
        CardInfo notBlockedCard = cardInfoRepository.getNotBlockedCard(cardNumber);
        return !ObjectUtils.isEmpty(notBlockedCard);
    }


    /**
     * @param cardNumber Card number
     * @param pinCode    pin code to access to card
     * @return return true if pin code is correct
     */
    public boolean checkPinCode(Long cardNumber, Integer pinCode) {
        return !ObjectUtils.isEmpty(cardInfoRepository.getNotBlockedCard(cardNumber, pinCode));
    }


    /**
     * Block card if it's contained in DB and not blocked
     *
     * @param request request contains card number
     * @return Return true if it's successfully blocked
     */
    public boolean blockCard(BlockCardRequest request) {
        CardInfo notBlockedCard = cardInfoRepository.getNotBlockedCard(request.getCardNumber());

        if (!ObjectUtils.isEmpty(notBlockedCard)) {

            notBlockedCard.setBlocked(true);

            try {
                cardInfoRepository.save(notBlockedCard);
                return true;
            } catch (DataAccessException e) {
                //todo add logger (eg log4j)
                System.out.println("Error: Error while blocking card" + e.getMessage());
                return false;
            }

        }
        return false;
    }


    /**
     * Withdraw money from not blocked card
     * Break if current balance less than amount of money for withdraw
     * Add operation log with code withdraw_money and current date
     *
     * @param request Card number, pin code, amount of money for withdraw
     * @return amount of money for withdraw, cardNumber, current date, card balance after withdraw
     */
    public WithdrawMoneyReport withdrawMoney(WithdrawMoneyRequest request) {
        CardInfo notBlockedCard = cardInfoRepository.getNotBlockedCard(request.getCardNumber(), request.getPinCode());
        if (!ObjectUtils.isEmpty(notBlockedCard)) {
            Long currentBalance = notBlockedCard.getBalance();

            //User cannot amount more money than on balance
            if (currentBalance < request.getMoneyAmount()) {
                return null;
            }

            //Change card balance
            notBlockedCard.setBalance(currentBalance - request.getMoneyAmount());

            List<OperationLog> operationLogs = notBlockedCard.getOperationLogs();

            //Create new info about operation with card
            OperationLog operationLog = new OperationLog();
            operationLog.setDate(new Date().getTime());
            operationLog.setOperationCode("withdraw_money");
            operationLog.setWithdrewMoney(request.getMoneyAmount());
            operationLog.setCardId(notBlockedCard.getId());
            operationLogs.add(operationLog);

            notBlockedCard.setOperationLogs(operationLogs);

            //Create response report withdraw Money Report
            WithdrawMoneyReport withdrawMoneyReport = new WithdrawMoneyReport();
            withdrawMoneyReport.setMoneyAmount(request.getMoneyAmount());
            withdrawMoneyReport.setCardBalance(notBlockedCard.getBalance());
            withdrawMoneyReport.setCardNumber(notBlockedCard.getCardNumber());
            withdrawMoneyReport.setDate(new Date().getTime());

            try {
                cardInfoRepository.save(notBlockedCard);
                return withdrawMoneyReport;
            } catch (DataAccessException e) {
                //todo add logger (eg log4j)
                System.out.println("Error: Error withdraw money card balance " + e.getMessage());
                return null;
            }

        }
        return null;
    }


    /**
     * Return current balance of not blocked card
     * Add operational log with show_balance code and current date
     *
     * @param cardNumber Card number
     * @param pinCode    pin code
     * @return cardNumber, current date, current card balance
     */
    public BalanceReport getBalance(Long cardNumber, Integer pinCode) {
        CardInfo notBlockedCard = cardInfoRepository.getNotBlockedCard(cardNumber, pinCode);
        if (!ObjectUtils.isEmpty(notBlockedCard)) {

            //Create response report
            BalanceReport balanceReport = new BalanceReport();
            balanceReport.setCardBalance(notBlockedCard.getBalance());
            balanceReport.setCardNumber(notBlockedCard.getCardNumber());
            balanceReport.setDate(new Date().getTime());

            List<OperationLog> operationLogs = notBlockedCard.getOperationLogs();

            //Create new info about operation with card
            OperationLog operationLog = new OperationLog();
            operationLog.setDate(new Date().getTime());
            operationLog.setOperationCode("show_balance");
            operationLog.setCardId(notBlockedCard.getId());
            operationLogs.add(operationLog);

            notBlockedCard.setOperationLogs(operationLogs);

            try {
                cardInfoRepository.save(notBlockedCard);
                return balanceReport;
            } catch (DataAccessException e) {
                //todo add logger (eg log4j)
                System.out.println("Error: Error getting card balance " + e.getMessage());
                return null;
            }

        }
        return null;
    }
}
