package com.hxb.structure.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.hxb.structure.model.DemoExcelModel;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-09 15:27:54
 */
@Slf4j
public class ExcelUtils {

    public static void main(String[] args) throws Exception{
        List<DemoExcelModel> read = read("D:/easyexcelTemplate.xlsx", DemoExcelModel.class);
        write(read,DemoExcelModel.class,ExcelTypeEnum.XLSX,true,new FileOutputStream("D://111111111111111111111111111.xlsx"));
    }

    @SuppressWarnings(value = "unchecked")
    public static <T extends BaseRowModel> List<T> read(String path, Class<T> model) {
        try (InputStream inputStream = new FileInputStream(path);
             BufferedInputStream bis = new BufferedInputStream(inputStream)) {
            // 解析每行结果在listener中处理
            AnalysisEventListener listener = new DefaultExcelListener<T>();
            EasyExcelFactory.readBySax(bis, new Sheet(1, 1, model), listener);
            return ((DefaultExcelListener) listener).getDataList();
        } catch (Exception e) {
            log.error("excel {} parse error", path, e);
            return Collections.emptyList();
        }
    }

    public static <T extends BaseRowModel> void write(List<T> dataList, Class<T> model, ExcelTypeEnum typeEnum, boolean needHead, OutputStream out) {
        ExcelWriter writer = EasyExcelFactory.getWriter(out, typeEnum, needHead);
        writer.write(dataList, new Sheet(1, 0, model));
        writer.finish();
    }

    private static class DefaultExcelListener<T> extends AnalysisEventListener {
        private List<T> dataList = new ArrayList<>();

        @Override
        public void invoke(Object o, AnalysisContext analysisContext) {
            dataList.add((T) o);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {

        }

        public List<T> getDataList() {
            return dataList;
        }
    }


    private ExcelUtils() {
    }
}
