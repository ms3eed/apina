package fi.evident.apina.spring.java.reader;

import java.io.IOException;
import java.io.InputStream;

public interface ClassPathResourceProcessor {
    void process(InputStream in) throws IOException;
}