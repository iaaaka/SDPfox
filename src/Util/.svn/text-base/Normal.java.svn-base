package Util;
//import ig.msk.util.*;
/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      IntegratedGenomics Moscow
 * @author A.Mironov
 * @version 1.0
 */

public class Normal {
  static double pisq=Math.sqrt(2*Math.PI);
//  public Normal() {}
  public static double density(double e, double sigma, double x)
    {return density(sigma,x-e);}
  public static double density(double sigma, double x)
    {return density(x/sigma)/sigma;}
  public static double density(double x)
    {return Math.exp(-x*x/2)/pisq;}

  public static double probGrater(double e, double sigma, double x)
    {return probGrater(sigma, x-e);}
  public static double probLess  (double e, double sigma, double x)
    {return probLess(sigma, x-e);}

  public static double probGrater(double sigma, double x)
    {return probGrater(x/sigma);}
  public static double probLess  (double sigma, double x)
    {return probLess(x/sigma);}

/*
*/
   static double std[]={
5.000000E-01,4.601721E-01,4.207403E-01,3.820886E-01,3.445783E-01, //0.0-0.4
3.085375E-01,2.742531E-01,2.419636E-01,2.118553E-01,1.840601E-01, //0.4-0.9
1.586553E-01,1.356661E-01,1.150697E-01,9.680055E-02,8.075671E-02, //1.0-1.4
6.680723E-02,5.479929E-02,4.456543E-02,3.593027E-02,2.871649E-02, //1.4-1.9
2.275006E-02,1.786436E-02,1.390340E-02,1.072408E-02,8.197529E-03, //2.0-1.4
6.209680E-03,4.661222E-03,3.467023E-03,2.555191E-03,1.865880E-03, //2.4-1.9
1.349967E-03,9.676712E-04,6.872021E-04,4.834825E-04,3.369808E-04, //3.0-1.4
2.326734E-04,1.591457E-04,1.078301E-04,7.237243E-05,4.811552E-05, //3.4-1.9
3.168603E-05,2.066872E-05,1.335410E-05,8.546021E-06,5.416953E-06, //4.0-1.4
3.400803E-06,2.114643E-06,1.302316E-06,7.943527E-07,4.798695E-07, //4.4-1.9
2.871050E-07,1.701223E-07,9.983440E-08,5.802207E-08,3.339612E-08, //5.0-1.4
1.903640E-08,1.074622E-08,6.007653E-09,3.326052E-09,1.823579E-09, //5.4-1.9
9.901219E-10,5.323753E-10,2.834714E-10,1.494721E-10,7.804901E-11, //6.0-1.4
4.035794E-11,2.066525E-11,1.047862E-11,5.261569E-12,2.616130E-12, //6.4-1.9
1.288081E-12,                                                     //7.0
};

  public static double probLess  (double x)
    {return probGrater(-x);}

   public static double probGrater(double x)
    {
    if(x==0) return 0.5;
    if(x<0) return 1-probLess(x);
    int idx=(int)((x+0.00001)/0.1);
    if(idx<0)  return 0.5;
    double w=0;
    if(idx>=std.length-1)
      {
      double z=(1-1/(x*x));
      w=density(x)/x*z;
//if(w<=0) w=0;
//System.out.println("return norm(exp)" + w);
      }
    else
      {
      double k=(x-idx*0.1)/0.1;
      w=std[idx]*(1-k)+std[idx+1]*k;
      }
    if(w<=0) w=0;


    return w;
    }
//===================================


  public static double probGraterApprox(double x)
  {
    if(x==0) return Math.log(0.5);

    int idx=(int)((x+0.00001)/0.1);
    if(idx<0)  return Math.log(0.5);
    double w=0, logW = 1;
    if(idx>=std.length-1)
      {
      double z=(1-1/(x*x));
      w=density(x)/x*z;
      logW = -x*x/2 - Math.log(pisq) - Math.log(x) - Math.log(z);
//if(w<=0) w=0;
//System.out.println("return norm(exp)" + w);
      }
    else
      {
      double k=(x-idx*0.1)/0.1;
      w=std[idx]*(1-k)+std[idx+1]*k;
      logW = Math.log(w);
      }
    if(w<0)
    {
       w=0;
       logW = 1;
    }


    return logW;
  }
}
