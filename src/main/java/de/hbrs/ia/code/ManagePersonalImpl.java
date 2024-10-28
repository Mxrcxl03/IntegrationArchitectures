package de.hbrs.ia.code;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.hbrs.ia.model.SalesMan;
import de.hbrs.ia.model.SocialPerformanceRecord;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class ManagePersonalImpl implements ManagePersonal {
        // MongoDB-Sammlungen
        private MongoCollection<Document> salesmencollection;
        private MongoCollection<Document> socialperformancecollection;

        public ManagePersonalImpl(MongoDatabase supermongo) {
            salesmencollection = supermongo.getCollection("SalesManCollection");
            socialperformancecollection = supermongo.getCollection("SocialPerformanceCollection");
        }

        // Methode zum Erstellen eines neuen SalesMan-Dokuments in der Datenbank
        @Override
        public void createSalesMan(SalesMan salesMan) {
            Document filter = new Document("sid",salesMan.getId());
            Document salesman = salesmencollection.find(filter).first();
            if (salesman != null) {System.out.println("salesman with sid" + salesMan.getId() + "already exists");
            return ;}
            Document document = salesMan.toDocument();
            // Speichern des Dokuments in der Collection
            salesmencollection.insertOne(document);
            System.out.println("Inserted document: " + document.toJson()); // Debugging-Ausgabe zur Bestätigung
        }

        // Methode zum Hinzufügen einer SocialPerformanceRecord für einen bestimmten SalesMan
        @Override
        public void addSocialPerformanceRecord(SocialPerformanceRecord record, SalesMan salesMan) {
            record.setgoalId(salesMan.getId());
            salesMan.addSPR(record);
            Document document = record.toDocument();
            socialperformancecollection.insertOne(document);
            System.out.println("Inserted document: " + document.toJson()); // Debugging-Ausgabe
        }

        /* Mit dieser Struktur gibt es keine separate update-Methode,
        da die Methode addSocialPerformanceRecord bereits die Funktionalität bietet,
        um neue Leistungsaufzeichnungen für bestehende Verkaufsmitarbeiter hinzuzufügen.
        Die Verknüpfung zwischen Verkaufsmitarbeitern und ihren Leistungsaufzeichnungen bleibt klar und übersichtlich. */

        // Methode zum Lesen eines bestimmten SalesMan anhand der ID (sid)
        @Override
        public SalesMan readSalesMan(int sid) {
            Document doc = salesmencollection.find(new Document("sid", sid)).first();
            // Überprüfen, ob das Dokument existiert, und ein SalesMan-Objekt zurückgeben
            return doc != null ? new SalesMan(doc.getString("firstname"), doc.getString("lastname"), doc.getInteger("sid")) : null;
        }

         // Methode zum Lesen aller SalesMan-Dokumente in der Collection
        @Override
        public List<SalesMan> readAllSalesMen() {
            List<SalesMan> salesMenList = new ArrayList<>();
            // Für jedes Dokument ein SalesMan-Objekt erstellen und zur Liste hinzufügen
            for (Document doc : salesmencollection.find()) {
                salesMenList.add(new SalesMan(doc.getString("firstname"), doc.getString("lastname"), doc.getInteger("sid")));
            }
            return salesMenList;
        }

        // Methode zum Lesen der SocialPerformanceRecords eines bestimmten SalesMan
        @Override
        public List<SocialPerformanceRecord> readSocialPerformanceRecord(SalesMan salesMan) {
            List<SocialPerformanceRecord> records = new ArrayList<>();
            // Suche nach SocialPerformanceRecords basierend auf der SalesMan-ID
            for (Document doc : socialperformancecollection.find(new Document("goalId", salesMan.getId()))) {
                records.add(new SocialPerformanceRecord(doc.getInteger("goalId"), doc.getInteger("year")));
            }
            return records;
        }

        // Methode zum Löschen eines bestimmten SalesMan-Dokuments anhand der ID (sid)
        @Override
        public void deleteSalesMan(int sid) {
            salesmencollection.deleteOne(new Document("sid", sid)); // Löschen des Dokuments aus der Datenbank
        }

         // Methode zum Löschen eines bestimmten SocialPerformanceRecord für einen SalesMan
        @Override
        public void deleteSocialPerformanceRecord(SocialPerformanceRecord record, SalesMan salesMan) {
            socialperformancecollection.deleteOne(
                    new Document("year", record.getYear()).append("goalId", record.getgoalId())
                    .append("value", record.getvalue()).append("actualValue", record.getactualValue()));// salesMan wird benötigt, um die Zuordnung herzustellen
                    salesMan.delete(record);
        }

        // Methode zum Löschen aller Dokumente aus beiden Sammlungen
        public void deleteAll() {
            salesmencollection.deleteMany(new Document()); // Löschen aller SalesMan-Dokumente
            socialperformancecollection.deleteMany(new Document()); // Löschen aller SocialPerformance-Dokumente
        }
}

//Was will der von mir