package com.brapeba.suptraining;

import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joanmi on 05-Nov-15.
 */
public class Constants
{
    public static final int INITNUMELEMENTS=4; // on strings.xml from element0 to element(INITNUMELEMENTS-1)
    public static final String TAG="SUPTRAINING";
    public static final String ARG_SECTION_NUMBER = "section_number";
    public static final String MYPREFSOR="myprefsor";
    public static Session session;
    public static List<Element> tableSession, tableConfig; //all elements of session
    public static Map<Integer,ElementsData> listElementsData; //using Integer instead of String=R.string.<name> because of localizations change maps!
    public static Boolean isSaved=false;  //to know if session at Intro fragment is new or saved
    public static List<Session> allSessions; //=new ArrayList<>(); //all sessions saved

    public void Constants()
    {
    }
}
