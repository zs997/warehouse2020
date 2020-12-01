package cn.zs.model.column;

/**
 * @version: V1.0
 * @author: zs
 * @className: ColumnL
 * @packageName: cn.zs.model
 * @data: 2020-11-29 14:52
 **/
import java.util.ArrayList;
import java.util.HashMap;

import static cn.zs.model.Params.*;
public class ColumnL extends ColumnM{

    double g;
    double elg;
    /**
     * 计算单个通道内 最大间隔期望
     * */

    void calculG(){
        HashMap<String,Double> gMap = new HashMap<>();
        ArrayList<ArrayList<Double>> state = new ArrayList<>();
        ArrayList<Double> state1 = new ArrayList<>();
        ArrayList<Double> state2 = new ArrayList<>();
        state.add(state1);
        state.add(state2);

        //初始化所有状态空间
        state1.add(0.5 * f + wc);
        state2.add((double)f);
       for (int j = 1; j <= N - 2;j++) {
           state1.add(state1.get(j-1) + f);
           state2.add(state2.get(j - 1) + f);
        }
        state1.add(state1.get(N - 2) + f);
        state1.add(N * f + 2 * wc);

        //step1 Gn+1(gc,gl) = gl 初始化 计算n+1
        for (int i = 0; i < state.size(); i++) {
            for (int j = 0; j < state.get(i).size(); j++) {
                double gc = state.get(i).get(j);
                for (int m = 0; m < state.size(); m++) {
                    for (int n = 0; n < state.get(m).size(); n++) {
                        double gl = state.get(m).get(n);
                        if (gc > gl){
                            continue;
                        }
                        String key = (N + 1) + "," + gc + "," + gl;
                        gMap.put(key,gl);
                    }
                }
            }
        }
        state1.remove(state1.size()-1);
        //step2 计算 n   Gn(gc,gl) = (1-pn) * max{gl,gc+0.5f+wc} + pn * max{gl,0.5f+wc}
        for (int i = 0; i < state.size(); i++) {
            for (int j = 0; j < state.get(i).size(); j++) {
                double gc = state.get(i).get(j);
                for (int m = 0; m < state.size(); m++) {
                    for (int n = 0; n < state.get(m).size(); n++) {
                        double gl = state.get(m).get(n);
                        if (gc > gl){
                            continue;
                        }
                        double pin = itemPickFreq[locations[N-1]];
                        String key1 = (N + 1) + "," + (gc + 0.5*f + wc) + "," + (Math.max(gl,gc + 0.5*f + wc)) ;
                        String key2 = (N + 1) + "," + (0.5*f + wc) + "," + (Math.max(gl,0.5*f + wc)) ;
                        String key = N + "," + gc + "," + gl;
                        double v = (1 - pin)*  gMap.get(key1) + pin * gMap.get(key2);
                        gMap.put(key,v);
                    }
                }
            }
        }
        state1.remove(state1.size()-1);
        state2.remove(state2.size()-1);
        //step3
        //第一层 遍历库位
        for(int index = N - 1;index >= 1;index--) {

            for (int i = 0; i < state.size(); i++) {
                for (int j = 0; j < state.get(i).size(); j++) {
                    double gc = state.get(i).get(j);
                    for (int m = 0; m < state.size(); m++) {
                        for (int n = 0; n < state.get(m).size(); n++) {
                            double gl = state.get(m).get(n);
                            if (gc > gl) {
                                continue;
                            }
                            double pij = itemPickFreq[locations[index - 1]];
                            String key1 = (index + 1) + "," + (gc + f) + "," + (Math.max(gl, gc + f));
                            String key2 = (index + 1) + "," + ((double)f) + "," + (Math.max(gl, f));
                            String key = (index) + "," + (gc) + "," + gl;
                            double v = (1 - pij) * gMap.get(key1) + pij * gMap.get(key2);
                            gMap.put(key, v);
                        }
                    }
                }
            }
            if (state1.size() > 0){
               state1.remove(state1.size()-1);
            }
            if (state2.size() > 0){
               state2.remove(state2.size()-1);
            }
        }
        g = gMap.get(1 + "," + (0.5 * f + wc) + "," + (0.5 * f + wc));
       // System.out.println(g);
    }

   /*
   //老办法 只能计算特殊值
    void calculG(){
        int gl,gc;
        double G [][][] = new double[24+2][24+2][24+2];
        for(gl=1;gl<=N+1;gl++)
            for(gc=1;gc<=gl;gc++)
                G[N+1][gc][gl]=gl;

        for(gl=1;gl<=N;gl++)
            for(gc=1;gc<=gl;gc++)
                G[N][gc][gl]=(1-itemPickFreq[locations[N-1]]) * G[N+1][(int)(gc+0.5*f+wc)][(int)(Math.max(gl,gc+0.5*f+wc))]
        +itemPickFreq[locations[N-1]] * G[N+1][(int)(0.5*f+wc)][(int)(Math.max(gl,0.5*f+wc))];

        for(int j=N-1;j>=1;j--)
        {
            for(gl=1;gl<=j;gl++)
                for(gc=1;gc<=gl;gc++)
                    G[j][gc][gl]=(1-itemPickFreq[locations[j-1]]) *  G[j+1][(int)(gc+f)][(int)(Math.max(gl,gc+f))]
            + itemPickFreq[locations[j-1]] * G[j+1][f][(int)(Math.max(gl,f))];
        }
        g = G[1][1][1];

    }
    */
    /**
     * 计算Elg
     * */
    void calculElg(){
        elg = lastProb * firstProb * elr
                + (lastProb + firstProb - 2 * lastProb * firstProb) * enterProb * (2 * wc + N * f)
                + (1 - lastProb)*(1 - firstProb) * 2 * (2 * wc + N * f - g);
    }

   /**
    * 从库位分配开始计算成本
    * */
    public void calculCost(int no,int usedA,int usedB,int usedC,int numA,int numB
            ,int numC,int assignmentMode,double lastFirstProb,double lastEnterProb){
        //前三组 只需要该巷道内的库位分配情况
        assignABC(numA,numB,numC,assignmentMode);
        calculElr();
        calculEnterProb();

        calculG();

        //需要知道其他参数
        calculLastProb(usedA,usedB,usedC);
        calculElc(no);

        //计算该通道之前没有通道访问
        calculFirstProb(lastFirstProb,lastEnterProb);
        calculElg();
        cost=(elg + elc)/nonEmptyProb;
    }
    /**
     * 简化版 从库位分配开始计算成本
     * 把ColumnR的方法覆盖了
     * */
    public void calculCost(int numA,int numB, int numC,int assignmentMode){
        calculCost(1,numA,numB,numC,numA,numB,numC,assignmentMode,1,0);
    }
    /**
     * 简化版 重写父类 防止出错 默认分配方式为0 不对称分配
     * */
    public void calculCost(int numA,int numB,int numC){
        calculCost(numA, numB, numC,0);
    }
    /**
     * 已经库位分配完毕 随其他参数变化的成本
     * */
    public void calculCost(int no,int usedA,int usedB,int usedC
            ,double lastFirstProb,double lastEnterProb){
        //需要知道其他参数
        calculLastProb(usedA,usedB,usedC);
        calculElc(no);

        //计算该通道之前没有通道访问
        calculFirstProb(lastFirstProb,lastEnterProb);
        calculElg();
        cost=(elg + elc)/nonEmptyProb;
    }

}