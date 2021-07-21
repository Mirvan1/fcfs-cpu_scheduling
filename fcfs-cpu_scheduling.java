/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp_20160807004;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author MirvanSADIGLI_20160807004
 */
//javac tp_20160807004.java
//java tp_20160807004 jobs.txt      or     java <package_name>.20160807004 jobs.txt
public class tp_20160807004 {

 public static void main(String[] args) throws FileNotFoundException {
      File file=new File(args[0]);
      String [] ab;
      String burst="";
      int pid=0;
      List<Integer>arr=new ArrayList<>();
      List<String> list=new ArrayList<>();
      Object[] objects=new Object[list.size()];
      Scanner sc=new Scanner(file);
      List<Integer>pidN=new ArrayList<>();// process id number
      while (sc.hasNextLine()) {            
             String data = sc.nextLine();
            ab=data.split(":");
            burst=ab[1];//cpu and io burst values
            burst=burst.replace("(","");    
            burst=burst.replace(")","");
            String[] tuple=burst.split(";");
            burst=burst.replace(";",",");// remove all mark excluding num
            for(int i=0;i<tuple.length;i++){
                tuple = burst.split("\\r?\\n");//lf and cr
                list.add(tuple[i]);  
            } 
            objects = list.toArray(); 
            pid++;    
            pidN.add(pid);//add pid value arraylist everytime
      }   
        String dict[] = Arrays.stream(objects)
                                    .toArray(String[]::new);
        sc.close(); 
        for(int i=0;i<dict.length;i++){
            String []strnum =new String[dict.length];
            strnum=dict[i].split(",");//use split to remove comma
            for(int j=0;j<strnum.length;j++){                 
                 arr.add(Integer.parseInt(strnum[j])); //tuples generated in one arraylist
                 
            } 
         } 
      int[] ret = new int[arr.size()];
      List <Integer> ioburst=new ArrayList(); 
      List <Integer> cpuburst=new ArrayList();
      for(int i=0; i < ret.length; i++)
      {
       ret[i] = arr.get(i).intValue();//tuple values filling in array
       if(i%2==0){
         cpuburst.add(ret[i]);//if index is even then the value is cpu value
       }
       else{
           ioburst.add(ret[i]);//if index is odd then the value is io value
       }
    }
     Integer[] ioarr = new Integer[ioburst.size()]; 
     ioarr = ioburst.toArray(ioarr); //io-b values filling in array
     Integer[] cpuarr = new Integer[cpuburst.size()]; 
     cpuarr = cpuburst.toArray(cpuarr); //cpu-b values filling in array

        int wtt[]=new int[cpuarr.length*2];//create an array WAITING time for ALL PROCCESS
        int tat[]=new int[cpuarr.length*2];//create an array TURN AROUND  time for ALL PROCCESS
        //int rand=(int)(Math.random()*3);
        wtt[0]=0;  //first waiting time must be  0
        Collections.sort(pidN, Collections.reverseOrder());
        int averageTurnAroundTime=0;
        int averageWaitingTime=0;
        System.out.println("WAITING TIME"+"\t"+"TURNAROUND TIME"+"\t"+" PROCESS IDs");
        for(int i=1;i<cpuarr.length;i++){               
           wtt[i]=cpuarr[i-1]+wtt[i-1]+ioarr[i];//formula of calculated waiting time
           tat[i]=wtt[i]+cpuarr[i];//formula calculated turn around time
             if (ioarr[i]>0){
                System.out.println(wtt[i]+"\t\t"+tat[i]+"\t\t"+" P"+pidN); //io burst value is greater than 0,means io-burst continue
                   }                
            else if(ioarr[i]==-1){
                   int terminatedValue=pidN.remove(pidN.size()-1);      //if io burst value is -1,means terminates            
                   try{
                      System.out.print("TERMINATED PROCESS ID:"+""+"P"+terminatedValue+"\n");//print terminated process id
                    }
                    catch(Exception e){
                        System.out.println("PR terminatdd"+e);
                    }
                 }
            else{
                System.out.println("All process terminated"+ioarr[i]);
            }
            int tat_length=tat[i]/tat.length;   //average turn around time
            int wait_length=wtt[i]/wtt.length;//average waiting time
            averageTurnAroundTime+=tat_length;
            averageWaitingTime+=wait_length;
           }
        System.out.println("averageTurnAroundTime:"+averageTurnAroundTime);
        System.out.println("averageWaitingTime:"+averageWaitingTime);
    }
}
