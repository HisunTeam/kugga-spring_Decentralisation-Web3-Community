package com.hisun.kugga.module.system.controller.admin.sensitiveword.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.hisun.kugga.framework.excel.core.annotations.DictFormat;
import com.hisun.kugga.framework.excel.core.convert.DictConvert;
import com.hisun.kugga.module.system.enums.DictTypeConstants;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 敏感词 Excel VO
 *
 * @author 永不言败
 */
@Data
public class SensitiveWordExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("敏感词")
    private String name;

    @ExcelProperty("标签")
    private List<String> tags;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("创建时间")
    private Date createTime;

}
