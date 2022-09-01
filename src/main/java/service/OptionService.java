package service;

import domain.Option;

import java.io.IOException;
import java.util.List;

public interface OptionService {
    List<Option> getQuestionOptions(long questionId) throws IOException;
}
