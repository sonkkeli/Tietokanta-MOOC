package lisays;

import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LisaysJdbcSovellus {

    public static void main(String[] args) throws Exception {
        // Toteuta uuden henkilön lisääminen tietokantatauluun Henkilo tänne
        
        Scanner lukija = new Scanner(System.in);
        
        System.out.println("Minkä niminen henkilö lisätään?");
        String nimi = lukija.nextLine();

        Connection connection = DriverManager.getConnection("jdbc:h2:./henkilotietokanta", "sa", "");
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Henkilo (nimi) VALUES (?)");
        stmt.setString(1, nimi);
        stmt.executeUpdate();
        System.out.println("Lisätään " + nimi);

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
            Logger.getLogger(LisaysJdbcSovellus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
