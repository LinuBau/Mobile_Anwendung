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


/**
 * Diese Klasse ist für das Onboarding beim erstmaligem Start der App verantwortlich.
 * Diese Klasse zeigt den Nutzern, wie die App funktioniert
 * und erkärt diese ebenfalls die Funktionen der App.
 */
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
                        TapTarget.forView(fragment.addButton, "Hinzufügen", "Tippe hier, um ein neuen Post zu erstellen.")
                                .outerCircleColor(R.color.md_theme_tertiary)
                                .targetCircleColor(android.R.color.white)
                                .transparentTarget(true)
                                .cancelable(true),
                        TapTarget.forView(fragment.recyclerView, "Elementliste", "Hier werden alle Posts angezeigt.")
                                .outerCircleColor(R.color.md_theme_tertiary)
                                .targetCircleColor(android.R.color.white)
                                .transparentTarget(true)
                                .cancelable(true),
                        TapTarget.forView(fragment.fristSpinner, "Filter", "Verwende dieses Dropdown-Menü um nach Kategorien zu filtern.")
                                .outerCircleColor(R.color.md_theme_tertiary)
                                .targetCircleColor(android.R.color.white)
                                .transparentTarget(true)
                                .cancelable(true),
                        TapTarget.forView(tab,"Message","Hier findest du alle deine Chats")
                                .outerCircleColor(R.color.md_theme_tertiary)
                                .targetCircleColor(android.R.color.white)
                                .transparentTarget(true)
                                .cancelable(true),
                        TapTarget.forView(fragment.loginButton,"Login","Log dich hier ein, um Chats schreiben zu können")
                                .outerCircleColor(R.color.md_theme_tertiary)
                                .targetCircleColor(android.R.color.white)
                                .transparentTarget(true)
                                .cancelable(true)
                )
                .listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        Toast.makeText(parent, "Onboarding - Home: beendet!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                        // Optional: Aktionen für jeden Schritt
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        Toast.makeText(parent, "Onboarding - Home: abgebrochen!", Toast.LENGTH_SHORT).show();
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
                        TapTarget.forView(fragment.titel, "Beitragstitel", "Das ist der Titel des Posts.")
                                .outerCircleColor(R.color.md_theme_tertiary)
                                .targetCircleColor(android.R.color.white)
                                .transparentTarget(true)
                                .cancelable(false),
                        TapTarget.forView(fragment.inhalt, "Beschreibung", "Hier findest du weitere Details zu diesem Post.")
                                .outerCircleColor(R.color.md_theme_tertiary)
                                .targetCircleColor(android.R.color.white)
                                .transparentTarget(true)
                                .cancelable(false)
                );

        // Zusätzliche Ziele abhängig vom Status
        if (MainActivity.isLogtin) {
            sequence.target(
                    TapTarget.forView(fragment.messageButton, "Nachricht senden", "Tippe hier, um den Ersteller des Beitrags zu kontaktieren. Merke: Du musst dafür eingeloggt sein!")
                            .outerCircleColor(R.color.md_theme_tertiary)
                            .targetCircleColor(android.R.color.white)
                            .transparentTarget(true)
                            .cancelable(false)
            );
        } else {
            sequence.target(
                    TapTarget.forView(fragment.loginButton, "Login", "Melde dich an, um weitere Aktionen, wie Chats nutzen zu können.")
                            .outerCircleColor(R.color.md_theme_tertiary)
                            .targetCircleColor(android.R.color.white)
                            .transparentTarget(true)
                            .cancelable(false)
            );
        }
        sequence.listener(new TapTargetSequence.Listener() {
            @Override
            public void onSequenceFinish() {
                Toast.makeText(parent, "Onboarding - Posts: abgeschlossen!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                // Optional: Aktionen für jeden Schritt
            }

            @Override
            public void onSequenceCanceled(TapTarget lastTarget) {
                Toast.makeText(parent, "Onboarding - Posts: abgebrochen!", Toast.LENGTH_SHORT).show();
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
                TapTarget.forView(fragment.endbutton, "Füge einen Post hinzu", "Drücke hier, um einen neuen Post hinzuzufügen.")
                        .outerCircleColor(R.color.md_theme_tertiary)
                        .targetCircleColor(android.R.color.white)
                        .transparentTarget(true)
                        .cancelable(false),
                TapTarget.forView(fragment.flied1, "Titel eingeben", "Hier gibst du den Titel für deinen Post ein.")
                        .outerCircleColor(R.color.md_theme_tertiary)
                        .targetCircleColor(android.R.color.white)
                        .transparentTarget(true)
                        .cancelable(false),
                TapTarget.forView(fragment.spinner1, "Kategorie wählen", "Wähle eine Kategorie aus, um deinen Post einzusortieren.")
                        .outerCircleColor(R.color.md_theme_tertiary)
                        .targetCircleColor(android.R.color.white)
                        .transparentTarget(true)
                        .cancelable(false)
        );


        sequence.start();
        dataManager.saveBoolean("fristA",false);
    }


}
