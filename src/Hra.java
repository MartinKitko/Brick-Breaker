import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import javax.swing.JOptionPane;
/**
 * Hlavna trieda ktora riadi chod celej hry a ostatne triedy
 * 
 * @author Martin Kitko
 * @version 20.12.2020
 */
public class Hra {
    private Blok[][] zoznamBlokov;
    private Lopticka lopticka;
    private Level level;
    private Plosina plosina;
    private Menu menu;
    private Manazer manazer;
    private Random generator;
    private boolean jeAktivna;
    private double rychlost;
    private double smerX;
    private double smerY;
    private int trvanieHry;
    private int aktLevel;
    private int skore;
    
    /**
     * Konstruktor triedy Hra
     */
    public Hra() {
        this.level = new Level(this);
        this.plosina = new Plosina();
        this.lopticka = new Lopticka();
        this.menu = new Menu();
        this.generator = new Random();
        this.manazer = new Manazer();
        
        this.manazer.spravujObjekt(this);
        this.manazer.spravujObjekt(this.plosina);
        this.manazer.spravujObjekt(this.menu);
        
        this.aktLevel = 1;
        this.smerX = 1;
        this.smerY = 1;
        this.trvanieHry = 0;
        this.skore = 0;
        this.menu.zobraz();
        this.startHry();
    }
    
    /**
     * Skryje uvodne menu a nasledne spusta hru v kazdom leveli
     */
    public void aktivuj() {
        if (!this.jeAktivna && !this.menu.jeZobrazene() && this.aktLevel == 7) {
            this.level.zapisLevelDoSuboru();
            this.level.skry();
            this.menu.zobraz("uvod1");
        } else if (!this.jeAktivna) {
            if (this.menu.jeZobrazene()) {
                this.aktLevel = this.menu.getAktLevel();
                this.menu.skry();
                this.plosina.resetujPolohu();
                this.plosina.zobraz();
                this.lopticka.zobraz();
                this.zoznamBlokov = this.level.nastavLevel(this.aktLevel);
            } else {
                this.jeAktivna = true;
            }
        }
    }
    
    /**
     * Hlavna metoda ktora riadi pohyb lopticky a jej kontroluje jej kolizie
     */
    public void startHry() {
        // suradnice lopticky
        double x;
        double y;
        
        while (true) {
            try {
                Thread.sleep(0);
            } catch (java.lang.InterruptedException exception) {
            }
            while (this.jeAktivna) {
                x = this.lopticka.getX();
                y = this.lopticka.getY();
                
                // kontrola kolizii lopticky so stenami a plosinou
                if (x + this.lopticka.getVelkost() > 800) {
                    this.smerX = -1;
                } else if (this.plosina.obsahujeSuradnice(x, y)) {
                    this.smerY = -1 + this.generator.nextDouble() / 10;
                } else if (x < 0) {
                    this.smerX = 1;
                } else if (y < 0) {
                    this.smerY = 1;
                } else if (y > 500) {
                    this.menu.zobraz("prehra");
                    this.koniecHry("Prehral si");
                    System.exit(0);
                }
                
                // kontrola kolizii lopticky s blokmi
                boolean vsetkyZnicene = true;
                for (int i = 0; i < this.zoznamBlokov.length; i++) {
                    for (int j = 0; j < this.zoznamBlokov[0].length; j++) {
                        if (this.zoznamBlokov[i][j].obsahujeSuradnice(x, y, this.lopticka.getVelkost()) && 
                            !this.zoznamBlokov[i][j].jeZniceny()) {
                            this.zoznamBlokov[i][j].znicSa();
                            this.skore += 5;
                            
                            if (this.zoznamBlokov[i][j].strana(x, y, this.rychlost)) {
                                this.smerX *= -1;
                            } else {
                                this.smerY *= -1;
                            }
                            
                            this.lopticka.posun(this.rychlost * smerX, this.rychlost * smerY);
                            x = this.lopticka.getX();
                            y = this.lopticka.getY();
                        }
                        if (!this.zoznamBlokov[i][j].jeZniceny()) {
                            vsetkyZnicene = false;
                        }
                    }   
                }
                
                // kontrola prejdenia levelu a konca hry
                if (vsetkyZnicene) {
                    if (this.aktLevel >= 5) {
                        this.menu.zobraz("vyhra");
                        this.koniecHry("Vyhral si");
                        System.exit(0);
                    } else {
                        this.posunLevel();
                    }
                } else {
                    this.lopticka.posun(this.rychlost * smerX, this.rychlost * smerY);
                }
            }
        }
    }
    
    /**
     * Nastavuje rychlost lopticky
     * @param rychlost  nova rychlost lopticky
     */
    public void nastavRychlost(double rychlost) {
        this.rychlost = rychlost;
    }
    
    /**
     * Vykona sa pri kliknuti lavym lacidlom mysi
     * @param x x-ova suradnica kurzora mysi
     * @param y y-ova suradnica kurzora mysi
     */
    public void lavyKlik(int x, int y) {
        if (!this.jeAktivna && !this.menu.jeZobrazene()) {
            this.zmenNakliknutyBlok(x, y, 1);
        }
    }
    
    /**
     * Vykona sa pri kliknuti pravym lacidlom mysi
     * @param x x-ova suradnica kurzora mysi
     * @param y y-ova suradnica kurzora mysi
     */
    public void pravyKlik(int x, int y) {
        if (!this.jeAktivna && !this.menu.jeZobrazene()) {
            this.zmenNakliknutyBlok(x, y, 3);
        }
    }
    
    /**
     * Zastavi a znovu spusti hru
     */
    public void pauza() {
        if (this.jeAktivna) {
            this.jeAktivna = false;
            this.manazer.prestanSpravovatObjekt(this.plosina);
        } else {
            this.jeAktivna = true;
            this.manazer.spravujObjekt(this.plosina);
        }
    }
    
    /**
     * Pocita trvanie hry
     */
    public void tik() {
        if (this.jeAktivna) {
            this.trvanieHry++;
        }
    }
    
    /**
     * Pripravi hru na dalsi level
     */
    private void posunLevel() {
        this.jeAktivna = false;
        this.level.skry();
        this.lopticka.resetujPolohu();
        this.plosina.resetujPolohu();
        this.aktLevel++;
        this.zoznamBlokov = this.level.nastavLevel(this.aktLevel);
        this.smerX = 1;
        this.smerY = 1;
        this.skore += 50;
    }
    
    /**
     * Edituje bloky v leveli kliknutim
     * @param x x-ova suradnica kurzora mysi
     * @param y y-ova suradnica kurzora mysi
     * @param tlacidlo cislo tlacidla mysi
     */
    private void zmenNakliknutyBlok(int x, int y, int tlacidlo) {
        for (int i = 0; i < this.zoznamBlokov.length; i++) {
            for (int j = 0; j < this.zoznamBlokov[0].length; j++) {
                if (this.zoznamBlokov[i][j].obsahujeSuradnice(x, y, 0)) {
                    if (tlacidlo == 1) {
                        if (this.zoznamBlokov[i][j].jeZniceny()) {
                            this.zoznamBlokov[i][j].zobrazSa();
                        } else {
                            this.zoznamBlokov[i][j].znicSa();
                        }
                    } else if (tlacidlo == 3) {
                        if (!this.zoznamBlokov[i][j].jeZniceny()) {
                            this.zoznamBlokov[i][j].posunFarbu();
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Vypise tabulku skore
     */
    private void zobrazSkore() {
        File subor = new File("skore.txt");
        try {
            Scanner citac = new Scanner(subor);
            String vypisSkore = "";
            while (citac.hasNextLine()) {
                vypisSkore += citac.nextLine();
                vypisSkore += "\n";
            }
            citac.close();
            JOptionPane.showMessageDialog(null, vypisSkore);
        } catch (java.io.IOException exception) {
        }
    }
    
    /**
     * Dopise nove skore do suboru na novy riadok
     * @param trvanie Upravene trvanie hry ako String
     */
    private void zapisSkoreDoSuboru(String trvanie) {
        File subor = new File("src/skore.txt");
        String meno = JOptionPane.showInputDialog("Zadaj meno alebo prezyvku: ");
        if (meno != null && !meno.equals("")) {
            try {
                PrintWriter zapisovac = new PrintWriter(new FileWriter(subor, true));
                zapisovac.printf("     %3d             %s                 %s\n", this.skore, trvanie, meno);
                zapisovac.close();
            } catch (java.io.IOException exception) {
            }
        }
    }
    
    /**
     * Ukonci hru a zobrazi jej vysledok
     * @param sprava zaciatok spravy ktora sa vypise na konci hry
     */
    private void koniecHry(String sprava) {
        this.manazer.prestanSpravovatObjekt(this.plosina);
        this.manazer.prestanSpravovatObjekt(this);
        this.jeAktivna = false;
        this.trvanieHry /= 20;
        String trvanie = "";
        if (this.trvanieHry / 60 == 0) {
            trvanie += "0:";
        } else {
            trvanie += this.trvanieHry / 60 + ":";
        }
        if (this.trvanieHry % 60 < 10) {
            trvanie += "0";
        }
        trvanie += this.trvanieHry % 60;
        this.skore -= this.trvanieHry;
        if (this.skore < 0) {
            this.skore = 0;
        }
        JOptionPane.showMessageDialog(null, sprava + ", trvanie hry: " + trvanie + "\n" +
                                      "Tvoje skore je: " + this.skore);
        this.zapisSkoreDoSuboru(trvanie);
        this.zobrazSkore();
    }
}
