package com.zt.bookkeeping.ledger.domain.invitation.service;

import com.zt.bookkeeping.ledger.domain.invitation.entity.InvitationAgg;
import com.zt.bookkeeping.ledger.domain.invitation.entity.InvitationCodeVO;
import com.zt.bookkeeping.ledger.domain.invitation.factory.InvitationFactory;
import com.zt.bookkeeping.ledger.domain.invitation.repository.InvitationRepository;
import com.zt.bookkeeping.ledger.domain.ledger.entity.LedgerAgg;
import com.zt.bookkeeping.ledger.domain.ledger.repository.LedgerRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class InvitationDomainService {

    @Resource
    private InvitationRepository invitationRepository;

    @Resource
    private InvitationFactory invitationFactory;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 8;
    private final SecureRandom random = new SecureRandom();

    public InvitationAgg getLatestInvitationCode(String ledgerNo, String userNo) {
        // 获取用户账本下最新的邀请码
        InvitationAgg invitationAgg = invitationRepository.loadLatestInvitationAgg(
                ledgerNo, userNo);
        // 是否有效
        if (invitationAgg != null && invitationAgg.isValid()) {
            // 返回这个邀请码
            return invitationAgg;
        }

        // 生成一个邀请码并返回
        invitationAgg = generateInvitationCode(userNo, ledgerNo);
        invitationAgg.create();
        // 插入数据库
        invitationRepository.insert(invitationAgg);
        return invitationAgg;
    }

    public InvitationAgg generateInvitationCode(String userNo, String ledgerNo) {
        InvitationCodeVO code = generateRandomCode();
        // 检查是否重复
        while (invitationRepository.isCodeExist(code)) {
            code = generateRandomCode();
        }
        return invitationFactory.createInvitationAgg(ledgerNo, userNo, code);
    }

    private InvitationCodeVO generateRandomCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return new InvitationCodeVO(code.toString());
    }

    public void useInvitationCode(String code, String userNo) {
        // 查询这个邀请码
        InvitationAgg invitationAgg = invitationRepository.loadByCode(new InvitationCodeVO(code));
        if (invitationAgg == null) {
            return;
        }

        // 邀请码有效，使用邀请码
        invitationAgg.useInvitation(userNo);
        // 更新数据库
        invitationRepository.update(invitationAgg);

        // 发送领域事件
    }

    public InvitationAgg loadByCode(InvitationCodeVO code) {
        return invitationRepository.loadByCode(code);
    }

}
