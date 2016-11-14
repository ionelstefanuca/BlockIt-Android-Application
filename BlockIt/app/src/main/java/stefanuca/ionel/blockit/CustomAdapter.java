package stefanuca.ionel.blockit;

/**
 * Created by ionel on 11/28/2015.
 */

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class CustomAdapter extends BaseAdapter {

    private static final String TAG = CustomAdapter.class.getSimpleName();
    private ArrayList<DataModelListaNeagra> listArray;
    private  final SharedPreferences pref;

    public CustomAdapter(SharedPreferences pref) {
        this.pref=pref;

        listArray = new ArrayList<DataModelListaNeagra>();
        Map<String,?> array = this.pref.getAll();
        for(Map.Entry<String,?> entry : array.entrySet()){
            listArray.add(new DataModelListaNeagra(entry.getValue().toString(),entry.getKey()));
        }
    }

    @Override
    public int getCount() {
        return listArray.size();    // total number of elements in the list
    }

    @Override
    public Object getItem(int i) {
        return listArray.get(i);    // single item in the list
    }

    @Override
    public long getItemId(int i) {
        return i;                   // index number
    }

    @Override
    public View getView(int index, View view, final ViewGroup parent) {

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.my_list, parent, false);
            view.setBackgroundResource(R.drawable.button_effect1);

            if(index%4==0)
                view.setBackgroundResource(R.drawable.button_effect1);
            else if(index%4==1)
                view.setBackgroundResource(R.drawable.button_effect2);
            else if(index%4==2)
                view.setBackgroundResource(R.drawable.button_effect3);
            else
                view.setBackgroundResource(R.drawable.button_effect4);


        }

        final DataModelListaNeagra dataModel = listArray.get(index);

        TextView textView = (TextView) view.findViewById(R.id.myList_numarTel);
        textView.setText(dataModel.getNumarTelefon());

        TextView textView1 = (TextView) view.findViewById(R.id.myList_nume);
        textView1.setText("" + dataModel.getNumeDetinator());

        Button button = (Button)view.findViewById(R.id.myList_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = pref.edit();
                editor.remove(dataModel.getNumarTelefon());
                editor.commit();


                //pentru a face un refresh la lista
                listArray = new ArrayList<DataModelListaNeagra>();
                Map<String,?> array = pref.getAll();
                for(Map.Entry<String,?> entry : array.entrySet()){

                    listArray.add(new DataModelListaNeagra(entry.getValue().toString(), entry.getKey()));
                }
                notifyDataSetChanged();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return view;
    }
}