package com.Bank.Bean;

import java.sql.Timestamp;

public class TransactionBean {

    private long accno;
    private int tid;
    private Timestamp time;
    private double amount;
    private String t_type;

    // Parameterized constructor
    public TransactionBean(long accno, int tid, Timestamp time, double amount, String t_type) {
        this.accno = accno;
        this.tid = tid;
        this.time = time;
        this.amount = amount;
        this.t_type = t_type;
    }

    // Getter and Setter methods
    public long getAccno() {
        return accno;
    }

    public void setAccno(long accno) {
        this.accno = accno;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getT_type() {
        return t_type;
    }

    public void setT_type(String t_type) {
        this.t_type = t_type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "accno=" + accno +
                ", tid=" + tid +
                ", time=" + time +
                ", amount=" + amount +
                ", t_type='" + t_type + '\'' +
                '}';
    }
}
