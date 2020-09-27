package com.cashmachine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "card_info", schema = "public", catalog = "cash_machine")
public class CardInfo {
    private Integer id;
    private Long cardNumber;
    private Integer pinCode;
    private Long balance;
    private Boolean blocked;

    @Id
    @JsonIgnore
    @Column(name = "id")
    @SequenceGenerator(name = "cardInfoSeq", sequenceName = "card_info_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cardInfoSeq")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "card_number")
    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Basic
    @Column(name = "pin_code")
    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }

    @Basic
    @Column(name = "balance")
    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    @Basic
    @Column(name = "blocked")
    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardInfo cardInfo = (CardInfo) o;

        if (!Objects.equals(id, cardInfo.id)) return false;
        if (!Objects.equals(cardNumber, cardInfo.cardNumber)) return false;
        if (!Objects.equals(pinCode, cardInfo.pinCode)) return false;
        if (!Objects.equals(balance, cardInfo.balance)) return false;
        return Objects.equals(blocked, cardInfo.blocked);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = 31 * result + (pinCode != null ? pinCode.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (blocked != null ? blocked.hashCode() : 0);
        return result;
    }


    private List<OperationLog> operationLogs;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id")
    public List<OperationLog> getOperationLogs() {
        return operationLogs;
    }

    public void setOperationLogs(List<OperationLog> operationLogs) {
        this.operationLogs = operationLogs;
    }
}
