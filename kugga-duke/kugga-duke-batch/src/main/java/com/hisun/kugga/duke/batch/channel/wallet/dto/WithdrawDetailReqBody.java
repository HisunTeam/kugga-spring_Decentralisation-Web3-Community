package com.hisun.kugga.duke.batch.channel.wallet.dto;

import com.hisun.kugga.framework.common.wallet.WalletBaseReqBody;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author: zhou_xiong
 */
@Data
public class WithdrawDetailReqBody extends WalletBaseReqBody {
    /**
     * Wallet order number
     */
    @NotEmpty(message = "orderNo cannot be empty")
    private String orderNo;
}

