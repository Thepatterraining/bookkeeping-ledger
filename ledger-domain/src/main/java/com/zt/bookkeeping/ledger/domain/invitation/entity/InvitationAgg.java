package com.zt.bookkeeping.ledger.domain.invitation.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zt.bookkeeping.ledger.common.base.AbstractAgg;
import com.zt.bookkeeping.ledger.common.base.AbstractPO;
import com.zt.bookkeeping.ledger.common.enums.ResultCode;
import com.zt.bookkeeping.ledger.domain.exception.DomainException;
import com.zt.bookkeeping.ledger.domain.invitation.event.InvitationCodeCreatedEvent;
import com.zt.bookkeeping.ledger.domain.invitation.event.InvitationCodeUsedEvent;
import com.zt.bookkeeping.ledger.domain.ledger.event.LedgerCreatedEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Builder
@TableName("ledger")
@Getter
public class InvitationAgg extends AbstractAgg {

    private Long invitationId;
    private InvitationCodeVO invitationCode;
    private String ledgerNo;
    private String inviterNo;
    private InvitationStatusVO status;
    private LocalDateTime expireTime;
    private Integer maxUseCount;
    private Integer usedCount;

    // 使用邀请码
    public void useInvitation(String userNo) {
        // 验证邀请码有效性
        if (!isValid()) {
            throw new DomainException(ResultCode.INVITATION_INVALID);
        }
        // 更新使用状态
        usedCount++;
        status = InvitationStatusVO.USED;
        // 注册邀请码已使用事件
        registerDomainEvent(new InvitationCodeUsedEvent(this));
    }

    // 检查是否有效
    public boolean isValid() {
        // 检查状态
        if (!status.isValid()) {
            return false;
        }

        // 检查过期时间
        if (LocalDateTime.now().isAfter(expireTime)) {
            return false;
        }

        // 检查使用次数
        if (usedCount >= maxUseCount) {
            return false;
        }
        return true;
    }

    public void create() {
        // 注册邀请码已创建事件
        registerDomainEvent(new InvitationCodeCreatedEvent(this));
    }

}
