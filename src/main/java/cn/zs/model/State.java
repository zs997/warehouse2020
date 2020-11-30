/**
 * projectName: warehouse2020
 * fileName: State.java
 * packageName: cn.zs.model
 * date: 2020-11-27 19:19
 * copyright(c) 2019-2021 hust
 */
package cn.zs.model;

/**
 * @version: V1.0
 * @author: zs
 * @className: State
 * @packageName: cn.zs.model
 * @data: 2020-11-27 19:19
 * @description：动态规划算法中用于保存状态
 **/
public class State {
    int no;
    //记录某一状态的上一个最优状态 共计使用多少
    int lastA;
    int lastB;
    int lastC;
    //记录某一状态最优解
    double targetValue;
    //最优解下 该通道号 货物情况
    int numA;
    int numB;
    int numC;
    //最优解下 该通道的 成本
    double costi;
    //某一状态最优解下 该通道的概率值 对要求解的状态来说 是last
    double enterProb;
    double evenProb;
    double firstProb;
    int assignmentMode;

    public State(){}

    public double getEnterProb() {
        return enterProb;
    }

    public void setEnterProb(double enterProb) {
        this.enterProb = enterProb;
    }

    public double getEvenProb() {
        return evenProb;
    }

    public void setEvenProb(double evenProb) {
        this.evenProb = evenProb;
    }

    public double getTargetValue() {
        return targetValue;
    }
    public void setTargetValue(double targetValue) {
        this.targetValue = targetValue;
    }
    public int getNo() {
        return no;
    }
    public void setNo(int no) {
        this.no = no;
    }

    public int getLastA() {
        return lastA;
    }

    public void setLastA(int lastA) {
        this.lastA = lastA;
    }

    public int getLastB() {
        return lastB;
    }

    public void setLastB(int lastB) {
        this.lastB = lastB;
    }

    public int getLastC() {
        return lastC;
    }

    public void setLastC(int lastC) {
        this.lastC = lastC;
    }

    public int getNumA() {
        return numA;
    }
    public void setNumA(int numA) {
        this.numA = numA;
    }
    public int getNumB() {
        return numB;
    }
    public void setNumB(int numB) {
        this.numB = numB;
    }
    public int getNumC() {
        return numC;
    }
    public void setNumC(int numC) {
        this.numC = numC;
    }
    public double getCosti() {
        return costi;
    }
    public void setCosti(double costi) {
        this.costi = costi;
    }
    public int getAssignmentMode() {
        return assignmentMode;
    }
    public void setAssignmentMode(int assignmentMode) {
        this.assignmentMode = assignmentMode;
    }

    public double getFirstProb() {
        return firstProb;
    }

    public void setFirstProb(double firstProb) {
        this.firstProb = firstProb;
    }
}