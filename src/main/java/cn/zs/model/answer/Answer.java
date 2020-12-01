/**
 * projectName: warehouse2020
 * fileName: ReturnRes.java
 * packageName: cn.zs.model
 * date: 2020-11-27 18:46
 * copyright(c) 2019-2021 hust
 */
package cn.zs.model.answer;

import java.util.List;

/**
 * @version: V1.0
 * @author: zs
 * @className: ReturnRes
 * @packageName: cn.zs.model
 * @data: 2020-11-27 18:46
 **/
public class Answer {
    List<ColumnAnswer> columnAnswers;
    double targetValue;
    double sumCost;
    double checkCost;
    int overallA;
    int overallB;
    int overallC;
    //记录  时间 案例号 路径策略等
    String timeCost;
    String caseNo;
    String remark;

    public int getOverallA() {
        return overallA;
    }

    public void setOverallA(int overallA) {
        this.overallA = overallA;
    }

    public int getOverallB() {
        return overallB;
    }

    public void setOverallB(int overallB) {
        this.overallB = overallB;
    }

    public int getOverallC() {
        return overallC;
    }

    public void setOverallC(int overallC) {
        this.overallC = overallC;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "columnAnswers=" + columnAnswers +
                ", targetValue=" + targetValue +
                ", sumCost=" + sumCost +
                ", checkCost=" + checkCost +
                ", timeCost='" + timeCost + '\'' +
                ", caseNo='" + caseNo + '\'' +
                ", remark='" + remark + '\'' +
                ", overallA=" + overallA +
                ", overallB=" + overallB +
                ", overallC=" + overallC +
                '}';
    }

    public String getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(String timeCost) {
        this.timeCost = timeCost;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ColumnAnswer> getColumnAnswers() {
        return columnAnswers;
    }
    public void setColumnAnswers(List<ColumnAnswer> columnAnswers) {
        this.columnAnswers = columnAnswers;
    }
    public double getTargetValue() {
        return targetValue;
    }
    public void setTargetValue(double targetValue) {
        this.targetValue = targetValue;
    }
    public double getSumCost() {
        return sumCost;
    }
    public void setSumCost(double sumCost) {
        this.sumCost = sumCost;
    }
    public double getCheckCost() {
        return checkCost;
    }
    public void setCheckCost(double checkCost) {
        this.checkCost = checkCost;
    }
}