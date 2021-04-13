package com.example.mongo;

import com.example.mongo.model.Student;
import com.example.mongo.repository.ZipCodesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.scheduler.Schedulers;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication(exclude = {RedisRepositoriesAutoConfiguration.class})
@Slf4j
@RequiredArgsConstructor
public class MongoDemoApplication {

    private final ZipCodesRepository zipCodesRepository;

    public static void main(String[] args) {
        SpringApplication.run(MongoDemoApplication.class, args);
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.mongo.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .description("My Reactive API")
                .title("My Domain object API")
                .version("1.0.0")
                .build();
    }

    //        @Bean
    public ApplicationRunner runner() {
        return (args) -> {
            List<Student> students = Arrays.asList(
                    new Student("id1", "kiran", Student.Gender.MALE),
                    new Student("id2", "Bobby", Student.Gender.MALE),
                    new Student("id3", "Shanti", Student.Gender.FEMALE),
                    new Student("id4", "Sruthi", Student.Gender.FEMALE)
            );
//            studentRepository.saveAll(students);
        };
    }

    //    @Bean
    public ApplicationRunner reactiveRunner() {
        return args -> zipCodesRepository.findAllByState("MA")
                .subscribeOn(Schedulers.boundedElastic())
                .delayElements(Duration.ofMillis(500))
                .subscribe(v -> log.info("{}", v));
    }
}
