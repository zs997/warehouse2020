/**
 * projectName: warehouse2020
 * fileName: Main.java
 * packageName: cn.zs
 * date: 2020-11-27 11:26
 * copyright(c) 2019-2021 hust
 */
package cn.zs;

import cn.zs.algorithm.*;
import cn.zs.dao.TxtDao;
import cn.zs.model.answer.Answer;
import cn.zs.model.Params;

/**
 * @version: V1.0
 * @author: zs
 * @className: Main
 * @packageName: cn.zs
 * @data: 2020-11-27 11:26
 **/
import java.util.ArrayList;

import static cn.zs.model.Params.*;
public class Main {
    public static void main(String[] args) {
        checkData();
    }
    public static void checkData(){
        String rootPath = System.getProperty("user.dir");
        TxtDao txtDao = new TxtDao();
        String read = txtDao.read(rootPath + "\\data\\input\\input-case"+ 12 + ".txt");
        Params.processingData(read);
        String params = Params.getParams();
        System.out.println(params);
    }
    public static void run(){
        String [] shape = {"L","M","R","S"};

        TxtDao txtDao = new TxtDao();
        String rootPath = System.getProperty("user.dir");

        ArrayList<Solution> solutions = new ArrayList<>();
        solutions.add(new SolutionL());
        solutions.add(new SolutionM());
        solutions.add(new SolutionR());
        solutions.add(new SolutionS());
        ArrayList<Answer> answers = new ArrayList<>();
        for (int i = 0; i < shape.length; i++) {
            Answer ans = new Answer();
            ans.setRemark(shape[i] + "型拣货");
            answers.add(ans);
        }
        for(int caseNo = 1; caseNo <= 12; caseNo++){
            String read = txtDao.read(rootPath + "\\data\\input\\input-case"+ caseNo + ".txt");
            Params.processingData(read);
            for (int i = 0; i < shape.length; i++) {
                Solution s = solutions.get(i);
                Answer ans = answers.get(i);
                ans.setCaseNo(String.valueOf(caseNo));
                ans.setOverallA(overallA);
                ans.setOverallB(overallB);
                ans.setOverallC(overallC);
                long startTime = System.currentTimeMillis();
                s.doDP(ans);
                long endTime = System.currentTimeMillis();
                s.check(ans);
                ans.setTimeCost(String.valueOf((endTime-startTime)/1000.0)+ "s");
                System.out.println(ans);
                txtDao.write(rootPath + "\\data\\output\\output-"+ shape[i] +  caseNo + ".txt",ans);
            }
        }
    }
}