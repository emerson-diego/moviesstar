package br.araujos.moviesstar.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import br.araujos.moviesstar.dto.MovieDTO;
import br.araujos.moviesstar.repository.MovieRepository;
import br.araujos.moviesstar.services.MovieService;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final MovieService movieService;

    public BatchConfiguration(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            MovieService movieService) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.movieService = movieService;
    }

    @Bean
    public ItemReader<MovieDTO> reader() {
        // Implemente seu MovieItemReader
        return new MovieItemReader(movieService);
    }

    /*
     * @Bean
     * public ItemProcessor<MovieDTO, Movie> processor() {
     * // Implemente seu MovieItemProcessor
     * return new MovieItemProcessor();
     * }
     */

    @Bean
    public ItemWriter<MovieDTO> writer(MovieRepository movieRepository) {
        return new MovieItemWriter(movieService);
    }

    @Bean
    public Step step1(ItemReader<MovieDTO> reader, ItemWriter<MovieDTO> writer) {
        return new StepBuilder("step1", jobRepository)
                .<MovieDTO, MovieDTO>chunk(1, transactionManager)
                .reader(reader)
                .writer(writer)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job importMovieJob(Step step1) {
        return new JobBuilder("importMovieJob", jobRepository)
                .start(step1)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(25);
        taskExecutor.initialize();
        return taskExecutor;
    }
}
