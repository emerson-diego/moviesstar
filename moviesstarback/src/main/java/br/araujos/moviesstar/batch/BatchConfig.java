package br.araujos.moviesstar.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import br.araujos.moviesstar.dto.MovieDTO;
import br.araujos.moviesstar.services.MovieService;

@Configuration
public class BatchConfig {

    @Autowired
    private MovieService movieService;

    @Bean
    Job createJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("job", jobRepository)
                .flow(createStep(jobRepository, transactionManager)).end().build();
    }

    @Bean
    Step createStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step", jobRepository)
                .<MovieDTO, MovieDTO>chunk(1, transactionManager)
                .allowStartIfComplete(true)
                .reader(new CustomReader(movieService))
                // .processor(new CustomProcessor())
                .writer(new CustomWriter(movieService))
                .build();
    }
}
