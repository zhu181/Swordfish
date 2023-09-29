/*******************************************************************************
 * Copyright (c) 2023 Maxprograms.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/epl-v10.html
 *
 * Contributors:
 *     Maxprograms - initial API and implementation
 *******************************************************************************/

package com.maxprograms.swordfish.mt;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.maxprograms.mt.AzureTranslator;
import com.maxprograms.mt.ChatGptTranslator;
import com.maxprograms.mt.DeepLTranslator;
import com.maxprograms.mt.GoogleTranslator;
import com.maxprograms.mt.MTranslator;
import com.maxprograms.mt.MyMemoryTranslator;
import com.maxprograms.mt.YandexTranslator;
import com.maxprograms.swordfish.TmsServer;

public class MT {

    private MTranslator translator;

    private String srcLang;
    private String tgtLang;

    private boolean googleEnabled;
    private String googleKey;
    private String googleSrcLang;
    private String googleTgtLang;
    private boolean neural;

    private boolean azureEnabled;
    private String azureKey;
    private String azureSrcLang;
    private String azureTgtLang;

    private boolean yandexEnabled;
    private String yandexKey;
    private String yandexSrcLang;
    private String yandexTgtLang;

    private boolean deeplEnabled;
    private String deeplKey;
    private boolean deeplProPlan;
    private String deeplSrcLang;
    private String deeplTgtLang;

    private boolean chatGptEnabled;
    private String chatGptKey;
    private String model;

    private boolean myMemoryEnabled;
    private String myMemoryKey;
    private String myMemorySrcLang;
    private String myMemoryTgtLang;

    private ChatGptTranslator chatGpt;

    public MT() throws IOException {
        loadDefaults();
        translator = new MTranslator();
        if (azureEnabled) {
            AzureTranslator az = new AzureTranslator(azureKey);
            az.setSourceLanguage(azureSrcLang);
            az.setTargetLanguage(azureTgtLang);
            translator.addEngine(az);
        }
        if (googleEnabled) {
            GoogleTranslator gt = new GoogleTranslator(googleKey, neural);
            gt.setSourceLanguage(googleSrcLang);
            gt.setTargetLanguage(googleTgtLang);
            translator.addEngine(gt);
        }
        if (yandexEnabled) {
            YandexTranslator yt = new YandexTranslator(yandexKey);
            yt.setSourceLanguage(yandexSrcLang);
            yt.setTargetLanguage(yandexTgtLang);
            translator.addEngine(yt);
        }
        if (deeplEnabled) {
            DeepLTranslator dl = new DeepLTranslator(deeplKey, deeplProPlan);
            dl.setSourceLanguage(deeplSrcLang);
            dl.setTargetLanguage(deeplTgtLang);
            translator.addEngine(dl);
        }
        if (chatGptEnabled) {
            chatGpt = new ChatGptTranslator(chatGptKey, model);
            translator.addEngine(chatGpt);
        }
        if (myMemoryEnabled) {
            MyMemoryTranslator mm = new MyMemoryTranslator(myMemoryKey);
            mm.setSourceLanguage(myMemorySrcLang);
            mm.setTargetLanguage(myMemoryTgtLang);
            translator.addEngine(mm);
        }
    }

    public boolean hasEngines() {
        return translator.hasEngines();
    }

    private void loadDefaults() throws IOException, JSONException {
        JSONObject json = TmsServer.getPreferences();

        JSONObject google = json.getJSONObject("google");
        googleEnabled = google.getBoolean("enabled");
        googleKey = google.getString("apiKey");
        googleSrcLang = google.getString("srcLang");
        googleTgtLang = google.getString("tgtLang");
        neural = google.getBoolean("neural");

        JSONObject azure = json.getJSONObject("azure");
        azureEnabled = azure.getBoolean("enabled");
        azureKey = azure.getString("apiKey");
        azureSrcLang = azure.getString("srcLang");
        azureTgtLang = azure.getString("tgtLang");

        JSONObject yandex = json.getJSONObject("yandex");
        yandexEnabled = yandex.getBoolean("enabled");
        yandexKey = yandex.getString("apiKey");
        yandexSrcLang = yandex.getString("srcLang");
        yandexTgtLang = yandex.getString("tgtLang");

        JSONObject deepl = json.getJSONObject("deepl");
        deeplEnabled = deepl.getBoolean("enabled");
        deeplKey = deepl.getString("apiKey");
        if (deepl.has("proPlan")) {
            deeplProPlan = deepl.getBoolean("proPlan");
        } else {
            deeplProPlan = true;
        }
        deeplProPlan = deepl.getBoolean("proPlan");
        deeplSrcLang = deepl.getString("srcLang");
        deeplTgtLang = deepl.getString("tgtLang");

        if (json.has("chatGpt")) {
            JSONObject chatGptOption = json.getJSONObject("chatGpt");
            chatGptEnabled = chatGptOption.getBoolean("enabled");
            chatGptKey = chatGptOption.getString("apiKey");
            switch (chatGptOption.getString("model")) {
                case "gpt-3.5-turbo-instruct":
                    model = ChatGptTranslator.TURBO_INSTRUCT;
                    break;
                default:
                    MessageFormat mf = new MessageFormat("Invalid ChatGPT model: {0}");
                    throw new JSONException(mf.format(new String[] { chatGptOption.getString("model") }));
            }
        } else {
            chatGptEnabled = false;
        }

        JSONObject myMemory = json.getJSONObject("myMemory");
        myMemoryEnabled = myMemory.getBoolean("enabled");
        myMemoryKey = myMemory.getString("apiKey");
        myMemorySrcLang = myMemory.getString("srcLang");
        myMemoryTgtLang = myMemory.getString("tgtLang");
    }

    public List<JSONObject> translate(String text) throws IOException, InterruptedException {
        if (chatGptEnabled) {
            chatGpt.setSourceLanguage(srcLang);
            chatGpt.setTargetLanguage(tgtLang);
        }
        return translator.translate(text);
    }

    public void setProjectSourceLanguage(String srcLang) {
        this.srcLang = srcLang;
    }

    public void setProjectTargetLanguage(String tgtLang) {
        this.tgtLang = tgtLang;
    }
}