package com.cashmachine.repository;

import com.cashmachine.model.CardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CardInfoRepository extends JpaRepository<CardInfo, Long> {

    @Query("select ci from CardInfo ci " +
            "where ci.cardNumber=:cardNumber " +
            "      and ci.blocked=false ")
    CardInfo getNotBlockedCard(@Param("cardNumber") Long cardNumber);


    //todo Change pinCode on hashCode or encrypted code
    @Query("select ci from CardInfo ci " +
            "where ci.cardNumber=:cardNumber " +
            "      and ci.pinCode=:pinCode " +
            "      and ci.blocked=false ")
    CardInfo getNotBlockedCard(
            @Param("cardNumber") Long cardNumber,
            @Param("pinCode") Integer pinCode

    );


}
