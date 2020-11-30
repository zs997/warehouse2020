/**
 * projectName: warehouse2020
 * fileName: Main.java
 * packageName: cn.zs
 * date: 2020-11-27 11:26
 * copyright(c) 2019-2021 hust
 */
package cn.zs;

import cn.zs.algorithm.SolutionL;
import cn.zs.algorithm.SolutionM;
import cn.zs.algorithm.SolutionR;
import cn.zs.algorithm.SolutionS;
import cn.zs.dao.TxtDao;
import cn.zs.model.Answer;
import cn.zs.model.Params;

/**
 * @version: V1.0
 * @author: zs
 * @className: Main
 * @packageName: cn.zs
 * @data: 2020-11-27 11:26
 **/
import static cn.zs.model.Params.*;
public class Main {
    public static void main(String[] args) {
        TxtDao txtDao = new TxtDao();
        String rootPath = System.getProperty("user.dir");

        for(int caseNo = 1; caseNo <= 12; caseNo++){
            String read = txtDao.read(rootPath + "\\data\\input\\input-case"+ caseNo + ".txt");
            Params.processingData(read);

//            Answer answerR = new Answer();
//            long startTime = System.currentTimeMillis();
//            SolutionR.doDP(answerR);
//            long endTime = System.currentTimeMillis();
//            SolutionR.check(answerR);
//            answerR.setRemark("return型拣货");
//            answerR.setCaseNo(String.valueOf(caseNo));
//            answerR.setOverallA(overallA);
//            answerR.setOverallB(overallB);
//            answerR.setOverallC(overallC);
//            answerR.setTimeCost(String.valueOf((endTime-startTime)/1000.0)+ "s");
//            System.out.println(answerR);
//            txtDao.write(rootPath + "\\data\\output\\output-r"+ caseNo + ".txt",answerR);
//
//            Answer answerS = new Answer();
//            startTime = System.currentTimeMillis();
//            SolutionS.doDP(answerS);
//            endTime = System.currentTimeMillis();
//            SolutionS.check(answerS);
//            answerS.setRemark("s型拣货");
//            answerS.setCaseNo(String.valueOf(caseNo));
//            answerS.setOverallA(overallA);
//            answerS.setOverallB(overallB);
//            answerS.setOverallC(overallC);
//            answerS.setTimeCost(String.valueOf((endTime-startTime)/1000.0)+ "s");
//            System.out.println(answerS);
//            txtDao.write(rootPath + "\\data\\output\\output-s"+ caseNo + ".txt",answerS);
//
            Answer answerL = new Answer();
           long startTime = System.currentTimeMillis();
            SolutionL.doDP(answerL);
           long  endTime = System.currentTimeMillis();
        //   SolutionS.check(answerL);
            answerL.setRemark("l型拣货");
            answerL.setCaseNo(String.valueOf(caseNo));
            answerL.setOverallA(overallA);
            answerL.setOverallB(overallB);
            answerL.setOverallC(overallC);
            answerL.setTimeCost(String.valueOf((endTime-startTime)/1000.0)+ "s");
            System.out.println(answerL);
            txtDao.write(rootPath + "\\data\\output\\output-l"+ caseNo + ".txt",answerL);


//            Answer answerM = new Answer();
//           long startTime = System.currentTimeMillis();
//            SolutionM.doDP(answerM);
//           long  endTime = System.currentTimeMillis();
//           //SolutionS.check(answerM);
//            answerM.setRemark("M型拣货");
//            answerM.setCaseNo(String.valueOf(caseNo));
//            answerM.setOverallA(overallA);
//            answerM.setOverallB(overallB);
//            answerM.setOverallC(overallC);
//            answerM.setTimeCost(String.valueOf((endTime-startTime)/1000.0)+ "s");
//            System.out.println(answerM);
//            txtDao.write(rootPath + "\\data\\output\\output-m"+ caseNo + ".txt",answerM);
        }
    }
}