package com.zt.bookkeeping.ledger.application.invitation.service;

import com.zt.bookkeeping.ledger.application.invitation.dto.CreateInvitationRequest;
import com.zt.bookkeeping.ledger.application.invitation.dto.UseInvitationCodeRequest;
import com.zt.bookkeeping.ledger.application.ledger.dto.CreateLedgerRequest;
import com.zt.bookkeeping.ledger.application.ledger.dto.UpdateLedgerBudgetRequest;
import com.zt.bookkeeping.ledger.application.ledger.dto.UpdateLedgerRequest;
import com.zt.bookkeeping.ledger.common.base.DomainEvent;
import com.zt.bookkeeping.ledger.common.enums.ResultCode;
import com.zt.bookkeeping.ledger.domain.exception.AggNotExistsException;
import com.zt.bookkeeping.ledger.domain.invitation.entity.InvitationAgg;
import com.zt.bookkeeping.ledger.domain.invitation.service.InvitationDomainService;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerBudgetVO;
import com.zt.bookkeeping.ledger.domain.ledger.factory.LedgerFactory;
import com.zt.bookkeeping.ledger.domain.ledger.service.LedgerDomainService;
import com.zt.bookkeeping.ledger.infrastructure.util.UserContextHolder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class InvitationCommandApplicationService {

    @Resource
    private ApplicationEventPublisher eventPublisher;

    @Resource
    private InvitationDomainService invitationDomainService;


    public String getLatestCode(CreateInvitationRequest request) {
        // 获取用户ID
        String userNo = UserContextHolder.getCurrentUserNo();

        // 查询有效的邀请码
        InvitationAgg invitationAgg = invitationDomainService.getLatestInvitationCode(request.getLedgerNo(), userNo);

        // 获取注册的事件进行发布
        List<DomainEvent> domainEventList = invitationAgg.getDomainEvents();
        domainEventList.forEach(event -> eventPublisher.publishEvent(event));

        // 返回邀请码
        return invitationAgg.getInvitationCode().getCode();
    }

    public void useCode(UseInvitationCodeRequest request) {
        // 获取用户ID
        String userNo = UserContextHolder.getCurrentUserNo();

        invitationDomainService.useInvitationCode(request.getInvitationCode(), userNo);
    }


}
