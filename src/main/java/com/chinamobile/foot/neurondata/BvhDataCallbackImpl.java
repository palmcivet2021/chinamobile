package com.chinamobile.foot.neurondata;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.FloatByReference;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class BvhDataCallbackImpl implements BvhDataCallback {
    private static int i = 1;
    private  List<Float> bvhDataList = new ArrayList();

    @Override
    public void FrameDataReceived(Pointer customedObj, Pointer sender, BvhDataHeader header, FloatByReference data) {
        System.out.println("######Invoke BvhDataCallback  successfully!#####,i=" + i++);
        System.out.println("customedObj=" + customedObj);
        System.out.println("sender=" + sender);
        System.out.println("bvh.header=" + header + ",name=" + new String(header.AvatarName));
        System.out.println("bvh.data=" + data.getValue());

        //bvhDataList.add(data.getValue());
        NeuronDataReaderTest.bvhDataList.add(data.getValue());

        /*while (true) {
            if (header != null) {
                System.out.println("######Invoke BvhDataCallback  successfully!#####,i=" + i++);
                System.out.println("customedObj=" + customedObj);
                System.out.println("sender=" + sender);
                System.out.println("header=" + header + ",name=" + new String(header.AvatarName));
                System.out.println("data=" + data.getValue());

                bvhDataList.add(data.getValue());
            }
        }*/
    }

    public List<Float> getBvhDataList(){
        return bvhDataList;
    }

}
