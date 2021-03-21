
/**
 * Trieda Lopticka ktora predstavuje lopticku ktora sa pohybuje po obrazovke
 * 
 * @author Martin Kitko
 * @version 20.12.2020
 */
public class Lopticka {
    private Kruh lopticka;
    
    /**
     * Konstruktor triedy Lopticka ktory vytvori lopticku
     */
    public Lopticka() {
        this.lopticka = new Kruh();
        this.lopticka.zmenFarbu("yellow");
        this.lopticka.posunNa(390, 435);
    }
    
    /**
     * Vrati velkost lopticky
     * @return priemer lopticky
     */
    public int getVelkost() {
        return this.lopticka.getVelkost();
    }
    
    /**
     * Vrati x-ovu suradnicu lopticky
     * @return x-ova suradnica lopticky
     */
    public double getX() {
        return this.lopticka.getX();
    }
    
    /**
     * Vrati y-ovu suradnicu lopticky
     * @return y-ova suradnica lopticky
     */
    public double getY() {
        return this.lopticka.getY();
    }
    
    /**
     * Zobrazi lopticku
     */
    public void zobraz() {
        this.lopticka.zobraz();
    }
    
    /**
     * Posunie lopticku o zadane hodnoty
     * @param x hodnota o ktoru sa ma posunut na x-ovej osi
     * @param y hodnota o ktoru sa ma posunut na y-ovej osi
     */
    public void posun(double x, double y) {
        try {
            this.lopticka.posun(x, y);
        } catch (java.util.ConcurrentModificationException exception) {
        }
    }
    
    /**
     * Posunie plosinu na pociatocnu poziciu
     */
    public void resetujPolohu() {
        this.lopticka.posunNa(390, 435);
    }
}
