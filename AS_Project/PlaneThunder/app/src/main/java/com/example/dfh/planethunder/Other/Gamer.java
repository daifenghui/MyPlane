package com.example.dfh.planethunder.Other;

import java.io.Serializable;

/**
 * Created by dfh on 19-8-29.
 */

public class Gamer implements Serializable {
        // 玩家姓名
        private String name;
        // 玩家分数
        private int score;

	public Gamer(String name, int score) {
            this.name = name;
            this.score = score;
        }
    public String getName() {
        return name;
    }
    public int getScore() {
        return score;
    }
    @Override
    public String toString() {
        return "Gamer [name=" + name + ", score=" + score + "]";
    }
}