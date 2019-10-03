package main;

import metier.RequeteAssurance;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            RequeteAssurance r = new RequeteAssurance();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
