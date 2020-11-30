package cn.zs.model;
public class Params {
    //拣货概率 计算所得数据 用map存比较好 以后可改进
    //作用是map 存ABC的拣货概率
    public  static double itemPickFreq[] = new double[3];
   // public  static HashMap<String,Double> itemPickFreq = new HashMap<>();

    //原始数据
    // M排货架 每排N个库位
    public   static int M,N;
    // a b c 三种货物各占总库存百分比
    public  static  double aOfLocation,bOfLocation,cOfLocation;
    //订单长度
    public  static  int orderLength;
    // A B C 三种货物占订单的比例
    public  static  double aOfOrder,bOfOrder,cOfOrder;
    //仓库尺寸
    public   static int f,wa;
    public   static double wc;

    //其他参数 计算所得
    //ABC各自总数
    public  static int overallA,overallB,overallC;
    //订单不为空的概率 和数据案例有关
    public static double nonEmptyProb;

    //不实例化对象
    private Params(){}

    /**
     * @description:初始化原始数据 第 1 步
     * */
    private static void initData(String data){
        data =data.trim();
        String[] datas = data.split("\\s+");
        //不必加异常处理 异常直接退出
        M = Integer.valueOf(datas[0]);
        orderLength = Integer.valueOf(datas[1]);
        aOfOrder = Double.valueOf(datas[2]);
        bOfOrder = Double.valueOf(datas[3]);
        cOfOrder = Double.valueOf(datas[4]);
        wc = Double.valueOf(datas[5]);
        f = Integer.valueOf(datas[6]);
        wa = Integer.valueOf(datas[7]);
        aOfLocation = Double.valueOf(datas[8]);
        bOfLocation = Double.valueOf(datas[9]);
        cOfLocation = Double.valueOf(datas[10]);
        N = Integer.valueOf(datas[11]);
    }

    /**
     * @description:计算ABC各自有多少个 第 2 步
     */
    private  static void calculOverallABC(){
        int all = N * M;
        overallC =(int)Math.round(all* cOfLocation);		//c是整数 先不舍弃c
        overallA =(int)Math.round(all* aOfLocation);
        overallB = all- overallA- overallC;
    }

    /**
   * @description：计算拣货概率 第 3 步
   */
    private static void calculItemPickFreq(){
        itemPickFreq[0]= orderLength * aOfOrder/overallA;
        itemPickFreq[1]=orderLength*bOfOrder/overallB;
        itemPickFreq[2]=orderLength*cOfOrder/overallC;
    }
    /**
     * @description:计算订单非空的概率 第 4 步
     * */
    private static void calculNonEmptyProb(){
        nonEmptyProb=1-Math.pow((1- itemPickFreq[0]), overallA)
                *Math.pow((1- itemPickFreq[1]), overallB)*Math.pow((1- itemPickFreq[2]), overallC);
    }

    public  static  void processingData(String data){
        initData(data);
        calculOverallABC();
        calculItemPickFreq();
        calculNonEmptyProb();
    }

    public  static  String getParams(){
        return
                "M=" + M +
                " N=" + N +
                " aOfLocation=" + aOfLocation +
                " bOfLocation=" + bOfLocation +
                " cOfLocation=" + cOfLocation +
                " orderlength=" + orderLength +
                " aOfOrder=" + aOfOrder +
                " bOfOrder=" + bOfOrder +
                " cOfOrder=" + cOfOrder +
                " f=" + f +
                " wa=" + wa +
                " wc=" + wc +
                " overallA=" + overallA +
                " overallB=" + overallB +
                " overallC=" + overallC +
                " nonEmptyProb=" + nonEmptyProb +
                " itemPickFreq[A]=" + itemPickFreq[0]+
                " itemPickFreq[B]=" + itemPickFreq[1]+
                " itemPickFreq[C]=" + itemPickFreq[2];
    }
}
