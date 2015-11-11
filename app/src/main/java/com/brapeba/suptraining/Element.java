package com.brapeba.suptraining;

import java.io.Serializable;

/**
 * Created by joanmi on 05-Nov-15.
 */
public class Element implements Serializable
{
    private int code;
    private int timesdone;

    public Element(int c1)
    {
        this.code=c1;
        this.timesdone=0;
    }

    public int sumTimes(int numtimes)
    {
        this.timesdone+=numtimes;
        return this.timesdone;
    }

    public int getCode() { return this.code; }
    public int getTimesdone() { return this.timesdone; }
}
