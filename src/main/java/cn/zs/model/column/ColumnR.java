package cn.zs.model.column;
/**
 * @descrip: Return拣货策略
 * */
import static cn.zs.model.Params.*;
public class ColumnR implements Column{
    //分配到该巷道的 ABC 个数
     int numA;
     int numB;
     int numC;
    //货架的库位 存放货物编号 每一种存放 表示一种库位分配
    // 0 1 2 分别表示 A B C
     Integer locations[] = new Integer[N] ;
    //进入巷道的概率
    double enterProb;
    //该通道是最后要访问的概率
    double lastProb;
    //通道内行走长度期望
    double elr;
    //横通道的长度期望
    double elc;
    //总成本
    double cost;

    public ColumnR(){
    }
    /**
     * @description:给定该通道内各种货物的数量，分配到库位 第 1 步
     * */
     void assignABC(int numA,int numB,int numC){
        this.numA=numA;
        this.numB=numB;
        this.numC=numC;
        int i =0;
        for(;i < numA ;i++){
            locations[i] = 0;
        }
        for(i = numA;i <(numA + numB);i++){
            locations[i] = 1;
        }
        for(i = numA + numB;i < locations.length;i++){
            locations[i] = 2;
        }
    }
     void assignABC(int numA,int numB){
        assignABC(numA,numB,N-numA-numB);
    }

    /**
     * @description：计算通道内的距离 第 2 步
     * */
     void calculElr(){
        double sum = 0;
        double multi;
        for(int j = 1;j <= N;j++)
        {
            double base = (wc + j*f - 0.5*f) * (itemPickFreq[locations[j-1]]);
            multi = 1;
            for(int h = j + 1;h <= N;h++)
            {
                multi = multi * (1-itemPickFreq[locations[h-1]]);
            }
            sum += 2*multi*base;
        }
        elr = sum;
    }

    /**
     * @description：计算进入该巷道的概率 第 3 步
     * */
     void calculEnterProb(){
        double multi = 1;
        for(int i = 0;i < N; i++){
            multi = multi * (1-itemPickFreq[locations[i]]);
        }
        enterProb = 1 - multi;
    }
    /**
     * @description：计算该通道是最后一个要访问的概率，与分配状态有关 第 4 步
     * 其实就是知道分配了该通道之后，还剩啥货物，计算都不拣选的概率
     * @param：分到通道1到该通道（包括）一共用的ABC个数。
     * @param:usedA 表示用多少A
     * */
     void calculLastProb(int usedA,int usedB,int usedC){
        lastProb = Math.pow((1-itemPickFreq[0]),overallA - usedA)*Math.pow((1-itemPickFreq[1]),overallB - usedB)
                * Math.pow((1-itemPickFreq[2]),overallC - usedC);
    }
    /**
     * @param:no 是第几排货架 1~ m
     * @description：计算横通道距离 第 5 步
     * */
     void calculElc(int no){
        elc=2 * wa * (no-1) * enterProb * lastProb;
    }

    /**
     * @description：第1巷道初始库位分配时，可以将所有单个通道内的库位分配情况穷举
     * 所以单个通道内路径长度只需要计算一次。
     * */
    public void calculCost(int no,int usedA,int usedB,int usedC,int numA,int numB,int numC){
       //前三组 只需要该巷道内的库位分配情况
        assignABC(numA,numB,numC);
        calculElr();
        calculEnterProb();

        //需要知道其他参数
        calculLastProb(usedA,usedB,usedC);
        calculElc(no);
        cost=(elr+elc)/nonEmptyProb;
    }
    /**
     * @description:简化版 初始化第一个通道各种分配方案 计算成本
     * */
    public void calculCost(int numA,int numB,int numC){
        calculCost(1,numA,numB,numC,numA,numB,numC);
    }
    /**
     *  @description:分配好库位，计算完enterprob,Elr,再进行调用
     *  随着所在列和在此之前的库位分配情况不同，横通道的距离期望不同
     *  Elr可以重复使用，Elc要重新计算
     * */
    public void calculCost(int no,int usedA,int usedB,int usedC){
        calculLastProb(usedA,usedB,usedC);
        calculElc(no);
        cost=(elr+elc)/nonEmptyProb;
    }

    @Override
    public void calculCost(int no, int usedA, int usedB, int usedC, int numA, int numB, int numC, int assignmentMode, double lastFirstProb, double lastEnterProb) {

    }

    @Override
    public void calculCost(int no, int usedA, int usedB, int usedC, double lastEvenProb, double lastEnterProb) {

    }

    @Override
    public void calculCost(int no, int usedA, int usedB, int usedC, int numA, int numB, int numC, double lastEvenProb, double lastEnterProb) {

    }

    public double getCost() {
        return cost;
    }

    public double getEnterProb() {
        return enterProb;
    }

    @Override
    public double getEvenProb() {
        return 0;
    }

    @Override
    public double getFirstProb() {
        return 0;
    }
}
