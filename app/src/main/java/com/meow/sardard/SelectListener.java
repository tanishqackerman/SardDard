package com.meow.sardard;

import com.meow.sardard.Models.PDFData;

public interface SelectListener {
    void OnPDFClicked(PDFData data);
    void OnSemClicked(String s, int semname);
}
