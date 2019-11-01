package runningAnalyse.services;

import runningAnalyse.model.RaSeanceIntervalleData;
import runningAnalyse.model.RaSeanceResumeData;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface RaSeanceIntervalleServices {
    public List<RaSeanceIntervalleData> createIntervallesFromCsv(InputStream _csvIs, RaSeanceResumeData _seanceResume)  throws IOException;
//
//    void insertSeanceIntervalle(RaSeanceIntervalleData _seanceIntervalle);
//    void insertSeanceIntervalleFromCsv(InputStream _csvIs, long _IdSeanceResume) throws IOException;
//    void update(RaSeanceResumeData _seanceResume);
		// duplique pour test git
	  void insertSeanceIntervalle(RaSeanceIntervalleData _seanceIntervalle);
      void insertSeanceIntervalleFromCsv(InputStream _csvIs, long _IdSeanceResume) throws IOException;
      void update(RaSeanceResumeData _seanceResume);
	  	// duplique pour test git 11:54 branche1
	  void insertSeanceIntervalle(RaSeanceIntervalleData _seanceIntervalle);
      void insertSeanceIntervalleFromCsv(InputStream _csvIs, long _IdSeanceResume) throws IOException;
      void update(RaSeanceResumeData _seanceResume);
}
