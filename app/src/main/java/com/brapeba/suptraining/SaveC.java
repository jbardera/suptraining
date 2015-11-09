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
        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by joanmi on 09-Nov-15.
 */
public class SaveC
{
    static String FILENAME = "SupSessions";
    static List<Session> allSessionsTemp;
    static FileOutputStream fos;
    static FileInputStream fin;

    public SaveC()
    {
    }

    static public boolean saveToInternalStorage(Session mySession,Activity activity)
    {
        // 1st reading all previous stored counts
        allSessionsTemp=readFromInternalStorage(activity);

        // now adding the one to save
        if (allSessionsTemp==null) { allSessionsTemp=new ArrayList<Session>(); }
        allSessionsTemp.add(mySession);

        // and saving
        try
        {
            FileOutputStream fos = activity.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream of = new ObjectOutputStream(fos);
            of.writeObject(allSessionsTemp);
            of.flush();
            of.close();
            fos.close();
        } catch (Exception e) { Log.e(Constants.TAG, e.getMessage()); return false;}

        return true;
    }

    static public List<Session> readFromInternalStorage(Activity activity)
    {
        List<Session> toReturn=null;
        FileInputStream fis;

        try
        {
            fis = activity.openFileInput(FILENAME);
            ObjectInputStream oi = new ObjectInputStream(fis);
            toReturn = (List<Session>) oi.readObject();
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

    static public boolean dumpMemToInternalStorage(List<Session> aCounts,Activity activity)
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
        } catch (Exception e) { Log.e(Constants.TAG, e.getMessage()); return false;}

        return true;
    }

}
