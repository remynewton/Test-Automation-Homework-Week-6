package com.laba.solvd.hw.Case;
import com.laba.solvd.hw.Enums.*;
public interface ICrime {
    String getDescription();
    void setSeverity(Severity severity);
    Severity getSeverity();
}