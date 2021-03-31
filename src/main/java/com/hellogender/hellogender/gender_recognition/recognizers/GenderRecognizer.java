package com.hellogender.hellogender.gender_recognition.recognizers;

import com.hellogender.hellogender.models.Gender;

public interface GenderRecognizer {
    Gender recognize(String name);
}
