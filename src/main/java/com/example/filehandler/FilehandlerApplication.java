package com.example.filehandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class FilehandlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilehandlerApplication.class, args);
    }

}
