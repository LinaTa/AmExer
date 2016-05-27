package de.ostfalia.amexer;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
/**
 * The class generates an action after pressing a button from offices_activity
 * and reference every button to a different website from Ostfalia.de
 *
 * @author Natasza Szczypien
 */

public class Offices extends AppCompatActivity {

    /* Activity objects */
    private Button internationalOffice;
    private Button immatrikulationsbuero;
    private Button careerService;
    private Button serviceBueros;
    private Button studienfoerderung;
    private Button studienberatung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offices);

        setImageActionBar();
        initActivityObjects();
        setButtonAction();
    }

    /**
     * Initializing the aktivity buttons
     */
    private void initActivityObjects() {
        internationalOffice = (Button) this.findViewById(R.id.button_international_office);
        immatrikulationsbuero = (Button) this.findViewById(R.id.button_immatrikulationsbuero);
        careerService = (Button) this.findViewById(R.id.button_career_Service);
        serviceBueros = (Button) this.findViewById(R.id.button_servicebueros);
        studienfoerderung = (Button) this.findViewById(R.id.button_studienfoerderung);
        studienberatung = (Button) this.findViewById(R.id.button_studienberatung);
    }

    /**
     * Puts an Image to the Action Bar
     */
    private void setImageActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.mipmap.ic_offices);
            actionBar.setDisplayShowTitleEnabled(false);
            Log.i(this.getClass().toString(), String.valueOf(R.string.actionBarEnabled));
        } else {
            Log.i(this.getClass().toString(), String.valueOf(R.string.actionBarDisabled));
        }
    }

    private void setButtonAction() {
        internationalOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.international_office_url)));
                startActivity(intent);
            }
        });

        immatrikulationsbuero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,  Uri.parse(getString(R.string.immatrikulations_buero_url)));
                startActivity(intent);
            }
        });

        careerService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.career_service_url)));
                startActivity(intent);
            }
        });

        serviceBueros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.service_bueros_url)));
                startActivity(intent);
            }
        });

        studienfoerderung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.studienforderung_url)));
                startActivity(intent);
            }
        });

        studienberatung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.studienberatung_url)));
                startActivity(intent);
            }
        });
    }

}
