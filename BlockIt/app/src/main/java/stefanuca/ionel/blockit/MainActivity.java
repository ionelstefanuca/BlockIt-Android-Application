package stefanuca.ionel.blockit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void adaugaNumar(View btn) {

        LayoutInflater li = LayoutInflater.from(context);
        View dialog_edit = li.inflate(R.layout.adauga_numar, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(dialog_edit);
        final EditText ed_numar = (EditText) dialog_edit.findViewById(R.id.numar);
        final  EditText ed_nume = (EditText) dialog_edit.findViewById(R.id.nume);

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adaugaNumarDA(ed_nume.getText().toString(),ed_numar.getText().toString());
            }
        });
        alertDialogBuilder.setNegativeButton("Nu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adaugaNumarNU();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void adaugaNumarNU( )
    {
        Toast toast = Toast.makeText(this, "Datele nu au fost salvate!", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void adaugaNumarDA(String nume,String numar)
    {
        Toast toast;
        String mesaj="";
        boolean salvam=true;

        if(!nume.matches("[a-z0-9A-Z_ ]+"))
        {
            mesaj+="Forma numelui introdus este gresita";
            salvam =false;
        }

        if(!numar.matches("[0-9]*"))
        {
            mesaj+="\nNumarul trebuie sa fie format doar din cifre";
            salvam =false;
        }

        if(salvam)
        {
            SharedPreferences pref = getSharedPreferences("numereBlocateXML", 0);
            SharedPreferences.Editor edit = pref.edit();
            edit.putString(numar,nume);
            edit.commit();

            mesaj="Datele au fost salvate cu succes!";
        }
        toast = Toast.makeText(this, mesaj, Toast.LENGTH_LONG);
        toast.show();
    }


    public void listaNeagra( View v)
    {
        Intent intent = new Intent(this, ListaNeagra.class);
        startActivity(intent);
    }

    public void blocheazaSmsUri( View v)
    {
        Intent intent = new Intent(this, BlocheazaSMSActivitate.class);
        startActivity(intent);
    }

    public void istoric(View v)
    {
        Intent intent = new Intent(this, IstoricActivitate.class);
        startActivity(intent);
    }




    //codul pentru meniu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);


        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_settings:
                startActivity(new Intent(this,Settings.class));
                break;
        }
        return true;
    }
}
