package stefanuca.ionel.blockit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Map;

/**
 * Created by ionel on 11/28/2015.
 */
public class PrimireSMS extends BroadcastReceiver {
    private Context context;
    private Toast toast ;

    /////////////////////////////
    //Variabile de blocare
        private String numereDinListaNeagra="";
        private String smsUriCuContinutInterzis="";
        private String numereNecunoscute=""; //care nu sunt in lista de contact
    /////////////////


    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
        String MSG_TYPE=intent.getAction();
        this.initializareVariabileDeBlocare();

        if(MSG_TYPE.equals("android.provider.Telephony.SMS_RECEIVED"))
        {
            Bundle bundle = intent.getExtras();
            Object messages[] = (Object[]) bundle.get("pdus");
            SmsMessage smsMessage[] = new SmsMessage[messages.length];
            for (int n = 0; n < messages.length; n++)
            {
                smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
            }


             if (numereDinListaNeagra.equals("da")&&(numarBlocat(smsMessage[0].getOriginatingAddress())))
            {
                toast = Toast.makeText(context,"(Lista Neagra)BLOCKED Received SMS: " + smsMessage[0].getMessageBody(), Toast.LENGTH_LONG);
                toast.show();
                abortBroadcast();
                this.scrieSmsInXmL(smsMessage[0].getOriginatingAddress(),smsMessage[0].getDisplayMessageBody());
            }
            else if(smsUriCuContinutInterzis.equals("da") && contimutulSmsUlui(smsMessage[0].getDisplayMessageBody()))
            {
                toast = Toast.makeText(context,"(Continut interzis)BLOCKED Received SMS: " + smsMessage[0].getMessageBody(), Toast.LENGTH_LONG);
                toast.show();
                abortBroadcast();
                this.scrieSmsInXmL(smsMessage[0].getOriginatingAddress(), smsMessage[0].getDisplayMessageBody());
            }
            else if(numereNecunoscute.equals("da")&&!esteNumarNecunoscut(smsMessage[0].getOriginatingAddress()))
            {
                toast = Toast.makeText(context,"(Numar necunoscut)BLOCKED Received SMS: " + smsMessage[0].getMessageBody(), Toast.LENGTH_LONG);
                toast.show();
                abortBroadcast();
                this.scrieSmsInXmL(smsMessage[0].getOriginatingAddress(),smsMessage[0].getDisplayMessageBody());
            }
        }
    }

    //verificam daca sms-ul contine un anumit cuvant
    //sau o anumita fraza
    private boolean contimutulSmsUlui(String smsUl)
    {
        SharedPreferences model = context.getSharedPreferences("cuvinteFrazeSMSUri", 0);
        SharedPreferences.Editor editor = model.edit();

        Map<String, ?> prefMap=model.getAll();
        for(String key:prefMap.keySet()) {

           if((smsUl.toUpperCase()).contains(key.toUpperCase()))
               return true;
        }
        return false;
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




    private void scrieSmsInXmL(String numar,String mesaj)
    {
        /// sms[/]0760080892[/]Acesta este un sms blocat[/]

        String key=  java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        String valoare = "sms[/]"+numar+"[/]"+mesaj+"[/]";

        SharedPreferences model = context.getSharedPreferences("CallIstoricSMS", 0);
        SharedPreferences.Editor editor = model.edit();
        editor.putString(key,valoare);
        editor.commit();
    }


    private void initializareVariabileDeBlocare()
    {
          SharedPreferences model = context.getSharedPreferences("settingsXML", 0);
          numereDinListaNeagra=model.getString("sms_lista_neagra","nu");
          smsUriCuContinutInterzis=model.getString("block_sms_with_forbidden_content","nu");
          numereNecunoscute=model.getString("block_sms_no_list_contact","nu");
    }
}
