
/**
 * Trieda Blok ktora predstavuje jeden samostatny blok
 * 
 * @author Martin Kitko 
 * @version 20.12.2020
 */
public class Blok {
    private Obdlznik blok;
    private int sirkaBloku;
    private int vyskaBloku;
    private int aktFarba;
    private boolean zniceny;
    private String farba;
    private final String[] farby = {"black", "blue", "green", "red", "yellow", "darkGray"};
    /**
     * Konstruktor triedy Blok ktory vytvori dany blok podla zadanych parametrov
     */
    public Blok(int x, int y, int sirka, int vyska, String farba) {
        this.blok = new Obdlznik();
        this.sirkaBloku = sirka;
        this.vyskaBloku = vyska;
        this.aktFarba = 0;
        this.blok.zmenStrany(this.sirkaBloku, this.vyskaBloku);
        this.blok.posunVodorovne(x);
        this.blok.posunZvisle(y);
        this.farba = farba;
        this.blok.zmenFarbu(farba);
        this.blok.zobraz();
    }
    
    /**
     * Kontroluje ci blok obsahuje suradnice zadane ako parametre
     * @param x x-ova suradnica
     * @param y y-ova suradnica
     * @param velkost velkost lopticky
     * @return vrati true pokial obsahuje zadane suradnice
     */
    public boolean obsahujeSuradnice(double x, double y, int velkost) {
        return x + velkost > this.blok.getX() && x < this.blok.getX() + this.sirkaBloku &&
               y + velkost > this.blok.getY() && y < this.blok.getY() + this.vyskaBloku;
    }
    
    /**
     * Kontroluje ci sa lopticka dotkla jednej z bocnych stran bloku
     * @param x x-ova suradnica
     * @param y y-ova suradnica
     * @param rychlost aktualna rychlost ktorou sa lopticka pohybuje
     * @return true / false
     */
    public boolean strana(double x, double y, double rychlost) {
        return (x + (30 - rychlost) <= this.blok.getX() || x + rychlost >= this.blok.getX() + this.sirkaBloku) &&
               (y + (30 - rychlost) >= this.blok.getY() && y + rychlost <= this.blok.getY() + this.vyskaBloku);
    }
    
    /**
     * Vrati true alebo false podla toho ci je blok zniciteny
     * @return true / false
     */
    public boolean jeZniceny() {
        return this.zniceny;
    }
    
    /**
     * Znici (skryje) blok
     */
    public void znicSa() {
        if (!this.zniceny) {
            this.zniceny = true;
            this.blok.skry();
        }
    }
    
    /**
     * Zobrazi blok
     */
    public void zobrazSa() {
        this.zniceny = false;
        this.blok.zobraz();
    }
    
    /**
     * Zmeni farbu bloku
     * @param farba nova farba na ktoru chceme bloku nastavit
     */
    public void zmenFarbu(String farba) {
        this.blok.zmenFarbu(farba);
        this.farba = farba;
    }
    
    /**
     * Pri kazdom volani postupne meni farbu bloku
     */
    public void posunFarbu() {
        if (this.aktFarba < 5) {
            this.aktFarba++;
        } else {
            this.aktFarba = 0;
        }
        this.farba = this.farby[this.aktFarba];
        this.blok.zmenFarbu(this.farba);
        this.blok.zobraz();
    }
    
    /**
     * Vrati znak podla typu a farby bloku
     * @return znak reprezentujuci dany blok
     */
    public String dajString() {
        String znak = "";
        if (!this.zniceny) {
            switch (this.farba) {
                case "black": 
                    znak = "x";
                    break;
                case "blue":
                    znak = "b";
                    break;
                case "green":
                    znak = "g";
                    break;
                case "red":
                    znak = "r";
                    break;
                case "yellow":
                    znak = "y";
                    break;
                case "darkGray":
                    znak = "d";
                    break;
                default:
                    znak = "x";
            }
        } else {
            znak = " ";
        }
        return znak;
    }
}
