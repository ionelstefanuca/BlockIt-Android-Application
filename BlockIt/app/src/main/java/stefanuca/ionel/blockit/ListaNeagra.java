package stefanuca.ionel.blockit;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

public class ListaNeagra extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_neagra);

        SharedPreferences model = getSharedPreferences("numereBlocateXML", 0);
        CustomAdapter adapter = new CustomAdapter(model);
        ListView theListView = (ListView) findViewById(R.id.listaNeagra);
        theListView.setAdapter(adapter);
    }
}
