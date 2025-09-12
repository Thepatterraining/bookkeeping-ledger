package com.zt.bookkeeping.ledger.infrastructure.factory;

import com.zt.bookkeeping.ledger.domain.generator.SnowFlakeGenerator;
import com.zt.bookkeeping.ledger.domain.invitation.entity.InvitationAgg;
import com.zt.bookkeeping.ledger.domain.invitation.entity.InvitationCodeVO;
import com.zt.bookkeeping.ledger.domain.invitation.entity.InvitationStatusVO;
import com.zt.bookkeeping.ledger.domain.invitation.factory.InvitationFactory;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Desc:
 * ------------------------------------
 * Author:zt@meituan.com
 * Date:2025/9/1
 * Time:17:27
 */
@Component
public class InvitationFactoryImpl implements InvitationFactory {


    @Override 
    public InvitationAgg createInvitationAgg(String ledgerNo, String userNo, InvitationCodeVO invitationCodeVO) {
        return InvitationAgg.builder()
                .ledgerNo(ledgerNo)
                .invitationCode(invitationCodeVO)
                .inviterNo(userNo)
                .status(InvitationStatusVO.ACTIVE)
                .expireTime(LocalDateTime.now().plusDays(1))
                .maxUseCount(1)
                .usedCount(0)
                .build();
    }


}
