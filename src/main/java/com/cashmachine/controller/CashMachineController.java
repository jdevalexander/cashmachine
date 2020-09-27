package com.cashmachine.controller;

import com.cashmachine.report.BalanceReport;
import com.cashmachine.report.BlockCardRequest;
import com.cashmachine.report.WithdrawMoneyReport;
import com.cashmachine.report.WithdrawMoneyRequest;
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
    public ResponseEntity<Boolean> checkCardStatus(
            @RequestParam Long cardNumber
    ) {
        boolean checkCardStatus = cashMachineService.checkCardStatus(cardNumber);
        if (checkCardStatus) {
            return ResponseEntity.ok(true);
        }
        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "checkPinCode", method = RequestMethod.GET)
    public ResponseEntity<Boolean> checkPinCode(
            @RequestParam Long cardNumber,
            @RequestParam Integer pinCode
    ) {
        boolean checkPinCode = cashMachineService.checkPinCode(cardNumber, pinCode);
        if (checkPinCode) {
            return ResponseEntity.ok(true);
        }
        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "blockCard", method = RequestMethod.PUT)
    public ResponseEntity<Boolean> blockCard(
            @RequestBody BlockCardRequest blockCardRequest
    ) {
        boolean blockCard = cashMachineService.blockCard(blockCardRequest);
        if (blockCard) {
            return ResponseEntity.ok(true);
        }
        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);

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
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @RequestMapping(value = "withdrawMoney", method = RequestMethod.POST)
    public ResponseEntity<WithdrawMoneyReport> withdrawMoney(
            @RequestBody WithdrawMoneyRequest withdrawMoneyRequest
    ) {

        WithdrawMoneyReport withdrawMoneyReport = cashMachineService.withdrawMoney(withdrawMoneyRequest);
        if (withdrawMoneyReport != null) {
            return ResponseEntity.ok(withdrawMoneyReport);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }


}
