package com.chinamobile.foot.util;

import com.chinamobile.foot.edf.EDFException;
import com.chinamobile.foot.edf.EDFreader;
import com.chinamobile.foot.edf.EDFwriter;

import java.io.IOException;

public class EdfUtil {

    public static void readEdfFile(String fileName){
        int i, edfsignals, filetype;

        EDFreader hdl;

        try{
            hdl = new EDFreader(fileName);
        }catch(IOException e){
            System.out.printf("An error occured: ");
            System.out.printf(e.getMessage());
            return;
        }catch(EDFException e){
            System.out.printf("An error occured: ");
            System.out.printf(e.getMessage());
            return;
        }


        System.out.printf("Startdate: %02d-%02d-%02d\n", hdl.getStartDateDay(), hdl.getStartDateMonth(), hdl.getStartDateYear());
        System.out.printf("Starttime: %02d:%02d:%02d\n", hdl.getStartTimeHour(), hdl.getStartTimeMinute(), hdl.getStartTimeSecond());
        filetype = hdl.getFileType();
        if((filetype == EDFreader.EDFLIB_FILETYPE_EDF)  || (filetype == EDFreader.EDFLIB_FILETYPE_BDF)){
            System.out.printf("Patient: %s\n", hdl.getPatient());
            System.out.printf("Recording: %s\n", hdl.getRecording());
        } else {
            System.out.printf("Patient code: %s\n", hdl.getPatientCode());
            System.out.printf("Gender: %s\n", hdl.getPatientGender());
            System.out.printf("Birthdate: %s\n", hdl.getPatientBirthDate());
            System.out.printf("Patient name: %s\n", hdl.getPatientName());
            System.out.printf("Patient additional: %s\n", hdl.getPatientAdditional());
            System.out.printf("Admin. code: %s\n", hdl.getAdministrationCode());
            System.out.printf("Technician: %s\n", hdl.getTechnician());
            System.out.printf("Equipment: %s\n", hdl.getEquipment());
            System.out.printf("Recording additional: %s\n", hdl.getRecordingAdditional());
        }
        System.out.printf("Reserved: %s\n", hdl.getReserved());
        edfsignals = hdl.getNumSignals();
        System.out.printf("Number of signals: %d\n", hdl.getNumSignals());
        System.out.printf("Number of datarecords: %d\n", hdl.getNumDataRecords());
        System.out.printf("Datarecord duration: %f\n", (double)(hdl.getLongDataRecordDuration()) / 10000000.0);


        for(i=0; i<edfsignals; i++){
            try{
                System.out.printf("Signal: %s\n", hdl.getSignalLabel(i));
                System.out.printf("Samplefrequency: %f Hz\n", hdl.getSampleFrequency(i));
                System.out.printf("Transducer: %s\n", hdl.getTransducer(i));
                System.out.printf("Physical dimension: %s\n", hdl.getPhysicalDimension(i));
                System.out.printf("Physical minimum: %f\n", hdl.getPhysicalMinimum(i));
                System.out.printf("Physical maximum: %f\n", hdl.getPhysicalMaximum(i));
                System.out.printf("Digital minimum: %d\n", hdl.getDigitalMinimum(i));
                System.out.printf("Digital maximum: %d\n", hdl.getDigitalMaximum(i));
                System.out.printf("Prefilter: %s\n", hdl.getPreFilter(i));
                System.out.printf("Samples per datarecord: %d\n", hdl.getSampelsPerDataRecord(i));
                System.out.printf("Total samples in file: %d\n", hdl.getTotalSamples(i));
                System.out.printf("Reserved: %s\n", hdl.getReserved(i));
            }catch(EDFException e){
                System.out.printf("An error occured: ");
                System.out.printf(e.getMessage());
                return;
            }
        }

        double[] buf = new double[8];

        int err;

        try{
            err = hdl.readPhysicalSamples(0, buf);
        } catch(IOException e) {
            System.out.printf("An error occured: ");
            System.out.printf(e.getMessage());
            return;
        } catch(EDFException e) {
            System.out.printf("An error occured: ");
            System.out.printf(e.getMessage());
            return;
        }


        for(i=0; i<hdl.annotationslist.size(); i++){
            System.out.printf("annotation: onset: %d:%02d:%02d    description: %s    duration: %d\n",
                    (hdl.annotationslist.get(i).onset / 10000000) / 3600,
                    ((hdl.annotationslist.get(i).onset / 10000000) % 3600) / 60,
                    (hdl.annotationslist.get(i).onset / 10000000) % 60,
                    hdl.annotationslist.get(i).description,
                    hdl.annotationslist.get(i).duration);
        }

    }

    public static void writeEdfFile(String fileName, long[][] edfData){
        int i, j, chan, err,
                sf1=edfData[0].length,
                //sf2=1500,
                edfsignals = 1,
                datrecs=edfData.length;

        double q1, q2, sine_17, sine_26;

        double[] buf1 = new double[sf1];
        //double[] buf2 = new double[sf2];

        EDFwriter hdl;

        try {
            hdl = new EDFwriter(fileName, EDFwriter.EDFLIB_FILETYPE_EDFPLUS, edfsignals);
        }catch(IOException e){
            System.out.printf("An error occured while opening the file: ");
            System.out.printf(e.getMessage());
            return;
        }catch(EDFException e){
            System.out.printf("An error occured while opening the file: ");
            System.out.printf(e.getMessage());
            return;
        }

        for(chan=0; chan<edfsignals; chan++){
            hdl.setPhysicalMaximum(chan, 3000);
            hdl.setPhysicalMinimum(chan, -3000);
            hdl.setDigitalMaximum(chan, 32767);
            hdl.setDigitalMinimum(chan, -32768);
            hdl.setPhysicalDimension(chan, String.format("uV"));
        }

        hdl.setSampleFrequency(0, sf1);
        //hdl.setSampleFrequency(1, sf2);
        hdl.setSignalLabel(0, String.format("sine 1.7Hz", chan + 1));
        //hdl.setSignalLabel(1, String.format("sine 2.6Hz", chan + 1));


        try{
            for(i=0; i<datrecs; i++){
                for(j=0; j<sf1; j++){
                    //q1 += sine_17;
                    //buf1[j] = 2000.0 * Math.sin(q1);
                    buf1[j] = new Long(edfData[i][j]).doubleValue();
                }

                err = hdl.writePhysicalSamples(buf1);
                if(err != 0){
                    System.out.printf("writePhysicalSamples() returned error: %d\n", err);
                    return;
                }

            }
        }catch(IOException e){
            System.out.printf("An error occured while writing to the file: ");
            System.out.printf(e.getMessage());
            return;
        }

        hdl.writeAnnotation(0, -1, "Recording starts");

        hdl.writeAnnotation(datrecs * 10000, -1, "Recording ends");

        try{
            hdl.close();
        }catch(IOException e){
            System.out.printf("An error occured while closing the file: ");
            System.out.printf(e.getMessage());
            return;
        }catch(EDFException e){
            System.out.printf("An error occured while closing the file: ");
            System.out.printf(e.getMessage());
            return;
        }

    }


    public static void main(String[] args){
        long[][] edfData=new long[][]{{1,2,3,4,5,6,7,8},{10,20,30,40,50,60,70,80},{100,200,300,400,500,600,700,800}};//生成待存储的矩阵
        //EdfUtil.writeEdfFile("edftest1.edf", edfData);

        EdfUtil.readEdfFile("edftest1.edf");

    }

}
