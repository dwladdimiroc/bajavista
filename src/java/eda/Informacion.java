/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eda;

/**
 *
 * @author daniel
 */
public class Informacion {

    int id;
    long timestamp;
    String text;
    double objective;
    double subjective;
    double positive;
    double negative;
    int need;
    double ranking;
    int retweets;

    public Informacion(int id, long timestamp, String text, double objective, double subjective, double positive, double negative, int need) {
        this.id = id;
        this.timestamp = timestamp;
        this.text = text;
        this.objective = objective;
        this.subjective = subjective;
        this.positive = positive;
        this.negative = negative;
        this.need = need;
        this.ranking = 0;
        this.retweets = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getObjective() {
        return objective;
    }

    public void setObjective(double objective) {
        this.objective = objective;
    }

    public double getSubjective() {
        return subjective;
    }

    public void setSubjective(double subjective) {
        this.subjective = subjective;
    }

    public double getPositive() {
        return positive;
    }

    public void setPositive(double positive) {
        this.positive = positive;
    }

    public double getNegative() {
        return negative;
    }

    public void setNegative(double negative) {
        this.negative = negative;
    }

    public int getNeed() {
        return need;
    }

    public void setNeed(int need) {
        this.need = need;
    }

    public double getRanking() {
        return ranking;
    }

    public void setRanking(double ranking) {
        this.ranking = ranking;
    }

    public int getRetweets() {
        return retweets;
    }

    public void setRetweets(int retweets) {
        this.retweets = retweets;
    }
    
    
}
