package metier;

import exceptions.ClientException;
import exceptions.NumSecuException;
import modele.Client;
import modele.NumSecu;
import modele.Risque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequeteAssurance {

    private Connection conn;

    public RequeteAssurance() throws SQLException, ClassNotFoundException {
        this.connect();
    }

    private Client makeClient(ResultSet rs) throws SQLException, NumSecuException, ClientException {
        int nClient = rs.getInt("nClient");
        String nom = rs.getString("nom");
        String prenom = rs.getString("prenom");
        String telephone = rs.getString("telephone");
        double revenu = rs.getFloat("revenu");
        // NUMSECU
        int nNumSecu = rs.getInt("nNumsecu");
        int sexe = rs.getInt("sexe");
        int anneeNaissance = rs.getInt("anneeNaissance");
        int moisNaissance = rs.getInt("moisNaissance");
        int departement = rs.getInt("departement");
        int commune = rs.getInt("commune");
        int ordre = rs.getInt("ordre");
        int cle = rs.getInt("cle");
        NumSecu secu = new NumSecu(nNumSecu, sexe, anneeNaissance, moisNaissance, departement, commune, ordre, cle);

        // RISQUE
        int nRisque = rs.getInt("nRisque");
        int risque = rs.getInt("niveau");
        Risque risqueC = new Risque(nRisque, risque);
        // Création du client
        return new Client(nClient, nom, prenom, telephone, revenu, secu, risqueC);
    }

    private void connect() throws ClassNotFoundException, SQLException {
        String driverClass = "org.postgresql.Driver";
        String url = "jdbc:postgresql://localhost:5432/Assurance";
        String user = "postgres";
        String password = "root";
        Class.forName(driverClass);
        this.conn = DriverManager.getConnection(url, user, password);
    }

    private int ajouteNumSecu(NumSecu s) throws SQLException {
        //language=PostgresPLSQL
        String sql = "Insert into numsecu (sexe, anneenaissance, moisnaissance, departement, commune, ordre, cle) " +
                "VALUES (" + s.getSexe() + ", " + s.getAnneeNaissance() + "," + s.getMoisNaissance() + ", " + s.getDepartement()
                + ", " + s.getCommune() + ", " + s.getOrdre() + ", " + s.getCle() + ")";
        PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pst.executeUpdate();
        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public List<Risque> ensRisques() throws SQLException {
        ArrayList<Risque> risqueList = new ArrayList<>();
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM risque");
        while (rs.next()) {
            risqueList.add(new Risque(rs.getInt("nRisque"), rs.getInt("niveau")));
        }
        rs.close();
        s.close();
        return risqueList;
    }

    public List<Client> ensClients() throws SQLException, NumSecuException, ClientException {
        ArrayList<Client> clientsList = new ArrayList<>();
        Statement s = conn.createStatement();
        String sql = "SELECT nclient,nom,prenom,c.nnumsecu,telephone,revenu,c.nrisque,sexe,anneenaissance,moisnaissance,departement,commune,ordre,cle,niveau " +
                "from client c,numsecu n,risque r " +
                "where c.nnumsecu = n.nnumsecu " +
                "and r.nrisque =c.nrisque";
        ResultSet rs = s.executeQuery(sql);
        while (rs.next()) {
            clientsList.add(makeClient(rs));
        }
        rs.close();
        s.close();
        return clientsList;
    }

    public List<Client> ensClients(String nomprenom) throws SQLException, NumSecuException, ClientException {
        ArrayList<Client> clientList = new ArrayList<>();
        String preparedSql = "SELECT nclient,nom,prenom,c.nnumsecu,telephone,revenu,c.nrisque,sexe,anneenaissance,moisnaissance,departement,commune,ordre,cle,niveau " +
                "from client c,numsecu n,risque r " +
                "where c.nnumsecu = n.nnumsecu " +
                "and r.nrisque =c.nrisque " +
                "and (UPPER(nom) LIKE (?)" +
                "or UPPER(prenom) LIKE (?))";
        nomprenom = '%' + nomprenom.toUpperCase() + '%';
        PreparedStatement ps = this.conn.prepareStatement(preparedSql);
        ps.setString(1, nomprenom);
        ps.setString(2, nomprenom);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            clientList.add(makeClient(rs));
        }
        rs.close();
        ps.close();
        return clientList;
    }

    public boolean ajouterClient(Client c) throws SQLException {
        int nSecu = ajouteNumSecu(c.getNumSecu());
        String sql = "INSERT INTO client (\"nom\", \"prenom\", \"nnumsecu\", \"telephone\", \"revenu\", \"nrisque\") " +
                "VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, c.getNom());
        ps.setString(2, c.getPrenom());
        ps.setInt(3, nSecu);
        ps.setString(4, c.getTelephone());
        ps.setFloat(5, (float) c.getRevenu());
        ps.setInt(6, c.getRisque().getnRisque());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        return rs.next();
    }

    public boolean supprimerClient(Client c) throws SQLException {

        String sql = "DELETE from numsecu where nnumsecu = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, c.getNumSecu().getnNumSecu());
        int row = ps.executeUpdate();
        return row != 0;
    }

    public boolean modifierClient(Client c) throws SQLException {
        String sql = "UPDATE client set nom = ? ,prenom = ? , telephone = ? , revenu = ? WHERE nclient = ?";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, c.getNom());
        ps.setString(2, c.getPrenom());
        ps.setString(3, c.getTelephone());
        ps.setFloat(4, (float) c.getRevenu());
        ps.setFloat(5, c.getnClient());

        int rows = ps.executeUpdate();
        return rows != 0;
    }

    public static void main(String[] args) {
        RequeteAssurance requeteAssurance = null;
        try {
            requeteAssurance = new RequeteAssurance();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            assert requeteAssurance != null;

            requeteAssurance.supprimerClient(requeteAssurance.ensClients("gre").get(0));
            NumSecu numSecu = new NumSecu(1, 0, 8, 33, 63, 622);
            Client gregoire = new Client("Defoy", "Gregoire", "0660738987", 500.0, numSecu, new Risque(1, 1));
            if (requeteAssurance.ajouterClient(gregoire)) {
                System.out.println("Success");
            } else {
                System.out.println("Echec");
            }
            ArrayList<Client> clients = (ArrayList<Client>) requeteAssurance.ensClients("gre");
            Client c = clients.get(0);
            c.setRevenu(10000.0);
            System.out.println(requeteAssurance.modifierClient(c));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumSecuException | ClientException e) {
              System.out.println(e.getMessage());
        }
    }
}
