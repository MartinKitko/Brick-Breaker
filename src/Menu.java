
/**
 * Uvodne menu ktore sa zobrazi pred spustenim hry
 * 
 * @author Martin Kitko 
 * @version 20.12.2020
 */
public class Menu {
    private Obrazok obrazok;
    private String aktObrazok;
    private int aktLevel;
    private boolean jeZobrazene;
    /**
     * Konstruktor triedy Menu
     */
    public Menu() {
        this.obrazok = new Obrazok("src/obrazky/uvod1.png");
        this.aktObrazok = "uvod";
        this.aktLevel = 1;
        this.jeZobrazene = true;
    }
    
    /**
     * Vrati true alebo false podla toho ci je menu zobrazene
     * @return true / false, atribut jeZobrazene
     */
    public boolean jeZobrazene() {
        return this.jeZobrazene;
    }
    
    /**
     * Vrati aktualny level
     * @return cislo aktualneho levelu
     */
    public int getAktLevel() {
        return this.aktLevel;
    }
    
    /**
     * Zobrazi menu
     */
    public void zobraz() {
        this.obrazok.zobraz();
        this.jeZobrazene = true;
    }
    
    /**
     * Zobrazi menu s novym obrazkom zadanym ako parameter
     * @param nazov obrazku ktory sa ma zobrazit
     */
    public void zobraz(String nazov) {
        this.obrazok.zmenObrazok("src/obrazky/" + nazov + ".png");
        this.obrazok.zobraz();
        this.jeZobrazene = true;
    }
    
    /**
     * Skryje menu
     */
    public void skry() {
        this.obrazok.skry();
        this.jeZobrazene = false;
    }
    
    /**
     * Pri stlaceni pravej sipky na klavenici posunie oznacenie levelu vpravo
     */
    public void posunVpravo() {
        if (this.aktLevel == 7) {
            this.aktLevel = 1;
        } else {
            this.aktLevel++;
        }
        this.aktualizujObrazok();
    }
    
    /**
     * Pri stlaceni lavej sipky na klavenici posunie oznacenie levelu vlavo
     */
    public void posunVlavo() {
        if (this.aktLevel == 1) {
            this.aktLevel = 7;
        } else {
            this.aktLevel--;
        }
        this.aktualizujObrazok();
    }
    
    /**
     * Pri stlaceni sipky dole posunie oznacenie levelu na spodny riadok
     */
    public void posunDole() {
        this.aktLevel = 6;
        this.aktualizujObrazok();
    }
    
    /**
     * Pri stlaceni sipky hore posunie oznacenie levelu na spodny horny
     */
    public void posunHore() {
        this.aktLevel = 1;
        this.aktualizujObrazok();
    }
    
    /**
     * Pri kliknuti lavym tlacidlom mysi otvor a zatvor informacie
     * @param x aktualna x-ova suradnica kurzora mysi
     * @param y aktualna y-ova suradnica kurzora mysi
     */
    public void lavyKlik(int x, int y) {
        if (this.obrazok.jeViditelny() && x > 750 && y < 50) {
            if (!this.aktObrazok.equals("info")) {
                this.obrazok.zmenObrazok("src/obrazky/info.png");
                this.aktObrazok = "info";
            } else if (this.aktObrazok.equals("info")) {
                this.obrazok.zmenObrazok("src/obrazky/uvod" + this.aktLevel + ".png");
                this.aktObrazok = "uvod";
            }
        }
    }
    
    /**
     * Zmeni obrazok podla aktulne vybraneho levelu
     */
    private void aktualizujObrazok() {
        this.obrazok.zmenObrazok("src/obrazky/uvod" + this.aktLevel + ".png");
    }
}
