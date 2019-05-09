package com.hxb.structure.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.util.Date;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-09 15:53:47
 */
@Data
public class DemoExcelModel extends BaseRowModel {
    @ExcelProperty(value = "编号")
    private String number;
    @ExcelProperty(value = "名称")
    private String name;
    @ExcelProperty(value = "状态")
    private Integer status;
    @ExcelProperty(value = "时间",format = "yyyy/MM/dd")
    private Date time;
}
