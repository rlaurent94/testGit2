package runningAnalyse.services;

import runningAnalyse.model.RaLieuSeanceData;
import runningAnalyse.model.RaMeteoSeanceData;
import runningAnalyse.model.RaTypeSeanceData;

import java.util.List;

public interface RaCommonServices {
    List<RaTypeSeanceData> getTypeSeance();
    List<RaLieuSeanceData> getLieuSeance();
    List<RaMeteoSeanceData> getMeteoSeance();
}
