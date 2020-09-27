package com.cashmachine.service;

import com.cashmachine.model.CardInfo;
import com.cashmachine.model.OperationLog;
import com.cashmachine.report.BalanceReport;
import com.cashmachine.report.WithdrawMoneyReport;
import com.cashmachine.repository.CardInfoRepository;
import com.cashmachine.repository.OperationCatalogRepository;
import com.cashmachine.repository.OperationLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class CashMachineService {

    private CardInfoRepository cardInfoRepository;
    private OperationLogRepository operationLogRepository;
    private OperationCatalogRepository operationCatalogRepository;

    @Autowired
    public CashMachineService(CardInfoRepository cardInfoRepository, OperationLogRepository operationLogRepository, OperationCatalogRepository operationCatalogRepository) {
        this.cardInfoRepository = cardInfoRepository;
        this.operationLogRepository = operationLogRepository;
        this.operationCatalogRepository = operationCatalogRepository;
    }


    public boolean checkCardStatus(Long cardNumber) {
        CardInfo notBlockedCard = cardInfoRepository.getNotBlockedCard(cardNumber);
        return !ObjectUtils.isEmpty(notBlockedCard);
    }


    public boolean checkPinCode(Long cardNumber, Integer pinCode) {
        return !ObjectUtils.isEmpty(cardInfoRepository.getNotBlockedCard(cardNumber, pinCode));
    }


    public boolean blockCard(Long cardNumber) {
        CardInfo notBlockedCard = cardInfoRepository.getNotBlockedCard(cardNumber);

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


    public WithdrawMoneyReport withdrawMoney(Long cardNumber, Integer pinCode, Long moneyAmount) {
        CardInfo notBlockedCard = cardInfoRepository.getNotBlockedCard(cardNumber, pinCode);
        if (!ObjectUtils.isEmpty(notBlockedCard)) {
            Long currentBalance = notBlockedCard.getBalance();

            //User cannot amount more money than on balance
            if (currentBalance < moneyAmount) {
                return null;
            }

            //Change card balance
            notBlockedCard.setBalance(currentBalance - moneyAmount);

            List<OperationLog> operationLogs = notBlockedCard.getOperationLogs();

            //Create new info about operation with card
            OperationLog operationLog = new OperationLog();
            operationLog.setDate(new Date().getTime());
            operationLog.setOperationCode("withdraw_money");
            operationLog.setWithdrewMoney(moneyAmount);
            operationLogs.add(operationLog);

            notBlockedCard.setOperationLogs(operationLogs);

            //Create response report withdraw Money Report
            WithdrawMoneyReport withdrawMoneyReport = new WithdrawMoneyReport();
            withdrawMoneyReport.setMoneyAmount(moneyAmount);
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


    public BalanceReport getBalance(Long cardNumber, Integer pinCode) {
        CardInfo notBlockedCard = cardInfoRepository.getNotBlockedCard(cardNumber, pinCode);
        if (!ObjectUtils.isEmpty(notBlockedCard)) {

            BalanceReport balanceReport = new BalanceReport();
            balanceReport.setCardBalance(notBlockedCard.getBalance());
            balanceReport.setCardNumber(notBlockedCard.getCardNumber());
            balanceReport.setDate(new Date().getTime());

            List<OperationLog> operationLogs = notBlockedCard.getOperationLogs();

            OperationLog operationLog = new OperationLog();
            operationLog.setDate(new Date().getTime());
            operationLog.setOperationCode("show_balance");
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
