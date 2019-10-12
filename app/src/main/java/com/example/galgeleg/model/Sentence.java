package com.example.galgeleg.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class Sentence {
    private Set<String> wordLibrary;
    private List<String> solution;
    private ArrayList<Character> usedLetters;
    private StringBuilder visibleSentence;
    private int wrongGuess = 0;
    private boolean gameIsWon;
    private boolean gameIsLost;
    private int difficulty = 1;

    public Sentence(){
        this.usedLetters = new ArrayList<>();
        this.visibleSentence = new StringBuilder();
        restart();
    }

    {
        this.wordLibrary = new HashSet<>();
        wordLibrary.add("bil");
        wordLibrary.add("computer");
        wordLibrary.add("programmering");
        wordLibrary.add("motorvej");
        wordLibrary.add("busrute");
        wordLibrary.add("gangsti");
        wordLibrary.add("skovsnegl");
        wordLibrary.add("solsort");
        wordLibrary.add("nitten");
    }

    public void restart(){
        usedLetters.clear();
        wrongGuess = 0;
        gameIsWon = false;
        solution = addSolution(difficulty);
        opdateVisibleSentence();
    }

    private void opdateVisibleSentence() {
        visibleSentence.setLength(0);
        gameIsWon = true;

        for (String w : solution){
            char[] word = w.toCharArray();
            for (char letter : word){
                if (usedLetters.contains(letter)) visibleSentence.append(letter);
                else {
                    visibleSentence.append("?");
                    gameIsWon = false;
                }
            }
        }
    }

    private List<String> addSolution(int difficulty) {
        ArrayList<String> solution = new ArrayList<>();
        for (int i = 0; i < difficulty; i++) {
            int newWordInx = new Random().nextInt(wordLibrary.size());
            solution.add((String) wordLibrary.toArray()[newWordInx]);
        }
        return solution;
    }

    public void guessedLetter(char letter) {
        Log.d(TAG, "guessedLetter: " + letter);
        if (gameIsWon || usedLetters.contains(letter)) return;

        usedLetters.add(letter);

        if (!solution.contains(""+letter) && ++wrongGuess > 6) gameIsLost = true;

        opdateVisibleSentence();
    }


    public Set<String> getWordLibrary() {
        return wordLibrary;
    }

    public void setWordLibrary(Set<String> wordLibrary) {
        this.wordLibrary = wordLibrary;
    }

    public List<String> getSolution() {
        return solution;
    }

    public void setSolution(List<String> solution) {
        this.solution = solution;
    }

    public ArrayList<Character> getUsedLetters() {
        return usedLetters;
    }

    public void setUsedLetters(ArrayList<Character> usedLetters) {
        this.usedLetters = usedLetters;
    }

    public StringBuilder getVisibleSentence() {
        return visibleSentence;
    }

    public void setVisibleSentence(StringBuilder visibleSentence) {
        this.visibleSentence = visibleSentence;
    }

    public int getWrongGuess() {
        return wrongGuess;
    }

    public void setWrongGuess(int wrongGuess) {
        this.wrongGuess = wrongGuess;
    }

    public boolean isGameIsWon() {
        return gameIsWon;
    }

    public void setGameIsWon(boolean gameIsWon) {
        this.gameIsWon = gameIsWon;
    }

    public boolean isGameIsLost() {
        return gameIsLost;
    }

    public void setGameIsLost(boolean gameIsLost) {
        this.gameIsLost = gameIsLost;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "Sentence{" +
                "wordLibrary=" + wordLibrary +
                ", solution=" + solution +
                ", usedLetters=" + usedLetters +
                ", visibleSentence=" + visibleSentence +
                ", wrongGuess=" + wrongGuess +
                ", gameIsWon=" + gameIsWon +
                ", gameIsLost=" + gameIsLost +
                ", difficulty=" + difficulty +
                '}';
    }
}
