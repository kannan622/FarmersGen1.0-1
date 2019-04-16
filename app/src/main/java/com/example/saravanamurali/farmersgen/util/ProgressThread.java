package com.example.saravanamurali.farmersgen.util;

public class  ProgressThread extends Thread {

   public void run(){

       for(int count=0;count<=2;count++){

           try {
               Thread.sleep(800);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }

    }

}
