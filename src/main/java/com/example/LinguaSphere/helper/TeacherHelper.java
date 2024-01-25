package com.example.LinguaSphere.helper;

import com.example.LinguaSphere.entity.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TeacherHelper {

    public List<Long>  removeMismatched(List<Long> userMaterials, List<Material> teacherMaterials) {
        List<Long> finalList = new ArrayList<>();
        for (Long userMaterialId : userMaterials
             ) {
            if (teacherMaterials.stream().anyMatch(
                    material -> Objects.equals(material.getId(), userMaterialId))) {
                finalList.add(userMaterialId);
            }
        }
        return finalList;
    }

}
