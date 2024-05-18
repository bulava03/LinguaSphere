package com.example.LinguaSphere;

import com.example.LinguaSphere.entity.TeacherParams;
import com.example.LinguaSphere.extra.TeacherRatingCalc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class LinguaSphereApplication {

	public static void main(String[] args) {
		TeacherRatingCalc.calculateWeights();
		SpringApplication.run(LinguaSphereApplication.class, args);
	}

}
