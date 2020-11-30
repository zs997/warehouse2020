package cn.zs.model;

public interface Column {
     void calculCost(int no,int usedA,int usedB,int usedC,int numA,int numB,int numC);
     void calculCost(int numA,int numB,int numC);
     void calculCost(int no,int usedA,int usedB,int usedC);
     double getCost();
     double getEnterProb();
}
