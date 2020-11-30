package cn.zs.model;

/**
 * @version: V1.0
 * @author: zs
 * @className: ColumnS
 * @packageName: cn.zs.model
 * @data: 2020-11-28 17:04
 * @description: S型拣货策略
 **/
import static cn.zs.model.Params.*;
public class ColumnS extends ColumnR{
    double evenProb;
    double els;

    void calculEvenProb(double lastEvenProb,double lastEnterProb){
        evenProb = lastEvenProb * (1 - lastEnterProb) + (1 - lastEvenProb) * lastEnterProb;
    }
    /**
     * 要先知道 三个概率 和 elr
     * */
    void calculEls(){
        els = (1 - evenProb * lastProb) * enterProb * (2 * wc  + N * f) + evenProb * lastProb * elr ;
    }
    /**
     * 从库位分配开始计算 第一个通道中 evenProb = 1
     * @param : lastEvenProb 某一状态下 该通道的某一分配 会有上一个状态 上一状态的最优分配已经计算了，就是上一状态的最优化情况下 上一个通道的概率
     * */
    public void calculCost(int no,int usedA,int usedB,int usedC,int numA,int numB,int numC,double lastEvenProb,double lastEnterProb){
        //前三组 只需要该巷道内的库位分配情况
        assignABC(numA,numB,numC);
        calculElr();
        calculEnterProb();

        //需要知道其他参数
        calculLastProb(usedA,usedB,usedC);
        calculElc(no);

        //需要其他参数
        calculEvenProb(lastEvenProb,lastEnterProb);
        calculEls();
        cost = (els + elc)/nonEmptyProb;
    }
    /**
     * @description 计算第一通道 简化版本
     */
    public void calculCost(int numA, int numB,int numC){
        calculCost(1,numA,numB,numC,numA,numB,numC,1,0);
    }
    /**
     * 分配好库位 用于二次计算
     * */
    public void calculCost(int no,int usedA,int usedB,int usedC,double lastEvenProb,double lastEnterProb){
        //需要知道其他参数 Elr只与单通道内的库位分配有关
        calculLastProb(usedA,usedB,usedC);
        calculElc(no);

        calculEvenProb(lastEvenProb,lastEnterProb);
        calculEls();
        cost = (els + elc)/nonEmptyProb;
    }

    public double getEvenProb() {
        return evenProb;
    }
}