package br.com.arthurocfernandes.pismocodingassessment;

import org.springframework.boot.SpringApplication;

public class TestPismoCodingAssessmentApplication {

    public static void main(String[] args) {
        SpringApplication.from(PismoCodingAssessmentApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
