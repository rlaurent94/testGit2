package runningAnalyse.services;

import runningAnalyse.model.RaSeanceResumeData;
import runningAnalyse.model.RaSeanceStatData;

import java.util.List;

public interface RaSeancesStatServices {
    RaSeanceStatData calculateStatLastSeances(int lastSeanceNumber, String _typeSeance, List<String> _excludeMeteoSeance);
    RaSeanceStatData calculateStatSeance(RaSeanceResumeData _seanceResume);
    RaSeanceStatData calculateStatSeance(long _seanceId);
	// dupliquer pour test git depuis testGit2_1
	RaSeanceStatData calculateStatLastSeances(int lastSeanceNumber, String _typeSeance, List<String> _excludeMeteoSeance);
    RaSeanceStatData calculateStatSeance(RaSeanceResumeData _seanceResume);
    RaSeanceStatData calculateStatSeance(long _seanceId);
}
