package advance;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GoogleAPI {
    public static String GOOGLE_TRANSLATE_URL = "";

    public static String GOOGLE_AUDIO_URL = "";

    public static String GOOGLE_SEARCH_URL = "";

    public static TranslateToHTML translateToHTML = new TranslateToHTML();

    public static SearchToHTML searchToHTML = new SearchToHTML();
}
