package cn.zs.dao;
import cn.zs.model.Answer;

import java.io.*;


public class TxtDao {
    public  String read(String filePath){
        String data = null ;
        try {
            String encoding="GBK";
            File file=new File(filePath);
            System.out.println("文件地址：" + file.getAbsolutePath());
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                //只读第一行
                if ((data = bufferedReader.readLine()) != null){
                   // System.out.println("读取的数据是："+data);
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return  data;
    }
    public void write(String path, Answer answer){
        File file = null;
        FileWriter fw = null;
        file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            System.out.println(file.getAbsolutePath());
            fw = new FileWriter(file);
            fw.write(answer.toString()+"\r\n"); //加上换行
            fw.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(fw != null){
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
