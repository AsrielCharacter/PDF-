import context.WordTableContext;
import entity.impl.BaiCiZhanWordTable;
import factory.impl.BaiCiZhanWordTableFactory;

import java.io.File;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        System.out.println("欢迎使用百词斩PDF转单词工具！");
//        System.out.println(Class.forName("Client"));
        while (true) {
            System.out.println("输入模式：1,百词斩单词模式");
            Scanner modeScanner = new Scanner(System.in);
            if (modeScanner.nextInt() == 1) {
                BCZ();
            }

            continue;
        }

//        System.out.println(scanner.next());
//        System.out.println("输入PDF单词文件路径(xxxx.pdf)");
//        Scanner scanner2 = new Scanner(System.in);
//        System.out.println(scanner2.next());
//        System.out.println("默认输出文件路径：本路径下");
    }

    //百词斩
    public static void BCZ() throws Exception {
        System.out.println("输入PDF单词文件路径(xxxx.pdf)");
        Scanner fileScanner = new Scanner(System.in);
        String fileName = fileScanner.nextLine();
        System.out.println("默认输出文件路径：本路径下");
        if (!ifFileLife(fileName)){
            System.out.println("文件不存在或路径错误，请重新输入！");
            return;
        }
        System.out.println("开始转换...");

        WordTableContext context = new WordTableContext();
        context.registerFactory(new BaiCiZhanWordTableFactory());
        BaiCiZhanWordTable table = (BaiCiZhanWordTable) context.getFactory();
        System.out.println(fileName+"转换中...");
        //提供PDF文件路径
        table.PDFReader(new File(fileName));
        //保存原始读取文件
        table.saveToFile(new File("wordTable.txt"));
        //格式转换
        String string = table.formatConversion(new File("wordTable.txt"));
        System.out.println(string);
        System.out.println(table.getWordCount(string));
        table.saveToFileByWordCount500(new File("wordTable.txt"));
        //保存格式转换后的文件
        table.saveToFile(new File("allWordTable.txt"), string);
        System.out.println("转换完成！");

    }

    public static boolean ifFileLife(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            return true;
        } else {
            return false;
        }
    }
}
