package juegoSerpiente.persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class ScoreService {
    private static final String FILE_NAME = "highscore.dat";

    // Leer récord desde archivo
    public static int getHighScore() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            return Integer.parseInt(br.readLine());
        } catch (Exception e) {
            return 0;
        }
    }

    // Guardar récord
    public static void saveHighScore(int score) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            bw.write(String.valueOf(score));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
