package nimet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NimetJdbcSovellus {

    public static void main(String[] args) throws Exception {
        // Toteuta nimien tulostaminen tietokantataulusta Henkilo tänne
        
        // luodaan yhteys jdbc:n yli h2-tietokantaan nimeltä "tietokanta"
        // käyttäjätunnuksena on "sa" ja salasanana tyhjä        
        Connection connection = DriverManager.getConnection("jdbc:h2:./henkilotietokanta", "sa", "");
        
        // luodaan kyely "SELECT * FROM Opiskelija", jolla haetaan
        // kaikki tiedot Opiskelija-taulusta
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Henkilo");
        
        // suoritetaan kysely -- tuloksena resultSet-olio
        ResultSet resultSet = statement.executeQuery();
        
        while(resultSet.next()) {
            String nimi = resultSet.getString("nimi");
            System.out.println(nimi);
        }
        // suljetaan yhteys tietokantaan
        connection.close();
    }

    private static void alustaTietokantaJaLuoNimia() {
        // mikäli poistat vahingossa tietokannan tai teet siihen muutoksia,
        // voit ajaa tämän metodin jolloin tietokantataulu luodaan uudestaan
        // ja siihen lisätään nimiä

        // palvelu http://listofrandomnames.com on tällaisissa varsin näppärä
        String nimet = "('Love'),"
                + "('Bruce'),"
                + "('Ray'),"
                + "('Fidelia'),"
                + "('Celia'),"
                + "('Mimi'),"
                + "('Monique'),"
                + "('Kristan'),"
                + "('Dane'),"
                + "('Anneliese'),"
                + "('Clemencia'),"
                + "('Jen'),"
                + "('Karen'),"
                + "('Tai'),"
                + "('Jesica'),"
                + "('Marya'),"
                + "('Kiesha'),"
                + "('Elidia'),"
                + "('Takisha'),"
                + "('Cleo')";
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./henkilotietokanta", "sa", "")) {
            conn.prepareStatement("DROP TABLE Henkilo IF EXISTS;").executeUpdate();
            conn.prepareStatement("CREATE TABLE Henkilo(id serial, nimi varchar(255));").executeUpdate();
            conn.prepareStatement("INSERT INTO Henkilo (nimi) VALUES " + nimet + ";").executeLargeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(NimetJdbcSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
