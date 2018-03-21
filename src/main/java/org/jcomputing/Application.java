package org.jcomputing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.*;

@Controller
@EnableAutoConfiguration
public class Application {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/teams")
    @ResponseBody
    String teams() {
        String query = "select * from 2018_team";
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        StringBuilder builder = new StringBuilder();

        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection("jdbc:mysql://ncaatournamentpool.cvksgnszabxj.us-east-2.rds.amazonaws.com:3306/ebdb",
                    "user", "password");
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                builder.append(rs.getString("name") + "<br />");
            }
        } catch (SQLException sqlEx) { sqlEx.printStackTrace(); }
        finally { //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        return builder.toString();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}

