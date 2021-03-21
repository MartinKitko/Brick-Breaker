//import java.awt.MouseInfo;
/**
 * Trieda Plosina predstavuje odrazova plosinu ktora sluzi na odrazanie lopticky
 * 
 * @author Martin Kitko 
 * @version 20.12.2020
 */
public class Plosina {
    private Obdlznik plosina;
    private int sirkaPlosiny;
    private int vyskaPlosiny;
    /**
     * Konstruktor triedy Plosina ktory vytvara plosinu
     */
    public Plosina() {
        this.plosina = new Obdlznik();
        this.sirkaPlosiny = 120;
        this.vyskaPlosiny = 10;
        this.plosina.zmenStrany(this.sirkaPlosiny, this.vyskaPlosiny);
        this.plosina.zmenFarbu("black");
        this.plosina.posunVodorovne(285);
        this.plosina.posunZvisle(430);
    }
    
    /**
     * Zobrazi plosinu
     */
    public void zobraz() {
        this.plosina.zobraz();
    }
    
    /**
     * Pri stlaceni pravej sipky na klavesnici posunie plosinu vpravo
     */
    public void posunVpravo() {
        if (this.plosina.getX() + this.sirkaPlosiny > 770 && 
            this.plosina.getX() + this.sirkaPlosiny < 800) {
            this.plosina.posunVodorovne(800 - this.plosina.getX() - this.sirkaPlosiny);
        } else if (this.plosina.getX() + this.sirkaPlosiny <= 770) {
            this.plosina.posunVodorovne(30);
        }
    }
    
    /**
     * Pri stlaceni lavej sipky na klavesnici posunie plosinu vlavo
     */
    public void posunVlavo() {
        if (this.plosina.getX() < 30 && this.plosina.getX() > 0) {
            this.plosina.posunVodorovne(-this.plosina.getX());
        } else if (this.plosina.getX() >= 30) {
            this.plosina.posunVodorovne(-30);
        }
    }
    
    /**
     * Posunie plosinu na pociatocnu poziciu
     */
    public void resetujPolohu() {
        this.plosina.posunVodorovneNa(345);
    }
    
    /**
     * Kontroluje ci plosina obsahuje suradnice zadane ako parametre
     * @param  x  x-ova suradnica
     * @param  y  y-ova suradnica
     */
    public boolean obsahujeSuradnice(double x, double y) {
        return x + 30 > this.plosina.getX() && x < this.plosina.getX() + this.sirkaPlosiny &&
               y + 30 > this.plosina.getY() && y + 30 < this.plosina.getY() + this.vyskaPlosiny;
    }
    
    /**
     * Sluzi pre ovladanie plosiny pohybom mysi
     */
    /*public void tik() {
        this.plosina.posunVodorovneNa((int) MouseInfo.getPointerInfo().getLocation().getX() - this.sirkaPlosiny / 2);
    }*/
}
