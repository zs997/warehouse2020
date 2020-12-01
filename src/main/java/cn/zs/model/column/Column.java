package cn.zs.model.column;

public interface Column {
     void calculCost(int no,int usedA,int usedB,int usedC,int numA,int numB,int numC);
     void calculCost(int numA,int numB,int numC);
     void calculCost(int no,int usedA,int usedB,int usedC);
     void calculCost(int no,int usedA,int usedB,int usedC,int numA,int numB,int numC,int assignmentMode,double lastFirstProb,double lastEnterProb);
     void calculCost(int no,int usedA,int usedB,int usedC,double lastEvenProb,double lastEnterProb);
     void calculCost(int no,int usedA,int usedB,int usedC,int numA,int numB,int numC,double lastEvenProb,double lastEnterProb);
     double getCost();
     double getEnterProb();
     double getEvenProb();
     double getFirstProb();
}
