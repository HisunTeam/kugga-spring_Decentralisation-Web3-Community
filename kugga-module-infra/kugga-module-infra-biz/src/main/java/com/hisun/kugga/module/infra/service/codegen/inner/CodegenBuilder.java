package com.hisun.kugga.module.infra.service.codegen.inner;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.google.common.collect.Sets;
import com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO;
import com.hisun.kugga.module.infra.convert.codegen.CodegenConvert;
import com.hisun.kugga.module.infra.dal.dataobject.codegen.CodegenColumnDO;
import com.hisun.kugga.module.infra.dal.dataobject.codegen.CodegenTableDO;
import com.hisun.kugga.module.infra.enums.codegen.CodegenColumnHtmlTypeEnum;
import com.hisun.kugga.module.infra.enums.codegen.CodegenColumnListConditionEnum;
import com.hisun.kugga.module.infra.enums.codegen.CodegenTemplateTypeEnum;
import org.springframework.stereotype.Component;

import java.util.*;

import static cn.hutool.core.text.CharSequenceUtil.*;

/**
 * 代码生成器的 Builder，负责：
 * 1. 将数据库的表 {@link TableInfo} 定义，构建成 {@link CodegenTableDO}
 * 2. 将数据库的列 {@link TableField} 构定义，建成 {@link CodegenColumnDO}
 */
@Component
public class CodegenBuilder {

    /**
     * 字段名与 {@link CodegenColumnListConditionEnum} 的默认映射
     * 注意，字段的匹配以后缀的方式
     */
    private static final Map<String, CodegenColumnListConditionEnum> columnListOperationConditionMappings =
            MapUtil.<String, CodegenColumnListConditionEnum>builder()
                    .put("name", CodegenColumnListConditionEnum.LIKE)
                    .put("time", CodegenColumnListConditionEnum.BETWEEN)
                    .put("date", CodegenColumnListConditionEnum.BETWEEN)
                    .build();

    /**
     * 字段名与 {@link CodegenColumnHtmlTypeEnum} 的默认映射
     * 注意，字段的匹配以后缀的方式
     */
    private static final Map<String, CodegenColumnHtmlTypeEnum> columnHtmlTypeMappings =
            MapUtil.<String, CodegenColumnHtmlTypeEnum>builder()
                    .put("status", CodegenColumnHtmlTypeEnum.RADIO)
                    .put("sex", CodegenColumnHtmlTypeEnum.RADIO)
                    .put("type", CodegenColumnHtmlTypeEnum.SELECT)
                    .put("image", CodegenColumnHtmlTypeEnum.UPLOAD_IMAGE)
                    .put("file", CodegenColumnHtmlTypeEnum.UPLOAD_FILE)
                    .put("amount", CodegenColumnHtmlTypeEnum.EDITOR)
                    .put("description", CodegenColumnHtmlTypeEnum.EDITOR)
                    .put("demo", CodegenColumnHtmlTypeEnum.EDITOR)
                    .put("time", CodegenColumnHtmlTypeEnum.DATETIME)
                    .put("date", CodegenColumnHtmlTypeEnum.DATETIME)
                    .build();

    /**
     * 多租户编号的字段名
     */
    public static final String TENANT_ID_FIELD = "tenantId";
    /**
     * {@link com.hisun.kugga.framework.mybatis.core.dataobject.BaseDO} 的字段
     */
    public static final Set<String> BASE_DO_FIELDS = new HashSet<>();
    /**
     * 新增操作，不需要传递的字段
     */
    private static final Set<String> CREATE_OPERATION_EXCLUDE_COLUMN = Sets.newHashSet("id");
    /**
     * 修改操作，不需要传递的字段
     */
    private static final Set<String> UPDATE_OPERATION_EXCLUDE_COLUMN = Sets.newHashSet();
    /**
     * 列表操作的条件，不需要传递的字段
     */
    private static final Set<String> LIST_OPERATION_EXCLUDE_COLUMN = Sets.newHashSet("id");
    /**
     * 列表操作的结果，不需要返回的字段
     */
    private static final Set<String> LIST_OPERATION_RESULT_EXCLUDE_COLUMN = Sets.newHashSet();

    static {
        Arrays.stream(ReflectUtil.getFields(BaseDO.class)).forEach(field -> BASE_DO_FIELDS.add(field.getName()));
        BASE_DO_FIELDS.add(TENANT_ID_FIELD);
        // 处理 OPERATION 相关的字段
        CREATE_OPERATION_EXCLUDE_COLUMN.addAll(BASE_DO_FIELDS);
        UPDATE_OPERATION_EXCLUDE_COLUMN.addAll(BASE_DO_FIELDS);
        LIST_OPERATION_EXCLUDE_COLUMN.addAll(BASE_DO_FIELDS);
        LIST_OPERATION_EXCLUDE_COLUMN.remove("createTime"); // 创建时间，还是可能需要传递的
        LIST_OPERATION_RESULT_EXCLUDE_COLUMN.addAll(BASE_DO_FIELDS);
        LIST_OPERATION_RESULT_EXCLUDE_COLUMN.remove("createTime"); // 创建时间，还是需要返回的
    }

    public CodegenTableDO buildTable(TableInfo tableInfo) {
        CodegenTableDO table = CodegenConvert.INSTANCE.convert(tableInfo);
        initTableDefault(table);
        return table;
    }

    /**
     * 初始化 Table 表的默认字段
     *
     * @param table 表定义
     */
    private void initTableDefault(CodegenTableDO table) {
        // 以 system_dept 举例子。moduleName 为 system、businessName 为 dept、className 为 Dept
        // 如果希望以 System 前缀，则可以手动在【代码生成 - 修改生成配置 - 基本信息】，将实体类名称改为 SystemDept 即可
        String tableName = table.getTableName().toLowerCase();
        // 第一步，_ 前缀的前面，作为 module 名字；第二步，moduleName 必须小写；
        table.setModuleName(subBefore(tableName, '_', false).toLowerCase());
        // 第一步，第一个 _ 前缀的后面，作为 module 名字; 第二步，可能存在多个 _ 的情况，转换成驼峰; 第三步，businessName 必须小写；
        table.setBusinessName(toCamelCase(subAfter(tableName, '_', false)).toLowerCase());
        // 驼峰 + 首字母大写；第一步，第一个 _ 前缀的后面，作为 class 名字；第二步，驼峰命名
        table.setClassName(upperFirst(toCamelCase(subAfter(tableName, '_', false))));
        // 去除结尾的表，作为类描述
        table.setClassComment(StrUtil.removeSuffixIgnoreCase(table.getTableComment(), "表"));
        table.setTemplateType(CodegenTemplateTypeEnum.CRUD.getType());
    }

    public List<CodegenColumnDO> buildColumns(Long tableId, List<TableField> tableFields) {
        List<CodegenColumnDO> columns = CodegenConvert.INSTANCE.convertList(tableFields);
        int index = 1;
        for (CodegenColumnDO column : columns) {
            column.setTableId(tableId);
            column.setOrdinalPosition(index++);
            // 初始化 Column 列的默认字段
            processColumnOperation(column); // 处理 CRUD 相关的字段的默认值
            processColumnUI(column); // 处理 UI 相关的字段的默认值
        }
        return columns;
    }

    private void processColumnOperation(CodegenColumnDO column) {
        // 处理 createOperation 字段
        column.setCreateOperation(!CREATE_OPERATION_EXCLUDE_COLUMN.contains(column.getJavaField())
                && !column.getPrimaryKey()); // 对于主键，创建时无需传递
        // 处理 updateOperation 字段
        column.setUpdateOperation(!UPDATE_OPERATION_EXCLUDE_COLUMN.contains(column.getJavaField())
                || column.getPrimaryKey()); // 对于主键，更新时需要传递
        // 处理 listOperation 字段
        column.setListOperation(!LIST_OPERATION_EXCLUDE_COLUMN.contains(column.getJavaField())
                && !column.getPrimaryKey()); // 对于主键，列表过滤不需要传递
        // 处理 listOperationCondition 字段
        columnListOperationConditionMappings.entrySet().stream()
                .filter(entry -> StrUtil.endWithIgnoreCase(column.getJavaField(), entry.getKey()))
                .findFirst().ifPresent(entry -> column.setListOperationCondition(entry.getValue().getCondition()));
        if (column.getListOperationCondition() == null) {
            column.setListOperationCondition(CodegenColumnListConditionEnum.EQ.getCondition());
        }
        // 处理 listOperationResult 字段
        column.setListOperationResult(!LIST_OPERATION_RESULT_EXCLUDE_COLUMN.contains(column.getJavaField()));
    }

    private void processColumnUI(CodegenColumnDO column) {
        // 基于后缀进行匹配
        columnHtmlTypeMappings.entrySet().stream()
                .filter(entry -> StrUtil.endWithIgnoreCase(column.getJavaField(), entry.getKey()))
                .findFirst().ifPresent(entry -> column.setHtmlType(entry.getValue().getType()));
        // 如果是 Boolean 类型时，设置为 radio 类型.
        // 其它类型，因为字段名可以相对保障，所以不进行处理。例如说 date 对应 datetime 类型.
        if (Boolean.class.getSimpleName().equals(column.getJavaType())) {
            column.setHtmlType(CodegenColumnHtmlTypeEnum.RADIO.getType());
        }
        // 兜底，设置默认为 input 类型
        if (column.getHtmlType() == null) {
            column.setHtmlType(CodegenColumnHtmlTypeEnum.INPUT.getType());
        }
    }

}
