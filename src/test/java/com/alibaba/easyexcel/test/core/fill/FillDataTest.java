package com.alibaba.easyexcel.test.core.fill;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.alibaba.easyexcel.test.core.style.StyleData;
import com.alibaba.easyexcel.test.core.style.StyleDataListener;
import com.alibaba.easyexcel.test.util.TestFileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.LoopMergeStrategy;
import com.alibaba.excel.write.merge.OnceAbsoluteMergeStrategy;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.AbstractVerticalCellStyleStrategy;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;

/**
 *
 * @author Jiaju Zhuang
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FillDataTest {

    private static File file07;
    private static File file03;
    private static File simpleTemplate07;
    private static File simpleTemplate03;
    private static File fileComplex07;
    private static File complexFillTemplate07;
    private static File fileComplex03;
    private static File complexFillTemplate03;
    private static File fileHorizontal07;
    private static File horizontalFillTemplate07;
    private static File fileHorizontal03;
    private static File horizontalFillTemplate03;

    @BeforeClass
    public static void init() {
        file07 = TestFileUtil.createNewFile("fill07.xlsx");
        file03 = TestFileUtil.createNewFile("fill03.xls");
        simpleTemplate07 = TestFileUtil.readFile("fill" + File.separator + "simple.xlsx");
        simpleTemplate03 = TestFileUtil.readFile("fill" + File.separator + "simple.xls");
        fileComplex07 = TestFileUtil.createNewFile("fillComplex07.xlsx");
        complexFillTemplate07 = TestFileUtil.readFile("fill" + File.separator + "complex.xlsx");
        fileComplex03 = TestFileUtil.createNewFile("fillComplex03.xls");
        complexFillTemplate03 = TestFileUtil.readFile("fill" + File.separator + "complex.xls");
        fileHorizontal07 = TestFileUtil.createNewFile("fillHorizontal07.xlsx");
        horizontalFillTemplate07 = TestFileUtil.readFile("fill" + File.separator + "horizontal.xlsx");
        fileHorizontal03 = TestFileUtil.createNewFile("fillHorizontal03.xls");
        horizontalFillTemplate03 = TestFileUtil.readFile("fill" + File.separator + "horizontal.xls");
    }

    @Test
    public void t01Fill07() {
        fill(file07, simpleTemplate07);
    }

    @Test
    public void t02Fill03() {
        fill(file03, simpleTemplate03);
    }

    @Test
    public void t03ComplexFill07() {
        complexFill(fileComplex07, complexFillTemplate07);
    }

    @Test
    public void t04ComplexFill03() {
        complexFill(fileComplex03, complexFillTemplate03);
    }

    @Test
    public void t05HorizontalFill07() {
        horizontalFill(fileHorizontal07, horizontalFillTemplate07);
    }

    @Test
    public void t06HorizontalFill03() {
        horizontalFill(fileHorizontal03, horizontalFillTemplate03);
    }

    private void horizontalFill(File file, File template) {
        ExcelWriter excelWriter = EasyExcel.write(file).withTemplate(template).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();
        excelWriter.fill(data(), fillConfig, writeSheet);
        excelWriter.fill(data(), fillConfig, writeSheet);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", "2019年10月9日13:28:28");
        excelWriter.fill(map, writeSheet);
        excelWriter.finish();

        List<Object> list = EasyExcel.read(file).sheet().headRowNumber(0).doReadSync();
        Assert.assertEquals(list.size(), 5L);
        Map<String, String> map0 = (Map<String, String>)list.get(0);
        Assert.assertEquals("张三", map0.get(2));
    }

    private void complexFill(File file, File template) {
        ExcelWriter excelWriter = EasyExcel.write(file).withTemplate(template).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        excelWriter.fill(data(), fillConfig, writeSheet);
        excelWriter.fill(data(), fillConfig, writeSheet);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", "2019年10月9日13:28:28");
        map.put("total", 1000);
        excelWriter.fill(map, writeSheet);
        excelWriter.finish();
        List<Object> list = EasyExcel.read(file).sheet().headRowNumber(3).doReadSync();
        Assert.assertEquals(list.size(), 21L);
        Map<String, String> map19 = (Map<String, String>)list.get(19);
        Assert.assertEquals("张三", map19.get(0));
    }

    private void fill(File file, File template) {
        FillData fillData = new FillData();
        fillData.setName("张三");
        fillData.setNumber(5.2);
        EasyExcel.write(file, FillData.class).withTemplate(template).sheet().doFill(fillData);
    }

    private List<FillData> data() {
        List<FillData> list = new ArrayList<FillData>();
        for (int i = 0; i < 10; i++) {
            FillData fillData = new FillData();
            list.add(fillData);
            fillData.setName("张三");
            fillData.setNumber(5.2);
        }
        return list;
    }

}
