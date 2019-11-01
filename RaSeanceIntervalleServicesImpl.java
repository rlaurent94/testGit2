package runningAnalyse.services;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;
import runningAnalyse.model.RaSeanceIntervalleData;
import runningAnalyse.model.RaSeanceResumeData;
// dupliqueé testGit2
import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;
import runningAnalyse.model.RaSeanceIntervalleData;
import runningAnalyse.model.RaSeanceResumeData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
// dupliqué dans testGit2

@Service
public class RaSeanceIntervalleServicesImpl implements RaSeanceIntervalleServices {

    public List<RaSeanceIntervalleData> createIntervallesFromCsv(InputStream _csvIs, RaSeanceResumeData _seanceResume) throws IOException {
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(_csvIs));
        ) {
            List<RaSeanceIntervalleData> result = new ArrayList<>();
            CSVReader csvReader = new CSVReader(br, ',');

            csvReader.readNext();
            String[] fields;
            while ((fields = csvReader.readNext()) != null) {
                RaSeanceIntervalleData newSeanceIntervalle = createSeanceIntervalleFromCsvLine(fields, _seanceResume);
                if (newSeanceIntervalle != null)
                    result.add(newSeanceIntervalle);
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    private RaSeanceIntervalleData createSeanceIntervalleFromCsvLine(String[] _fields, RaSeanceResumeData
            _seanceResume) {
        if (_fields[0].equals("Summary")) return null;

        RaSeanceIntervalleData newSeanceIntervalle = new RaSeanceIntervalleData();
        String splitStr[];

        newSeanceIntervalle.setSeanceResume(_seanceResume);
        newSeanceIntervalle.setNumero(_fields[0]);
        newSeanceIntervalle.setDuree(_fields[1]);
        newSeanceIntervalle.setDistance(_fields[3]);
        newSeanceIntervalle.setGainAltitude(_fields[4]);
        newSeanceIntervalle.setPerteAltitude(_fields[5]);
        newSeanceIntervalle.setAllureMoy(_fields[6]);
        splitStr = _fields[12].split("\\.");
        newSeanceIntervalle.setFcMoy(splitStr[0]); // format csv is like 120.3 => suppress .3
        splitStr = _fields[15].split("\\.");
        newSeanceIntervalle.setCalories(splitStr[0]); // format csv is like 120.3 => suppress .3

        String newId = "";
        newId = String.format("%d-%d-%d", _seanceResume.getSemaine(), _seanceResume.getNumeroSeance(), newSeanceIntervalle.getNumero());

        newSeanceIntervalle.setId(newId);

        return newSeanceIntervalle;
    }

}
