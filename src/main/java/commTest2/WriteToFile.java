package commTest2;

import java.io.*;

/**
 * 23:25:18.398 [pool-1-thread-1] INFO  commTest.AskInfoHandler - 准考证号码: 16682512697
 * 23:25:20.890 [pool-1-thread-1] INFO  commTest.AskInfoHandler - 学号: 197864210009
 * 23:25:20.945 [pool-1-thread-1] INFO  commTest.AskInfoHandler - 姓名: 潘宏培
 * 23:25:20.989 [pool-1-thread-1] INFO  commTest.AskInfoHandler - 报名批次: 2020年9月统考
 * 23:25:21.048 [pool-1-thread-1] INFO  commTest.AskInfoHandler - 考试科目: 大学英语B
 * 23:25:21.114 [pool-1-thread-1] INFO  commTest.AskInfoHandler - 通过情况: 合格
 * 23:25:21.166 [pool-1-thread-1] INFO  commTest.AskInfoHandler - 得分: 91
 */
public class WriteToFile {

    public static void main(String[] args) throws Exception {

        StringBuilder sb = new StringBuilder();
        sb.append("16682512697      ").append("       197864210009").append("     潘宏培").append("      2020年9月统考")
                .append("  大学英语B2").append("     合格").append("     91").append("\n");

//        writeTxtFile(sb.toString(), new File("D:\\file\\8998.txt"));
        readTxtFile(sb.toString(), new File("src\\main\\files\\examResult.txt"));
    }


    public static void writeTxtFile(String content, File fileName)
            throws Exception {

        FileWriter fwriter = null;
        fwriter = new FileWriter(fileName);

        fwriter.write(content); //这里要放入string类型

        fwriter.flush();

        fwriter.close();
    }



    public static void readTxtFile(String content, File fileName)
            throws Exception {

        try {


            if(fileName.isFile() && fileName.exists()) {

                InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName), "utf-8");

                BufferedReader br = new BufferedReader(isr);

                String lineTxt = null;
                String lastLine = null;
                while(true){
                    lastLine = lineTxt;
                    if((lineTxt = br.readLine()) == null){
                        FileWriter fwriter = new FileWriter(fileName,true);
                        fwriter.write(content);
                        fwriter.flush();
                        fwriter.close();
                        break;
                    }
                }


                br.close();

            } else {

                System.out.println("文件不存在!");

            }

        } catch (Exception e) {

            System.out.println("文件读取错误!");

        }
    }




}
