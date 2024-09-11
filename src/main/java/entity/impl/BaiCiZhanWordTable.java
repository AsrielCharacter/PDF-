package entity.impl;

import entity.WordTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

@AllArgsConstructor
public class BaiCiZhanWordTable implements WordTable {
    private static final String WORD_TABLE_NAME = "百词斩单词表";
    private static final Log log = LogFactory.getLog(BaiCiZhanWordTable.class);
    private PDDocument document;
    private PDFTextStripper stripper;
    // 单词表内容
    public String content = null;

    public BaiCiZhanWordTable() {
    }


    //pdf文件路径
    public void PDFReader(@NonNull File file) throws Exception {
        document = PDDocument.load(file);
        stripper = new PDFTextStripper();
        content = stripper.getText(document);
    }

    //获取单词表内容
    public String PDFWriter() throws IOException {
        if(document == null){
            return null;
        }
        stripper = new PDFTextStripper();
        content = stripper.getText(document);
        return stripper.getText(document);
    }
    //保存单词表内容到文件
    public void saveToFile(@NonNull File file) throws IOException {
        if (content == null) {
            log.info("单词表内容为空，无法保存");
            return;
        }
        if (!file.exists()) {
            log.info("文件不存在，创建文件");
            file.createNewFile();
        }else{
            log.info("文件已存在，删除重新创建");
            file.delete();
            file.createNewFile();
        }
        OutputStreamWriter fos = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
        fos.write(content);
        fos.close();
    }

    public void saveToFile(@NonNull File file,String content) throws IOException {
        if (content == null) {
            log.info("内容为空，无法保存");
            return;
        }
        if (!file.exists()) {
            log.info("文件不存在，创建文件");
            file.createNewFile();
        }else{
            log.info("文件已存在，删除重新创建");
            file.delete();
            file.createNewFile();
        }
        OutputStreamWriter fos = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
        fos.write(content);
        fos.close();
    }

    //原始读取存储内容
    public String getFileOriginalContent(@NonNull File file) throws IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    //文件格式转换
    public String formatConversion(@NonNull File file) throws IOException {
        //格式转换
        if(document == null){
            log.info("文件为空，无法格式转换");
            return "";
        }
        //读取文字信息
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        StringBuilder sb = new StringBuilder();


        while ((line = br.readLine()) != null) {
            //查询是否有空格,排除标题 .contains(" ")也可以
            Boolean findSpace =line.matches(".*\\s.*");
            if(findSpace){
                //只获取单词
                String firstWord =line.split(" ")[0];
                //排除换行中文
                if(firstWord.matches("[a-zA-Z0-9]+")){
                    sb.append(firstWord+",");
                }
            }
        }
        br.close();
        return sb.toString();
    }

    //读取单词数量
    public int getWordCount(String words) {
        return words.split(",").length;
    }

    //默认按单词数量分500个单词一文件保存单词表
    public void saveToFileByWordCount500(@NonNull File file) throws IOException {
        //判断内容是否为空
        if(content == null) {
            log.info("单词表内容为空，无法保存");
            return;
        }
        ArrayList<String[]> list = new ArrayList<>();
        String[] strings=formatConversion(file).split(",");
        int count= (int) Math.ceil((double) strings.length/500);
        for(int i=0;i<count;i++){
            //判断是否超出数组长度
            if (i*500+500>=strings.length){
                list.add(Arrays.copyOfRange(strings,i*500,strings.length));
            }else {
                list.add(Arrays.copyOfRange(strings,i*500,i*500+500));
            }
        }
        //无分割符的数据
        list.forEach(item-> {
            for (int i = 0; i < item.length; i++) {
                System.out.println(item[i]);
            }
        });
        //分割符的数据
        for (int i = 0; i < list.size(); i++) {
            try {
                saveToFile(new File(WORD_TABLE_NAME + i + ".txt"), String.join(",", list.get(i)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void saveToFileByWordCount(@NonNull File file,String words) throws IOException {

    }
}
