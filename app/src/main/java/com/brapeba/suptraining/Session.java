package com.brapeba.suptraining;

import java.util.Date;
import java.util.List;

/**
 * Created by joanmi on 06-Nov-15.
 */
public class Session
{
    private String name;
    private String date;
    private List<Element> listElement;

    public Session(String s1,String s2,List<Element> le1)
    {
        this.name=s1;
        this.date=s2;
        this.listElement=le1;
    }

    public void SetElement(List<Element> e1)
    {
        this.listElement=e1;
    }

    public String getName() { return this.name; }
    public String getDate() { return this.date; }
    public List<Element> getElements() { return this.listElement; }
}
