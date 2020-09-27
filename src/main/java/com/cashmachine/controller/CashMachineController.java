package com.cashmachine.controller;

import com.cashmachine.report.BalanceReport;
import com.cashmachine.report.WithdrawMoneyReport;
import com.cashmachine.service.CashMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cashMachine/")
public class CashMachineController {

    private CashMachineService cashMachineService;

    @Autowired
    public CashMachineController(CashMachineService cashMachineService) {
        this.cashMachineService = cashMachineService;
    }


    @RequestMapping(value = "checkCardStatus", method = RequestMethod.GET)
    public boolean checkCardStatus(
            @RequestParam Long cardNumber
    ) {
        return cashMachineService.checkCardStatus(cardNumber);
    }

    @RequestMapping(value = "checkPinCode", method = RequestMethod.GET)
    public boolean checkPinCode(
            @RequestParam Long cardNumber,
            @RequestParam Integer pinCode
    ) {
        return cashMachineService.checkPinCode(cardNumber, pinCode);
    }

    @RequestMapping(value = "blockCard", method = RequestMethod.POST)
    public ResponseEntity<Boolean> blockCard(
            @RequestBody Long cardNumber
    ) {
        boolean blockCard = cashMachineService.blockCard(cardNumber);
        if (blockCard) {
            return ResponseEntity.ok(true);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = "getBalance", method = RequestMethod.GET)
    public ResponseEntity<BalanceReport> getBalance(
            @RequestParam Long cardNumber,
            @RequestParam Integer pinCode
    ) {
        BalanceReport balance = cashMachineService.getBalance(cardNumber, pinCode);
        if (balance != null) {
            return ResponseEntity.ok(balance);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    }


    @RequestMapping(value = "withdrawMoney", method = RequestMethod.GET)
    public ResponseEntity<WithdrawMoneyReport> withdrawMoney(
            @RequestParam Long cardNumber,
            @RequestParam Integer pinCode,
            @RequestParam Long moneyAmount
    ) {

        WithdrawMoneyReport withdrawMoneyReport = cashMachineService.withdrawMoney(cardNumber, pinCode, moneyAmount);
        if (withdrawMoneyReport != null) {
            return ResponseEntity.ok(withdrawMoneyReport);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    }


}
