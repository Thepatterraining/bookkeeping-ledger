package com.zt.bookkeeping.ledger.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zt.bookkeeping.ledger.domain.invitation.entity.InvitationAgg;
import com.zt.bookkeeping.ledger.domain.invitation.entity.InvitationCodeVO;
import com.zt.bookkeeping.ledger.domain.invitation.entity.InvitationStatusVO;
import com.zt.bookkeeping.ledger.domain.invitation.repository.InvitationRepository;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerBudgetVO;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerStatusVO;
import com.zt.bookkeeping.ledger.domain.ledger.repository.LedgerRepository;
import com.zt.bookkeeping.ledger.infrastructure.db.InvitationMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.LedgerBudgetMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.LedgerMapper;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.InvitationPO;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.LedgerBudgetPO;
import com.zt.bookkeeping.ledger.infrastructure.db.entity.LedgerPO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public class InvitationRepositoryImpl implements InvitationRepository {

    @Resource
    private InvitationMapper invitationMapper;



    @Override
    public InvitationAgg loadLatestInvitationAgg(String ledgerNo, String inviterNo) {
        // 查询账本基本信息
        LambdaQueryWrapper<InvitationPO>  wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InvitationPO::getLedgerNo, ledgerNo)
                .eq(InvitationPO::getInviterNo, inviterNo)
                .eq(InvitationPO::getStatus, InvitationStatusVO.ACTIVE.getCode())
                .le(InvitationPO::getExpireTime, LocalDateTime.now())
                .last("limit 1");
        InvitationPO invitationPO = invitationMapper.selectOne(wrapper);
        if (invitationPO == null) {
            return null;
        }
        return toEntity(invitationPO);
    }

    @Override
    public Boolean isCodeExist(InvitationCodeVO code) {
        // 查询账本基本信息
        LambdaQueryWrapper<InvitationPO>  wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InvitationPO::getCode, code.getCode())
                .last("limit 1");
        InvitationPO invitationPO = invitationMapper.selectOne(wrapper);
        return invitationPO != null;
    }

    @Override
    public void insert(InvitationAgg invitationAgg) {
        InvitationPO invitationPO = toPO(invitationAgg);
        invitationMapper.insert(invitationPO);
    }

    @Override
    public void update(InvitationAgg invitationAgg) {
        InvitationPO invitationPO = toPO(invitationAgg);
        invitationMapper.updateById(invitationPO);
    }

    @Override
    public InvitationAgg loadByCode(InvitationCodeVO code) {
        LambdaQueryWrapper<InvitationPO>  wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InvitationPO::getCode, code.getCode())
                .last("limit 1");
        InvitationPO invitationPO = invitationMapper.selectOne(wrapper);
        return toEntity(invitationPO);
    }

    private InvitationPO toPO(InvitationAgg invitationAgg) {
        return InvitationPO.builder()
                .id(invitationAgg.getInvitationId())
                .code(invitationAgg.getInvitationCode().getCode())
                .ledgerNo(invitationAgg.getLedgerNo())
                .inviterNo(invitationAgg.getInviterNo())
                .expireTime(invitationAgg.getExpireTime())
                .maxUseCount(invitationAgg.getMaxUseCount())
                .usedCount(invitationAgg.getUsedCount())
                .status(invitationAgg.getStatus().getCode())
                .build();
    }

    private InvitationAgg toEntity(InvitationPO invitationPO) {
        return InvitationAgg.builder()
                .invitationId(invitationPO.getId())
                .invitationCode(new InvitationCodeVO(invitationPO.getCode()))
                .ledgerNo(invitationPO.getLedgerNo())
                .inviterNo(invitationPO.getInviterNo())
                .expireTime(invitationPO.getExpireTime())
                .maxUseCount(invitationPO.getMaxUseCount())
                .usedCount(invitationPO.getUsedCount())
                .status(InvitationStatusVO.of(invitationPO.getStatus()))
                .build();
    }

}
