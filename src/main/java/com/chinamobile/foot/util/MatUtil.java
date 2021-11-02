package com.chinamobile.foot.util;

import com.jmatio.io.MatFileReader;
import com.jmatio.io.MatFileWriter;
import com.jmatio.types.MLArray;
import com.jmatio.types.MLDouble;
import com.jmatio.types.MLInt32;
import com.jmatio.types.MLInt64;

import java.io.IOException;
import java.util.ArrayList;

public class MatUtil {

    public static void readMatFile(String fileName) throws Exception {
        MatFileReader read = new MatFileReader(fileName);
        MLArray mlArray=read.getMLArray("doubleArray");//mat存储的就是img矩阵变量的内容
        MLDouble d=(MLDouble)mlArray;
        double[][] matrix=(d.getArray());//只有jmatio v0.2版本中才有d.getArray方法
        System.out.println(matrix);
    }

    public static void readMatFile1(String fileName) throws Exception {
        MatFileReader read = new MatFileReader(fileName);
        MLArray mlArray=read.getMLArray("longArray");//mat存储的就是img矩阵变量的内容
        MLInt64 d=(MLInt64)mlArray;
        long[][] matrix=(d.getArray());//只有jmatio v0.2版本中才有d.getArray方法
        int len = matrix.length > 40 ? 40 : matrix.length;
        for(int i=0; i<len; i++){
            for(int j=0; j<matrix[i].length; j++){
                System.out.print(matrix[i][j]+",");
            }
            System.out.println("");
        }
        //System.out.println(matrix);
    }

    public static void writeMatFile(String fileName, long[][] matData) throws Exception{
        MLInt64 intArray = new MLInt64("longArray", matData);
        ArrayList list=new ArrayList();//由于MatFileWriter()构造函数的参数为list类型，所以需要创建一个ArrayList
        list.add(intArray);
        new MatFileWriter(fileName,list);//将矩阵写入到.mat文件中，文件名为matTest.mat
        System.out.println("mat writer done!");
    }

    public static void writeMatFile() throws Exception{
        /*double[][] matTest=new double[][]{{1,2,3,4},{5,6,76,34}};//生成待存储的矩阵
        MLDouble mlDouble=new MLDouble("doubleArray",matTest);//doubleArray就是matlab中上述矩阵的标示符，load()之后，在matlab中使用doubleArray访问此矩阵
        ArrayList list=new ArrayList();//由于MatFileWriter()构造函数的参数为list类型，所以需要创建一个ArrayList
        list.add(mlDouble);
        new MatFileWriter("D:\\project\\文档\\移动\\matTest.mat",list);//将矩阵写入到.mat文件中，文件名为matTest.mat
        System.out.println("mat writer done!");*/

        long[][] matData=new long[][]{{1,2,3,4,5,6,7,8},{10,20,30,40,50,60,70,80},{100,200,300,400,500,600,700,800}};//生成待存储的矩阵
        MLInt64 intArray = new MLInt64("longArray", matData);
        ArrayList list1=new ArrayList();//由于MatFileWriter()构造函数的参数为list类型，所以需要创建一个ArrayList
        list1.add(intArray);
        new MatFileWriter("D:\\project\\文档\\移动\\matTest1.mat",list1);//将矩阵写入到.mat文件中，文件名为matTest.mat
        System.out.println("mat writer done!");

    }

    public static void main(String[] args) throws Exception{
        //readMatFile("D:\\project\\文档\\移动\\A03E.mat");
        readMatFile1("D:\\project\\文档\\移动\\mat\\1627626156.mat");
        //writeMatFile();
    }

}
