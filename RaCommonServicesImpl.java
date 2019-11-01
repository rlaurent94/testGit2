package runningAnalyse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import runningAnalyse.model.RaLieuSeanceData;
import runningAnalyse.model.RaMeteoSeanceData;
import runningAnalyse.model.RaTypeSeanceData;
import runningAnalyse.repository.RaLieuSeanceRepo;
import runningAnalyse.repository.RaMeteoSeanceRepo;
import runningAnalyse.repository.RaTypeSeanceRepo;

import java.util.List;

@Service
public class RaCommonServicesImpl implements RaCommonServices {

    private RaTypeSeanceRepo typeSeanceRepo;
    private RaLieuSeanceRepo lieuSeanceRepo;
    private RaMeteoSeanceRepo meteoSeanceRepo;
	// duplication pour test git ( depuis testGit2 )

    @Autowired
    public RaCommonServicesImpl(RaTypeSeanceRepo _typeSeanceRepo, RaLieuSeanceRepo _lieuSeanceRepo, RaMeteoSeanceRepo _meteoSeanceRepo) {
        typeSeanceRepo= _typeSeanceRepo;
        lieuSeanceRepo= _lieuSeanceRepo;
        meteoSeanceRepo =_meteoSeanceRepo;
    }

    public List<RaTypeSeanceData> getTypeSeance() {
        return typeSeanceRepo.findAllByOrderByTypeSeance();
    }

    public List<RaLieuSeanceData> getLieuSeance() {
        return lieuSeanceRepo.findAllByOrderByLieuSeance();
    }

    public List<RaMeteoSeanceData> getMeteoSeance() { return meteoSeanceRepo.findAllByOrderByMeteoSeance(); }
}
