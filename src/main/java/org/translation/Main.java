package org.translation;

import java.util.*;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {

        Translator translator = new JSONTranslator("sample.json");
        //Translator translator = new InLabByHandTranslator();

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        final String quit = "quit";
        CountryCodeConverter converter = new CountryCodeConverter();
        LanguageCodeConverter languageConverter = new LanguageCodeConverter();
        while (true) {
            String country = promptForCountry(translator);
            if (country.equals(quit)) {
                break;
            }
            String countryCode = converter.fromCountry(country);
            String selectedLanguageName = promptForLanguage(translator, countryCode);
            if (selectedLanguageName.equals(quit)) {
                break;
            }
            String languageCode = languageConverter.fromLanguage(selectedLanguageName);
            String translation = translator.translate(countryCode.toLowerCase(), languageCode.toLowerCase());
            System.out.println(country + " in " + selectedLanguageName + " is " + translation);
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (quit.equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        List<String> countries = translator.getCountries();
        countries.sort(String.CASE_INSENSITIVE_ORDER);
        for (String country : countries) {
            CountryCodeConverter converter = new CountryCodeConverter();
            String countryName = converter.fromCountryCode(country);
            System.out.println(countryName);
        }
        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {
        LanguageCodeConverter languageConverter = new LanguageCodeConverter();
        List<String> languageCodes = translator.getCountryLanguages(country.toLowerCase());
        List<String> languageNames = new ArrayList<>();
        for (String code : languageCodes) {
            String languageName = languageConverter.fromLanguageCode(code);
            if (languageName != null) {
                languageNames.add(languageName);
            }
        }
        Collections.sort(languageNames);
        for (String languageName : languageNames) {
            System.out.println(languageName);
        }

        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
