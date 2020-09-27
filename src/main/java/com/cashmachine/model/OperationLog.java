package com.cashmachine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "operation_log", schema = "public", catalog = "cash_machine")
public class OperationLog {
    private Integer id;
    private Long date;
    private String operationCode;
    private Long withdrewMoney;
    private Integer cardId;

    @Id
    @JsonIgnore
    @Column(name = "id")
    @SequenceGenerator(name = "operationLogSeq", sequenceName = "operation_log_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operationLogSeq")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date")
    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    @Basic
    @Column(name = "operation_code")
    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    @Basic
    @Column(name = "withdrew_money")
    public Long getWithdrewMoney() {
        return withdrewMoney;
    }

    public void setWithdrewMoney(Long withdrewMoney) {
        this.withdrewMoney = withdrewMoney;
    }

    @Basic
    @Column(name = "card_id")
    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OperationLog that = (OperationLog) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(date, that.date)) return false;
        if (!Objects.equals(operationCode, that.operationCode))
            return false;
        if (!Objects.equals(withdrewMoney, that.withdrewMoney))
            return false;
        return Objects.equals(cardId, that.cardId);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (operationCode != null ? operationCode.hashCode() : 0);
        result = 31 * result + (withdrewMoney != null ? withdrewMoney.hashCode() : 0);
        result = 31 * result + (cardId != null ? cardId.hashCode() : 0);
        return result;
    }

}
