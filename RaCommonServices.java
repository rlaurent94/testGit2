package runningAnalyse.services;

import runningAnalyse.model.RaLieuSeanceData;
import runningAnalyse.model.RaMeteoSeanceData;
import runningAnalyse.model.RaTypeSeanceData;

import java.util.List;

public interface RaCommonServices {
    List<RaTypeSeanceData> getTypeSeance();
    List<RaLieuSeanceData> getLieuSeance();
    List<RaMeteoSeanceData> getMeteoSeance();
	// duplication de ligne pour test changement master et push sur master
	List<RaTypeSeanceData> getTypeSeance();
    List<RaLieuSeanceData> getLieuSeance();
    List<RaMeteoSeanceData> getMeteoSeance();
}
