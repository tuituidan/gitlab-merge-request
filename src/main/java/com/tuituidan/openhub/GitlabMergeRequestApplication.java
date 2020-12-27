package com.tuituidan.openhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * GitlabMergeRequestApplication.
 *
 * @author tuituidan
 * @version 1.0
 */
@SpringBootApplication
@EnableCaching
public class GitlabMergeRequestApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitlabMergeRequestApplication.class, args);
    }

}
