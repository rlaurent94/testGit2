package runningAnalyse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import runningAnalyse.exception.RaExceptionSeanceNotFound;
import runningAnalyse.model.RaSeanceIntervalleData;
import runningAnalyse.model.RaSeanceResumeData;
import runningAnalyse.model.RaSeanceStatData;
import runningAnalyse.model.RaStatData;
import runningAnalyse.repository.RaSeanceResumeRepo;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value="RaSeancesStatServicesImpl")
public class RaSeancesStatServicesImpl implements RaSeancesStatServices {

    RaSeanceResumeRepo seanceResumeRepo;

    @Autowired
    public RaSeancesStatServicesImpl(RaSeanceResumeRepo _seanceResumeRepo) {
        seanceResumeRepo = _seanceResumeRepo;
    }
	// duplication pour test git
	@Autowired
    public RaSeancesStatServicesImpl(RaSeanceResumeRepo _seanceResumeRepo) {
        seanceResumeRepo = _seanceResumeRepo;
    }

    public RaSeanceStatData calculateStatSeance(long _seanceId) {
        Optional<RaSeanceResumeData> seanceResume = seanceResumeRepo.findById(_seanceId);

        if (seanceResume.isPresent())
            return calculateStatSeance(seanceResume.get());
        else
            throw new RaExceptionSeanceNotFound(_seanceId);
    }

    public RaSeanceStatData calculateStatSeance(RaSeanceResumeData _seanceResume) {
        RaSeanceStatData result = new RaSeanceStatData();

        result.setPourcentageZone1(_seanceResume.getIntValPourcentageZone1());
        result.setPourcentageZone2(_seanceResume.getIntValPourcentageZone2());
        result.setPourcentageZone3(_seanceResume.getIntValPourcentageZone3());
        result.setPourcentageZone4(_seanceResume.getIntValPourcentageZone4());
        result.setPourcentageZone5(_seanceResume.getIntValPourcentageZone5());


        List<RaStatData> intervallesPerformance = new ArrayList<>();
        if (_seanceResume.getNbIntervalles() > 3) {
            int i = 0;
            List<RaSeanceIntervalleData> intervalles = _seanceResume.getIntervalles();

            for (i = 0; i < _seanceResume.getNbIntervalles() - 2; i++) {
                RaStatData newIntervalle = new RaStatData();
                newIntervalle.setDistance(intervalles.get(i).getDistance());
                newIntervalle.setFcMoy(intervalles.get(i).getFcMoy());
                newIntervalle.setDuree(intervalles.get(i).getDuree());
                newIntervalle.setVitesseMoy(intervalles.get(i).getVitesseMoy());
                newIntervalle.setCalories(intervalles.get(i).getCalories());
                newIntervalle.setRelativeAltitude(intervalles.get(i).getGainAltitude() - intervalles.get(i).getPerteAltitude());
                newIntervalle.setType(RaStatData.TYPE_INTERVALLE);
                newIntervalle.setUnite(RaStatData.UNITE_KM);
                newIntervalle.setSemaine(_seanceResume.getSemaine());
                newIntervalle.calculate(_seanceResume);
                intervallesPerformance.add(newIntervalle);
            }
            RaStatData lasStat = sumIntervalle(intervalles.get(_seanceResume.getNbIntervalles() - 2), intervalles.get(_seanceResume.getNbIntervalles() - 1), _seanceResume);
            lasStat.setType(RaStatData.TYPE_INTERVALLE);
            lasStat.setUnite(RaStatData.UNITE_KM);
            lasStat.setSemaine(_seanceResume.getSemaine());
            intervallesPerformance.add(lasStat);

            result.setSeanceStat(intervallesPerformance);
        }

        return result;
    }

    public RaSeanceStatData calculateStatLastSeances(int _lastSeanceNumber, String _typeSeance, List<String> _excludeMeteoSeance) {
        RaSeanceStatData result = new RaSeanceStatData();

        List<RaSeanceResumeData> seances = seanceResumeRepo.findAllWithSort();
        int nbTotalSeance = seances.size();
        if (_lastSeanceNumber > nbTotalSeance) _lastSeanceNumber = nbTotalSeance - 1;

        int pourcentageZone1 = 0;
        int pourcentageZone2 = 0;
        int pourcentageZone3 = 0;
        int pourcentageZone4 = 0;
        int pourcentageZone5 = 0;
        List<RaStatData> seancesStat = new ArrayList<>();
        int nbTypeSeance = 0;
        double performanceCumule = 0;

        for (int counter = _lastSeanceNumber; counter > 0; counter--) {
            RaSeanceResumeData seance =seances.get(counter - 1);

            if(!_typeSeance.equals("All") && !seance.getTypeSeance().equals(_typeSeance))
                continue;

            if(seance.getMeteo() != null && _excludeMeteoSeance != null && _excludeMeteoSeance.contains(seance.getMeteo()))
                continue;

            nbTypeSeance++;
            pourcentageZone1 += seance.getIntValPourcentageZone1();
            pourcentageZone2 += seance.getIntValPourcentageZone2();
            pourcentageZone3 += seance.getIntValPourcentageZone3();
            pourcentageZone4 += seance.getIntValPourcentageZone4();
            pourcentageZone5 += seance.getIntValPourcentageZone5();

            performanceCumule += seance.getPerformance();

            RaStatData seanceStat = new RaStatData();
            seanceStat.setPerformance(seance.getPerformance());
            seanceStat.setPerformanceCumule(performanceCumule / nbTypeSeance);
            seanceStat.setVitesseMoy(seance.getVitesseMoy());
            seanceStat.setSemaine(seance.getSemaine());
            seanceStat.setType(RaStatData.TYPE_SEANCES);
            seanceStat.setUnite(RaStatData.UNITE_SEMAINE);
            seancesStat.add(seanceStat);
        }

        if (nbTypeSeance > 0) {
            pourcentageZone1 /= nbTypeSeance;
            pourcentageZone2 /= nbTypeSeance;
            pourcentageZone3 /= nbTypeSeance;
            pourcentageZone4 /= nbTypeSeance;
            pourcentageZone5 /= nbTypeSeance;

            result.setPourcentageZone1(pourcentageZone1);
            result.setPourcentageZone2(pourcentageZone2);
            result.setPourcentageZone3(pourcentageZone3);
            result.setPourcentageZone4(pourcentageZone4);
            result.setPourcentageZone5(pourcentageZone5);
            result.setSeanceStat(seancesStat);
        }

        return result;
    }

    private RaStatData sumIntervalle(RaSeanceIntervalleData si1, RaSeanceIntervalleData si2, RaSeanceResumeData _seanceResume) {
        double distance = si1.getDistance() + si2.getDistance();
        int fcmoy = (si1.getFcMoy() + si2.getFcMoy()) / 2;
        LocalTime duree1 = si1.getDuree();
        LocalTime duree2 = si2.getDuree();
        LocalTime duree = duree1.plusHours(duree2.getHour()).plusMinutes(duree2.getMinute()).plusSeconds(duree2.getSecond());
        double vitesseMoy = (si1.getVitesseMoy() + si2.getVitesseMoy()) / 2;
        int calorie = si1.getCalories() +si2.getCalories();

        RaStatData newIntervalle = new RaStatData();
        newIntervalle.setDistance(distance);
        newIntervalle.setFcMoy(fcmoy);
        newIntervalle.setDuree(duree);
        newIntervalle.setVitesseMoy(vitesseMoy);
        newIntervalle.setCalories(si1.getCalories() + si2.getCalories());
        newIntervalle.setRelativeAltitude(si1.getRelativeAltitude() + si2.getRelativeAltitude());


        newIntervalle.calculate(_seanceResume);

        return newIntervalle;
    }
}
