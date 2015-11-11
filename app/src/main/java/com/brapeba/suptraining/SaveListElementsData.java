package com.brapeba.suptraining;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

/**
 * Created by joanmi on 09-Nov-15.
 */
public class SaveListElementsData
{
    static String FILENAME = "ListElementsData";
    static Map<Integer,ElementsData> allSessionsTemp; //=new HashMap<>();
    static FileOutputStream fos;
    static FileInputStream fin;

    public SaveListElementsData()
    {
    }

    static public Map<Integer,ElementsData> readFromInternalStorage(Activity activity)
    {
        Map<Integer,ElementsData> toReturn = null;
        FileInputStream fis;

        try
        {
            fis = activity.openFileInput(FILENAME);
            ObjectInputStream oi = new ObjectInputStream(fis);
            toReturn = (Map<Integer,ElementsData>) oi.readObject();
            oi.close();
        } catch (FileNotFoundException e)
        {
            Log.e(Constants.TAG, e.getMessage());
        } catch (IOException e)
        {
            Log.e(Constants.TAG, e.getMessage());
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return toReturn;
    }

    static public boolean dumpMemToInternalStorage(Map<Integer,ElementsData> aCounts, Activity activity)
    {
        // saving all mem to disk
        try
        {
            FileOutputStream fos = activity.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream of = new ObjectOutputStream(fos);
            of.writeObject(aCounts);
            of.flush();
            of.close();
            fos.close();
        } catch (Exception e)
        {
            Log.e(Constants.TAG, e.getMessage());
            return false;
        }

        return true;
    }

}
