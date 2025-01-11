package com.example.testapi.onboarding;

import android.view.View;
import android.widget.Toast;

import com.example.testapi.R;
import com.example.testapi.activitys.AddResourceFragment;
import com.example.testapi.activitys.ClickedNotice;
import com.example.testapi.activitys.ListViewFragment;
import com.example.testapi.activitys.MainActivity;
import com.example.testapi.dataobjects.UserDataManager;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

public class Onboarding {
    private  MainActivity parent;
    private UserDataManager dataManager;
    private  static  Boolean isfristTimeList;
    private  static  Boolean isfristNotice;
    private  static  Boolean isfistAdd;

    public Onboarding(MainActivity parent, UserDataManager userDataManager){
        this.parent = parent;
        this.dataManager = userDataManager;
    }
    public  void showOnboarding(ListViewFragment fragment){
        Onboarding.isfristTimeList = dataManager.getBoolean("fristL");
        if (!isfristTimeList){
            return;
        }
        View tab = parent.tabLayout.findViewById(R.id.navigation_message);
        TapTargetSequence sequence = new TapTargetSequence(parent)
                .targets(
                        TapTarget.forView(fragment.addButton, "Hinzufügen", "Tippe hier, um ein neues Element hinzuzufügen.")
                                .outerCircleColor(R.color.md_theme_tertiary)
                                .targetCircleColor(android.R.color.white)
                                .transparentTarget(true)
                                .cancelable(true),
                        TapTarget.forView(fragment.recyclerView, "Elementliste", "Hier werden die verfügbaren Elemente angezeigt.")
                                .outerCircleColor(R.color.md_theme_tertiary)
                                .targetCircleColor(android.R.color.white)
                                .transparentTarget(true)
                                .cancelable(true),
                        TapTarget.forView(fragment.fristSpinner, "Filter", "Verwende dieses Dropdown-Menü, um Elemente zu filtern.")
                                .outerCircleColor(R.color.md_theme_tertiary)
                                .targetCircleColor(android.R.color.white)
                                .transparentTarget(true)
                                .cancelable(true),
                        TapTarget.forView(tab,"Message","Hier kannst du deine dierct Nachrichten sehen")
                                .outerCircleColor(R.color.md_theme_tertiary)
                                .targetCircleColor(android.R.color.white)
                                .transparentTarget(true)
                                .cancelable(true),
                        TapTarget.forView(fragment.loginButton,"Login","Hier kannst du dich einloggen")
                                .outerCircleColor(R.color.md_theme_tertiary)
                                .targetCircleColor(android.R.color.white)
                                .transparentTarget(true)
                                .cancelable(true)
                )
                .listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        Toast.makeText(parent, "Onboarding abgeschlossen!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                        // Optional: Aktionen für jeden Schritt
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        Toast.makeText(parent, "Onboarding abgebrochen!", Toast.LENGTH_SHORT).show();
                    }
                });

        // Sequenz starten
        sequence.start();
        dataManager.saveBoolean("fristL",false);

    }
    public void  showOnboarding(ClickedNotice fragment){
        Onboarding.isfristTimeList = dataManager.getBoolean("fristN");
        if (!isfristTimeList){
            return;
        }
        TapTargetSequence sequence = new TapTargetSequence(parent)
                .targets(
                        TapTarget.forView(fragment.titel, "Beitragstitel", "Dies ist der Titel des ausgewählten Beitrags.")
                                .outerCircleColor(R.color.md_theme_tertiary)
                                .targetCircleColor(android.R.color.white)
                                .transparentTarget(true)
                                .cancelable(false),
                        TapTarget.forView(fragment.inhalt, "Beschreibung", "Hier findest du weitere Details zu diesem Beitrag.")
                                .outerCircleColor(R.color.md_theme_tertiary)
                                .targetCircleColor(android.R.color.white)
                                .transparentTarget(true)
                                .cancelable(false)
                );

        // Zusätzliche Ziele abhängig vom Status
        if (MainActivity.isLogtin) {
            sequence.target(
                    TapTarget.forView(fragment.messageButton, "Nachricht senden", "Tippe hier, um den Ersteller des Beitrags zu kontaktieren.")
                            .outerCircleColor(R.color.md_theme_tertiary)
                            .targetCircleColor(android.R.color.white)
                            .transparentTarget(true)
                            .cancelable(false)
            );
        } else {
            sequence.target(
                    TapTarget.forView(parent.findViewById(R.id.login_button), "Login", "Melde dich an, um weitere Aktionen durchzuführen.")
                            .outerCircleColor(R.color.md_theme_tertiary)
                            .targetCircleColor(android.R.color.white)
                            .transparentTarget(true)
                            .cancelable(false)
            );
        }
        sequence.listener(new TapTargetSequence.Listener() {
            @Override
            public void onSequenceFinish() {
                Toast.makeText(parent, "Onboarding abgeschlossen!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                // Optional: Aktionen für jeden Schritt
            }

            @Override
            public void onSequenceCanceled(TapTarget lastTarget) {
                Toast.makeText(parent, "Onboarding abgebrochen!", Toast.LENGTH_SHORT).show();
            }
        });
        sequence.start();


        dataManager.saveBoolean("fristN",false);
    }
    public void  showOnboarding(AddResourceFragment fragment){
        Onboarding.isfristTimeList = dataManager.getBoolean("fristA");
        if (!isfristTimeList){
            return;
        }
        TapTargetSequence sequence = new TapTargetSequence(parent).targets(
                TapTarget.forView(fragment.endbutton, "Füge einen Eintrag hinzu", "Drücke hier, um einen neuen Eintrag hinzuzufügen.")
                        .outerCircleColor(R.color.md_theme_tertiary)
                        .targetCircleColor(android.R.color.white)
                        .transparentTarget(true)
                        .cancelable(false),
                TapTarget.forView(fragment.flied1, "Titel eingeben", "Hier gibst du den Titel für deinen Eintrag ein.")
                        .outerCircleColor(R.color.md_theme_tertiary)
                        .targetCircleColor(android.R.color.white)
                        .transparentTarget(true)
                        .cancelable(false),
                TapTarget.forView(fragment.spinner1, "Kategorie wählen", "Wähle eine Kategorie aus, um deinen Eintrag zu sortieren.")
                        .outerCircleColor(R.color.md_theme_tertiary)
                        .targetCircleColor(android.R.color.white)
                        .transparentTarget(true)
                        .cancelable(false)
        );


        sequence.start();
        dataManager.saveBoolean("fristA",false);
    }


}
