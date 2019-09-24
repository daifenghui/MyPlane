package com.example.dfh.planethunder.Other;

/**
 * Created by dfh on 19-8-20.
 */

public class Rank {
    public int getRanknum() {
        return ranknum;
    }

    public void setRanknum(int ranknum) {
        this.ranknum = ranknum;
    }

    private int ranknum;
    private String name;
    private int score;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
