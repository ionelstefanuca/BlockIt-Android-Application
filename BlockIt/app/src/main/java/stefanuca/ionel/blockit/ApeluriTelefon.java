package stefanuca.ionel.blockit;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by ionel on 11/28/2015.
 */
public class ApeluriTelefon extends BroadcastReceiver
{
    private Context context;
    public final static String LOG_TAG = "PhoneStateReceiver";
    private TelephonyManager telephonyManager;
    private ITelephony telephonyService;
    private String numarTelefon;
    private Toast toast ;
    private int contor=0;

    ///////////////////////////////////
    //variabile de blocare
        private String numereDinListaNeagra="";
        private String numarPrivat="";
        private String numereNecunoscute=""; //care nu sunt in lista de contact
    ///////////////////////////////////


    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
        telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);

        initializareVariabileDeBlocare();

        Class c = null;
        try {
            c = Class.forName(telephonyManager.getClass().getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Method m = null;
        try {
            m = c.getDeclaredMethod("getITelephony");
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        m.setAccessible(true);
        try {
            telephonyService = (ITelephony)m.invoke(telephonyManager);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //aici avem numarul de telefon
        numarTelefon =  intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        System.out.println("Incoming call " + numarTelefon);
        telephonyManager.listen(callBlockListener, PhoneStateListener.LISTEN_CALL_STATE);
    }//onReceive()


    PhoneStateListener callBlockListener = new PhoneStateListener()
    {
        public void onCallStateChanged(int state, String incomingNumber)
        {
            if(state==TelephonyManager.CALL_STATE_RINGING)
            {
                    if(numarPrivat.equals("da")&&numarTelefon==null) { // inseamana ca numarul este privat
                        try {
                            telephonyService.endCall();
                            scrieSmsInXmL("Numar Privat", "Numarul este privat");

                            toast = Toast.makeText(context,"Apel" + "Numar Privat", Toast.LENGTH_LONG);
                            toast.show();

                        } catch (Exception e)
                        {
                        }
                    }
                else if(numereDinListaNeagra.equals("da")&&numarBlocat(numarTelefon))
                    {
                        try {
                            telephonyService.endCall();
                            scrieSmsInXmL(numarTelefon, "Lista neagra");

                            toast = Toast.makeText(context,"Blocare numar din lista neagra", Toast.LENGTH_LONG);
                            toast.show();

                            System.out.println("Apel" + "Apartine listei negre");

                        } catch (Exception e) {
                        }
                    }
                else if(numereNecunoscute.equals("da")&&(!esteNumarNecunoscut(numarTelefon)||numarTelefon==null))
                    {
                        try {
                            telephonyService.endCall();
                            if(numarTelefon==null)
                               scrieSmsInXmL("Numar Privat","");
                            else
                                scrieSmsInXmL(numarTelefon,"Nu apartine contactelor");

                            toast = Toast.makeText(context,"Blocare numar care nu se afla in lista de contacte", Toast.LENGTH_LONG);
                            toast.show();

                        } catch (Exception e) {
                        }
                    }
            }

            telephonyManager.listen(callBlockListener, PhoneStateListener.LISTEN_NONE);
        }
    };

    private void scrieSmsInXmL(String numar,String mesaj)
    {
        /// sms[/]0760080892[/]Acesta este un sms blocat[/]

        String key=  java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        String valoare = "call[/]"+numar+"[/]"+mesaj+"[/]";

        SharedPreferences model = context.getSharedPreferences("CallIstoricSMS", 0);
        SharedPreferences.Editor editor = model.edit();
        editor.putString(key,valoare);
        editor.commit();
    }


    private boolean numarBlocat(String nr)
    {
        SharedPreferences model = context.getSharedPreferences("numereBlocateXML", 0);
        SharedPreferences.Editor editor = model.edit();

        if(nr!=null) {
            nr = nr.replace("+", "");
            nr = nr.replace("-", "");
            nr = nr.replace(" ", "");
            nr = nr.replace("(", "");
            nr = nr.replace(")", "");
        }

        Map<String, ?> prefMap=model.getAll();
        for(String key:prefMap.keySet()) {

            if(nr.equals(key))
                    return true;
            else
            {
                // vom lua ultimele 10 cifre si le vom compara
                //astfel scapam de acel + si de prefix

                if(nr.length()>10)
                {
                    String copie="";
                    copie = nr.substring(nr.length()-10,nr.length());

                    if(copie.equals(key))
                        return true;
                }
            }
        }
        return false;
    }

    private boolean esteNumarNecunoscut(String nr)
    {
        String number="" ;

        if(nr!=null) {
            number = nr;
            number = number.replace("+", "");
            number = number.replace("-", "");
            number = number.replace(" ", "");
            number = number.replace("(", "");
            number = number.replace(")", "");
        }


        Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (phones.moveToNext())
        {
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            phoneNumber = phoneNumber.replace("-", "");
            phoneNumber = phoneNumber.replace("+", "");
            phoneNumber = phoneNumber.replace(" ", "");
            phoneNumber = phoneNumber.replace("(", "");
            phoneNumber = phoneNumber.replace(")", "");


            if(number.equals(phoneNumber))
                return true;
            else
            {
                // vom lua ultimele 10 cifre si le vom compara
                //astfel scapam de acel + si de prefix

                if(number.length()>10)
                {
                    String copie="";
                    copie = number.substring(number.length()-10,number.length());

                    if(copie.equals(phoneNumber))
                        return true;
                }
            }
        }
        phones.close();
        return false;
    }

    private void initializareVariabileDeBlocare()
    {
        SharedPreferences model = context.getSharedPreferences("settingsXML", 0);
        numereDinListaNeagra=model.getString("call_lista_neagra","nu");
        numarPrivat=model.getString("private_nr_call","nu");
        numereNecunoscute=model.getString("block_call_no_list_contact","nu");
    }
}

