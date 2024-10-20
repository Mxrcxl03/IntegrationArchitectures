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

        private MongoCollection<Document> salesmencollection;
        private MongoCollection<Document> socialperformancecollection;

        public ManagePersonalImpl(MongoDatabase supermongo) {
            salesmencollection = supermongo.getCollection("SalesManCollection");
            socialperformancecollection = supermongo.getCollection("SocialPerformanceCollection");
        }
        @Override
        public void createSalesMan(SalesMan salesMan) {
            Document document = new Document();
            document.append("firstname" , salesMan.getFirstname());
            document.append("lastname" , salesMan.getLastname());
            document.append("sid" , salesMan.getId());
            // ... now storing the object
            salesmencollection.insertOne(document);
            System.out.println("Inserted document: " + document.toJson()); // Debugging-Ausgabe
        }

        @Override
        public void addSocialPerformanceRecord(SocialPerformanceRecord record, SalesMan salesMan) {
            record.setgoalId(salesMan.getId());
            Document document = new Document();
            document.append("goalId" , record.getgoalId());
            document.append("goalDesc", record.getgoalDesc());
            document.append("value" , record.getvalue());
            document.append("actualValue" , record.getactualValue());
            document.append("year" , record.getYear());
            socialperformancecollection.insertOne(document);
            System.out.println("Inserted document: " + document.toJson());
        }

        /* Mit dieser Struktur gibt es keine separate update-Methode,
        da die Methode addSocialPerformanceRecord bereits die Funktionalität bietet,
        um neue Leistungsaufzeichnungen für bestehende Verkaufsmitarbeiter hinzuzufügen.
        Die Verknüpfung zwischen Verkaufsmitarbeitern und ihren Leistungsaufzeichnungen bleibt klar und übersichtlich. */

        @Override
        public SalesMan readSalesMan(int sid) {
            Document doc = salesmencollection.find(new Document("sid", sid)).first();
            return doc != null ? new SalesMan(doc.getString("firstname"), doc.getString("lastname"), doc.getInteger("sid")) : null;
        }

        @Override
        public List<SalesMan> readAllSalesMen() {
            List<SalesMan> salesMenList = new ArrayList<>();
            for (Document doc : salesmencollection.find()) {
                salesMenList.add(new SalesMan(doc.getString("firstname"), doc.getString("lastname"), doc.getInteger("sid")));
            }
            return salesMenList;
        }

        @Override
        public List<SocialPerformanceRecord> readSocialPerformanceRecord(SalesMan salesMan) {
            List<SocialPerformanceRecord> records = new ArrayList<>();
            for (Document doc : socialperformancecollection.find(new Document("goalId", salesMan.getId()))) {
                records.add(new SocialPerformanceRecord(doc.getInteger("goalId"), doc.getString("goalDesc"), doc.getInteger("year"), doc.getInteger("value"), doc.getInteger("actualValue")));
            }
            return records;
        }

        @Override
        public void deleteSalesMan(int sid) {
            salesmencollection.deleteOne(new Document("sid", sid));
        }

        @Override
        public void deleteSocialPerformanceRecord(SocialPerformanceRecord record, SalesMan salesMan) {
            socialperformancecollection.deleteOne(
                    new Document("year", record.getYear()).append("goalId", record.getgoalId()).append("goalDesc", record.getgoalDesc())
                    .append("value", record.getvalue()).append("actualValue", record.getactualValue())); // salesMan wird benötigt, um die Zuordnung herzustellen
        }

        public void deleteAll() {
            salesmencollection.deleteMany(new Document());
            socialperformancecollection.deleteMany(new Document());
        }
}

