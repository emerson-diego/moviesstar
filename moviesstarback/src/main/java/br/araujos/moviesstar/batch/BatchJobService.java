package br.araujos.moviesstar.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchJobService {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job createJob;

    public void runJobs() {
        try {
            jobLauncher.run(createJob, new JobParameters());
        } catch (JobExecutionAlreadyRunningException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JobRestartException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
