/**
 * projectName: warehouse2020
 * fileName: ReturnColumnRes.java
 * packageName: cn.zs.model
 * date: 2020-11-27 18:45
 * copyright(c) 2019-2021 hust
 */
package cn.zs.model.answer;

/**
 * @version: V1.0
 * @author: zs
 * @className:
 * @packageName: cn.zs.model
 * @data: 2020-11-27 18:45
 **/
public class ColumnAnswer {
    public int rowNO,numA,numB,numC;
    //结果 一个通道的成本
    public double costi;
    //分配模式 ABC顺序分配为0 对称分配为1（默认0）
    public int assignmentMode;

    public ColumnAnswer(int rowNO, int numA, int numB, int numC, double costi,int assignmentMode){
        this.rowNO=rowNO;
        this.numA=numA;
        this.numB=numB;
        this.numC=numC;
        this.costi=costi;
        this.assignmentMode = assignmentMode;
    }
    private ColumnAnswer(){ }

    public void setAnswer(int rowNO,int numA,int numB,int numC,double costi,int assignmentMode){
        this.rowNO=rowNO;
        this.numA=numA;
        this.numB=numB;
        this.numC=numC;
        this.costi=costi;
        this.assignmentMode = assignmentMode;
    }

    @Override
    public String toString() {
        return "ColumnAnswer{" +
                "rowNO=" + rowNO +
                ", numA=" + numA +
                ", numB=" + numB +
                ", numC=" + numC +
                ", costi=" + costi +
                ", assignmentMode=" + assignmentMode +
                '}';
    }
}