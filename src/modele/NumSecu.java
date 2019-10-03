package modele;

import exceptions.NumSecuException;

public class NumSecu {
    private int nNumSecu;
    private int sexe;
    private int anneeNaissance;
    private int moisNaissance;
    private int departement;
    private int commune;
    private int ordre;
    private int cle;


    public NumSecu(int sexe, int anneeNaissance, int moisNaissance, int departement, int commune, int ordre)
            throws NumSecuException{
        if (sexe == 1 || sexe == 2)
            this.sexe = sexe;
        else
            throw new NumSecuException("La valeur passée pour le sexe doit être 1 ou 2");

        if (anneeNaissance >= 0 && anneeNaissance <= 99)
            this.anneeNaissance = anneeNaissance;
        else
            throw new NumSecuException("L'année de naissance doit être comprise entre 0 ou 99");

        if (moisNaissance >= 1 && moisNaissance <= 12)
            this.moisNaissance = moisNaissance;
        else
            throw new NumSecuException("Le mois de naissance doit être compris entre 1 et 12");
        if (departement > 0 && departement < 96)
            this.departement = departement;
        else
            throw new NumSecuException("Le départment doit être compris entre 1 et 95");

        if (commune > 0 && commune <= 990)
            this.commune = commune;
        else
            throw new NumSecuException("La commune doit être comprise entre 1 et 990");

        if (ordre > 0 && ordre <= 999)
            this.ordre = ordre;
        else
            throw new NumSecuException("L'ordre doit être compris entre 1 et 999");
        String s = Integer.toString(sexe) +
                String.format("%02d", anneeNaissance) +
                String.format("%02d", moisNaissance) +
                String.format("%02d", departement) +
                String.format("%03d", commune) +
                String.format("%03d", ordre);
        long cleCalc = 97 - (Long.parseLong(s) % 97);
        this.cle = (int) cleCalc;
    }

    public NumSecu(int nNumSecu, int sexe, int anneeNaissance, int moisNaissance, int departement,
                   int commune, int ordre, int cle) throws NumSecuException {
        this.nNumSecu = nNumSecu;
        if (sexe == 1 || sexe == 2)
            this.sexe = sexe;
        else
            throw new NumSecuException("La valeur passée pour le sexe doit être 1 ou 2");

        if (anneeNaissance >= 0 && anneeNaissance <= 99)
            this.anneeNaissance = anneeNaissance;
        else
            throw new NumSecuException("L'année de naissance doit être comprise entre 0 ou 99");

        if (moisNaissance >= 1 && moisNaissance <= 12)
            this.moisNaissance = moisNaissance;
        else
            throw new NumSecuException("Le mois de naissance doit être compris entre 1 et 12");
        if (departement > 0 && departement < 96)
            this.departement = departement;
        else
            throw new NumSecuException("Le départment doit être compris entre 1 et 95");

        if (commune > 0 && commune <= 990)
            this.commune = commune;
        else
            throw new NumSecuException("La commune doit être comprise entre 1 et 990");

        if (ordre > 0 && ordre <= 999)
            this.ordre = ordre;
        else
            throw new NumSecuException("L'ordre doit être compris entre 1 et 999");

        String s = Integer.toString(sexe) +
                String.format("%02d", anneeNaissance) +
                String.format("%02d", moisNaissance) +
                String.format("%02d", departement) +
                String.format("%03d", commune) +
                String.format("%03d", ordre);
        long cleCalc = 97 - (Long.parseLong(s) % 97);
        if (cleCalc == cle)
            this.cle = cle;
        else
            throw new NumSecuException("La clé ne correspond pas aux valeurs précédement entrées");
    }

    public int getSexe() {
        return sexe;
    }

    public int getAnneeNaissance() {
        return anneeNaissance;
    }

    public int getMoisNaissance() {
        return moisNaissance;
    }

    public int getDepartement() {
        return departement;
    }

    public int getCommune() {
        return commune;
    }

    public int getOrdre() {
        return ordre;
    }

    public int getCle() {
        return cle;
    }

    public int getnNumSecu() {
        return nNumSecu;
    }

    @Override
    public String toString() {
        return "NumSecu{" +
                "nNumSecu=" + nNumSecu +
                ", sexe=" + sexe +
                ", anneeNaissance=" + anneeNaissance +
                ", moisNaissance=" + moisNaissance +
                ", departement=" + departement +
                ", commune=" + commune +
                ", ordre=" + ordre +
                ", cle=" + cle +
                '}';
    }
}
