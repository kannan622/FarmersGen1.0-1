package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.example.saravanamurali.farmersgen.models.ProceedBtnCheckingDTO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JSONResponseProceedBtnDTO {

    @SerializedName("records")
    List<ProceedBtnCheckingDTO> proceedBtnCheckDTOList;

    public List<ProceedBtnCheckingDTO> getProceedBtnCheckDTOList() {
        return proceedBtnCheckDTOList;
    }
}
