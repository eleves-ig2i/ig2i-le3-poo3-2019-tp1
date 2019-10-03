package modele;

public class Risque {
    private int nRisque;
    private int niveau;

    public Risque(int nRisque, int niveau) {
        this.nRisque = nRisque;
        this.niveau = niveau;
    }

    public int getnRisque() {
        return nRisque;
    }

    @Override
    public String toString() {
        return "Risque{" +
                "nRisque=" + nRisque +
                ", niveau=" + niveau +
                '}';
    }
}
