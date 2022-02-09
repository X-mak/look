package com.look;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.look.mapper")
@SpringBootApplication
public class LookApplication {

    public static void main(String[] args) {
        SpringApplication.run(LookApplication.class, args);
    }

}
