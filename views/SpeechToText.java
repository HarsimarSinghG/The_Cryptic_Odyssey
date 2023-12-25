package views;
// Using the Google cloud sample code and some help from StackOverFlow for SpeechToText and Application Default Credentials
// Citation: https://cloud.google.com/docs/authentication/application-default-credentials
// Citation: https://stackoverflow.com/questions/25798200/java-record-mic-to-byte-array-and-play-sound
// Citation: https://cloud.google.com/speech-to-text/docs/samples/speech-transcribe-infinite-streaming?hl=en

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.speech.v1p1beta1.RecognitionAudio;
import com.google.cloud.speech.v1p1beta1.RecognitionConfig;
import com.google.cloud.speech.v1p1beta1.RecognizeResponse;
import com.google.cloud.speech.v1p1beta1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1p1beta1.SpeechRecognitionResult;
import com.google.cloud.speech.v1p1beta1.SpeechClient;
import com.google.cloud.speech.v1p1beta1.SpeechSettings;
import com.google.protobuf.ByteString;
import javafx.scene.input.KeyCode;


import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


public class SpeechToText {


    /**
     * The Convert method enables the microphone and listens
     * to the speech and convert it into text based on provided
     * language in the parameters.
     * @param language is the dialect that the player is using to play the game.
     * @author Harsimar Singh
     */

    public static String Convert(String language) throws Exception {

        try{

        // Constructing the speech client with speech settings.
        try (com.google.cloud.speech.v1p1beta1.SpeechClient speechClient = SpeechClient.create(
                SpeechSettings.newBuilder()
                        .build()
        ))  {


            // Configuring the type of speech to be detected with HertzRate and language.
            RecognitionConfig config =
                    RecognitionConfig.newBuilder()
                            .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                            .setSampleRateHertz(16000)
                            .setLanguageCode(language)
                            .setEnableAutomaticPunctuation(false)
                            .build();




            // Defining the TargetDataLine as in-built system microphone and enabling it to listen to the player.
            AudioFormat format = new AudioFormat(16000.0f, 16, 1, true, false);
            TargetDataLine microphone;
            byte[] audioData = new byte[1024];
            try {


                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                if (!AudioSystem.isLineSupported(info)) {
                    System.out.println("Microphone not supported");
                    System.exit(0);
                }
                microphone = (TargetDataLine) AudioSystem.getLine(info);
                microphone.open(format);


                microphone.start();


                // Reading the bytes from the speech.
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int newbytesRead = 0;
                int totalBytesRead = 0;
                try (AudioInputStream audioInputStream = new AudioInputStream(microphone)) {
                    while ((newbytesRead = audioInputStream.read(buffer)) != -1) {
                        totalBytesRead += newbytesRead;
                        if(totalBytesRead>=115000){
                            break;
                        }
                        outputStream.write(buffer, 0, newbytesRead);
                    }


                }
                audioData = outputStream.toByteArray();
                microphone.close();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }



            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(ByteString.copyFrom(audioData))
                    .build();
            // Sending in the audio to google cloud to get response for transcription.
            RecognizeResponse response = speechClient.recognize(config, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();


            // Creating the transcription based on the cloud result.
            StringBuilder transcription = new StringBuilder();
            for (SpeechRecognitionResult result : results) {
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                transcription.append(alternative.getTranscript());
            }
            return transcription.toString();
        }}catch (Exception e){
            System.out.println("Credentials are not validated for Google cloud.");
            return "";
        }
    }
}



