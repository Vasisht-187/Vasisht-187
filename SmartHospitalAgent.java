import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SmartHospitalAgent {
    private String dataFile;
    private Map<Integer, Map<String, String>> symptomData;
    private Map<Integer, String> diseaseConditions;

    public SmartHospitalAgent(String dataFile) {
        this.dataFile = dataFile;
        this.symptomData = new HashMap<>();
        this.diseaseConditions = new HashMap<>();
    }

    public void collectSymptoms() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(dataFile));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int patientId = Integer.parseInt(parts[0]);
            Map<String, String> symptoms = new HashMap<>();
            for (int i = 1; i < parts.length; i++) {
                symptoms.put("Symptom_" + i, parts[i].trim());
            }
            symptomData.put(patientId, symptoms);
        }
        reader.close();
    }

    public void analyzeSymptoms() {
        for (Map.Entry<Integer, Map<String, String>> entry : symptomData.entrySet()) {
            int patientId = entry.getKey();
            Map<String, String> symptoms = entry.getValue();
            String diseaseStatus = detectDisease(symptoms);
            diseaseConditions.put(patientId, diseaseStatus);
        }
    }

    public String detectDisease(Map<String, String> symptoms) {
        // Define simple rules for disease detection based on symptoms
        // For demonstration, let's assume if any symptom is 'severe', the disease is 'critical'
        if (symptoms.containsValue("severe")) {
            return "critical";
        } else {
            return "normal";
        }
    }

    public Map<Integer, String> generatePatientReport() {
        return diseaseConditions;
    }

    public static void main(String[] args) {
        String dataFile = "patientSymptoms.csv";
        SmartHospitalAgent agent = new SmartHospitalAgent(dataFile);
        try {
            agent.collectSymptoms();
            agent.analyzeSymptoms();
            Map<Integer, String> patientReport = agent.generatePatientReport();
            System.out.println("Patient Report:");
            for (Map.Entry<Integer, String> entry : patientReport.entrySet()) {
                System.out.println("Patient ID: " + entry.getKey() + ", Disease: " + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
