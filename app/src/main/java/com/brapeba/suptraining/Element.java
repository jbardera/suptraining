package com.brapeba.suptraining;

/**
 * Created by joanmi on 05-Nov-15.
 */
public class Element
{
    private String name;
    private int code;
    private int timesdone;

    public Element(String s1,int c1)
    {
        this.name=s1;
        this.code=c1;
        this.timesdone=0;
    }

    public int sumTimes(int numtimes)
    {
        this.timesdone+=numtimes;
        return this.timesdone;
    }

    public String getName() { return this.name; }
    public int getCode() { return this.code; }
    public int getTimesdone() { return this.timesdone; }
}
