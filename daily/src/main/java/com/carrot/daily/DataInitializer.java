package com.carrot.daily;


import com.carrot.daily.domain.DCategory;
import com.carrot.daily.repository.DCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    @Transactional
    public CommandLineRunner dCategoryList(DCategoryRepository dCategoryRepository){
        return args -> {
            List<String> dCategoryNames = Arrays.asList("모임", "맛집", "고민/사연", "분실/실종", "기타");
            for(String name : dCategoryNames){
                dCategoryRepository.findByName(name).ifPresentOrElse(
                        dCategory -> {

                        },
                        () -> {
                            dCategoryRepository.save(DCategory.builder().name(name).build());
                        }
                );
            }
        };
    }
}
