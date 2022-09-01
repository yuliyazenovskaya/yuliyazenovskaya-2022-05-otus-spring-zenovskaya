package service.impl;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import domain.Option;
import service.OptionService;
import sun.applet.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OptionServiceImpl implements OptionService {
    private final String optionsCsv;
    private final List<Option> options = new ArrayList<>();

    public OptionServiceImpl(String optionsCsv) {
        this.optionsCsv = optionsCsv;
    }

    @Override
    public List<Option> getQuestionOptions(long questionId) throws IOException {
        if (options.size() == 0) readOptionsFromFile();
        return options.stream().filter(option -> option.questionId == questionId).collect(Collectors.toList());
    }

    private void readOptionsFromFile() throws IOException {
        InputStream inputStream = Main.class.getResourceAsStream(this.optionsCsv);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        CSVReader csvReader = new CSVReaderBuilder(bufferedReader).withSkipLines(1).build();
        String[] str;
        while ((str = csvReader.readNext()) != null) {
            options.add(new Option(
                    Long.parseLong(str[0]),
                    Long.parseLong(str[1]),
                    str[2]
            ));
        }
        csvReader.close();
        bufferedReader.close();
    }
}
