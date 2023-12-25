package views;
// Used sample code from Google Cloud for text translation
// Citation: https://cloud.google.com/translate/docs/advanced/translate-text-advance

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.*;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;




public class TranslatorExample {




    /**
     * The Translate method converts the provided text in English to the targetLanguage as
     * provided by the user.
     * @param targetLanguage is the dialect that the player is using to play the game.
     * @param sourceLanguage is English in which all the room descriptions are stored.
     * @param  textToTranslate is the text that the user wants to translate into the desired language.
     * @author Harsimar Singh
     */


    public static String Translate(String targetLanguage, String sourceLanguage, String textToTranslate ) {
        try{

        // Returning to avoid any errors related to (targetLanguage,sourceLanguage) pair having same values.
        if(sourceLanguage.equals(targetLanguage)){
            return textToTranslate;
        }

        Translate translate =  TranslateOptions.newBuilder().build().getService();


        // Perform the translation
        Translation translation = translate.translate(textToTranslate, Translate.TranslateOption.sourceLanguage(sourceLanguage), Translate.TranslateOption.targetLanguage(targetLanguage));
        return translation.getTranslatedText();}
        catch (Exception e){
            System.out.println("Credentials are not validated for Google cloud.");
            return textToTranslate;
        }
    }
}





