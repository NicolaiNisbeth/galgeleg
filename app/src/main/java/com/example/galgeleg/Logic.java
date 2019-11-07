package com.example.galgeleg;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class Logic {
    private static Logic instance;

    private HashSet<String> wordLibrary;
    private List<String> solution;
    private ArrayList<String> usedLetters;
    private StringBuilder visibleSentence;
    private int lives = 6, difficulty = 1, wrongGuess = 0;
    private boolean gameIsWon, gameIsLost, previousGuessWasCorrect;

    private Logic(){
        this.wordLibrary = new HashSet<>();
    }

    public static Logic getInstance(){
        if (instance == null) instance = new Logic();

        return instance;
    }

    public void restart(){
        solution = addSolution(difficulty);
        usedLetters = new ArrayList<>();
        visibleSentence = new StringBuilder();
        lives = 6;
        wrongGuess = 0;
        gameIsWon = false;
        gameIsLost = false;
        previousGuessWasCorrect = false;
        opdateVisibleSentence();
    }

    private void opdateVisibleSentence() {
        visibleSentence.setLength(0);
        gameIsWon = true;


        for (int i = 0; i < solution.size(); i++) {
            for (int j = 0; j < solution.get(i).length(); j++) {
                String letter = solution.get(i).substring(j, j + 1);
                if (usedLetters.contains(letter)) visibleSentence.append(letter);
                else {
                    visibleSentence.append("_");
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

    public void guessedLetter(String letter) {
        //Log.d(TAG, "guessedLetter: " + letter);
        if (gameIsWon || usedLetters.contains(letter) || gameIsLost) return;

        usedLetters.add(letter);

        if (solutionHas(letter)) previousGuessWasCorrect = true;
        else {
            wrongGuess++;
            previousGuessWasCorrect = false;
            if (--lives <= 0) gameIsLost = true;
        }

        opdateVisibleSentence();
    }

    private boolean solutionHas(String letter) {
        return solution.get(0).contains(letter);
    }


    public String printArray(List<String> list) {
        StringBuilder out = new StringBuilder();
        for (String s : list){
            out.append(s.toUpperCase()).append(" ");
        }
        return out.toString();
    }

    public static String hentUrl(String url) throws IOException {
        System.out.println("Henter data fra " + url);
        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        StringBuilder sb = new StringBuilder();
        String linje = br.readLine();
        while (linje != null) {
            sb.append(linje).append("\n");
            linje = br.readLine();
        }
        return sb.toString();
    }

    /**
     * Hent ord fra DRs forside (https://dr.dk)
     * @return
     */
    public HashSet<String> hentOrdFraDr() throws Exception {
        String data = hentUrl("https://dr.dk");

        data = data.substring(data.indexOf("<body")). // fjern headere
                replaceAll("<.+?>", " ").toLowerCase(). // fjern tags
                replaceAll("&#198;", "æ"). // erstat HTML-tegn
                replaceAll("&#230;", "æ"). // erstat HTML-tegn
                replaceAll("&#216;", "ø"). // erstat HTML-tegn
                replaceAll("&#248;", "ø"). // erstat HTML-tegn
                replaceAll("&oslash;", "ø"). // erstat HTML-tegn
                replaceAll("&#229;", "å"). // erstat HTML-tegn
                replaceAll("[^a-zæøå]", " "). // fjern tegn der ikke er bogstaver
                replaceAll(" [a-zæøå] "," "). // fjern 1-bogstavsord
                replaceAll(" [a-zæøå][a-zæøå] "," "); // fjern 2-bogstavsord

        return new HashSet<>(Arrays.asList(data.replaceAll("\\W+"," ").trim().split(" ")));
    }


    /**
     * Hent ord og sværhedsgrad fra et online regneark. Du kan redigere i regnearket, på adressen
     * https://docs.google.com/spreadsheets/d/1RnwU9KATJB94Rhr7nurvjxfg09wAHMZPYB3uySBPO6M/edit?usp=sharing
     * @param sværhedsgrader en streng med de tilladte sværhedsgrader - f.eks "3" for at medtage kun svære ord, eller "12" for alle nemme og halvsvære ord
     * @throws Exception
     */

    public void hentOrdFraRegneark(String sværhedsgrader) throws Exception {
        String id = "1RnwU9KATJB94Rhr7nurvjxfg09wAHMZPYB3uySBPO6M";

        System.out.println("Henter data som kommasepareret CSV fra regnearket https://docs.google.com/spreadsheets/d/"+id+"/edit?usp=sharing");

        String data = hentUrl("https://docs.google.com/spreadsheets/d/" + id + "/export?format=csv&id=" + id);
        int linjeNr = 0;

        wordLibrary.clear();
        for (String linje : data.split("\n")) {
            if (linjeNr<20) System.out.println("linje = " + linje); // udskriv de første 20 linjer
            if (linjeNr++ < 1 ) continue; // Spring første linje med kolonnenavnene over
            String[] felter = linje.split(",", -1);// -1 er for at beholde tomme indgange, f.eks. bliver ",,," splittet i et array med 4 tomme strenge
            String sværhedsgrad = felter[0].trim();
            String ordet = felter[1].trim().toLowerCase();
            if (ordet.isEmpty()) continue; // spring over linjer med tomme ord
            if (!sværhedsgrader.contains(sværhedsgrad)) continue; // filtrér på sværhedsgrader
            wordLibrary.add(ordet);
        }

        System.out.println("muligeOrd = " + wordLibrary);
        restart();
    }

    @Override
    public String toString() {
        return "Logic{" +
                //"wordLibrary=" + wordLibrary +
                ", solution=" + solution +
                ", usedLetters=" + usedLetters +
                ", visibleSentence=" + visibleSentence +
                ", lives=" + lives +
                ", gameIsWon=" + gameIsWon +
                ", gameIsLost=" + gameIsLost +
                ", difficulty=" + difficulty +
                '}';
    }

    public boolean isGuessCorrect() {
        return previousGuessWasCorrect;
    }

    public void setPreviousGuessWasCorrect(boolean previousGuessWasCorrect) {
        this.previousGuessWasCorrect = previousGuessWasCorrect;
    }

    public Set<String> getWordLibrary() {
        return wordLibrary;
    }

    public void setWordLibrary(HashSet<String> wordLibrary) {
        this.wordLibrary = wordLibrary;
    }

    public List<String> getSolution() {
        return solution;
    }

    public void setSolution(List<String> solution) {
        this.solution = solution;
    }

    public ArrayList<String> getUsedLetters() {
        return usedLetters;
    }

    public void setUsedLetters(ArrayList<String> usedLetters) {
        this.usedLetters = usedLetters;
    }

    public StringBuilder getVisibleSentence() {
        return visibleSentence;
    }

    public void setVisibleSentence(StringBuilder visibleSentence) {
        this.visibleSentence = visibleSentence;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public boolean gameIsWon() {
        return gameIsWon;
    }

    public void setGameIsWon(boolean gameIsWon) {
        this.gameIsWon = gameIsWon;
    }

    public boolean gameIsLost() {
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

    public int getWrongGuess() {
        return wrongGuess;
    }

    public void setWrongGuess(int wrongGuess) {
        this.wrongGuess = wrongGuess;
    }
}
