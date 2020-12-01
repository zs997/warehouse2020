package cn.zs.algorithm;

import cn.zs.model.*;
import cn.zs.model.answer.Answer;
import cn.zs.model.answer.ColumnAnswer;
import cn.zs.model.column.Column;
import cn.zs.model.column.ColumnL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static cn.zs.model.Params.*;

/**
 * @version: V1.0
 * @author: zs
 * @className: Solution
 * @packageName: cn.zs.algorithm
 * @data: 2020-11-27 16:49
 **/
public class SolutionL implements   Solution {
    public   void doDP(Answer answer) {
        //用map表示状态 m,i,j 为键 前i通道 A分配i个 B分配j个
        HashMap<String, State> stateMap = new HashMap<>();
        //用map保存可以重复使用的分配方案 1,i,j 为键 A分配i个 B分配j个
        HashMap<String, ColumnL> columnMap = new HashMap<>();
        //初始化第一通道 穷举 ABC分配到单个通道个数
        for (int i = 0; i <= N; i++) {
            if (i > overallA) {
                break;
            }
            for (int j = 0; j <= N - i; j++) {
                if (j > overallB) {
                    break;
                }
                int k = N - i - j;
                if (k > overallC) {
                    continue;
                }

                ColumnL col = new ColumnL();
                col.calculCost(i, j, k);
                //第一通道 A用i个 B用j个 最优分配方式为0
                String key0 = 1 + "," + i + "," + j +"," + 0 ;
                //为了节约计算量
                columnMap.put(key0, col);

                ColumnL colSymmetry = new ColumnL();
                colSymmetry.calculCost(i,j,k,1);
                //第一通道 A用i个 B用j个 最优分配方式为1
                String key1 = 1 + "," + i + "," + j + "," + 1 ;
                //为了节约计算量
                columnMap.put(key1, colSymmetry);

                String key = 1 + "," + i + "," + j;
                //状态值 第一通道 肯定不是对称分布
                State state = new State();
                state.setCosti(col.getCost());
                state.setTargetValue(col.getCost());
                state.setNo(1);
                state.setNumA(i);
                state.setNumB(j);
                state.setNumC(k);
                state.setLastA(0);
                state.setLastB(0);
                state.setLastC(0);
                state.setAssignmentMode(0);
                state.setEnterProb(col.getEnterProb());
                state.setFirstProb(col.getFirstProb());
                stateMap.put(key, state);
            }
        }
        //dp状态递推 m从第 2 通道 到 第 M-1
        for (int m = 2; m < M; m++) {
            //前 m 通道 库位总数
            int all = N * m;
            // i j k 分别是 ABC 各自货物数目
            for (int i = 0; i <= all; i++) {
                if (i > overallA) {
                    break;
                }
                for (int j = 0; j <= all - i; j++) {
                    if (j > overallB) {
                        break;
                    }
                    int k = all - i - j;
                    if (k > overallC) {
                        continue;
                    }
                    //在此 出现了新状态 m,i,j  计算此状态的最优解
                    String newKey = m + "," + i + "," + j;
                    int lastAll = N * (m - 1);
                    //newState保留 m,i,j 子问题的最优解
                    State newState = new State();
                    newState.setTargetValue(Double.MAX_VALUE);
                    newState.setNo(m);
                    //穷举前一个通道所有状态解，并且和本阶段的状态结合，取最优
                    for (int o = 0; o <= lastAll; o++)//穷举上一个通道解的状态
                    {
                        if (o > overallA) {
                            break;
                        }
                        for (int p = 0; p <= lastAll - o; p++) {
                            if (p > overallB) {
                                break;
                            }
                            int q = lastAll - o - p;
                            if (q > overallC) {
                                continue;
                            }
                            String lastKey = (m - 1) + "," + o + "," + p;
                            State lastState = stateMap.get(lastKey);
                            if (lastState == null) {
                                continue;
                            }
                            int ma = i - o;    // (i-o) m通道a个数
                            int mb = j - p;        //(j-p)  m通道b个数
                            int mc = k - q;    //(all-i-j)-(all_last-o-p)  m通道c个数
                            if ((ma < 0) || (mb < 0) || (mc < 0)){
                                //m通道不可行  但是没有用最优化条件
                                continue;
                            }
                            if ((ma + mb + mc) != N){
                                //这一项是必须的 保证ma(或者mb,mc)不超过N
                                continue;  //只有加和为N才能继续运行 否则跳转
                            }

//                            int lastNumA = lastState.getNumA();
//                            int lastNumB = lastState.getNumB();
//                            if(ma > lastNumA || (ma + mb)>( lastNumA + lastNumB)){
//                                //return 路径的最优化条件
//                                continue;
//                            }

                            //按顺序分配
                            String single0 = 1 + "," + ma + "," + mb + "," + 0;
                            ColumnL col0 = columnMap.get(single0);//单个通道的库位分配:库位分配,ELr,enterprob经过了计算
                            col0.calculCost(m, i, j, k,lastState.getFirstProb(),lastState.getEnterProb()); //只需要再计算lastprob,Elc,以及cost
                            if (lastState.getTargetValue() + col0.getCost() < newState.getTargetValue()) {
                                //除了Sofar_length和 lastABC都已经更新完毕
                                newState.setTargetValue(lastState.getTargetValue() + col0.getCost());
                                newState.setNumA(ma);
                                newState.setNumB(mb);
                                newState.setNumC(mc);
                                newState.setLastA(o);//此状态 m,i,j 下共用了ABC ijk个
                                newState.setLastB(p);
                                newState.setLastC(q);
                                newState.setAssignmentMode(0);
                                newState.setCosti(col0.getCost());
                                newState.setEnterProb(col0.getEnterProb());
                                newState.setFirstProb(col0.getFirstProb());
                            }
                            String single1 = 1 + "," + ma + "," + mb + "," + 1;
                            ColumnL col1 = columnMap.get(single1);//单个通道的库位分配:库位分配,ELr,enterprob经过了计算
                            col1.calculCost(m, i, j, k,lastState.getFirstProb(),lastState.getEnterProb()); //只需要再计算lastprob,Elc,以及cost
                            if (lastState.getTargetValue() + col1.getCost() < newState.getTargetValue()) {
                                //除了Sofar_length和 lastABC都已经更新完毕
                                newState.setTargetValue(lastState.getTargetValue() + col1.getCost());
                                newState.setNumA(ma);
                                newState.setNumB(mb);
                                newState.setNumC(mc);
                                newState.setLastA(o);//此状态 m,i,j 下共用了ABC ijk个
                                newState.setLastB(p);
                                newState.setLastC(q);
                                newState.setAssignmentMode(1);
                                newState.setCosti(col1.getCost());
                                newState.setEnterProb(col1.getEnterProb());
                                newState.setFirstProb(col1.getFirstProb());
                            }
                        }
                    }
                    stateMap.put(newKey, newState);
                }
            }
        }
        //最后一个通道情况,终点边界 m=M
        int i = overallA;
        int j = overallB;
        int k = overallC;
        int lastAll = N * (M - 1);
        //在此 出现了新状态 m,i,j  计算此状态的最优解
        String newKey = M + "," + i + "," + j ;
        //存 M,i,j 子问题的最优解 最后状态
        State newState = new State();
        newState.setTargetValue(Double.MAX_VALUE);
        newState.setNo(M);
        //穷举前一个通道所有状态解，并且和本阶段的状态结合，取最优
        for (int o = 0; o <= lastAll; o++){ //穷举上一个通道解的状态
            if (o > overallA) {
                break;
            }
            for (int p = 0; p <= lastAll - o; p++) {
                if (p > overallB) {
                    break;
                }
                int q = lastAll - o - p;
                if (q > overallC) {
                    continue;
                }
                String lastKey = (M - 1) + "," + o + "," + p;
                State lastState = stateMap.get(lastKey);
                if (lastState == null) {
                    continue;
                }
                int ma = i - o;    // (i-o) m通道a个数
                int mb = j - p;        //(j-p)  m通道b个数
                int mc = k - q;    //(all-i-j)-(all_last-o-p)  m通道c个数
                if ((ma < 0) || (mb < 0) || (mc < 0)){
                    //m通道不可行  但是没有用最优化条件
                    continue;
                }
                if ((ma + mb + mc) != N){
                    //这一项是必须的 保证ma(或者mb,mc)不超过N
                    continue;  //只有加和为N才能继续运行 否则跳转
                }
//                int lastNumA = lastState.getNumA();
//                int lastNumB = lastState.getNumB();
//                if(ma > lastNumA || (ma + mb)>( lastNumA + lastNumB)){
//                    //return 路径的最优化条件
//                    continue;
//                }
                //按顺序分配
                String single0 = 1 + "," + ma + "," + mb + "," + 0;
                ColumnL col0 = columnMap.get(single0);//单个通道的库位分配:库位分配,ELr,enterprob经过了计算
                col0.calculCost(M, i, j, k,lastState.getFirstProb(),lastState.getEnterProb()); //只需要再计算lastprob,Elc,以及cost
                if (lastState.getTargetValue() + col0.getCost() < newState.getTargetValue()) {
                    //除了Sofar_length和 lastABC都已经更新完毕
                    newState.setTargetValue(lastState.getTargetValue() + col0.getCost());
                    newState.setNumA(ma);
                    newState.setNumB(mb);
                    newState.setNumC(mc);
                    newState.setLastA(o);//此状态 m,i,j 下共用了ABC ijk个
                    newState.setLastB(p);
                    newState.setLastC(q);
                    newState.setAssignmentMode(0);
                    newState.setCosti(col0.getCost());
                    newState.setEnterProb(col0.getEnterProb());
                    newState.setFirstProb(col0.getFirstProb());
                }
                //对称分配
                String single1 = 1 + "," + ma + "," + mb + "," + 1;
                ColumnL col1 = columnMap.get(single1);//单个通道的库位分配:库位分配,ELr,enterprob经过了计算
                col1.calculCost(M, i, j, k,lastState.getFirstProb(),lastState.getEnterProb()); //只需要再计算lastprob,Elc,以及cost
                if (lastState.getTargetValue() + col1.getCost() < newState.getTargetValue()) {
                    //除了Sofar_length和 lastABC都已经更新完毕
                    newState.setTargetValue(lastState.getTargetValue() + col1.getCost());
                    newState.setNumA(ma);
                    newState.setNumB(mb);
                    newState.setNumC(mc);
                    newState.setLastA(o);//此状态 m,i,j 下共用了ABC ijk个
                    newState.setLastB(p);
                    newState.setLastC(q);
                    newState.setAssignmentMode(1);
                    newState.setCosti(col1.getCost());
                    newState.setEnterProb(col1.getEnterProb());
                    newState.setFirstProb(col1.getFirstProb());
                }
            }
        }
        stateMap.put(newKey, newState);
        //从后向前 获取最优解结构
        answer.setTargetValue(newState.getTargetValue());
        List<ColumnAnswer> list = new ArrayList<>();
        State state = newState;
        double sumCost = 0;
        //到第一通道状态时，结束
        while (state != null && state.getLastA() != 0){
            int no = state.getNo();
            int numA = state.getNumA();
            int numB = state.getNumB();
            int numC = state.getNumC();
            double costi = state.getCosti();
            sumCost += costi;
            int assignment_mode = state.getAssignmentMode();
            int lastA = state.getLastA();
            int lastB = state.getLastB();
            ColumnAnswer columnAnswer = new ColumnAnswer(no, numA, numB, numC, costi, assignment_mode);
            list.add(0,columnAnswer);
            String lastKey = (no - 1) + ","+ lastA + "," + lastB;
            state = stateMap.get(lastKey);
        }
        if (state != null){
            int no = state.getNo();
            int numA = state.getNumA();
            int numB = state.getNumB();
            int numC = state.getNumC();
            double costi = state.getCosti();
            sumCost += costi;
            int assignment_mode = state.getAssignmentMode();
            ColumnAnswer columnAnswer = new ColumnAnswer(no, numA, numB, numC, costi, assignment_mode);
            list.add(0,columnAnswer);
        }
        answer.setColumnAnswers(list);
        answer.setSumCost(sumCost);
    }

    public  void check(Answer answer){
        List<ColumnAnswer> columnAnswers = answer.getColumnAnswers();
        double checkCost = 0;
        int usedA = 0;
        int usedB = 0;
        int usedC = 0;
        double lastFirstProb = 1;
        double lastEnterProb = 0;
        for (int i = 0; i < columnAnswers.size(); i++) {
            ColumnAnswer columnAnswer = columnAnswers.get(i);
            int numA = columnAnswer.numA;
            int numB = columnAnswer.numB;
            int numC = columnAnswer.numC;
            int rowNO = columnAnswer.rowNO;
            int assignmentMode = columnAnswer.assignmentMode;

            Column col = new ColumnL();
            usedA += numA;
            usedB += numB;
            usedC += numC;
            col.calculCost(rowNO,usedA,usedB,usedC,numA,numB,numC,assignmentMode,lastFirstProb,lastEnterProb);
            checkCost += col.getCost();
            lastFirstProb =  lastFirstProb * (1 - lastEnterProb);
            lastEnterProb = col.getEnterProb();

        }
        answer.setCheckCost(checkCost);
    }
}

