package runningAnalyse.services;

import runningAnalyse.model.RaSeanceResumeData;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public interface RaSeanceResumeServices {
    List<RaSeanceResumeData> findAllSeanceResume();
    RaSeanceResumeData findById(long _id);
    RaSeanceResumeData addSeanceResume(RaSeanceResumeData _seanceResume);
    RaSeanceResumeData modifySeanceResume(RaSeanceResumeData _seanceResume);
    RaSeanceResumeData newSeanceResume();
    public RaSeanceResumeData updateIntervalleFromCsv(InputStream _csvIs, RaSeanceResumeData _seanceResume) throws IOException;
    public void resetFromCsv(InputStream _csvIs) throws IOException;
    public void deleteSeanceResume(long _seanceResumeId);
	// dupliquer pour test git depuis testGit2
	public RaSeanceResumeData updateIntervalleFromCsv(InputStream _csvIs, RaSeanceResumeData _seanceResume) throws IOException;
    public void resetFromCsv(InputStream _csvIs) throws IOException;
    public void deleteSeanceResume(long _seanceResumeId);
}
