package com.brapeba.suptraining;

/**
 * Created by joanmi on 07-Nov-15.
 */
public class ElementsData
{
    private String name;
    private String unit; //units when needed, such as "meters"
    private int inc; //increment to show at quick button
    private Boolean toShow=true; //enable/disable element at table

    public ElementsData(String s1,String s2,int i1)
    {
        this.name=s1;
        this.unit=s2;
        this.inc=i1;
    }

    public void setName(String s1) { this.name=s1; }
    public void setInc(int i1) { this.inc=i1; }
    public void setUnit(String s2) { this.unit=s2; }
    public void setToShow(Boolean ts) { this.toShow=ts; }

    public String getName() { return this.name; }
    public String getUnit() { return this.unit; }
    public int getInc() { return this.inc; }
    public Boolean getToShow() { return this.toShow; }
}

