package com.example.translation;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.translation.Database_All.Database;
import com.example.translation.models.Model;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class Widget extends AppWidgetProvider {

    int s  , v ; String number;
    SharedPreferences shrd;
    SharedPreferences.Editor editor;
    ArrayList<String> araay2 ;    ArrayList<String> str ;   ArrayList<String> array  ;
    Database db ;

    @Override
    public void onUpdate(Context context , AppWidgetManager appWidgetManager ,
                         int [] appWidgetIds){


        ComponentName thisWidget = new ComponentName(context ,
                Widget.class);
        int [] allWidgetIds = appWidgetManager .getAppWidgetIds(thisWidget);

        for (int widgetId :allWidgetIds  ){

           try {

               db = new Database(context);
               array = new ArrayList<>();
               araay2 = new ArrayList<>();
               str = new ArrayList<>();

               shrd = context.getSharedPreferences("trans file", Context.MODE_PRIVATE);
               editor = shrd.edit();

               s = Integer.parseInt(String.valueOf(db.getWordsCount()));
               v = Integer.parseInt(shrd.getString("widget", "0"));

               for (int x = 1; x <= s; ++x) {
                   str.add(String.valueOf(x));
               }

               if (v <= s) {
                   Model model = db.getWords(v+1);
                   number = model.getArabic_word() + "     =     " + model.getEnglish_word();
                   v++;
                   if (v == s) {
                       v = 0;
                       Toast.makeText(context, "تم اكمال دوره كامله", Toast.LENGTH_SHORT).show();
                   }
               }
               if (number.equals("null")){
                   v = 0 ;
                   editor.putString("widget" , String.valueOf(v));  editor.apply();
                   editor.apply();

               }

           }catch (Exception e ){
               v = 0 ;
               editor.putString("widget" , String.valueOf(v));  editor.apply();
               editor.apply();
           }
                editor.putString("widget" , String.valueOf(v));  editor.apply();
                editor.apply();
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.note_widget);
            remoteViews.setTextViewText(R.id.update , String.valueOf(number));
           // remoteViews.setInt(R.id.update, "setBackgroundColor", Color.argb(66, 255, 255, 0));
            Intent intent = new Intent(context , Widget .class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS , appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.update , pendingIntent);
            appWidgetManager.updateAppWidget(widgetId,remoteViews);

        }

    }

}