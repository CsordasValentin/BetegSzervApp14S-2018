package hu.petrik.betegszervapp;

public class Beteg {
    private long id;
    private String nev;
    private String taj;
    private String szerv;
    private String tipus;
    private long szerv_id;

    public Beteg(long id, String nev, String taj, String szerv, String tipus, long szerv_id) {
        this.id = id;
        this.nev = nev;
        this.taj = taj;
        this.szerv = szerv;
        this.tipus = tipus;
        this.szerv_id = szerv_id;
    }

    @Override
    public String toString() {
        return nev + " - " + taj;
    }
}
