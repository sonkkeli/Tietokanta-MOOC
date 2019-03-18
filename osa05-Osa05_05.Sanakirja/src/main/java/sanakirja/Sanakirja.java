package sanakirja;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sanakirja {

    @Autowired
    private JdbcTemplate jbtemp;

    public void lisaa(String sana, String kaannos) {
        jbtemp.update("INSERT INTO Sanasto (sana, kaannos) VALUES (?, ?)", sana, kaannos);
        
    }

    public String kaanna(String sana) {
        List<String> kaannokset = jbtemp.query(
                "SELECT * FROM Sanasto WHERE sana = ?",
                (rs, rowNum) -> rs.getString("kaannos"),
                sana);
        
        if (kaannokset.size()>0){
            return kaannokset.get(0);
        }
        
        return null;
    }

    public int sanojenLukumaara() {
        List<String> lkm = jbtemp.query(
                "SELECT COUNT(sana) FROM Sanasto;",
                (rs, rowNum) -> rs.getString("COUNT(sana)"));
        
        int lukumaara = Integer.valueOf(lkm.get(0));
        return lukumaara;
    }

    public List<String> kaannoksetListana() {
        List<String> lista = new ArrayList<>();

        lista = jbtemp.query(
                "SELECT * FROM Sanasto;",
                (rs, rowNum) -> rs.getString("sana")+ " = " + rs.getString("kaannos"));

        return lista;
    }
}
