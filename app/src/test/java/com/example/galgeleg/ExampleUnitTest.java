package com.example.galgeleg;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void afprøvMedSkovsnegl() {
        Logic game = Logic.getInstance();
        game.getWordLibrary().clear();
        game.getWordLibrary().add("skowsnegl");

        game.restart();

        game.guessedLetter("e");
        game.guessedLetter("s");
        assertEquals(0, game.getWrongGuess());

        assertEquals("s___s_e__", game.getVisibleSentence().toString());

        game.guessedLetter("q");
        assertEquals(1, game.getWrongGuess());

        assertFalse(game.gameIsWon());
        assertFalse(game.gameIsLost());
    }

    @Test
    public void prøvHentOrdFraDr() throws Exception {
        Logic spil = Logic.getInstance();
        spil.hentOrdFraDr();
        assertTrue("Mere end 100 ord fra DR", spil.getWordLibrary().size()>100);
    }
}