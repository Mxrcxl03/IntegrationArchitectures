package de.hbrs.ia.code;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import de.hbrs.ia.model.SalesMan;
import de.hbrs.ia.model.SocialPerformanceRecord;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManagePersonalImplTest {

    private ManagePersonalImpl managePersonal;
    private MongoClient client;
    private MongoDatabase supermongo;

    @BeforeEach
    void setUp() {
        client = new MongoClient("localhost", 27017);
        supermongo = client.getDatabase("SmartHooverLTD");
        managePersonal = new ManagePersonalImpl(supermongo);
    }

    @AfterEach
    void tearDown() {
        managePersonal.deleteAll(); // Löscht alle Dokumente in den Collections, um die Datenbank für den nächsten Test zu leeren
        //System.out.println(managePersonal);
        managePersonal = null; // Setzt die Instanz auf null, um Speicher freizugeben
    }
    // Testet die Erstellung eines SalesMan und ob die Daten korrekt gespeichert und abgerufen werden können
    @Test
    void createSalesMan() {
        SalesMan salesMan = new SalesMan("John", "Doe", 12345);
        managePersonal.createSalesMan(salesMan);
        Document doc = managePersonal.readSalesMan(salesMan.getId()).toDocument(); // Abrufen des SalesMan-Dokuments
        //System.out.println("Retrieved document: " + (doc != null ? doc.toJson() : "null"));
        Assertions.assertNotNull(doc); // Sicherstellen, dass das Dokument nicht null ist
        assertEquals("John", doc.getString("firstname")); // Überprüfen, ob der Vorname korrekt ist
        assertEquals("Doe", doc.getString("lastname"));
    }

    // Testet das Hinzufügen eines SocialPerformanceRecord zu einem bestimmten SalesMan
    @Test
    void addSocialPerformanceRecord() {
        SalesMan salesMan = new SalesMan("Jane", "Doe", 54321);
        SalesMan salesMan2 = new SalesMan("Yannick", "Doe", 55555);
        managePersonal.createSalesMan(salesMan);
        managePersonal.createSalesMan(salesMan2);

        SocialPerformanceRecord record = new SocialPerformanceRecord(54321, "Leadership Competence", 2024, 100, 2); // Erstellen eines neuen SocialPerformanceRecord
        SocialPerformanceRecord record2 = new SocialPerformanceRecord(55555, "Leadership Competence", 2024, 100, 2);
        managePersonal.addSocialPerformanceRecord(record, salesMan);
        managePersonal.addSocialPerformanceRecord(record2, salesMan);

        List<SocialPerformanceRecord> records = managePersonal.readSocialPerformanceRecord(salesMan); // Abrufen aller SocialPerformanceRecords des SalesMan
        //System.out.println(records);
        assertEquals(2, records.size()); // Überprüfen, ob zwei Records existiert
        assertEquals(2024, records.get(0).getYear()); // Überprüfen, ob das Jahr korrekt ist
        assertEquals(100, records.get(0).getvalue());
        assertEquals(2, records.get(0).getactualValue());
        assertEquals(54321, records.get(0).getgoalId());
        assertEquals(54321, records.get(1).getgoalId());
        assertEquals("Leadership Competence", records.get(0).getgoalDesc());
    }

    // Testet das Abrufen eines SalesMan anhand seiner ID
    @Test
    void readSalesMan() {
        SalesMan salesMan = new SalesMan("Alice", "Smith", 67890);
        managePersonal.createSalesMan(salesMan);

        SalesMan retrievedSalesMan = managePersonal.readSalesMan(67890);
        Assertions.assertNotNull(retrievedSalesMan);
        assertEquals("Alice", retrievedSalesMan.getFirstname());
        assertEquals("Smith", retrievedSalesMan.getLastname());
    }

    // Testet das Abrufen aller SalesMen in der Datenbank
    @Test
    void readAllSalesMen() {
        SalesMan salesMan1 = new SalesMan("Bob", "Brown", 11111);
        SalesMan salesMan2 = new SalesMan("Eve", "Black", 22222);
        managePersonal.createSalesMan(salesMan1);
        managePersonal.createSalesMan(salesMan2);

        List<SalesMan> allSalesMen = managePersonal.readAllSalesMen();
        assertEquals(2, allSalesMen.size());
    }

    // Testet das Abrufen von SocialPerformanceRecords eines bestimmten SalesMan
    @Test
    void readSocialPerformanceRecord() {
        SalesMan salesMan = new SalesMan("Charlie", "Green", 33333);
        managePersonal.createSalesMan(salesMan);

        SocialPerformanceRecord record = new SocialPerformanceRecord(33333, "Attitude towards Client", 2024, 150,2);
        managePersonal.addSocialPerformanceRecord(record, salesMan);

        List<SocialPerformanceRecord> records = managePersonal.readSocialPerformanceRecord(salesMan);
        assertEquals(1, records.size());
        assertEquals(2024, records.get(0).getYear());
        assertEquals(150, records.get(0).getvalue());
        assertEquals(2, records.get(0).getactualValue());
        assertEquals("Attitude towards Client", records.get(0).getgoalDesc());
        assertEquals(33333, records.get(0).getgoalId());
    }

    // Testet das Löschen eines SalesMan anhand seiner ID
    @Test
    void deleteSalesMan() {
        SalesMan salesMan = new SalesMan("David", "White", 44444);
        managePersonal.createSalesMan(salesMan);

        managePersonal.deleteSalesMan(44444); // Löschen des SalesMan anhand seiner ID
        Assertions.assertNull(managePersonal.readSalesMan(44444)); // Überprüfen, ob der SalesMan tatsächlich gelöscht wurde (null ist)
    }

    // Testet das Löschen eines SocialPerformanceRecord für einen bestimmten SalesMan
    @Test
    void deleteSocialPerformanceRecord() {
        SalesMan salesMan = new SalesMan("Frank", "Blue", 55555);
        managePersonal.createSalesMan(salesMan);

        SocialPerformanceRecord record = new SocialPerformanceRecord(55555, "Openness to Employee",2024, 200,2);
        managePersonal.addSocialPerformanceRecord(record, salesMan);

        managePersonal.deleteSocialPerformanceRecord(record, salesMan);
        List<SocialPerformanceRecord> records = managePersonal.readSocialPerformanceRecord(salesMan);
        assertEquals(0, records.size());
    }
}