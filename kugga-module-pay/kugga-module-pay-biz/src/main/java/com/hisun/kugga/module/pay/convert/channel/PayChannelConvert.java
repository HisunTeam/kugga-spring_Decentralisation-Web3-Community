package com.hisun.kugga.module.pay.convert.channel;

import com.hisun.kugga.framework.common.pojo.PageResult;
import com.hisun.kugga.module.pay.controller.admin.merchant.vo.channel.PayChannelCreateReqVO;
import com.hisun.kugga.module.pay.controller.admin.merchant.vo.channel.PayChannelExcelVO;
import com.hisun.kugga.module.pay.controller.admin.merchant.vo.channel.PayChannelRespVO;
import com.hisun.kugga.module.pay.controller.admin.merchant.vo.channel.PayChannelUpdateReqVO;
import com.hisun.kugga.module.pay.dal.dataobject.merchant.PayChannelDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PayChannelConvert {

    PayChannelConvert INSTANCE = Mappers.getMapper(PayChannelConvert.class);

    @Mapping(target = "config", ignore = true)
    PayChannelDO convert(PayChannelCreateReqVO bean);

    @Mapping(target = "config", ignore = true)
    PayChannelDO convert(PayChannelUpdateReqVO bean);

    @Mapping(target = "config", expression = "java(com.hisun.kugga.framework.common.util.json.JsonUtils.toJsonString(bean.getConfig()))")
    PayChannelRespVO convert(PayChannelDO bean);

    List<PayChannelRespVO> convertList(List<PayChannelDO> list);

    PageResult<PayChannelRespVO> convertPage(PageResult<PayChannelDO> page);

    List<PayChannelExcelVO> convertList02(List<PayChannelDO> list);


}
