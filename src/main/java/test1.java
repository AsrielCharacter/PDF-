import context.WordTableContext;

import entity.impl.BaiCiZhanWordTable;

import factory.impl.BaiCiZhanWordTableFactory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;

/**
 *         WordTableContext context = new WordTableContext();
 *         context.registerFactory(new BaiCiZhanWordTableFactory());
 *         BaiCiZhanWordTable table = (BaiCiZhanWordTable) context.getFactory();
 *         //提供PDF文件路径
 *         table.PDFReader(new File("F:\\zsb.pdf"));
 *         //保存原始读取文件
 *         table.saveToFile(new File("F:\\zsb.txt"));
 *         //格式转换
 *         String string = table.formatConversion(new File("F:\\zsb.txt"));
 *         System.out.println(string);
 *         //保存格式转换后的文件
 *         table.saveToFile(new File("F:\\zsb1.txt"), string);
 */
public class test1 {
    private static final Log log = LogFactory.getLog(test1.class);

    public static void main(String[] args) throws Exception {
        WordTableContext context = new WordTableContext();
        context.registerFactory(new BaiCiZhanWordTableFactory());
        BaiCiZhanWordTable table = (BaiCiZhanWordTable) context.getFactory();
        //提供PDF文件路径
        table.PDFReader(new File("F:\\zsb.pdf"));
        //保存原始读取文件
        table.saveToFile(new File("F:\\zsb.txt"));
        //格式转换
        String string = table.formatConversion(new File("F:\\zsb.txt"));
        System.out.println(string);
        System.out.println(table.getWordCount(string));
        table.saveToFileByWordCount500(new File("F:\\zsb.txt"));
        //保存格式转换后的文件
        table.saveToFile(new File("F:\\zsb1.txt"), string);


//        extracted();
    }

    private static void extracted() throws IOException {
        PDDocument doc = PDDocument.load(new File("F:\\zsb.pdf"));
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(doc);
        //保存
        File file = new File("F:\\zsb.txt");
        if (!file.exists()) {
            log.info("文件不存在，创建文件");
            file.createNewFile();
        }else{
            log.info("文件已存在，删除重新创建");
            file.delete();
            file.createNewFile();
        }
        OutputStreamWriter fos = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
        fos.write(text);
        //关闭流
        fos.close();
        log.info("文件保存成功,一共有"+text.length()+"个字符");
        //关闭文档
        doc.close();
        //读取文字信息
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line = null;
//        String firstWord =br.readLine();
//        System.out.println(firstWord.split(" ")[0]);
        while ((line = br.readLine()) != null) {
            //查询是否有空格,排除标题 .contains(" ")也可以
            Boolean findSpace =line.matches(".*\\s.*");
            if(findSpace){
                //只获取单词
                String firstWord =line.split(" ")[0];
                //排除换行中文
                if(firstWord.matches("[a-zA-Z0-9]+")){
                    System.out.println(firstWord);
                }
            }
        }
        //关闭流
        br.close();
        isr.close();
    }
}
