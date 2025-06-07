package com.example.triviavirsion2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SettingsPage extends AppCompatActivity {

    Button logoutButton;
    Spinner languageSpinner;
    Switch vibrationSwitch;

    private final String[] languages = {"English", "Spanish", "French", "Russian", "Arabic", "Hebrew"};
    private final String[] languageCodes = {"en", "es", "fr", "ru", "ar", "he"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.settings_page);

        logoutButton = findViewById(R.id.btn_logout);
        languageSpinner = findViewById(R.id.spinner_language);
        vibrationSwitch = findViewById(R.id.switch_vibration);

        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isVibrationEnabled = prefs.getBoolean("vibration_enabled", true);
        vibrationSwitch.setChecked(isVibrationEnabled);

        vibrationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("vibration_enabled", isChecked);
            editor.apply();

            if (isChecked) {
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (vibrator != null && vibrator.hasVibrator()) {
                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            vibrator.vibrate(100);
                        }
                        Toast.makeText(this, "Vibration enabled", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Vibration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Vibration not supported on this device", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Vibration disabled", Toast.LENGTH_SHORT).show();
            }
        });

        logoutButton.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsPage.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        String currentLang = getSavedLanguage();
        int spinnerPosition = 0;
        for (int i = 0; i < languageCodes.length; i++) {
            if (languageCodes[i].equals(currentLang)) {
                spinnerPosition = i;
                break;
            }
        }
        languageSpinner.setSelection(spinnerPosition);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean firstSelection = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (firstSelection) {
                    firstSelection = false;
                    return;
                }
                setLocale(languageCodes[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);

        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        if (langCode.equals("ar") || langCode.equals("he")) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", langCode);
        editor.apply();

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "en");
        if (!language.isEmpty()) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);

            Configuration config = getResources().getConfiguration();
            config.setLocale(locale);
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        }
    }

    private String getSavedLanguage() {
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        return prefs.getString("My_Lang", "en");
    }
}
