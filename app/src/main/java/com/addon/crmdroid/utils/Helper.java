package com.addon.crmdroid.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.addon.crmdroid.models.LeadSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Helper {
    private static List<LeadSource> leadSourceList;
    public static List<LeadSource> getLeadSourceList(Context context){
        if(leadSourceList==null){

        }
        return leadSourceList;
    }
    public static void setLeadSourceList(Context context,List<LeadSource> leadSourceList)
    {
        Helper.leadSourceList=leadSourceList;
    }



}
