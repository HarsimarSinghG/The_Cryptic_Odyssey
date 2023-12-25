package views;// Imports the Google Cloud client library
// Used sample code from Google Cloud API for text-to-speech functionality.
// Citation: https://cloud.google.com/text-to-speech/docs/samples/tts-quickstart?hl=en


import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class TextToSpeech {


    /**
     * The Speak method creates an mp3 file for the provided text.
     * @param roomName is the name with which the speech file will be stored as mp3 file.
     * @param roomDescription is the text that needs to be converted to speech.
     * @param  language is the dialect that the player is using to play the game.
     * @author Harsimar Singh
     */
    public static void Speak(String roomName, String roomDescription, String language){

        // Returning because farsi language is not supported for TextToSpeech
        if(language=="fa"){
            return;
        }
        try {

            // Instantiate a client.
            TextToSpeechClient textToSpeechClient = TextToSpeechClient.create();

            SynthesisInput input = SynthesisInput.newBuilder().setText(roomDescription).build();


            // Build the voice selection params with desired language and gender for the voice.
            VoiceSelectionParams voice =
                    VoiceSelectionParams.newBuilder()
                            .setLanguageCode(language)
                            .setSsmlGender(SsmlVoiceGender.NEUTRAL)
                            .build();


            AudioConfig audioConfig =
                    AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.MP3).build();


            // Get the response from cloud for the provided text and audio configuration.
            SynthesizeSpeechResponse response =
                    textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);


            ByteString audioContents = response.getAudioContent();


            // Create the mp3 file from the response.
            try (OutputStream out = new FileOutputStream(roomName+".mp3")) {
                out.write(audioContents.toByteArray());
                System.out.println("Audio content written to file \"output.mp3\"");
            }}
        catch(Exception e){
            System.out.println("An error occurred while playing sound for room description."+e.toString());
        }
    }
}

