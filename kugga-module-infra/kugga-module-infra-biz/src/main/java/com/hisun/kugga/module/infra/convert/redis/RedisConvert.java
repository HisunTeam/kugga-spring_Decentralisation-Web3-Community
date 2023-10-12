package com.hisun.kugga.module.infra.convert.redis;

import cn.hutool.core.util.StrUtil;
import com.hisun.kugga.framework.redis.core.RedisKeyDefine;
import com.hisun.kugga.module.infra.controller.admin.redis.vo.RedisKeyRespVO;
import com.hisun.kugga.module.infra.controller.admin.redis.vo.RedisMonitorRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Mapper
public interface RedisConvert {

    RedisConvert INSTANCE = Mappers.getMapper(RedisConvert.class);

    default RedisMonitorRespVO build(Properties info, Long dbSize, Properties commandStats) {
        RedisMonitorRespVO respVO = RedisMonitorRespVO.builder().info(info).dbSize(dbSize)
                .commandStats(new ArrayList<>(commandStats.size())).build();
        commandStats.forEach((key, value) -> {
            respVO.getCommandStats().add(RedisMonitorRespVO.CommandStat.builder()
                    .command(StrUtil.subAfter((String) key, "cmdstat_", false))
                    .calls(Integer.valueOf(StrUtil.subBetween((String) value, "calls=", ",")))
                    .usec(Long.valueOf(StrUtil.subBetween((String) value, "usec=", ",")))
                    .build());
        });
        return respVO;
    }

    List<RedisKeyRespVO> convertList(List<RedisKeyDefine> list);

}