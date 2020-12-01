/**
 * projectName: warehouse2020
 * fileName: ColumnM.java
 * packageName: cn.zs.model
 * date: 2020-11-30 14:33
 * copyright(c) 2019-2021 hust
 */
package cn.zs.model.column;

/**
 * @version: V1.0
 * @author: zs
 * @className: ColumnM
 * @packageName: cn.zs.model
 * @data: 2020-11-30 14:33
 **/
import  static  cn.zs.model.Params.*;
public class ColumnM extends ColumnR {
    double firstProb;
    double elm1;
    double elm2;
    double elm;

    /* *
     * 要修改分配库位函数
     * */
    void assignABC(int numA,int numB,int numC,int assignmentMode){
        //分配方式为1 对称分配
        if (assignmentMode == 1){
            assignABCSymmetry(numA,numB,numC);
        }else {
            assignABC(numA,numB,numC);
        }
    }
    void assignABCSymmetry(int numA,int numB,int numC){
        this.numA = numA;
        this.numB = numB;
        this.numC = numC;
        int halfA = (int)Math.ceil(numA / 2.0);
        int halfB = (int )Math.ceil(numB/2.0); //numA/2 若是小数向上取整，整除开，不变化
        int i ;
        for(i = 0;i < halfA;i++)
        {
            locations[i] = 0;
        }
        for(;i<(halfA + halfB);i++)
        {
            locations[i] = 1;
        }
        for(;i<(halfA + halfB + numC);i++)
        {
            locations[i] = 2;
        }
        for(;i<(halfA + numB + numC);i++)
        {
            locations[i] = 1;
        }
        for(;i < N;i++)
        {
            locations[i] = 0;
        }
    }

    /*
    * 计算Eli1  只需要单个通道库位分配情况 不需要其他数据
    * */
    void calculElm1(){
        double sum = 0;
        int halfN = (int) Math.ceil( N/2.0);
        double multi;
        double base;
        for(int j = halfN + 1;j <= N;j++) //起点应为 halfN
        {
            base=(wc + (N-j) * f + 0.5*f)*(itemPickFreq[locations[j-1]]);
            multi=1;
            for(int h = halfN + 1;h < j;h++) //起点应为 halfN
            {
                multi = multi * (1 - itemPickFreq[locations[h - 1]]);
            }
            sum += 2*multi*base;
        }
        elm1 = sum;
    }
    /**
     * 计算Elm2 只需要单个通道库位分配情况 不需要其他数据
     * */
    void calculElm2(){
        double sum = 0;
        int halfN = (int)Math.ceil(N/2.0);
        double base;
        double multi;
        for(int j = 1;j <= halfN;j++) //起点为0
        {
            base = (wc + j*f - 0.5*f)*(itemPickFreq[locations[j - 1]]);
            multi=1;
            for(int h = j+1;h <= halfN;h++) //起点为j
            {
                multi = multi*(1-itemPickFreq[locations[h-1]]);
            }
            sum += 2*multi*base;
        }
        elm2 = sum;
    }

    /**
     * @description: 计算该通道之前没有进入通道的概率
     * 需要已知上一个通道的进入概率，及上一个通道之前没有进入的概率
     * */
    void calculFirstProb(double lastFirstProb,double lastEnterProb)
    {
        firstProb = lastFirstProb * (1 - lastEnterProb);
    }

    void calculElm(){
        elm = lastProb * firstProb * elr
                + (lastProb + firstProb - 2 * lastProb * firstProb) * enterProb * (2 * wc + N * f)
                + (1 - lastProb) * (1 - firstProb) * (elm1 + elm2);
    }

    /**
     * @description：第1巷道初始库位分配时，可以将所有单个通道内的库位分配情况穷举
     * 所以单个通道内路径长度只需要计算一次。
     * 计算成本 从库位分配开始计算
     * */
    public void calculCost(int no,int usedA,int usedB,int usedC,int numA,int numB,int numC,int assignmentMode,double lastFirstProb,double lastEnterProb){
        //前三组 只需要该巷道内的库位分配情况
        assignABC(numA,numB,numC,assignmentMode);
        calculElr();
        calculEnterProb();
        calculElm1();
        calculElm2();

        //需要知道其他参数
        calculLastProb(usedA,usedB,usedC);
        calculElc(no);
        calculFirstProb(lastFirstProb,lastEnterProb);

        calculElm();
        cost = (elm + elc)/nonEmptyProb;
    }
    /**
     * 简化版 从库位分配开始计算 只用于1号 通道
     * */
    public void calculCost(int numA,int numB,int numC){
        calculCost(1,numA,numB,numC,numA,numB,numC,0,1,0);
    }
    /**
     * 简化版 从库位分配开始计算 只用于1号 通道
     * */
    public void calculCost(int numA,int numB,int numC,int assignmentMode){
        calculCost(1,numA,numB,numC,numA,numB,numC,assignmentMode,1,0);
    }

    /**
     * 确定的库位分配  计算随变数据
     * */
    public void calculCost(int no,int usedA,int usedB,int usedC,double lastFirstProb,double lastEnterProb){
        //需要知道其他参数
        calculLastProb(usedA,usedB,usedC);
        calculElc(no);
        calculFirstProb(lastFirstProb,lastEnterProb);

        calculElm();
        cost = (elm + elc)/nonEmptyProb;
    }

    public double getFirstProb() {
        return firstProb;
    }
}