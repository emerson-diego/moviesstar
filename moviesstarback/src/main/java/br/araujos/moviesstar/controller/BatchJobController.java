package br.araujos.moviesstar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.araujos.moviesstar.batch.BatchJobService;

@RestController
@RequestMapping("/batch")
public class BatchJobController {

    @Autowired
    private BatchJobService batchJobService;

    @PostMapping("/start-job")
    public ResponseEntity<String> startJob() {
        try {
            batchJobService.runJobs();
            return ResponseEntity.ok("Job iniciado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao iniciar o job: " + e.getMessage());
        }
    }
}