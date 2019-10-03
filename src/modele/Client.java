package modele;

import exceptions.ClientException;

public class Client {

    private int nClient;
    private String nom;
    private String prenom;
    private String telephone;
    private double revenu;
    private NumSecu numSecu;
    private Risque risque;

    public Client(int nClient, String nom, String prenom, String telephone,
                  double revenu, NumSecu numSecu, Risque risque) throws ClientException {
        this.nClient = nClient;
        if (nom == null || nom.equals("") ) throw new ClientException("Le nom ne doit pas être nul");
        this.nom = nom;
        if (prenom == null || prenom.equals("") ) throw new ClientException("Le prénom ne doit pas être nul");
        this.prenom = prenom;
        this.telephone = telephone;
        if (revenu < 0) throw new ClientException("Le revenu doit être positif");
        this.revenu = revenu;
        if (numSecu == null) throw new ClientException("Le numéro de sécurité sociale doit être renseigné");
        this.numSecu = numSecu;
        this.risque = risque;
    }

    public Client(String nom, String prenom, String telephone, double revenu, NumSecu numSecu, Risque risque) throws ClientException {
        this(-1, nom, prenom, telephone, revenu, numSecu, risque);
    }

    public int getnClient() {
        return nClient;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public double getRevenu() {
        return revenu;
    }

    public NumSecu getNumSecu() {
        return numSecu;
    }

    public Risque getRisque() {
        return risque;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setRevenu(double revenu) throws ClientException {
        if (revenu < 0) throw new ClientException("Le revenu doit être positif");
        this.revenu = revenu;
    }

    public void setRisque(Risque risque) {
        this.risque = risque;
    }

    @Override
    public String toString() {
        return "Client{" +
                "nClient=" + nClient +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephone='" + telephone + '\'' +
                ", revenu=" + revenu +
                ", nNumSecu=" + numSecu +
                ", nRisque=" + risque +
                '}' + '\n';
    }
}
