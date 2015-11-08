package com.brapeba.suptraining;

/**
 * Created by joanmi on 07-Nov-15.
 */
public class ElementsData
{
    private String unit;
    private int inc;

    public ElementsData(String s2,int i1)
    {
        this.unit=s2;
        this.inc=i1;
    }

    public void setInc(int i1) { this.inc=i1; }

    public String getUnit() { return this.unit; }
    public int getInc() { return this.inc; }
}

