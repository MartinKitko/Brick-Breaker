import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JOptionPane;
/**
 * Trieda Level ktora uchovava rozlozenie blokov viacerych levelov
 * 
 * @author Martin Kitko
 * @version 20.12.2020
 */
public class Level {
    private Blok[][] zoznamBlokov;
    private Hra hra;
    
    /**
     * Konstruktor triedy level
     */
    public Level(Hra hra) {
        this.hra = hra;
    }
    
    /**
     * Skryje vsetky bloky aktualneho levelu
     */
    public void skry() {
        for (int i = 0; i < this.zoznamBlokov.length; i++) {
            for (int j = 0; j < this.zoznamBlokov[0].length; j++) {
                this.zoznamBlokov[i][j].znicSa();
            }
        }
    }
    
    /**
     * Vola privatne metody reprezentujuce dany level
     * @param level cislo levelu ktory chceme nastavit
     * @return dvojrozmerne pole objektov typu Blok
     */
    public Blok[][] nastavLevel(int level) {
        switch (level) {
            case 1:
                return this.level1();
            case 2:
                return this.level2();
            case 3:
                return this.level3();
            case 4:
                return this.vytvorLevelZoSuboru("level4");
            case 5:
                return this.level5();
            case 6:
                return this.vytvorLevelZoSuboru("");
            case 7:
                return this.levelEdit();
            default:
                return this.level1();
        }
    }
    
    /**
     * Zapise level do suboru zadaneho ako parameter
     */
    public void zapisLevelDoSuboru() {
        String nazovSuboru;
        do {
            nazovSuboru = JOptionPane.showInputDialog("Zadaj nazov suboru bez pripony: ");
            if (nazovSuboru.equals("level4")) {
                JOptionPane.showMessageDialog(null, "Zadaj iny nazov");
            } else if (nazovSuboru.equals("")) {
                JOptionPane.showMessageDialog(null, "Nezadal si ziadny nazov"); 
            }
        } while (nazovSuboru.equals("level4") || nazovSuboru.equals(""));
        File subor = new File("src/levely/" + nazovSuboru + ".txt");
        try {
            PrintWriter zapisovac = new PrintWriter(subor);
            zapisovac.print(this.dajZnakyLevelu());
            zapisovac.close();
        } catch (java.io.IOException exception) {
            JOptionPane.showMessageDialog(null, "Zapis sa nepodaril");
        }
    }
    
    /**
     * Vytvori level zo suboru zadaneho ako parameter
     * @param nazovSuboru nazov suboru z ktoreho sa ma vytvorit level
     * @return dvojrozmerne pole objektov typu Blok
     */
    private Blok[][] vytvorLevelZoSuboru(String nazovSuboru)  {
        if (nazovSuboru.equals("")) {
            do {
                nazovSuboru = JOptionPane.showInputDialog("Zadaj nazov suboru bez pripony: ");
                if (nazovSuboru.equals("")) {
                    JOptionPane.showMessageDialog(null, "Nezadal si ziadny nazov"); 
                }
            } while (nazovSuboru.equals(""));
        }
        File subor = new File("src/levely/" + nazovSuboru + ".txt");
        String farba = "black";
        try {
            Scanner citac = new Scanner(subor);
            this.zoznamBlokov = new Blok[citac.nextInt()][citac.nextInt()];
            int poradieRiadku = 0;
            while (citac.hasNextLine()) {
                String riadok = citac.nextLine();
                for (int i = 0; i < riadok.length(); i++) {
                    switch (riadok.charAt(i)) {
                        case 'b':
                            farba = "blue";
                            break;
                        case 'g':
                            farba = "green";
                            break;
                        case 'r':
                            farba = "red";
                            break;
                        case 'y':
                            farba = "yellow";
                            break;
                        case 'd':
                            farba = "darkGray";
                            break;
                        default:
                            farba = "black";
                    }
                    this.zoznamBlokov[poradieRiadku - 1][i] = new Blok(17 + 65 * i, 10 + 35 * poradieRiadku, 60, 30, farba);
                    if (riadok.charAt(i) == ' ') {
                        this.zoznamBlokov[poradieRiadku - 1][i].znicSa();
                    }
                }
                poradieRiadku++;
            }
            citac.close();
            this.hra.nastavRychlost(3.3);
        } catch (java.io.IOException excpetion) {
            JOptionPane.showMessageDialog(null, "Vytvorenie neprebehlo uspesne");
        }
        return this.zoznamBlokov;
    }
    
    /**
     * Vytvori a vrati String obsahujuci aktualny level reprezentovany znakmi
     * @return String ktory obsahuje znaky reprezentujuce dany level
     */
    private String dajZnakyLevelu() {
        String levelString = this.zoznamBlokov.length + " " + this.zoznamBlokov[0].length + "\n";
        for (int i = 0; i < this.zoznamBlokov.length; i++) {
            for (int j = 0; j < this.zoznamBlokov[i].length; j++) {
                levelString += this.zoznamBlokov[i][j].dajString();
            }
            levelString += "\n";
        }
        return levelString;
    }
    
    /**
     * Inicializuje a vrati dvojrozmerne pole blokov reprezentujuce prvy level
     * @return dvojrozmerne pole objektov typu Blok
     */
    private Blok[][] level1() {
        this.zoznamBlokov = new Blok[4][8];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                this.zoznamBlokov[i][j] = new Blok(70 + 70 * j, 10 + 40 * i, 65, 35, "black");
            }
        }
        this.hra.nastavRychlost(3.1);
        return this.zoznamBlokov;
    }
    
    /**
     * Inicializuje a vrati dvojrozmerne pole blokov reprezentujuce druhy level
     * @return dvojrozmerne pole objektov typu Blok
     */
    private Blok[][] level2() {
        this.zoznamBlokov = new Blok[5][10];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                if (i % 2 == 0) {
                    this.zoznamBlokov[i][j] = new Blok(17 + 65 * j, 10 + 35 * i, 60, 30, "blue");
                } else {
                    this.zoznamBlokov[i][j] = new Blok(17 + 65 * j, 10 + 35 * i, 60, 30, "darkGray");
                }
            }
        }
        this.hra.nastavRychlost(3.3);
        return this.zoznamBlokov;
    }
    
    /**
     * Inicializuje a vrati dvojrozmerne pole blokov reprezentujuce treti level
     * @return dvojrozmerne pole objektov typu Blok
     */
    private Blok[][] level3() {
        this.zoznamBlokov = new Blok[3][14];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 14; j++) {
                if ((j + i) % 2 == 0) {
                    this.zoznamBlokov[i][j] = new Blok(30 + 45 * j, 10 + 65 * i, 40, 60, "gray");
                } else {
                    this.zoznamBlokov[i][j] = new Blok(30 + 45 * j, 10 + 65 * i, 40, 60, "darkGray");
                }
            }
        }
        this.hra.nastavRychlost(3.4);
        return this.zoznamBlokov;
    }
    
    /**
     * Inicializuje a vrati dvojrozmerne pole blokov reprezentujuce stvrty level
     * @return dvojrozmerne pole objektov typu Blok
     */
    private Blok[][] level5() {
        this.zoznamBlokov = new Blok[7][10];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 10; j++) {
                if (i % 2 == 0) {
                    this.zoznamBlokov[i][j] = new Blok(50 + 60 * j, 10 + 30 * i, 55, 25, "black");
                } else {
                    this.zoznamBlokov[i][j] = new Blok(75 + 55 * j, 10 + 30 * i, 50, 25, "red");
                }
            }
        }
        this.hra.nastavRychlost(3.3);
        return this.zoznamBlokov;
    }
    
    /**
     * Inicializuje a vrati dvojrozmerne pole blokov reprezentujuce level na editovanie
     * @return dvojrozmerne pole objektov typu Blok
     */
    private Blok[][] levelEdit() {
        this.zoznamBlokov = new Blok[6][10];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 10; j++) {
                this.zoznamBlokov[i][j] = new Blok(17 + 65 * j, 10 + 35 * i, 60, 30, "black");
            }
        }
        this.hra.nastavRychlost(3.3);
        return this.zoznamBlokov;
    }
}
