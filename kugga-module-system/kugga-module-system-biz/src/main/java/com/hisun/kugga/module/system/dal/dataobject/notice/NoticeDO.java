package com.hisun.kugga.module.system.dal.dataobject.notice;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hisun.kugga.framework.common.enums.CommonStatusEnum;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import com.hisun.kugga.module.system.enums.notice.NoticeTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知公告表
 *
 * @author ruoyi
 */
@TableName("system_notice")
@KeySequence("system_notice_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
public class NoticeDO extends BaseDO {

    /**
     * 公告ID
     */
    private Long id;
    /**
     * 公告标题
     */
    private String title;
    /**
     * 公告类型
     * <p>
     * 枚举 {@link NoticeTypeEnum}
     */
    private Integer type;
    /**
     * 公告内容
     */
    private String content;
    /**
     * 公告状态
     * <p>
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;

}
