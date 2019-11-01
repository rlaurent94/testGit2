package runningAnalyse.services;


import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import runningAnalyse.exception.RaExceptionDuplicateSeance;
import runningAnalyse.exception.RaExceptionMissingData;
import runningAnalyse.model.RaMeteoSeanceData;
import runningAnalyse.model.RaSeanceIntervalleData;
import runningAnalyse.model.RaSeanceResumeData;
import runningAnalyse.repository.RaSeanceIntervalleRepo;
import runningAnalyse.repository.RaSeanceResumeRepo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;


@Service()
public class RaSeanceResumeServicesImpl implements RaSeanceResumeServices {

    private RaSeanceResumeRepo seanceResumeRepo;

    private RaSeanceIntervalleRepo seanceIntervalleRepo;

    private RaSeanceIntervalleServices seanceIntervalleServices;

    @Autowired
    public RaSeanceResumeServicesImpl(RaSeanceResumeRepo _seanceResumeRepo, RaSeanceIntervalleRepo _seanceIntervalleRepo,RaSeanceIntervalleServices _seanceIntervalleServices) {
        seanceResumeRepo = _seanceResumeRepo;
        seanceIntervalleRepo = _seanceIntervalleRepo;
        seanceIntervalleServices = _seanceIntervalleServices;
    }


    public List<RaSeanceResumeData> findAllSeanceResume() {
        return seanceResumeRepo.findAllByOrderByIdDesc();
    }


    public RaSeanceResumeData findById(long _id) {
        Optional<RaSeanceResumeData> result = seanceResumeRepo.findById(_id);

        if (result.isPresent())
            return result.get();
        else
            return null;
    }

    /**
     * Create a new seance resume with some values from the last seance resume in data base
     *
     * @return RaSeanceResumeData
     */
    public RaSeanceResumeData newSeanceResume() {
        List<RaSeanceResumeData> allSeanceResumes;
        RaSeanceResumeData lastSeanceResume;
        RaSeanceResumeData result = new RaSeanceResumeData();

        allSeanceResumes = seanceResumeRepo.findAllByOrderBySemaineDesc();
        if (allSeanceResumes.size() > 0) {
            lastSeanceResume = allSeanceResumes.get(0);

            result.setFcMax(lastSeanceResume.getFcMax());
            result.setFcRepos(lastSeanceResume.getFcRepos());
            result.setMeteo(RaMeteoSeanceData.NORMAL);
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public RaSeanceResumeData addSeanceResume(RaSeanceResumeData _seanceResume) {
        saveSeanceResumeInDb(_seanceResume);
        return _seanceResume;
    }

    @Transactional(rollbackFor = Exception.class)
    public RaSeanceResumeData modifySeanceResume(RaSeanceResumeData _seanceResume) {
        saveSeanceResumeInDb(_seanceResume);
        return _seanceResume;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteSeanceResume(long _seanceResumeId) {
        seanceIntervalleRepo.deleteByIdSeanceResume(_seanceResumeId);

        Optional<RaSeanceResumeData> seanceResume = seanceResumeRepo.findById(_seanceResumeId);
        if (seanceResume.isPresent()) {
            seanceResumeRepo.delete(seanceResume.get());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public RaSeanceResumeData updateIntervalleFromCsv(InputStream _csvIs, RaSeanceResumeData _seanceResume) throws IOException {
        List<RaSeanceIntervalleData> intervalles = seanceIntervalleServices.createIntervallesFromCsv(_csvIs, _seanceResume);

        seanceIntervalleRepo.deleteByIdSeanceResume(_seanceResume.getId());
        _seanceResume.setIntervalles(intervalles);
        seanceResumeRepo.save(_seanceResume);

        return _seanceResume;
    }


    @Transactional(rollbackFor = Exception.class)
    public void resetFromCsv(InputStream _csvIs) throws IOException {
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(_csvIs));
        ) {
            CSVReader csvReader = new CSVReader(br, ';');

            String[] fields;
            seanceIntervalleRepo.deleteAll();
            seanceResumeRepo.deleteAll();
            while ((fields = csvReader.readNext()) != null) {
                createSeanceResumeFromCsvLine(fields);
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // ==> added meteo
    private void createSeanceResumeFromCsvLine(String[] _fields) {
        RaSeanceResumeData newSeanceResume = new RaSeanceResumeData();

        int i = 0;
        newSeanceResume.setSemaine(_fields[i++]);
        newSeanceResume.setNumeroSeance(_fields[i++]);
        newSeanceResume.setTypeSeance(_fields[i++]);
        newSeanceResume.setLieu(_fields[i++]);
        newSeanceResume.setPourcentageZone1(_fields[i++]);
        newSeanceResume.setPourcentageZone2(_fields[i++]);
        newSeanceResume.setPourcentageZone3(_fields[i++]);
        newSeanceResume.setPourcentageZone4(_fields[i++]);
        newSeanceResume.setPourcentageZone5(_fields[i++]);
        newSeanceResume.setDistance(_fields[i++]);
        newSeanceResume.setDuree(_fields[i++]);
        newSeanceResume.setVitesseMoy(_fields[i++]);
        newSeanceResume.setAllureMoy(_fields[i++]);
        newSeanceResume.setFcMoy(_fields[i++]);
        newSeanceResume.setCadenceMoy(_fields[i++]);
        newSeanceResume.setLongueurFouleeMoy(_fields[i++]);
        newSeanceResume.setCalories(_fields[i++]);
        newSeanceResume.setTeAerobie(_fields[i++]);
        newSeanceResume.setTeAnaerobie(_fields[i++]);
        newSeanceResume.setFcMax(_fields[i++]);
        newSeanceResume.setFcRepos(_fields[i++]);

        saveSeanceResumeInDb(newSeanceResume);
    }

    private void saveSeanceResumeInDb(RaSeanceResumeData _seanceResume) {

        if (!_seanceResume.checkValue()) throw new RaExceptionMissingData();

        _seanceResume.calculate();

        // new seance resume => calculate a new id
        if (_seanceResume.getId() == 0) {
            long id = _seanceResume.getSemaine() * 100000 + _seanceResume.getNumeroSeance();
            // check if exist
            Optional<RaSeanceResumeData> seance = seanceResumeRepo.findById(id);
            if (seance.isPresent())
                throw new RaExceptionDuplicateSeance();

            _seanceResume.setId(_seanceResume.getSemaine() * 100000 + _seanceResume.getNumeroSeance());
        }
        seanceResumeRepo.save(_seanceResume);
    }
}