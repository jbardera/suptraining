package com.brapeba.suptraining;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by joanmi on 06-Nov-15.
 */
public class Session implements Serializable
{
    private String name;
    private Date date;
    private List<Element> listElement;
    private Boolean toBeDeleted;

    public Session(String s1,List<Element> le1)
    {
        this.name=s1;
        this.date=new Date();
        this.listElement=le1;
        this.toBeDeleted=false;
    }

    public void setElement(List<Element> e1)
    {
        this.listElement=e1;
    }
    public void setName(String s1)
    {
        this.name=s1;
    }
    public void setToBeDeleted(Boolean b1)
    {
        this.toBeDeleted=b1;
    }

    public String getName() { return this.name; }
    public Date  getDate() { return this.date; }
    public List<Element> getElements() { return this.listElement; }
    public Boolean getToBeDeleted() { return this.toBeDeleted; }
}
